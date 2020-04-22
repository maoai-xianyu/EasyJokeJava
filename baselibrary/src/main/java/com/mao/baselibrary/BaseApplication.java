package com.mao.baselibrary;

import android.app.Application;
import android.content.Context;

/**
 * @author zhangkun
 * @time 2020-04-18 23:57
 * @Description
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.beforeCreate();
        this.initApplication();
        this.afterOnCreate();
    }

    protected abstract void beforeCreate();

    protected abstract void initApplication();

    protected abstract void afterOnCreate();

    public static Context context() {
        return instance.getApplicationContext();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

}
