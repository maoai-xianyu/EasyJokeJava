package com.mao.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * @author zhangkun
 * @time 2020-04-19 19:56
 * @Description 引擎的规范
 */
public interface IHttpEngine {
    // get

    void get(boolean cache,Context context, String url, Map<String, Object> params, EngineCallBack callBack);


    // post
    void post(boolean cache,Context context, String url, Map<String, Object> params, EngineCallBack callBack);


    // 下载文件

    // 上传文件


    // https 添加证书
}
