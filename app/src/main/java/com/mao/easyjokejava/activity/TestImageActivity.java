package com.mao.easyjokejava.activity;

import android.content.Intent;
import android.view.View;

import com.mao.easyjokejava.R;
import com.mao.framelibrary.BaseSkinActivity;

import java.util.ArrayList;

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/4/9.
 * Version 1.0
 * Description:
 */
public class TestImageActivity extends BaseSkinActivity {
    private ArrayList<String> mImageList;
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
        Intent intent = new Intent(this,SelectImageActivity.class);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_COUNT,9);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_MODE,SelectImageActivity.MODE_MULTI);
        intent.putStringArrayListExtra(SelectImageActivity.EXTRA_DEFAULT_SELECTED_LIST, mImageList);
        intent.putExtra(SelectImageActivity.EXTRA_SHOW_CAMERA, true);
        startActivity(intent);
    }
}
