package com.mao.easyjokejava.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.mao.baselibrary.base.BaseFragment;
import com.mao.baselibrary.baseUtils.GsonU;
import com.mao.baselibrary.baseUtils.LogU;
import com.mao.baselibrary.http.HttpUtils;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.easyjokejava.R;
import com.mao.easyjokejava.adapter.DiscoverListAdapter;
import com.mao.easyjokejava.model.BaseEntity;
import com.mao.easyjokejava.model.DiscoverResult;
import com.mao.framelibrary.HttpStringCallBack;
import com.mao.framelibrary.banner.BannerAdapter;
import com.mao.framelibrary.banner.BannerView;
import com.mao.framelibrary.banner.BannerViewPager;
import com.mao.framelibrary.recyclerview.view.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/3.
 */
public class FindFragment extends BaseFragment implements BannerViewPager.BannerItemClickListener {

    private static final String TAG = "MainActivity";

    @ViewById(R.id.recycler_view)
    private WrapRecyclerView mRecyclerView;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        // https://api.apiopen.top/getWangYiNews?page=1&count=20&version=2.0

        HttpUtils.with(context).url("https://api.apiopen.top/getWangYiNews")
                .addParam("page", 1)
                .addParam("count", 40)
                .cache(true)
                .execute(new HttpStringCallBack<String>() {
                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onSuccess(String result) {

                        LogU.d(" result "+result);
                        LogU.d(" result thread "+Thread.currentThread());


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LogU.d(" result thread  main "+Thread.currentThread());
                                BaseEntity<DiscoverResult> convert = GsonU.convert(result, new TypeToken<BaseEntity<DiscoverResult>>() {
                                }.getType());

                                // 先显示列表
                                List<DiscoverResult> resultList = convert.getResult();

                                List<DiscoverResult> banner = new ArrayList<>(resultList.subList(0, 4));

                                List<DiscoverResult> listContainer = new ArrayList<>(resultList.subList(4, resultList.size()));

                                showListData(listContainer);
                                addBannerView(banner);
                            }
                        });

                    }
                });
    }

    /**
     * 初始化Banner
     *
     * @param banners
     */
    private void addBannerView(final List<DiscoverResult> banners) {
        Log.e("TAG", "banners --> " + banners.size());

        // 后台没有轮播那就不添加
        if (banners.size() <= 0) {
            return;
        }

        BannerView bannerView = (BannerView) LayoutInflater.from(context)
                .inflate(R.layout.layout_banner_view, mRecyclerView, false);

        // 自己把万能的无限轮播看一下
        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                if (convertView == null) {
                    convertView = new ImageView(context);
                }
                ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context).load(banners.get(position).getImage()).into((ImageView) convertView);
                return convertView;
            }

            @Override
            public int getCount() {
                return banners.size();
            }

            @Override
            public String getBannerDesc(int position) {
                return banners.get(position).getTitle();
            }
        });

        bannerView.setOnBannerItemClickListener(this);
        // 开启滚动
        bannerView.startRoll();

        mRecyclerView.addHeaderView(bannerView);
    }

    /**
     * 显示列表
     *
     * @param list
     */
    private void showListData(List<DiscoverResult> list) {
        final DiscoverListAdapter listAdapter = new DiscoverListAdapter(context, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(listAdapter);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void click(int position) {
        // 轮播点击
        Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
    }
}
