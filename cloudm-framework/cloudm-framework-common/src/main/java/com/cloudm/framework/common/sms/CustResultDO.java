package com.cloudm.framework.common.sms;

/**
 * @description: 发送短息返回的数据结构
 * @author: Courser
 * @date: 2017/3/17
 * @version: V1.0
 */

public class CustResultDO<T> {
    private T alibaba_aliqin_fc_sms_num_send_response;//发送成功返回结果
    private T error_response;//发送失败返回结果


    public T getError_response() {
        return error_response;
    }

    public void setError_response(T error_response) {
        this.error_response = error_response;
    }

    public T getAlibaba_aliqin_fc_sms_num_send_response() {
        return alibaba_aliqin_fc_sms_num_send_response;
    }

    public void setAlibaba_aliqin_fc_sms_num_send_response(
            T alibaba_aliqin_fc_sms_num_send_response) {
        this.alibaba_aliqin_fc_sms_num_send_response = alibaba_aliqin_fc_sms_num_send_response;
    }
}
