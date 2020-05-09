package com.mao.easyjokejava.test;

import android.content.Intent;
import android.view.View;

import com.mao.baselibrary.ioc.OnClick;
import com.mao.easyjokejava.R;
import com.mao.easyjokejava.test.util.HookStartActivityUtil;
import com.mao.framelibrary.BaseSkinActivity;

public class HookActivity extends BaseSkinActivity {


    @Override
    protected void initData() {
        HookStartActivityUtil.init(this, HookProxyActivity.class);


    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_hook);
    }

    @OnClick({R.id.bt})
    public void click(View v) {
        Intent intent = new Intent(this, HookTestActivity.class);
        startActivity(intent);
    }

}
