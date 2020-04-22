package com.mao.easyjokejava;

import android.view.View;
import android.widget.TextView;

import com.mao.baselibrary.baseUtils.LogU;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.easyjokejava.test.DataBaseActivity;
import com.mao.easyjokejava.test.FixedActivity;
import com.mao.framelibrary.BaseSkinActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

public class LoadingActivity extends BaseSkinActivity {

    @ViewById(R.id.tv)
    private TextView mTv;
    @ViewById(R.id.tvP)
    private TextView tvP;
    @ViewById(R.id.tvData)
    private TextView tvData;

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        mTv.setText("跳转mainActivity");
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_loading);
    }

    @OnClick({R.id.tv, R.id.tvP, R.id.tvFix})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                startActivity(MainActivity.class, true);
                break;
            case R.id.tvFix:
                startActivity(FixedActivity.class, true);
                break;
            case R.id.tvData:
                startActivity(DataBaseActivity.class, true);
                break;
            case R.id.tvP:
                AndPermission.with(this)
                        .runtime()
                        .permission(Permission.READ_EXTERNAL_STORAGE,
                                Permission.WRITE_EXTERNAL_STORAGE
                        )
                        .onGranted(permissions -> {
                            // Storage permission are allowed.
                            for (int i = 0; i < permissions.size(); i++) {
                                LogU.d("权限 " + permissions.get(i));
                            }
                        })
                        .onDenied(permissions -> {
                            // Storage permission are not allowed.
                        })
                        .start();
                break;
        }

    }

}
