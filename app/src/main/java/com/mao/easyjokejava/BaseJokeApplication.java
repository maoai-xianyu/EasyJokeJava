package com.mao.easyjokejava;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;
import com.mao.baselibrary.BaseApplication;
import com.mao.baselibrary.ExceptionCrashHandler;
import com.mao.baselibrary.fixBug.FixDexManager;


/**
 * @author zhangkun
 * @time 2020-04-16 22:18
 * @Description
 */
public class BaseJokeApplication extends BaseApplication {

    public static PatchManager mPatchManager;


    @Override
    protected void beforeCreate() {

    }


    @Override
    protected void initApplication() {

    }


    @Override
    protected void afterOnCreate() {

        // 设置全局异常捕捉类
        ExceptionCrashHandler.getInstance().init(this);

        /*mPatchManager = new PatchManager(this);
        // 初始化版本，获取当前应用的版本
        mPatchManager.init(packageName(this));
        // 加载之前的 apatch 包
        mPatchManager.loadPatch();*/

        try {
            // 耗时，需要优化，一般是在 400ms
            FixDexManager fixDexManager = new FixDexManager(this);
            // 加载所有修复的Dex包
            fixDexManager.loadFixDex();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }
}
