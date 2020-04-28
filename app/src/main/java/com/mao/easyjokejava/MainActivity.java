package com.mao.easyjokejava;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import com.mao.baselibrary.baseUtils.ToastUtils;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.easyjokejava.test.MessageService;
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

        startService(new Intent(this, MessageService.class));
        Intent mIntent = new Intent(this, MessageService.class);
        bindService(mIntent, conn, Context.BIND_AUTO_CREATE);


        Intent intent = new Intent();
        intent.setAction("com.study.aidl.user");
        // 在Android 5.0之后google出于安全的角度禁止了隐式声明Intent来启动Service.也禁止使用Intent filter.否则就会抛个异常出来
        intent.setPackage("com.mao.easyjokejava");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        /*Intent mIntent = new Intent(this, MessageService.class);
        // 请求绑定连接 服务端
        bindService(mIntent, conn, Context.BIND_AUTO_CREATE);*/

    }

    private UserAidl mUserAidlProxy;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 连接好， service 就是服务端给我我们的 IBinder
            mUserAidlProxy = UserAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

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

                try {
                    ToastUtils.show("用户名 "+ mUserAidlProxy.getUsername());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tvP:
                try {
                    ToastUtils.show("密码 "+ mUserAidlProxy.getUserPsw());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

        }


    }


}
