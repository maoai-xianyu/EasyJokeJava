package com.mao.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * @author zhangkun
 * @time 2020-04-19 19:58
 * @Description
 */
public interface EngineCallBack {

    public void onPerExecute(Context context, Map<String, Object> params);


    // 错误
    public void onError(Exception e);

    // 成功 返回对象会出问题
    public void onSuccess(String result);

    public final EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack() {

        @Override
        public void onPerExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
