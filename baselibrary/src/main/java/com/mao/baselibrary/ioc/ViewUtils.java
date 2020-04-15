package com.mao.baselibrary.ioc;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.mao.baselibrary.baseUtils.CheckNetUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zhangkun
 * @time 2020-04-15 09:32
 */
public class ViewUtils {


    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);

    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);

    }

    // 兼容 上面三个方法， Object 是反射需要执行的类
    private static void inject(ViewFinder viewFinder, Object object) {
        injectFiled(viewFinder, object);
        injectEvent(viewFinder, object);
    }

    /**
     * 注入属性
     *
     * @param viewFinder
     * @param object
     */
    private static void injectFiled(ViewFinder viewFinder, Object object) {
        // 1. 获取类里面所有的的属性
        Class<?> clazz = object.getClass();
        // 获取所有的
        Field[] fields = clazz.getDeclaredFields();

        // 2. 获取 ViewById 里面的value值
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                // 3. findViewById 找到view
                int viewId = viewById.value();
                View view = viewFinder.findViewById(viewId);
                if (view != null) {
                    // 能够注入所有修饰符 private 和 public ..
                    field.setAccessible(true);
                    try {
                        // 4. 动态的注入找到的View
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    /**
     * 注入事件
     *
     * @param viewFinder
     * @param object
     */
    private static void injectEvent(ViewFinder viewFinder, Object object) {
        // 1. 获取类里面的所有方法
        Method[] declaredMethods = object.getClass().getDeclaredMethods();

        // 2. 获取 OnClick 里面的value值

        for (Method method : declaredMethods) {
            OnClick viewOnClick = method.getAnnotation(OnClick.class);
            if (viewOnClick != null) {
                // 3. findViewById 找到view
                int[] viewIds = viewOnClick.value();
                for (int viewId : viewIds) {
                    View view = viewFinder.findViewById(viewId);

                    //扩展功能 检测网络
                    boolean isChecked = method.getAnnotation(CheckNet.class) != null;

                    if (view != null) {
                        // 4.  View.OnClickListener
                        view.setOnClickListener(new DeclaredOnClickListener(method, object, isChecked));
                    }
                }
            }
        }


        // 5. 动态的注入找到的View
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {

        private Method mMethod;
        private Object mObject;
        private boolean mIsChecked;

        public DeclaredOnClickListener(Method method, Object object, boolean isChecked) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsChecked = isChecked;
        }

        @Override
        public void onClick(View v) {
            // 是否要检测网络
            if (mIsChecked) {
                if (!CheckNetUtils.isInternetAvailable(v.getContext())) {
                    // 配置提示文案
                    Toast.makeText(v.getContext(), "网络有问题哦", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // 点击调用这个方法
            try {
                // 所有方法都可以，包括私有属性
                mMethod.setAccessible(true);
                // 5. 反射执行方法
                mMethod.invoke(mObject, v);
            } catch (
                    Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }


}
