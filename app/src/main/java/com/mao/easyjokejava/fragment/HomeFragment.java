package com.mao.easyjokejava.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mao.baselibrary.base.BaseFragment;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.easyjokejava.R;
import com.mao.framelibrary.indicator.ColorTrackTextView;
import com.mao.framelibrary.indicator.IndicatorAdapter;
import com.mao.framelibrary.indicator.TrackIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/3.
 */
public class HomeFragment extends BaseFragment {
    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华","同城","游戏"};
    @ViewById(R.id.indicator_view)
    private TrackIndicatorView mIndicatorContainer;
    private List<ColorTrackTextView> mIndicators;
    @ViewById(R.id.view_pager)
    private ViewPager mViewPager;
    private String TAG = "ViewPagerActivity";

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mIndicators = new ArrayList<>();
        initIndicator();
        initViewPager();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });

        /**
         * 添加一个切换的监听那个setOnPageChangeListener过时了
         * 这个看源码去吧
         */
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "position --> " + position + " positionOffset --> " + positionOffset);
                if (positionOffset > 0) {
                    // 获取左边
                    ColorTrackTextView left = mIndicators.get(position);
                    // 设置朝向
                    left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                    // 设置进度  positionOffset 是从 0 一直变化到 1 不信可以看打印
                    left.setCurrentProgress(1 - positionOffset);

                    // 获取右边
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                }
            }
        });
    }

    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {

        mIndicatorContainer.setAdapter(new IndicatorAdapter<ColorTrackTextView>() {
            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public ColorTrackTextView getView(int position, ViewGroup parent) {
                ColorTrackTextView colorTrackTextView = new ColorTrackTextView(context);
                // 设置颜色
                colorTrackTextView.setTextSize(20);
                colorTrackTextView.setChangeColor(Color.RED);
                colorTrackTextView.setText(items[position]);
                // 加入集合
                mIndicators.add(colorTrackTextView);
                return colorTrackTextView;
            }

            @Override
            public void highLightIndicator(ColorTrackTextView view) {
                // 当前选中的View
                view.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                view.setCurrentProgress(1);
            }

            @Override
            public void restoreIndicator(ColorTrackTextView view) {
                // 上一个View
                view.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                view.setCurrentProgress(0);
            }
        },mViewPager,false);
    }
}
