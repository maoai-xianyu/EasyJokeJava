package com.mao.easyjokejava.test.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.mao.baselibrary.baseUtils.LogU;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhangkun
 * @time 2020-05-08 23:36
 * @Description
 */
public class HookStartActivityUtil {

    public static void init(Context context, Class<?> proxyClass) {
        new HookStartActivityUtil(context, proxyClass);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            hookActivityManagerAndroidO();
        } else {

            hookStartActivity();
        }
        hookLaunchActivity();
        hookPackageManager(context);
    }



    private static Context mContext;
    private static Class<?> mProxyClass;

    private HookStartActivityUtil(Context context, Class<?> proxyClass) {
        mContext = context.getApplicationContext();
        mProxyClass = proxyClass;
    }

    private static void hookActivityManagerAndroidO() {

        try {
            //1. 获取 ActivityManager 里面的gDefault
            Class<?> amnClass = Class.forName("android.app.ActivityManager");

            // 获取属性
            Field iActivityManagerSingleton = amnClass.getDeclaredField("IActivityManagerSingleton");
            iActivityManagerSingleton.setAccessible(true);
            Object IActivityManagerSingleton = iActivityManagerSingleton.get(null);

            //2. 获取  IActivityManagerSingleton 中的mInstance属性

            Class<?> SingletonClass = Class.forName("android.util.Singleton");

            Field mInstance = SingletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            Object IActivityManager = mInstance.get(IActivityManagerSingleton);

            Class<?> iamClass = Class.forName("android.app.IActivityManager");

            Object proxy = Proxy.newProxyInstance(
                    HookStartActivityUtil.class.getClassLoader(),
                    new Class[]{iamClass},
                    new StartActivityInvocationHandler(IActivityManager)
            );

            // 重新指定
            mInstance.set(IActivityManagerSingleton, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void hookStartActivity() {

        try {
            // 3.1 获取ActivityManagerNative里面的gDefault
            Class<?> amnClass = Class.forName("android.app.ActivityManagerNative");
            // 获取属性
            Field gDefaultField = amnClass.getDeclaredField("gDefault");
            // 设置权限
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            // 3.2 获取gDefault中的mInstance属性
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            Object iamInstance = mInstanceField.get(gDefault);

            Class<?> iamClass = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(HookStartActivityUtil.class.getClassLoader(),
                    new Class[]{iamClass},
                    // InvocationHandler 必须执行者，谁去执行
                    new StartActivityInvocationHandler(iamInstance));

            // 3.重新指定
            mInstanceField.set(gDefault, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void hookLaunchActivity() {

        try {
            //1. 获取ActivityThread实例
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThread.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object sCurrentActivityThread = sCurrentActivityThreadField.get(null);
            //2. 获取ActivityThread中的mH
            // 设置权限
            Field mHField = activityThread.getDeclaredField("mH");
            mHField.setAccessible(true);
            Object mHandler = mHField.get(sCurrentActivityThread);
            //3. hook handleLaunchActivity
            // 给 mHandler 设置 CallBack回调,只能通过反射
            Class<?> handlerClass = Class.forName("android.os.Handler");
            Field mCallbackField = handlerClass.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            mCallbackField.set(mHandler, new HandlerCallBack());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class HandlerCallBack implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            // 每发消息，都会执行CallBack
            // 100 为  LAUNCH_ACTIVITY
            if (msg.what == 100) {
                LogU.d("成功获取");
                handleLaunchActivity(msg);
            }
            return false;
        }

        /**
         * 开始启动创建Activity拦截
         *
         * @param msg
         */
        private void handleLaunchActivity(Message msg) {
            Object record = msg.obj;
            // 1. 从ActivityClientRecord 获取过安检的intent
            try {
                Field intentField = record.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent safeIntent = (Intent) intentField.get(record);
                // 2. 从safeIntent中获取原来的originIntent
                Intent originIntent = safeIntent.getParcelableExtra(HookConfig.EXTRA_ORIGIN_INTENT);
                LogU.d("获取到了 原始的 originIntent" + originIntent);
                // 3. 重新设置回去
                if (originIntent != null) {
                    intentField.set(record, originIntent);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static class StartActivityInvocationHandler implements InvocationHandler {

        private Object mObject;

        public StartActivityInvocationHandler(Object object) {
            mObject = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            LogU.d("获取到了 IActivityManager 的所有方法" + method.getName());
            // 替换 Intent,过 AndroidManifest.xml 的检测
            //if (method.getName())
            if (method.getName().equals("startActivity")) {
                // 1. 首先获取原来的Intent
                int index = 0;
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent) {
                        index = i;
                        break;
                    }
                }
                Intent originIntent = (Intent) args[index];
                LogU.d("获取 originIntent" + originIntent);
                // 2. 创建一个安全的activity
                Intent saveIntent = new Intent(mContext, mProxyClass);
                // 绑定原来的Intent
                saveIntent.putExtra(HookConfig.EXTRA_ORIGIN_INTENT, originIntent);
                args[2] = saveIntent;

            }

            return method.invoke(mObject, args);

        }
    }

    private static void hookPackageManager(Context context) {
        try {
            // 兼容AppCompatActivity报错问题
            Class<?> forName = Class.forName("android.app.ActivityThread");
            Field field = forName.getDeclaredField("sCurrentActivityThread");
            field.setAccessible(true);
            Object activityThread = field.get(null);
            // 我自己执行一次那么就会创建PackageManager，系统再获取的时候就是下面的iPackageManager
            Method getPackageManager = activityThread.getClass().getDeclaredMethod("getPackageManager");
            Object iPackageManager = getPackageManager.invoke(activityThread);

            PackageManagerHandler handler = new PackageManagerHandler(iPackageManager);
            Class<?> iPackageManagerIntercept = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iPackageManagerIntercept}, handler);

            // 获取 sPackageManager 属性
            Field iPackageManagerField = activityThread.getClass().getDeclaredField("sPackageManager");
            iPackageManagerField.setAccessible(true);
            iPackageManagerField.set(activityThread, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class PackageManagerHandler implements InvocationHandler {
        private Object mActivityManagerObject;

        public PackageManagerHandler(Object iActivityManagerObject) {
            this.mActivityManagerObject = iActivityManagerObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Log.e("TAG", "methodName = " + method.getName());
            if (method.getName().startsWith("getActivityInfo")) {
                ComponentName componentName = new ComponentName(mContext, mProxyClass);
                args[0] = componentName;
            }
            return method.invoke(mActivityManagerObject, args);
        }
    }

}
