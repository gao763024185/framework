package com.cloudm.framework.common.util.weather.bo;


import com.cloudm.framework.common.util.weather.bo.all.ForecastBO;
import com.cloudm.framework.common.util.weather.bo.base.LiveBO;
import lombok.Data;

import java.util.List;

/**
 * @description: 天气接口返回结果
 * @author: sunyinhui
 * @date 29/11/18.
 * @version: v1.0
 */
@Data
public class WeatherResult {

    private String status;

    private String count;

    private String info;

    private String infocode;

    private List<LiveBO> lives;

    private List<ForecastBO> forecasts;

}
