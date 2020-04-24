package com.mao.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * @author zhangkun
 * @time 2020-04-18 14:21
 * @Description Dialog View的辅助处理类
 */
class DialogViewHelper {

    private View mContentView = null;

    // 软引用
    private SparseArray<WeakReference<View>> mViews;


    public DialogViewHelper() {
        mViews = new SparseArray<>();

    }

    public DialogViewHelper(Context context, int layoutResId) {
        this();
        // 两种都可以
        //mContentView = View.inflate(mContext, layoutResId, null);
        mContentView = LayoutInflater.from(context).inflate(layoutResId, null);

    }

    /**
     * 设置布局
     *
     * @param contentView
     */
    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        // 需要减少findViewById 的次数哦，需要做一个缓存
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }

    }

    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = viewWeakReference != null ? viewWeakReference.get() : null;
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param clickListener
     */
    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(clickListener);
        }
    }

    /**
     * 获取内容的 ContentView
     *
     * @return
     */
    public View getContentView() {
        return mContentView;
    }
}
