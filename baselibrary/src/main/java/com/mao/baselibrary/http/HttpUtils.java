package com.mao.baselibrary.http;

import android.content.Context;
import android.util.ArrayMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author zhangkun
 * @time 2020-04-19 19:55
 * @Description 自己的
 *
 * https://api.apiopen.top/getWangYiNews?page=1&count=20&version=2.0  // 获取新闻
 *
 * https://api.apiopen.top/videoHomeTab // 获取分类
 *
 *
 */
public class HttpUtils {


    // 默认的引擎
    private static IHttpEngine mIHttpEngine = new OkHttpEngine();

    // 直接带参数，链式调用
    private String mUrl;
    // 请求方式
    private static final int POST_TYPE = 0x0011;
    private static final int GET_TYPE = 0x0012;
    private int mType = GET_TYPE;

    // 上下文
    private Context mContext;


    private Map<String, Object> mParams;

    private HttpUtils(Context context) {
        mContext = context;
        mParams = new ArrayMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }


    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }


    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }


    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    // 添加参数
    public HttpUtils addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }


    public HttpUtils addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    // 添加回调 执行
    public void execute(EngineCallBack callBack) {
        if (callBack == null) {
            callBack = EngineCallBack.DEFAULT_CALL_BACK;
        }
        // 判断执行方法
        if (mType == POST_TYPE) {
            post(mUrl, mParams, callBack);
        }

        if (mType == GET_TYPE) {
            get(mUrl, mParams, callBack);
        }
    }

    public void execute() {
        execute(null);
    }


    // 在 Application 初始化引擎
    public static void init(IHttpEngine httpEngine) {
        mIHttpEngine = httpEngine;
    }

    /**
     * 每次可以自带引擎
     *
     * @param httpEngine
     */
    public void exchangeEngine(IHttpEngine httpEngine) {
        mIHttpEngine = httpEngine;
    }


    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mIHttpEngine.get(mContext, url, params, callBack);
    }

    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mIHttpEngine.post(mContext, url, params, callBack);

    }

    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }
        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }


    /**
     * 解析一个类上面的class信息
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
