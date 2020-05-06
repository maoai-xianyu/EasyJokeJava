package com.mao.framelibrary.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.mao.baselibrary.baseUtils.LogU;
import com.mao.baselibrary.baseUtils.MD5Util;
import com.mao.baselibrary.http.EngineCallBack;
import com.mao.baselibrary.http.HttpUtils;
import com.mao.baselibrary.http.IHttpEngine;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author zhangkun
 * @time 2020-04-19 19:59
 * @Description OkHttp 默认的引擎
 */
public class OkHttpEngine implements IHttpEngine {

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    private Handler mHandler = new Handler(Looper.getMainLooper());



    @Override
    public void post(boolean cache, Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {
        final String jointUrl = HttpUtils.jointParams(url, params);  //打印
        LogU.d("Post请求路径：" + jointUrl);

        // 了解 Okhhtp
        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 这个 两个回掉方法都不是在主线程中
                String result = response.body().string();
                LogU.e("Post返回结果：" + result);
                callBack.onSuccess(result);
            }
        });

    }


    /**
     * 组装post请求参数body
     */
    protected RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    // 添加参数
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key) + "");
                Object value = params.get(key);
                if (value instanceof File) {
                    // 处理文件 --> Object File
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody
                            .create(MediaType.parse(guessMimeType(file
                                    .getAbsolutePath())), file));
                } else if (value instanceof List) {
                    // 代表提交的是 List集合
                    try {
                        List<File> listFiles = (List<File>) value;
                        for (int i = 0; i < listFiles.size(); i++) {
                            // 获取文件
                            File file = listFiles.get(i);
                            builder.addFormDataPart(key + i, file.getName(), RequestBody
                                    .create(MediaType.parse(guessMimeType(file
                                            .getAbsolutePath())), file));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.addFormDataPart(key, value + "");
                }
            }
        }
    }


    /**
     * 猜测文件类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    @Override
    public void get(final boolean cache, Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {
        // 唯一标识
        url = HttpUtils.jointParams(url, params);

        LogU.d("Get请求路径：" + url);

        final String finalUrl = MD5Util.string2MD5(url);


        // 判断需不要需要缓存，然后判断有木有
        if (cache) {

            String cacheHttpRequestResultJson = CacheDataUtils.getCacheHttpRequestResultJson(finalUrl);
            if (!TextUtils.isEmpty(cacheHttpRequestResultJson)) {
                LogU.d("数据库有缓存 " + cacheHttpRequestResultJson);
                // 需要缓存，而且数据库有缓存,直接执行
                callBack.onSuccess(cacheHttpRequestResultJson);
            }

        }

        Request.Builder requestBuilder = new Request.Builder().url(url).tag(context);
        //可以省略，默认是GET请求
        Request request = requestBuilder.build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();
                // 获取数据之后会执行成功的方法
                // 2. 每次获取到的数据线，先比对

                if (cache) {
                    String cacheHttpRequestResultJson = CacheDataUtils.getCacheHttpRequestResultJson(finalUrl);
                    if (!TextUtils.isEmpty(resultJson)) {
                        if (resultJson.equals(cacheHttpRequestResultJson)) {
                            // 内容一样，说明数据一样，不需要刷新界面
                            LogU.d("新数据和缓存数据一样 不用刷新");
                            return;
                        }
                    }
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(resultJson);
                    }
                });

                LogU.d("Get返回结果：" + resultJson);
                // 缓存数据
                if (cache) {
                    CacheDataUtils.cacheData(finalUrl, resultJson);
                }

            }
        });
    }
}
