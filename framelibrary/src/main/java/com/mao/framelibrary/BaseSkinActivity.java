package com.mao.framelibrary;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

import com.mao.baselibrary.base.BaseActivity;
import com.mao.baselibrary.baseUtils.LogU;

/**
 * @author zhangkun
 * @time 2020-04-16 18:04
 * @Description 插件式换肤
 */
public abstract class BaseSkinActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {

            // 拦截view的创建
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                LogU.d("拦截View的创建");
                if (name.equals("Button")) {
                    TextView textView = new TextView(BaseSkinActivity.this);
                    textView.setText("button 拦截后换成 textview ");
                    textView.setTextColor(Color.parseColor("#00ff00"));
                    return textView;
                }
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });


        super.onCreate(savedInstanceState);
    }
}
