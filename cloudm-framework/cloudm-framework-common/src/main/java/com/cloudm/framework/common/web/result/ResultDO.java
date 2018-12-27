package com.cloudm.framework.common.web.result;

import com.cloudm.framework.common.web.result.base.BaseResult;

import java.io.Serializable;

/**
 * result yjx用
 * Created by jackson on 2017/6/26.
 */
public class ResultDO<T> extends BaseResult implements Serializable{
    private T result;

    public boolean isOk() {
        return success;
    }
    public void setOk(boolean success) {
        this.success = success;
    }
    public Integer getCode() {
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
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }
}
