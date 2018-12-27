package com.cloudm.framework.common.util;

/**
 * @description 经纬度相关工具类
 * @author: gaobh
 * @date: 2018/11/29 10:57
 * @version: v1.0
 */
public class LngLatUtil {
    /**
     * 中国陆地经纬度 最下的维度
     */
    private static final Double DOWN_LAT = 15.8293d;
    /**
     * 中国陆地经纬度 最上的维度
     */
    private static final Double UP_LAT = 53.55d;
    /**
     * 中国陆地经纬度 左边的经度
     */
    private static final Double LEFT_LON = 72.004d;
    /**
     * 中国陆地经纬度 右边的经度
     */
    private static final Double RIGHT_LON = 135.05d;


    /**
     * 判断经纬度点是否在中国内
     *
     * @param lat 纬度
     * @param lng 经度
     * @return true{在},false{不在}
     */
    public static boolean inChina(double lat, double lng) {
        return lng <= RIGHT_LON && lng >= LEFT_LON
                && lat >= DOWN_LAT && lat <= UP_LAT;
    }
}
