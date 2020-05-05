package com.mao.framelibrary;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mao.baselibrary.navigationbar.AbsNavigationBar;

/**
 * @author zhangkun
 * @time 2020-04-19 16:41
 * @Description
 */
public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {

    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {

        // 绑定效果
        setText(R.id.title, getParams().mTitle);
        setText(R.id.right_text, getParams().mRightText);
        setOnClickListener(R.id.right_text, getParams().mRightCLickListener);
        setOnClickListener(R.id.back, getParams().mLeftCLickListener);
        setVisibility(R.id.back, getParams().mBackVisible);

    }

    public static class Builder extends AbsNavigationBar.Builder {

        DefaultNavigationParams P;

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationParams(context, null);
        }


        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar(P);
            return defaultNavigationBar;
        }

        // 1. 设置所有的效果
        public Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }


        public Builder setRightText(String rightText) {
            P.mRightText = rightText;
            return this;
        }

        public Builder setRightIcon(int rRes) {
            P.mRightRes = rRes;
            return this;
        }


        public Builder hideBackIcon() {
            P.mBackVisible = View.INVISIBLE;
            return this;
        }

        public Builder setRightClickListener(View.OnClickListener onClickListener) {
            P.mRightCLickListener = onClickListener;
            return this;
        }

        public Builder setLeftClickListener(View.OnClickListener onClickListener) {
            P.mLeftCLickListener = onClickListener;
            return this;
        }

        public static class DefaultNavigationParams extends AbsNavigationBar.Builder.AbsNavigationParams {
            public String mTitle;
            public String mRightText;
            public int mRightRes;
            public int mBackVisible = View.VISIBLE;

            // 2. 放置所有效果

            public View.OnClickListener mRightCLickListener;
            public View.OnClickListener mLeftCLickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 关闭
                    ((Activity) mContext).finish();

                }
            };

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }


        }
    }


}
