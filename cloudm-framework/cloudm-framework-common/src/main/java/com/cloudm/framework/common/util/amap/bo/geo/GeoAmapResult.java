package com.cloudm.framework.common.util.amap.bo.geo;

import lombok.Data;

import java.util.List;

/**
 * @description: 根据地址解析经纬度
 * @author: sunyinhui
 * @date: 2018/3/20
 * @version: V1.0
 */
@Data
public class GeoAmapResult {

    private String status;

    private String info;

    private String infocode;

    private List<GeoAddressComponent> geocodes;
}
