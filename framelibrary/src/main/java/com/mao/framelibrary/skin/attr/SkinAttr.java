package com.mao.framelibrary.skin.attr;

import android.view.View;

/**
 * @author zhangkun
 * @time 2020-04-23 18:20
 * @Description
 */
public class SkinAttr {

    private String mResName;

    private SkinType mSkinType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName = resName;
        this.mSkinType = skinType;
    }

    public void skin(View view) {
        mSkinType.skin(view, mResName);
    }


}
