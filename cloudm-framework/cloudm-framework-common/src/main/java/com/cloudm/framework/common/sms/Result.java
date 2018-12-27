package com.cloudm.framework.common.sms;

/**
 * @description:发送短信成功失败的标识
 * @author: Courser
 * @date: 2017/3/17
 * @version: V1.0
 */
public class Result {
    private String err_code;
    private String model;
    private String success;
    public String getErr_code() {
        return err_code;
    }
    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getSuccess() {
        return success;
    }
    public void setSuccess(String success) {
        this.success = success;
    }
}