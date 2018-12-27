package com.cloudm.framework.common.util.amap.bo.regeo;

import lombok.Data;

/**
 * @description:
 * @author: sunyinhui
 * @date: 2018/3/20
 * @version: V1.0
 */
@Data
public class RegeocodeBO {

    private Object formatted_address;

    private AddressComponent addressComponent;

}
