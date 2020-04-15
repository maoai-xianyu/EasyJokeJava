package com.mao.easyjokejava;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mao.baselibrary.ioc.CheckNet;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.baselibrary.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.tv)
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        tv.setText("ICO");
    }


    @OnClick({R.id.tv})
    @CheckNet // 没有网络不执行方法
    private void onClick(View view) {
        Toast.makeText(this, "系那是", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.iv)
    private void onClickImage() {
        Toast.makeText(this, "点击图片", Toast.LENGTH_SHORT).show();
    }

}
