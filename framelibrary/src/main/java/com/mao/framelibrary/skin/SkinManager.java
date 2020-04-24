package com.mao.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;

import com.mao.framelibrary.BaseSkinActivity;
import com.mao.framelibrary.skin.attr.SkinView;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangkun
 * @time 2020-04-23 18:13
 * @Description 皮肤管理类
 */
public class SkinManager {


    private static SkinManager skinManager = null;

    private Context mContext;

    private Map<Activity, List<SkinView>> mSkinViews = new ArrayMap<>();

    private SkinResource mSkinResource;

    static {
        skinManager = new SkinManager();
    }

    public static SkinManager getInstance() {
        return skinManager;
    }

    private SkinManager() {
    }


    public void init(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * 加载皮肤
     *
     * @param skinPath
     * @return
     */
    public int loadSkin(String skinPath) {

        // 校验签名  增量更新再说

        // 初始化资源管理
        mSkinResource = new SkinResource(mContext, skinPath);

        // 改变皮肤

        Set<Activity> keys = mSkinViews.keySet();
        for (Activity key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            if (skinViews != null && skinViews.size() > 0) {
                for (SkinView skinView : skinViews) {
                    skinView.skin();
                }
            }
        }

        return 0;
    }


    /**
     * 恢复默认
     *
     * @return
     */
    public int restoreDefault() {
        return 0;
    }

    /**
     * 获取 SkinView 通过 activity
     *
     * @param activity
     * @return
     */
    public List<SkinView> getSkinViews(Activity activity) {

        return mSkinViews.get(activity);

    }

    public void register(BaseSkinActivity activity, List<SkinView> skinViews) {
        mSkinViews.put(activity, skinViews);
    }

    /**
     * 获取当前的皮肤资源
     */
    public SkinResource getSkinResource() {
        return mSkinResource;
    }
}
