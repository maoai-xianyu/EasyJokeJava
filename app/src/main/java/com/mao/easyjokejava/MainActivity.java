package com.mao.easyjokejava;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mao.baselibrary.baseUtils.LogU;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.framelibrary.BaseSkinActivity;

import java.io.File;
import java.lang.reflect.Method;

public class MainActivity extends BaseSkinActivity {


    @ViewById(R.id.tv)
    private TextView mTv;
    @ViewById(R.id.iv)
    private ImageView mIv;

    @Override
    protected void initData() {
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
        setContentView(R.layout.activity_main);
    }


    @OnClick(R.id.tv)
    private void onClick(View view) {

        LogU.d("换肤");

        // 读取本地一个  .skin里面的资源

        try {
            Resources superR = getResources();
            // 创建本地下载好的资源皮肤
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            //addAssetPath.setAccessible(true); // 私有的
            // 反射执行方法
            addAssetPath.invoke(assetManager, Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "easyJoke.skin");

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "easyJoke.skin");

            if (file.exists()){
                LogU.d("文件存在");
            }

            Resources resources = new Resources(assetManager, superR.getDisplayMetrics(), superR.getConfiguration());

            // 获取资源
            int drawableId = resources.getIdentifier("lichun", "drawable", "com.mao.easyjokejavaskin");
            // 用自己的资源获取图片
            Drawable drawable = resources.getDrawable(drawableId);
            mIv.setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
