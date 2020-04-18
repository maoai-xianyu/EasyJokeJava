package com.mao.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author zhangkun
 * @time 2020-04-18 14:19
 * @Description
 */
class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public AlertController(AlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }


    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    /**
     * 获取dialog
     *
     * @return
     */
    public AlertDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取Dialog的Window
     *
     * @return
     */
    public Window getWindow() {
        return mWindow;
    }


    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    public <T extends View> T getView(int viewId) {

        return mViewHelper.getView(viewId);
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param clickListener
     */
    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        mViewHelper.setOnClickListener(viewId, clickListener);
    }

    public static class AlertParams {

        public Context mContext;
        public int mThemeResId;
        // 是否能够取消 点击空白能否取消 默认可以取消点击阴影
        public boolean mCancelable = true;
        // dialog cancel 监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        // dialog dismiss 监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        // dialog key 监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        // 布局的view
        public View mView;
        // 布局的 LayoutId
        public int mViewLayoutResId;
        // 存放字文本的修改
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        // 设置宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 动画
        public int mAnimations = 0;
        // 默认位置
        public int mGravity = Gravity.CENTER;


        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;

        }

        /**
         * 绑定和设置参数
         *
         * @param alert
         */
        public void apply(AlertController alert) {

            // 设置参数

            // 1. 设置布局  DialogViewHelper
            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }
            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局 setContentView");
            }

            // 给Dialog 设置布局

            alert.getDialog().setContentView(viewHelper.getContentView());

            // 设置 controller 的 辅助类
            alert.setViewHelper(viewHelper);

            // 2. 设置文本
            int textSize = mTextArray.size();
            for (int i = 0; i < textSize; i++) {
                alert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            // 3. 设置点击
            int clickSize = mClickArray.size();
            for (int i = 0; i < clickSize; i++) {
                alert.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            // 4. 配置自定义效果 全屏  从底部弹出 默认动画

            Window window = alert.getWindow();
            // 设置位置
            window.setGravity(mGravity);
            // 设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }
            // 设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);

        }
    }
}
