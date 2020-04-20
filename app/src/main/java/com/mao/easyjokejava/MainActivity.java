package com.mao.easyjokejava;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mao.baselibrary.baseUtils.GsonU;
import com.mao.baselibrary.baseUtils.LogU;
import com.mao.baselibrary.baseUtils.ToastUtils;
import com.mao.baselibrary.dialog.AlertDialog;
import com.mao.baselibrary.http.HttpUtils;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.easyjokejava.model.NewsModel;
import com.mao.framelibrary.BaseSkinActivity;
import com.mao.framelibrary.DefaultNavigationBar;
import com.mao.framelibrary.HttpCallBack;
import com.mao.framelibrary.HttpStringCallBack;

public class MainActivity extends BaseSkinActivity {


    @ViewById(R.id.tv)
    private TextView mTv;

    @Override
    protected void initData() {

        HttpUtils.with(this).url("https://api.apiopen.top/getWangYiNews") // 路劲 apk 参数都需要放到jni中，防止反编译
                .addParam("page", 1)
                .addParam("count", 20)
                .get().execute(new HttpCallBack<NewsModel>() {

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess(NewsModel result) {

                LogU.d("直接转对象 result " + result);

            }
        });


        // 问题：
        // 1. 请求参数 很多但有些是公用的
        // 2. 回调每次都用 Json转换  但是不能够直接用泛型
        // 3. 数据库的问题，缓存 新闻类特有的效果，第三方的数据库都是缓存在 data/data/XXX/database  工厂设计模式和单例设计模式


        HttpUtils.with(this).url("https://api.apiopen.top/getWangYiNews") // 路劲 apk 参数都需要放到jni中，防止反编译
                .addParam("page", 1)
                .addParam("count", 20)
                .get().execute(new HttpStringCallBack() {

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess(String result) {
                NewsModel convert = GsonU.convert(result, NewsModel.class);
               LogU.d("GsonU 转 convert " + convert);
            }
        });


    }


    @Override
    protected void initView() {
        mTv.setText("ICO");

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


    @OnClick(R.id.tv)
    private void onClick(View view) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.detail_comment_dialog)
                .setText(R.id.submit_btn, "接收")

                .fromBottom(true)
                .fullWidth()
                .show();

        EditText editText = dialog.getView(R.id.comment_editor);

        dialog.setOnClickListener(R.id.account_icon_weibo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("微博分享 内容" + editText.getText().toString());
            }
        });
    }

}
