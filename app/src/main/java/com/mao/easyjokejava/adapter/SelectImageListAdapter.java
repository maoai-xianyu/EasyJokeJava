package com.mao.easyjokejava.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mao.easyjokejava.R;
import com.mao.framelibrary.recyclerview.adapter.CommonRecyclerAdapter;
import com.mao.framelibrary.recyclerview.adapter.ViewHolder;

import java.util.List;

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/4/9.
 * Version 1.0
 * Description:
 */
public class SelectImageListAdapter extends CommonRecyclerAdapter<String> {
    public SelectImageListAdapter(Context context, List<String> data) {
        super(context, data, R.layout.media_chooser_item);
    }

    @Override
    public void convert(ViewHolder holder, String item) {
        if(TextUtils.isEmpty(item)){
            // 显示拍照
            holder.setViewVisibility(R.id.camera_ll, View.VISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.INVISIBLE);
            holder.setViewVisibility(R.id.image, View.INVISIBLE);
        }else{
            // 显示图片
            holder.setViewVisibility(R.id.camera_ll, View.INVISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.VISIBLE);
            holder.setViewVisibility(R.id.image, View.VISIBLE);

            // 显示图片利用Glide
            ImageView imageView = holder.getView(R.id.image);
            Glide.with(mContext).load(item)
                    .centerCrop().into(imageView);
        }
    }
}
