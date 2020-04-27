package com.mao.framelibrary.skin.config;

import android.content.Context;

/**
 * @author zhangkun
 * @time 2020-04-26 21:50
 * @Description
 */
public class SkinPreUtils {

    private static SkinPreUtils mInstance;
    private Context mContext;

    private SkinPreUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }


    public static SkinPreUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SkinPreUtils.class) {
                if (mInstance == null) {
                    mInstance = new SkinPreUtils(context);
                }
            }
        }

        return mInstance;
    }


    /**
     * 保存当前皮肤路径
     *
     * @param skinPath
     */
    public void saveSkinPath(String skinPath) {
        mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_NAME, Context.MODE_PRIVATE)
                .edit().putString(SkinConfig.SKIN_PATH_NAME, skinPath).apply();

    }


    /**
     * 获取皮肤的路径
     * @return
     */
    public String getSkinPath() {
        return mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_NAME, Context.MODE_PRIVATE)
                .getString(SkinConfig.SKIN_PATH_NAME, "");
    }

    /**
     * 清空皮肤
     */
    public void clearSkinInfo () {
        saveSkinPath("");
    }
}
