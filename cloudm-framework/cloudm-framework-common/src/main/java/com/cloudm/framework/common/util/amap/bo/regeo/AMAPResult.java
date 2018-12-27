package com.cloudm.framework.common.util.amap.bo.regeo;

import lombok.Data;

/**
 * @description: 高德返回结构
 * @author: sunyinhui
 * @date: 2018/3/21
 * @version: V1.0
 */
@Data
public class AMAPResult {

    private String status;

    private String info;

    private String infocode;

    private RegeocodeBO regeocode;
}
