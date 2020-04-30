package com.mao.easyjokejava.test.service;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mao.baselibrary.baseUtils.LogU;

import java.util.List;

/**
 * @author zhangkun
 * @time 2020-04-29 10:15
 * @Description 5.0 以上的才有
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {

    private final int JobWakeUpId = 1;
    private JobScheduler mJobScheduler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 开启一个轮训
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        startJobScheduler();

        return START_STICKY;
    }


    public void startJobScheduler() {
        mJobScheduler.cancel(JobWakeUpId);
        JobInfo.Builder builder = new JobInfo.Builder(JobWakeUpId, new ComponentName(this, JobWakeUpService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setMinimumLatency(5000); //执行的最小延迟时间
            builder.setOverrideDeadline(5000);  //执行的最长延时时间
            builder.setMinimumLatency(5000);
            builder.setBackoffCriteria(5000, JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
        } else {
            builder.setPeriodic(5000);
        }
        /*builder.setRequiresCharging(true);// 设置是否充电的条件,默认false
        builder.setRequiresDeviceIdle(true);// 设置手机是否空闲的条件,默认false
        builder.setPersisted(true);//设备重启之后你的任务是否还要继续执行*/
        //开始定时执行该系统任务
        if (mJobScheduler.schedule(builder.build()) <= 0) {
            LogU.e("JobHandlerService  工作失败");
        } else {
            LogU.e("JobHandlerService  工作成功");
        }
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        // 开启定时任务，定时轮训,看 MessageService有木有被杀死
        // 如果杀死了就启动
        boolean msgServiceAlive = serviceAlive(MsgService.class.getName());
        if (!msgServiceAlive) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(this, MsgService.class));
            } else {
                startService(new Intent(this, MsgService.class));
            }
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        //
        return false;
    }


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    private boolean serviceAlive(String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
