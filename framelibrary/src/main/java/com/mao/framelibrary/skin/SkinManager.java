package com.mao.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.mao.baselibrary.baseUtils.LogU;
import com.mao.framelibrary.skin.attr.SkinView;
import com.mao.framelibrary.skin.callback.ISkinChangeListener;
import com.mao.framelibrary.skin.config.SkinConfig;
import com.mao.framelibrary.skin.config.SkinPreUtils;

import java.io.File;
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

    private Map<ISkinChangeListener, List<SkinView>> mSkinViews = new ArrayMap<>();

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

        // 每一次打开应用都会到这里，防止皮肤被任意删除，做一些措施

        String currentPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        LogU.d("currentPath " + currentPath);

        File file = new File(currentPath);

        if (!file.exists()) {
            // 不存在需要清空皮肤
            SkinPreUtils.getInstance(mContext).clearSkinInfo();
            return;
        }

        // 获取包名
        String packageName = mContext.getPackageManager()
                .getPackageArchiveInfo(currentPath, PackageManager.GET_ACTIVITIES)
                .packageName;
        if (TextUtils.isEmpty(packageName)) {
            // 不存在包
            SkinPreUtils.getInstance(mContext).clearSkinInfo();
            return;
        }

        // 校验签名  增量更新再说

        // 初始化资源管理
        mSkinResource = new SkinResource(mContext, currentPath);
    }

    /**
     * 加载皮肤
     *
     * @param skinPath
     * @return
     */
    public int loadSkin(String skinPath) {

        File file = new File(skinPath);
        if (!file.exists()) {
            // 不存在需要清空皮肤
            SkinPreUtils.getInstance(mContext).clearSkinInfo();
            return SkinConfig.SKIN_FILE_NO_EXISTS;
        }

        // 获取包名
        String packageName = mContext.getPackageManager()
                .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
                .packageName;
        if (TextUtils.isEmpty(packageName)) {
            // 不存在包
            SkinPreUtils.getInstance(mContext).clearSkinInfo();
            return SkinConfig.SKIN_FILE_ERROR;
        }

        // 判断当前的皮肤是不是一样的，一样就不用动
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (skinPath.equals(currentSkinPath)) {
            return SkinConfig.SKIN_CHANGE_NOTHING;
        }

        // 校验签名  增量更新再说

        // 初始化资源管理
        mSkinResource = new SkinResource(mContext, skinPath);
        changeSkin();

        // 保存皮肤路径
        saveSkinStatus(skinPath);

        return 0;
    }

    /**
     * 改变皮肤
     */
    private void changeSkin() {
        // 改变皮肤
        Set<ISkinChangeListener> keys = mSkinViews.keySet();
        for (ISkinChangeListener key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            if (skinViews != null && skinViews.size() > 0) {
                for (SkinView skinView : skinViews) {
                    skinView.skin();
                }

                // 通知换肤
                key.changeSkin(mSkinResource);
            }

        }
    }

    private void saveSkinStatus(String skinPath) {

        // 封装好的类一定不要嵌套
        SkinPreUtils.getInstance(mContext).saveSkinPath(skinPath);
    }


    /**
     * 恢复默认
     *
     * @return
     */
    public int restoreDefault() {
        // 判断当前有木有皮肤，没有皮肤就不要执行任何方法
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (TextUtils.isEmpty(currentSkinPath)) {
            return SkinConfig.SKIN_CHANGE_NOTHING;
        }

        // 当前运行的App的路径,apk
        String skinPath = mContext.getPackageResourcePath();
        LogU.d("skinPath 回复默认 " + skinPath);

        // 初始化资源管理
        mSkinResource = new SkinResource(mContext, skinPath);

        changeSkin();

        // 清空皮肤
        SkinPreUtils.getInstance(mContext).clearSkinInfo();

        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 获取 SkinView 通过 activity
     *
     * @param activity
     * @return
     */
    public List<SkinView> getSkinViews(ISkinChangeListener activity) {

        return mSkinViews.get(activity);

    }

    public void register(ISkinChangeListener skinChangeListener, List<SkinView> skinViews) {
        mSkinViews.put(skinChangeListener, skinViews);
    }

    /**
     * 获取当前的皮肤资源
     */
    public SkinResource getSkinResource() {
        return mSkinResource;
    }


    /**
     * 检测要不要换肤
     *
     * @param skinView
     */
    public void checkChangeSkin(SkinView skinView) {
        // 如果当前有皮肤，也就是保存了皮肤路径，就换一下皮肤

        String currentPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (!TextUtils.isEmpty(currentPath)) {
            skinView.skin();
        }

    }

    /**
     * 防止内存泄漏
     *
     * @param skinChangeListener
     */
    public void unregister(ISkinChangeListener skinChangeListener) {
        mSkinViews.remove(skinChangeListener);
    }
}
