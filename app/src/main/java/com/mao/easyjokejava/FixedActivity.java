package com.mao.easyjokejava;

import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mao.baselibrary.ExceptionCrashHandler;
import com.mao.baselibrary.baseUtils.LogU;
import com.mao.baselibrary.fixBug.FixDexManager;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.framelibrary.BaseSkinActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FixedActivity extends BaseSkinActivity {

    @ViewById(R.id.tv)
    private TextView mTv;

    @Override
    protected void initData() {

        // 获取上次的崩溃信息上传到服务器
        //LogU.d("  res  " + crashMsg());
        // crashMsg()

        // 阿里 有点问题
        // andFix();

        // 自己写
        fixDexBug();
    }

    private void fixDexBug() {
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        LogU.d(" fixFile " + fixFile);
        if (fixFile.exists()) {
            LogU.d(" fixFile 存在");
            FixDexManager fixDexManager = new FixDexManager(this);
            try {
                fixDexManager.fixDex(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    protected void initView() {
        mTv.setText("ICO");

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_fix);
    }


    @OnClick(R.id.tv)
    private void onClick(View view) {
        startActivity(TestActivity.class);
    }


    private void andFix() {
        // 测试 阿里云 修复bug , 直接获取本地内存卡里面的 fix.apatch
        LogU.d("  res  " + Environment.getExternalStorageDirectory());

        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
        if (fixFile.exists()) {
            LogU.d("文件存在");
            // 修复bug

            try {
                BaseJokeApplication.mPatchManager.addPatch(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String crashMsg() {
        String res = "";
        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
        if (crashFile.exists()) {
            // 上传服务器 之后在优化，这里只是一个演示 bug 发布新的版本供用户去下载  阿里的热修复解决问题
//            try {
//                FileInputStream fin = new FileInputStream(crashFile);
//
//                int length = fin.available();
//                byte[] buffer = new byte[length];
//                fin.read(buffer);
//                res = new String(buffer, "UTF-8");
//                fin.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            try {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(crashFile));

                char[] buffer = new char[1024];
                int len = 0;
                while ((len = inputStreamReader.read(buffer)) != -1) {
                    res = new String(buffer, 0, len);
                }

                LogU.d(" Tag " + res);

                inputStreamReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return res;
    }

}
