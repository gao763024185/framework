package com.cloudm.framework.common.consts;

/**
 * @description: 供配置相关常亮
 * @author: Courser
 * @date: 2017/3/16
 * @version: V1.0
 */
public interface ConfigConstant {

    String CONFIG_FILE = "application.properties";

    /**
     * jpush app key
     */
    String JPUSH_APP_KEY = "jpush.app.key";
    /**
     * jpush masterSecret
     */
    String JPUSH_MASTER_SECRET = "jpush.master.secret";
    /**
     * jpush apnsProduction
     */
    String JPUSH_APNS_PRODUCTION = "jpush.apns.production";
    /**
     * SMS 请求地址
     */
    String SMS_URL = "sms.url";
    /**
     * SMS TOP分配给应用的AppKey
     */
    String SMS_APP_KEY = "sms.app.key";
    /**
     * SMS 短信签名AppKey对应的secret值
     */
    String SMS_SECRET = "sms.secret";
    /**
     * SMS 短信类型，传入值请填写normal
     */
    String SMS_TYPE = "sms.type";
    /**
     * SMS 阿里大于账户配置的短信签名
     */
    String SMS_SIGN = "sms.sign";
    /**
     * SMS 阿里大于账户配置的短信模板ID
     */
    String SMS_TEMPLATE_CODE = "sms.template.code";
}
