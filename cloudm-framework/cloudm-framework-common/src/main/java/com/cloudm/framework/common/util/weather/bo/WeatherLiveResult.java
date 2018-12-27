package com.cloudm.framework.common.util.weather.bo;

import lombok.Data;

/**
 * @description:
 * @author: sunyinhui
 * @date 29/11/18.
 * @version: v1.0
 */
@Data
public class WeatherLiveResult {

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 城市编码
     */
    private String adcode;

    /**
     * 天气
     */
    private String weather;

    /**
     * 温度
     */
    private String temperature;

    /**
     * 风向
     */
    private String winddirection;

    /**
     * 风向
     */
    private String windpower;

    /**
     * 湿度
     */
    private String humidity;

    /**
     * 时间
     */
    private String reporttime;
}
