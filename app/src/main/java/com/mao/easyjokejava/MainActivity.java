package com.mao.easyjokejava;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.mao.baselibrary.baseUtils.ToastUtils;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.easyjokejava.test.service.GuardService;
import com.mao.easyjokejava.test.service.JobWakeUpService;
import com.mao.easyjokejava.test.service.MsgService;
import com.mao.framelibrary.BaseSkinActivity;
import com.mao.framelibrary.DefaultNavigationBar;

public class MainActivity extends BaseSkinActivity {


    @ViewById(R.id.tv)
    private TextView mTv;

    @Override
    protected void initData() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(new Intent(this, MsgService.class));
            startForegroundService(new Intent(this, GuardService.class));
        }else {
            startService(new Intent(this, MsgService.class));
            startService(new Intent(this, GuardService.class));
        }

        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.LOLLIPOP){
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForegroundService(new Intent(this, JobWakeUpService.class));
            }else {
                startService(new Intent(this, JobWakeUpService.class));
            }*/
            startService(new Intent(this, JobWakeUpService.class));
        }

    }


    @Override
    protected void initView() {

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


    @OnClick({R.id.tv, R.id.tvP})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:


                break;

            case R.id.tvP:

                break;

        }


    }


}
