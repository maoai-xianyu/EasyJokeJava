package com.mao.easyjokejava.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.mao.baselibrary.ioc.ViewById;
import com.mao.easyjokejava.R;
import com.mao.easyjokejava.uitl.PatchUtils;
import com.mao.framelibrary.BaseSkinActivity;

import java.io.File;

public class TestUpdateVersionActivity extends BaseSkinActivity {

    @ViewById(R.id.tv)
    private TextView mTv;


    private String mPatchPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
            "version_1.0_2.0.patch";

    private String mNewApkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
            "version_2.0.apk";

    @Override
    protected void initData() {

        // 1. 访问后台接口，需不需要更新版本


        // 2.  方式一： 需要更新版本，那么提示用户需要下载 （腾讯视频提示更新） 6M/20M
        // 方式二：直接下载，然后提示用户更新


        // 3. 下载完差分包之后，调用我们的方法去合并生成新的apk
        // 耗时操作，如何半？1. 开线程+Handler 2. AsyncTask  3. RxJava
        // 1. 本地的apk路径怎么来？已经安装了 1.0 版本  获取本地的 getPackageResourcePath() apk 路径
        PatchUtils.combine(getPackageResourcePath(), mNewApkPath, mPatchPath);

        // 4. 需要校验签名


        // 5. 安卓最新的版本

    }


    public void installAPK(String path) {
        File apkFile = new File(path);
        if (apkFile.exists()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri apkUri = FileProvider.getUriForFile(this, "com.boxfish.stu.provider", apkFile);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }
            startActivity(intent);
            finish();
        } else {
            //startApp();
        }
    }

    @Override
    protected void initView() {
        mTv.setText("更新版本");

        viewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_update);
    }

}
