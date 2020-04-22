package com.mao.easyjokejava;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mao.baselibrary.ioc.ViewById;
import com.mao.framelibrary.BaseSkinActivity;

public class TestActivity extends BaseSkinActivity {

    @ViewById(R.id.tv)
    private TextView mTv;

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        mTv.setText("测试热修复");

        viewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this,2/0+"bug修复测试", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_fix);
    }

}
