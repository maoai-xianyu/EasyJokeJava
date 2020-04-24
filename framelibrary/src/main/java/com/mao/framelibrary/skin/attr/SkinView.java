package com.mao.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * @author zhangkun
 * @time 2020-04-23 18:20
 * @Description
 */
public class SkinView {

    private View mView;

    private List<SkinAttr> mSkinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mSkinAttrs = skinAttrs;

    }

    public void skin() {
        for (SkinAttr skinAttr : mSkinAttrs) {
            skinAttr.skin(mView);
        }
    }
}
