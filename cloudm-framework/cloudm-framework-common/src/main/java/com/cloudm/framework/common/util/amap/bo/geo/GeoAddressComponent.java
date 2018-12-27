package com.cloudm.framework.common.util.amap.bo.geo;

import lombok.Data;

/**
 * @description:
 * @author: sunyinhui
 * @date: 2018/3/20
 * @version: V1.0
 */
@Data
public class GeoAddressComponent {

    /**
     * 坐标点所在省名称
     *  例如：北京市
     */
    private String province;
    /**
     *  坐标点所在城市名称
     *  当所在城市为北京、上海、天津、重庆四个直辖市时，该字段返回为空
     *  当所在城市属于县级市的时候，此字段为空
     */
    private Object city;
    /**
     * 城市编码
     *  例如：010
     */
    private Object citycode;
    /**
     *  坐标点所在区
     *   例如：海淀区
     */
    private Object district;

    /**
     * 经纬度 location: "116.407526,39.904030",
     */
    private String location;

}
