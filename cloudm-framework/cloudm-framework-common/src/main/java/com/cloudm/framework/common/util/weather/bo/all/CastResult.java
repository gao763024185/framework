package com.cloudm.framework.common.util.weather.bo.all;

import lombok.Data;

/**
 * @description:
 * @author: sunyinhui
 * @date 29/11/18.
 * @version: v1.0
 */
@Data
public class CastResult {

    /**
     * 日期
     */
    private String date;

    /**
     * 本月第几周
     */
    private String week;

    /**
     * 白天天气
     */
    private String dayweather;

    /**
     * 夜间天气
     */
    private String nightweather;

    /**
     * 日温
     */
    private String daytemp;

    /**
     * 夜温
     */
    private String nighttemp;

    /**
     * 白天风向
     */
    private String daywind;

    /**
     * 夜间风向
     */
    private String nightwind;

    /**
     * 白天风力
     */
    private String daypower;

    /**
     * 夜里风力
     */
    private String nightpower;
}
