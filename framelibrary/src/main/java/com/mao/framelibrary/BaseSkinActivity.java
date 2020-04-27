package com.mao.framelibrary;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.ViewCompat;

import com.mao.baselibrary.base.BaseActivity;
import com.mao.baselibrary.baseUtils.LogU;
import com.mao.framelibrary.skin.SkinManager;
import com.mao.framelibrary.skin.SkinResource;
import com.mao.framelibrary.skin.attr.SkinAttr;
import com.mao.framelibrary.skin.attr.SkinView;
import com.mao.framelibrary.skin.callback.ISkinChangeListener;
import com.mao.framelibrary.skin.support.SkinAppCompatViewInflater;
import com.mao.framelibrary.skin.support.SkinAppCompatViewInflaterAndroid23;
import com.mao.framelibrary.skin.support.SkinAppCompatViewInflaterNew;
import com.mao.framelibrary.skin.support.SkinAttrSupport;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangkun
 * @time 2020-04-16 18:04
 * @Description 插件式换肤
 */
public abstract class BaseSkinActivity extends BaseActivity implements LayoutInflater.Factory2, ISkinChangeListener {

    private final static String TAG = "BaseSkinActivity";

    private SkinAppCompatViewInflaterNew mAppCompatViewInflater;
    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

    private SkinAppCompatViewInflater mSkinAppCompatViewInflater;

    private SkinAppCompatViewInflaterAndroid23 mSkinAppCompatViewInflaterAndroid23;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);

        /*LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {

            // 拦截view的创建
            @Override
            public View onCreateView(View parent, String name, Context mContext, AttributeSet attrs) {

                LogU.d("拦截View的创建");
                if (name.equals("Button")) {
                    TextView textView = new TextView(BaseSkinActivity.this);
                    textView.setText("button 拦截后换成 textview ");
                    textView.setTextColor(Color.parseColor("#00ff00"));
                    return textView;
                }

                // 拦截到View的创建  获取View之后要去解析

                // 1. 创建View
                //
                // 2. 解析属性 src textColor background 自定义属性

                // 3. 统一交给SkinManager管理

                return null;
            }

            @Override
            public View onCreateView(String name, Context mContext, AttributeSet attrs) {ø
                return null;
            }
        });*/


        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        /*LogU.d("拦截View的创建");
        if (name.equals("Button")) {
            TextView textView = new TextView(BaseSkinActivity.this);
            textView.setText("button 拦截后换成 textview ");
            textView.setTextColor(Color.parseColor("#00ff00"));
            return textView;
        }*/

        // 拦截到View的创建  获取View之后要去解析

        // 1. 创建View
        View view = createView(parent, name, context, attrs);
        LogU.d("换肤 view " + view);
        // 2. 解析属性 src textColor background 自定义属性
        // 2.1 一个activity的布局肯定对应多个这样的 SkinView

        if (view != null) {
            List<SkinAttr> skinAttrs = SkinAttrSupport.getSkinAttrs(context, attrs);
            SkinView skinView = new SkinView(view, skinAttrs);
            // 3. 统一交给SkinManager管理
            managerSkinView(skinView);

            // 4. 判断要不要换肤
            SkinManager.getInstance().checkChangeSkin(skinView);
        }

        return view;
    }

    /**
     * 统一管理 SkinView
     */
    private void managerSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().register(this, skinViews);
        }
        skinViews.add(skinView);

    }

    @Override
    protected void onDestroy() {
        SkinManager.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void changeSkin(SkinResource skinResource) {

    }

    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinAppCompatViewInflaterNew();
        }

        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = (attrs instanceof XmlPullParser)
                    // If we have a XmlPullParser, we can detect where we are in the layout
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    // Otherwise we have to use the old heuristic
                    : shouldInheritContext((ViewParent) parent);
        }

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP, // Only read android:theme pre-L (L+ handles this anyway)
                true, // Read read app:theme as a fallback at all times for legacy reasons
                VectorEnabledTintResources.shouldBeUsed() //* Only tint wrap the mContext if enabled
        );
    }


    // 用android-24 的源码
    public View createView24(View parent, final String name, @NonNull Context context,
                             @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mSkinAppCompatViewInflater == null) {
            mSkinAppCompatViewInflater = new SkinAppCompatViewInflater();
        }

        // We only want the View to inherit its context if we're running pre-v21
        final boolean inheritContext = isPre21 && shouldInheritContext((ViewParent) parent);

        return mSkinAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, // Only read android:theme pre-L (L+ handles this anyway)
                true, // Read read app:theme as a fallback at all times for legacy reasons
                VectorEnabledTintResources.shouldBeUsed() // Only tint wrap the context if enabled
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }


    //-------------------android 23


    public View createView23(View parent, final String name, @NonNull Context context,
                             @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mSkinAppCompatViewInflaterAndroid23 == null) {
            mSkinAppCompatViewInflaterAndroid23 = new SkinAppCompatViewInflaterAndroid23();
        }

        // We only want the View to inherit it's context if we're running pre-v21
        final boolean inheritContext = isPre21 && true
                && shouldInheritContext23((ViewParent) parent);

        return mSkinAppCompatViewInflaterAndroid23.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true /* Read read app:theme as a fallback at all times for legacy reasons */
        );
    }

    private boolean shouldInheritContext23(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == getWindow().getDecorView() || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

}
