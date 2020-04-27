package com.mao.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.mao.baselibrary.baseUtils.LogU;

import java.lang.reflect.Method;

/**
 * @author zhangkun
 * @time 2020-04-23 18:13
 * @Description 皮肤资源
 */
public class SkinResource {

    /**
     * 资源通过这个对象获取
     */
    private Resources mSkinResources;
    private String mPackageName;

    public SkinResource(Context context, String skinPath) {

        try {
            Resources superR = context.getResources();
            // 创建本地下载好的资源皮肤
            AssetManager assetManager = null;
            assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            //addAssetPath.setAccessible(true); // 私有的
            // 反射执行方法
            addAssetPath.invoke(assetManager, skinPath);
            mSkinResources = new Resources(assetManager, superR.getDisplayMetrics(), superR.getConfiguration());

            // 获取skinPath包名
            mPackageName = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
                    .packageName;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过名字获取Drawable
     *
     * @param resName
     * @return
     */
    public Drawable getDrawableByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "drawable", mPackageName);
            LogU.d(" resId  Drawable " + resId + " mPackageName " + mPackageName + " resName " + resName);
            Drawable drawable = mSkinResources.getDrawable(resId);
            return drawable;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过名字获取颜色
     *
     * @param resName
     * @return
     */
    public ColorStateList getColorByName(String resName) {

        try {
            int resId = mSkinResources.getIdentifier(resName, "color", mPackageName);
            LogU.d(" resId color " + resId + " mPackageName " + mPackageName + " resName " + resName);
            ColorStateList colorStateList = mSkinResources.getColorStateList(resId);
            return colorStateList;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
