package com.mao.easyjokejava.selectimage;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mao.baselibrary.baseUtils.ToastUtils;
import com.mao.easyjokejava.R;
import com.mao.framelibrary.recyclerview.adapter.CommonRecyclerAdapter;
import com.mao.framelibrary.recyclerview.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/4/9.
 * Version 1.0
 * Description:
 */
public class SelectImageListAdapter extends CommonRecyclerAdapter<String> {
    private ArrayList<String> mResultImageList;
    private SelectImageListener mSelectImageListener;
    private int mMaxCount = 8;

    public SelectImageListAdapter(Context context, List<String> data, ArrayList<String> imageList, int maxCount) {
        super(context, data, R.layout.media_chooser_item);
        this.mResultImageList = imageList;
        this.mMaxCount = maxCount;
    }

    @Override
    public void convert(ViewHolder holder, String item, int position) {
        if (TextUtils.isEmpty(item)) {
            // 显示拍照
            holder.setViewVisibility(R.id.camera_ll, View.VISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.INVISIBLE);
            holder.setViewVisibility(R.id.image, View.INVISIBLE);

            holder.setOnIntemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 调用拍照，权限的问题
                }
            });
        } else {
            // 显示图片
            holder.setViewVisibility(R.id.camera_ll, View.INVISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.VISIBLE);
            holder.setViewVisibility(R.id.image, View.VISIBLE);

            // 显示图片利用Glide
            ImageView imageView = holder.getView(R.id.image);
            Glide.with(mContext).load(item)
                    .centerCrop().into(imageView);


            ImageView imageViewIndicator = holder.getView(R.id.media_selected_indicator);

            if (mResultImageList.contains(item)) {
                // 点亮√
                imageViewIndicator.setSelected(true);
            } else {
                imageViewIndicator.setSelected(false);
            }

            // 给条目增加点击事件
            holder.setOnIntemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // 没有就加入集合
                    if (!mResultImageList.contains(item)) {
                        if (mResultImageList.size() >= mMaxCount) {
                            ToastUtils.show("只能选择9张图片，不要太贪心哦！");
                            return;
                        }
                        mResultImageList.add(item);
                    } else {
                        mResultImageList.remove(item);
                    }
                    notifyItemChanged(position);

                    // 通知更新
                    if (mSelectImageListener != null) {
                        mSelectImageListener.select();
                    }

                }
            });
        }
    }

    public void setSelectImageListener(SelectImageListener selectImageListener) {
        mSelectImageListener = selectImageListener;
    }
}
