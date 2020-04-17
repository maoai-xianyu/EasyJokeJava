package com.mao.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mao.baselibrary.ioc.ViewUtils;

/**
 * @author zhangkun
 * @time 2020-04-16 18:04
 * @Description 整个应用的BaseActivity  模板设计模式
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Context context;
    public Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局layout
        setContentView();
        ViewUtils.inject(this);
        this.context = this;
        this.activity = this;
        // 初始化头部
        initTitle();
        // 初始化界面
        initView();
        // 初始化数据
        initData();

    }

    protected abstract void initData();


    protected abstract void initView();


    protected abstract void initTitle();


    protected abstract void setContentView();


    /**
     * 支持 findViewById
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T viewById(int viewId) {
        return findViewById(viewId);
    }


    /**
     * 启动activty
     *
     * @param cls
     */
    protected void startActivity(Class<?> cls) {
        this.startActivity(cls, null, false);
    }

    protected void startActivity(Class<?> cls, boolean isFinish) {
        this.startActivity(cls, null, isFinish);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        this.startActivity(cls, bundle, false);
    }

    protected void startActivity(Class<?> cls, Bundle bundle, boolean isFinish) {
        if (cls != null && this.activity != null && !this.activity.isFinishing()) {
            Intent intent = new Intent();
            if (bundle != null) {
                intent.putExtras(bundle);
            }

            intent.setClass(this.activity, cls);
            this.startActivity(intent);
            if (isFinish) {
                this.finish();
            }

        }
    }
}
