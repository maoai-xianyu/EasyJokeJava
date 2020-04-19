package com.mao.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * @author zhangkun
 * @time 2020-04-19 16:21
 * @Description 头部的基类
 */
public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {

    private P mParams;
    private View mNavigationView;
    private SparseArray<WeakReference<View>> mViews;

    protected AbsNavigationBar(P params) {
        this.mParams = params;
        this.mViews = new SparseArray<>();
        // 开始绑定view
        createAndBindView();
    }

    public P getParams() {
        return mParams;
    }

    /**
     * 绑定创建view
     */
    private void createAndBindView() {

        if (mParams.mParent == null) {
            // 获取 Activity的根布局
            // 获取 activity的根布局
            ViewGroup activityRoot = ((Activity) (mParams.mContext)).findViewById(android.R.id.content);
            mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
        }
        if (mParams.mParent == null) {
            return;
        }

        // 1. 创建view
        mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);

        // 2. 添加
        mParams.mParent.addView(mNavigationView, 0);
        applyView();
    }


    protected void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    private <T extends View> T findViewById(int viewId) {
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = viewWeakReference != null ? viewWeakReference.get() : null;
        if (view == null) {
            view = mNavigationView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }


    protected void setOnClickListener(int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
    }

    // Builder设计模式    套路: AbsNavigationBar   Builder  和 参数

    public abstract static class Builder {


        protected Builder(Context context, ViewGroup parent) {
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams {

            public Context mContext;

            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }
        }
    }
}
