package com.cloudm.framework.common.helper;


import com.cloudm.framework.common.consts.ConfigConstant;
import com.cloudm.framework.common.util.PropsUtil;

import java.util.Properties;

/**
 * @description: 属性文件辅助类
 * @author: Courser
 * @date:  2017/3/16
 * @version: V1.0
 */
public final class ConfigHelper {

    private static final Properties COFNIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE) ;


    /**
     * 获取 jpush app key
     * @return
     */
    public static   String  getJpushAppKey(){
        return PropsUtil.getString(COFNIG_PROPS,ConfigConstant.JPUSH_APP_KEY);
    }

    /**
     * 获取 jpush master secret
     * @return
     */
    public static String  getJpushMasterSecret(){
        return PropsUtil.getString(COFNIG_PROPS,ConfigConstant.JPUSH_MASTER_SECRET);
    }
    /**
     * 获取 jpush apnsProduction
     * @return
     */
    public static  Boolean  getJpushApnsProduction(){
        return PropsUtil.getBoolean(COFNIG_PROPS,ConfigConstant.JPUSH_APNS_PRODUCTION);
    }

    /**
     * 获取 sms 请求地址
     * @return
     */
    public static  String  getSmsUrl(){
        return PropsUtil.getString(COFNIG_PROPS,ConfigConstant.SMS_URL);
    }
    /**
     * 获取 sms TOP分配给应用的AppKey
     * @return
     */
    public static  String  getSmsAppKey(){
        return PropsUtil.getString(COFNIG_PROPS,ConfigConstant.SMS_APP_KEY);
    }
    /**
     * 获取 sms 短信签名AppKey对应的secret值
     * @return
     */
    public static  String  getSmsSecret(){
        return PropsUtil.getString(COFNIG_PROPS,ConfigConstant.SMS_SECRET);
    }
    /**
     * 获取 sms 短信类型，传入值请填写normal
     * @return
     */
    public static  String  getSmsType(){
        return PropsUtil.getString(COFNIG_PROPS,ConfigConstant.SMS_TYPE);
    }
    /**
     * 获取 sms 阿里大于账户配置的短信签名
     * @return
     */
    public static  String  getSmsSign(){
        return PropsUtil.getString(COFNIG_PROPS,ConfigConstant.SMS_SIGN);
    }
    /**
     * 获取 sms 阿里大于账户配置的短信模板ID
     * @return
     */
    public static  String  getSmsTemplateCode(){
        return PropsUtil.getString(COFNIG_PROPS,ConfigConstant.SMS_TEMPLATE_CODE);
    }

    public  static String getPropsValue(String key){
        return PropsUtil.getString(COFNIG_PROPS,key);
    }
}
