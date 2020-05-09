package com.gy.wanandroid.mvp.http;

import com.gy.wanandroid.mvp.http.api.Api;

import java.io.Serializable;

/**
 * 网络通用返回字段
 *
 */

public class BaseHttpBean<T> implements Serializable {
    private int errorCode;//0表示成功,非0表示失败
    private T data;//数据
    private String errorMsg;//Code不等于0时才存在,失败信息

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg == null ? "" : errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "BaseHttpBean{" +
                "errorCode=" + errorCode +
                ", data=" + data +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (errorCode == Api.RequestSuccess) {
            return true;
        } else {
            return false;
        }
    }
}
