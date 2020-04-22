package com.mao.framelibrary.http;

/**
 * @author zhangkun
 * @time 2020-04-21 17:41
 * @Description 缓存的实体类
 */
public class CacheData {

    // 请求连接
    private String mUrlKey;

    // 后台数据
    private String mResultJson;

    public CacheData() {
    }


    public CacheData(String urlKey, String resultJson) {
        mUrlKey = urlKey;
        mResultJson = resultJson;
    }

    public String getUrlKey() {
        return mUrlKey;
    }

    public void setUrlKey(String urlKey) {
        mUrlKey = urlKey;
    }

    public String getResultJson() {
        return mResultJson;
    }

    public void setResultJson(String resultJson) {
        mResultJson = resultJson;
    }

    @Override
    public String toString() {
        return "CacheData{" +
                "mUrlKey='" + mUrlKey + '\'' +
                ", mResultJson='" + mResultJson + '\'' +
                '}';
    }
}
