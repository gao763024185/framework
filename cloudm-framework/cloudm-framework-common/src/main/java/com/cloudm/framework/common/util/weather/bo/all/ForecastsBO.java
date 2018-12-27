package com.cloudm.framework.common.util.weather.bo.all;

import lombok.Data;

import java.util.List;

/**
 * @description: 天气预报
 * @author: sunyinhui
 * @date 29/11/18.
 * @version: v1.0
 */
@Data
public class ForecastsBO {

    
    private List<ForecastBO> forecastBOList;


}
