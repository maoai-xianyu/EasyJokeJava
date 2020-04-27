package com.mao.easyjokejava;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mao.baselibrary.baseUtils.LogU;
import com.mao.baselibrary.baseUtils.ToastUtils;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.framelibrary.BaseSkinActivity;
import com.mao.framelibrary.DefaultNavigationBar;
import com.mao.framelibrary.skin.SkinManager;
import com.mao.framelibrary.skin.SkinResource;

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

        LogU.d("mIv " + mIv);

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


    @OnClick({R.id.tv, R.id.change, R.id.changeDefault, R.id.jump})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
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

                    if (file.exists()) {
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
                break;

            case R.id.change:
                // 真正开发的过程，需要从服务器下载对应的皮肤
                String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "easyJokeChange.skin";
                File file = new File(skinPath);
                LogU.d("easyJokeChange.skin  是否存在 "+file.exists());
                int resultChange = SkinManager.getInstance().loadSkin(skinPath);
                break;
            case R.id.changeDefault:
                int resultDefault = SkinManager.getInstance().restoreDefault();
                break;
            case R.id.jump:
                startActivity(MainActivity.class);
                break;

        }


    }
    @Override
    public void changeSkin(SkinResource skinResource){
        // 做一些第三方的改变，还有自定义view的属性的修改
        ToastUtils.show("换肤了");
    }

}
