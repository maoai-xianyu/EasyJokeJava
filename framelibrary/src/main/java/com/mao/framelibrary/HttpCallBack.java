package com.mao.framelibrary;

import android.content.Context;

import com.google.gson.Gson;
import com.mao.baselibrary.http.EngineCallBack;
import com.mao.baselibrary.http.HttpUtils;

import java.util.Map;

/**
 * @author zhangkun
 * @time 2020-04-20 10:14
 * @Description
 */
public abstract class HttpCallBack<T> implements EngineCallBack {

    @Override
    public void onPerExecute(Context context, Map<String, Object> params) {

        // 添加共用参数 与项目的业务逻辑用

        // 项目名称  一系列共用参数
        params.put("app_name", "joke_essay");
        params.put("version_name", "5.7.0");
        params.put("ac", "wifi");
        params.put("device_id", "30036118478");
        params.put("device_brand", "Xiaomi");
        params.put("update_version_code", "5701");
        params.put("manifest_version_code", "570");
        params.put("longitude", "113.000366");
        params.put("latitude", "28.171377");
        params.put("device_platform", "android");
        onPreExecute();
    }

    // 执行方法
    private void onPreExecute() {

    }

    @Override
    public void onSuccess(String result) {

        Gson gson = new Gson();
        T resultBean = (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
        onSuccess(resultBean);

    }

    public abstract void onSuccess(T result);
}
