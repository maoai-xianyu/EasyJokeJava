package com.mao.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mao.framelibrary.skin.SkinManager;
import com.mao.framelibrary.skin.SkinResource;

/**
 * @author zhangkun
 * @time 2020-04-23 18:22
 * @Description
 */
public enum SkinType {

    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {

            SkinResource skinResource = getSkinResource();
            ColorStateList color = skinResource.getColorByName(resName);
            if (color == null) {
                return;
            }
            TextView textView = (TextView) view;
            textView.setTextColor(color);
        }

    },

    BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {
            // 可能是图片也可能是颜色
            SkinResource skinResource = getSkinResource();
            ColorStateList color = skinResource.getColorByName(resName);
            if (color != null) {
                view.setBackgroundColor(color.getDefaultColor());
                return;
            }
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    imageView.setBackground(drawable);
                } else {
                    view.setBackground(drawable);
                }

                return;
            }

        }
    },

    SRC("src") {
        @Override
        public void skin(View view, String resName) {
            // 可能是图片也可能是颜色
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(drawable);
                return;
            }
        }
    };

    protected SkinResource getSkinResource() {
        return SkinManager.getInstance().getSkinResource();
    }


    // 会根据名字调对应的方法
    private String mResName;

    SkinType(String resName) {
        this.mResName = resName;

    }

    public abstract void skin(View view, String resName);


    public String getResName() {
        return mResName;
    }
}
