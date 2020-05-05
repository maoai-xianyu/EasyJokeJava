package com.mao.easyjokejava.model;

import java.util.List;

/**
 * @author zhangkun
 * @time 2020-05-05 16:52
 * @Description
 */
public class BaseEntity<T> {

    private int code;
    private String message;
    private List<T> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
