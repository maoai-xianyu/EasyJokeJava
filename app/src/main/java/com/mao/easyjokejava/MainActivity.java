package com.mao.easyjokejava;

import android.view.View;
import android.widget.TextView;

import com.mao.baselibrary.baseUtils.ToastUtils;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.framelibrary.BaseSkinActivity;
import com.mao.framelibrary.DefaultNavigationBar;

public class MainActivity extends BaseSkinActivity {


    @ViewById(R.id.tv)
    private TextView mTv;

    @Override
    protected void initData() {
    }


    @Override
    protected void initView() {
        mTv.setText("ICO");
    }

    @Override
    protected void initTitle() {

        DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar
                .Builder(this)
                .setTitle("投稿")
                .setRightText("发布")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.show("右边发布");
                    }
                })
                .builder();

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }


    @OnClick({R.id.tv})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                break;

        }


    }


}
