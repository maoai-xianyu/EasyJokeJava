package com.mao.baselibrary.http;

/**
 * @author zhangkun
 * @time 2020-04-19 19:58
 * @Description
 */
public interface EngineCallBack {

    // 错误
    public void onError(Exception e);

    // 成功 返回对象会出问题
    public void onSuccess(String result);

    public final EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack() {
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
