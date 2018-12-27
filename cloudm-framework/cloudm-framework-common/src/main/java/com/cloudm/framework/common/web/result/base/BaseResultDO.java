package com.cloudm.framework.common.web.result.base;

import java.io.Serializable;

/**
 * 基础result yjx用
 * Created by jackson on 2017/6/26.
 */
public class BaseResultDO implements Serializable {
    private boolean ok=true;
    private int code;
    private String message;

    public boolean isOk() {
        return ok;
    }
    public void setOk(boolean ok) {
        this.ok = ok;
    }
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
}

