package com.cloudm.framework.common.util;

/**
 * @description: 判断是否在中国陆地
 * @author: sunyinhui
 * @date 25/07/18.
 * @version: v1.0
 */
public class PositionUtil {

    /**
     *  中国陆地经纬度
     */
    private static final Double DOWN_LAT = 15.8293d; // 最下的维度
    private static final Double UP_LAT = 53.55d;    // 最上的维度
    private static final Double LEFT_LON = 72.004d; // 左边的经度
    private static final Double RIGHT_LON = 135.05d;// 右边的经度


    /**
     * 判断经纬度点是否在中国陆地
     * @param lat 纬度
     * @param lng 经度
     * @return true{在},false{不在}
     */
    private static boolean inChina(double lat, double lng) {
        return lng <= RIGHT_LON && lng >= LEFT_LON && lat >= DOWN_LAT && lat <= UP_LAT;
    }
}
