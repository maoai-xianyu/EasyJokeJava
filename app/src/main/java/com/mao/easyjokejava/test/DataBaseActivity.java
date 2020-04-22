package com.mao.easyjokejava.test;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mao.baselibrary.baseUtils.LogU;
import com.mao.baselibrary.baseUtils.ToastUtils;
import com.mao.baselibrary.dialog.AlertDialog;
import com.mao.baselibrary.http.HttpUtils;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.easyjokejava.R;
import com.mao.easyjokejava.model.NewsModel;
import com.mao.easyjokejava.model.Person;
import com.mao.framelibrary.BaseSkinActivity;
import com.mao.framelibrary.DefaultNavigationBar;
import com.mao.framelibrary.HttpCallBack;
import com.mao.framelibrary.db.DaoSupportFactory;
import com.mao.framelibrary.db.IDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class DataBaseActivity extends BaseSkinActivity {


    @ViewById(R.id.tv)
    private TextView mTv;

    @Override
    protected void initData() {
        // 路劲 apk 参数都需要放到jni中，防止反编译  Ndk.  so 库
        HttpUtils.with(this).url("https://api.apiopen.top/getWangYiNews")
                .cache(true)
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


        /*HttpUtils.with(this).url("https://api.apiopen.top/getWangYiNews")
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
        });*/

        // 3. 数据库的问题，缓存 新闻类特有的效果，第三方的数据库都是缓存在 data/data/XXX/database  工厂设计模式和单例设计模式

        // 3.1 为什么用Factory 目前的数据是在 内存卡中，有的时候我们需要放到  data/data/XXX/database
        // 获取的Factory 不一样，那么写入的位置是可以不一样的

        // 3.2 面向接口编程 获取 IDaoSupport 那么不需要关系实现，目前的实现是我们自己写的，可以切换第三方

        // 3.3 为了高扩展
        IDaoSupport<Person> daoSupport = DaoSupportFactory.getInstance().getIDaoSupport(Person.class);
        // 最少知识原则
//        daoSupport.insert(new Person("mao",30));

        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            persons.add(new Person("mao" + i, 30 + i));
        }
        long startTime = System.currentTimeMillis();
        LogU.d("startTime  " + startTime);
        // daoSupport.insert(persons);  // 没有用事务 2359ms  用事务 76  再优化 31
        //  LitePal.saveAll(persons); // 308

        long endTime = System.currentTimeMillis();
        LogU.d("time   2359ms " + (endTime - startTime));

        List<Person> queryAll = daoSupport.querySupport().queryAll();

        //List<Person> queryAll = daoSupport.queryAll();
        //LogU.d("queryAll " + queryAll);

        List<Person> query = daoSupport.querySupport().selection("age = ? ").selectionArgs("33").query();

        //LogU.d("query age = 23 " + query);
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
        setContentView(R.layout.activity_data);
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