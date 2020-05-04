package com.mao.easyjokejava;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.framelibrary.BaseSkinActivity;

public class MainActivity extends BaseSkinActivity {


    @ViewById(R.id.tv)
    private TextView mTv;

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
        setContentView(R.layout.activity_main);
    }


    @OnClick({R.id.tv})
    private void onClick(View view) {


    }


}
