package com.mao.easyjokejava.adapter;

import android.content.Context;

import com.mao.easyjokejava.GlideImageLoader;
import com.mao.easyjokejava.R;
import com.mao.easyjokejava.model.DiscoverResult;
import com.mao.framelibrary.recyclerview.adapter.CommonRecyclerAdapter;
import com.mao.framelibrary.recyclerview.adapter.ViewHolder;

import java.util.List;


/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/4/8.
 * Version 1.0
 * Description:
 */
public class DiscoverListAdapter extends CommonRecyclerAdapter<DiscoverResult> {
    public DiscoverListAdapter(Context context, List<DiscoverResult> data) {
        super(context, data, R.layout.channel_list_item);
    }

    @Override
    public void convert(ViewHolder holder, DiscoverResult item,int position) {
        // 显示数据
       /* String str = item.getSubscribe_count() + " 订阅 | " +
                "总帖数 <font color='#FF678D'>" + item.getTotal_updates() + "</font>";*/
        holder.setText(R.id.channel_text, item.getTitle())
                /*.setText(R.id.channel_topic, item.getIntro())
                .setText(R.id.channel_update_info, Html.fromHtml(str))*/;

        // 是否是最新
        /*if (item.isIs_recommend()) {
            holder.setViewVisibility(R.id.recommend_label, View.VISIBLE);
        } else {
            holder.setViewVisibility(R.id.recommend_label, View.GONE);
        }*/
        // 加载图片
        holder.setImageByUrl(R.id.channel_icon, new GlideImageLoader("http://cms-bucket.ws.126.net/2020/0505/d1e9904dp00q9u35100d8c000s600e3c.png?imageView&thumbnail=140y88&quality=85"));
    }
}
