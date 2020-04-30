package com.mao.easyjokejava.test.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.mao.baselibrary.baseUtils.LogU;
import com.mao.baselibrary.baseUtils.ToastUtils;
import com.mao.easyjokejava.IProcessConnection;
import com.mao.easyjokejava.R;

/**
 * @author zhangkun
 * @time 2020-04-28 23:52
 * @Description 守护进程，双进程通讯
 */
public class GuardService extends Service {

    private final int GUARDID = 1;
    private static final String CHANNEL_ID = "NFCService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel Channel = new NotificationChannel(CHANNEL_ID, "主服务", NotificationManager.IMPORTANCE_HIGH);
            Channel.enableLights(true);//设置提示灯
            // Channel.setLightColor(Color.RED);//设置提示灯颜色
            Channel.setShowBadge(true);//显示logo
            Channel.setDescription("ytzn");//设置描述
            Channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC); //设置锁屏可见 VISIBILITY_PUBLIC=可见
            manager.createNotificationChannel(Channel);

            Notification notification = new Notification.Builder(this)
                    .setChannelId(CHANNEL_ID)
                    .setContentTitle("你我来守护")//标题
                    .setContentText("运行中...")//内容
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)//小图标一定需要设置,否则会报错(如果不设置它启动服务前台化不会报错,但是你会发现这个通知不会启动),如果是普通通知,不设置必然报错
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .build();
            startForeground(1, notification);//服务前台化只能使用startForeground()方法,不能使用 notificationManager.notify(1,notification); 这个只是启动通知使用的,使用这个方法你只需要等待几秒就会发现报错了

        } else {
            // 提高进程的优先级
            Notification notification = new Notification.Builder(this)
                    .setContentTitle("你我来守护")//设置标题
                    .setContentText("运行中...")//设置内容
                    .setWhen(System.currentTimeMillis())//设置创建时间
                    .setSmallIcon(R.mipmap.ic_launcher)//设置状态栏图标
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置通知栏图标
                    .build();

            startForeground(GUARDID, notification);
        }

        Intent mIntent = new Intent(this, MsgService.class);
        bindService(mIntent, conn, Context.BIND_IMPORTANT);


        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IProcessConnection.Stub() {
        };
    }


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 连接好， service 就是服务端给我我们的 IBinder
            LogU.d(" GuardService   连接上");
            ToastUtils.show("GuardService 建立连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 断开链接需要重新绑定链接

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(GuardService.this, MsgService.class));
            } else {
                startService(new Intent(GuardService.this, MsgService.class));
            }
            bindService(new Intent(GuardService.this, MsgService.class), conn, Context.BIND_IMPORTANT);

        }
    };
}
