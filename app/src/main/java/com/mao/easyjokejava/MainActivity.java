package com.mao.easyjokejava;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mao.baselibrary.baseUtils.ToastUtils;
import com.mao.baselibrary.dialog.AlertDialog;
import com.mao.baselibrary.ioc.OnClick;
import com.mao.baselibrary.ioc.ViewById;
import com.mao.framelibrary.BaseSkinActivity;

public class MainActivity extends BaseSkinActivity {


    @ViewById(R.id.tv)
    private TextView mTv;

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
