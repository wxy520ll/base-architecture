package com.xylibrary.http;

import java.io.Serializable;

/**
 * Created by jiajun.wang on 2018/2/25.
 */

public class ResultEntity<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public ResultEntity<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultEntity<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultEntity<T> setData(T data) {
        this.data = data;
        return this;
    }
}
