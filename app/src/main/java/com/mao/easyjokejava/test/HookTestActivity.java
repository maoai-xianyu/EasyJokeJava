package com.mao.easyjokejava.test;

import android.widget.TextView;

import com.mao.baselibrary.ioc.ViewById;
import com.mao.easyjokejava.R;
import com.mao.framelibrary.BaseSkinActivity;

public class HookTestActivity extends BaseSkinActivity {

    @ViewById(R.id.tv)
    private TextView bt;

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_hook_test);
    }

}
