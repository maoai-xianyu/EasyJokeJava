package com.mao.easyjokejava.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import androidx.annotation.Nullable;

import com.hc.essay.library.util.ImageUtil;
import com.mao.baselibrary.baseUtils.LogU;
import com.mao.easyjokejava.R;
import com.mao.easyjokejava.selectimage.ImageSelector;
import com.mao.easyjokejava.selectimage.SelectImageActivity;
import com.mao.framelibrary.BaseSkinActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/4/9.
 * Version 1.0
 * Description:
 */
public class TestImageActivity extends BaseSkinActivity {
    private ArrayList<String> mImageList;
    private final int SELECT_IMAGE_REQUEST = 0x0011;

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
        setContentView(R.layout.activity_test_image);
    }

    // 选择图片
    public void selectImage(View view){
        /*Intent intent = new Intent(this, SelectImageActivity.class);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_COUNT,9);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_MODE,SelectImageActivity.MODE_MULTI);
        intent.putStringArrayListExtra(SelectImageActivity.EXTRA_DEFAULT_SELECTED_LIST, mImageList);
        intent.putExtra(SelectImageActivity.EXTRA_SHOW_CAMERA, true);
        startActivityForResult(intent,SELECT_IMAGE_REQUEST);*/

        ImageSelector.create().count(9).multi()
                .origin(mImageList)
                .showCamera(true)
                .start(this,SELECT_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == SELECT_IMAGE_REQUEST && data != null){
                mImageList = data.getStringArrayListExtra(SelectImageActivity.EXTRA_RESULT);
                // 做一下显示
                LogU.e(mImageList.toString());
            }

        }
    }


    public void compressImg(View view){


        // 把选择好的图片做了一下压缩
        for (String path : mImageList) {
            // 做优化  第一个decodeFile有可能会内存移除
            // 一般后台会规定尺寸  800  小米 规定了宽度 720
            // 上传的时候可能会多张 for循环 最好用线程池 （2-3）
            Bitmap bitmap = ImageUtil.decodeFile(path);
            // 调用写好的native方法
            // 用Bitmap.compress压缩1/10
            ImageUtil.compressBitmap(bitmap, 75,
                    Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                            new File(path).getName()
            );
        }
    }
}
