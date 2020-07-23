package com.mao.easyjokejava;

import android.widget.TextView;

import com.mao.baselibrary.ioc.ViewById;
import com.mao.framelibrary.BaseSkinActivity;

public class HideActivity extends BaseSkinActivity {

    @ViewById(R.id.tv)
    private TextView mTv;

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        mTv.setText("隐式启动");

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_hide);
    }

}
