package com.mao.framelibrary.skin.support;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.mao.framelibrary.skin.attr.SkinAttr;
import com.mao.framelibrary.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangkun
 * @time 2020-04-23 18:14
 * @Description 皮肤属性解析的支持类
 */
public class SkinAttrSupport {

    /**
     * 获取SkinAttr的属性
     *
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        // background  textColor src

        List<SkinAttr> skinAttrs = new ArrayList<>();
        int attrLength = attrs.getAttributeCount();
        for (int index = 0; index < attrLength; index++) {
            // 获取名称 获取值
            String attributeName = attrs.getAttributeName(index);
            String attributeValue = attrs.getAttributeValue(index);

            // attributeName id 值 attributeValue @2131165318
            // LogU.d("属性名称的 attributeName " + attributeName + " 值 attributeValue " + attributeValue);

            // 只获取重要的信息
            SkinType skinType = getSkinType(attributeName);
            if (skinType != null) {
                // 资源名称 目前只有 attributeValue 是一个 @int 类型
                String resName = getResName(context, attributeValue);
                if (TextUtils.isEmpty(resName)) {
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(resName, skinType);
                skinAttrs.add(skinAttr);
            }


        }
        return skinAttrs;
    }

    /**
     * 获取资源的名称, @后面是id
     *
     * @param context
     * @param attributeValue
     * @return
     */
    private static String getResName(Context context, String attributeValue) {
        if (attributeValue.startsWith("@")) {
            attributeValue = attributeValue.substring(1);
            int resId = Integer.parseInt(attributeValue);

            return context.getResources().getResourceEntryName(resId);
        }

        return null;
    }

    /**
     * 通过名称获取SkinType
     *
     * @param attributeName
     * @return
     */
    private static SkinType getSkinType(String attributeName) {

        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (skinType.getResName().equals(attributeName)) {
                return skinType;
            }
        }

        return null;
    }
}
