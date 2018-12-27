package com.cloudm.framework.common.util.amap.bo.geo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 经纬度
 * @author: sunyinhui
 * @date: 2018/3/20
 * @version: V1.0
 */
@Data
public class LngLatBO {

    private BigDecimal lng;

    private BigDecimal lat;
}
