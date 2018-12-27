package com.cloudm.framework.common.consts;

/**
 * @description 极速api相关配置常量
 * @author: gaobh
 * @date: 2018/6/8 10:52
 * @version: v2.1
 */
public class JisuConfigConstant {
    /**
     * 企业对应的 app key
     */
    public static final String APP_KEY = "813038666cfc7f66";

    /**
     * 身份证验证接口
     */
    public static final String ID_CARD_VERIFY_URL = "http://api.jisuapi.com/idcardverify/verify";

    /**
     * 银行卡验证接口
     */
    public static final String BANK_CARD_VERIFY_URL = "http://api.jisuapi.com/bankcardverify4/verify";

    /**
     * 超时时间ms
     */
    public static final int TIME_OUT = 5000;

    /**
     * 接口响应成功
     */
    public static final String STATUS_SUCCESS = "0";
}
