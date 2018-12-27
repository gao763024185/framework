package com.cloudm.framework.common.util.weather;

import com.cloudm.framework.common.ex.BusinessProcessFailException;
import com.cloudm.framework.common.util.BeanUtil;
import com.cloudm.framework.common.util.HttpClientUtil;
import com.cloudm.framework.common.util.JsonUtil;
import com.cloudm.framework.common.util.StringUtil;
import com.cloudm.framework.common.util.amap.AmapUtil;
import com.cloudm.framework.common.util.amap.bo.regeo.AddressBO;
import com.cloudm.framework.common.util.weather.bo.WeatherLiveResult;
import com.cloudm.framework.common.util.weather.bo.WeatherResult;
import com.cloudm.framework.common.util.weather.bo.all.CastResult;
import com.cloudm.framework.common.util.weather.bo.all.ForecastBO;
import com.cloudm.framework.common.util.weather.bo.base.LiveBO;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description: 天气工具类
 * @author: sunyinhui
 * @date 29/11/18.
 * @version: v1.0
 */
@Slf4j
public class WeatherUtil {


    private static final String URL = "https://restapi.amap.com/v3/weather/weatherInfo?";

    private static final String KEY = "631b59229f1638b02e51d9155dbef10f";


    /**
     * 实时天气
     * 根据经纬度获取实时天气情况
     *
     * https://restapi.amap.com/v3/weather/weatherInfo?
     * key=631b59229f1638b02e51d9155dbef10f&
     * city=330102&
     * output=josn&
     * extensions=base
     *
     * @param lng
     * @param lat
     * @return
     */
    public static WeatherLiveResult getWeatherByLngLat(double lng, double lat) {

        AddressBO addressBO = AmapUtil.getAddress(lng, lat);

        String adcode = addressBO.getAdcode();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(URL)
                .append("output=json&")
                .append("&key=")
                .append(KEY)
                .append("&extensions=base")
                .append("&city=")
                .append(adcode);

        WeatherResult weatherResult = null;
        WeatherLiveResult weatherLiveResult = new WeatherLiveResult();
        try {
            weatherResult = httpGet(stringBuilder.toString());
            if (weatherResult != null && weatherResult.getLives() != null) {
                LiveBO liveBO = weatherResult.getLives().get(0);
                BeanUtil.copyProperties(liveBO, weatherLiveResult);
            }
        } catch (Exception e) {
            log.error(WeatherUtil.class.getSimpleName() + "#getWeatherByLngLat failed e:{}", e);
        }

        return weatherLiveResult;

    }



    /**
     * 天气预报
     * https://restapi.amap.com/v3/weather/weatherInfo?
     * key=631b59229f1638b02e51d9155dbef10f&
     * city=330102&
     * output=josn&
     * extensions=all
     *
     * @return
     */
    public static List<CastResult> forecastByLngLat(double lng, double lat) {
        AddressBO addressBO = AmapUtil.getAddress(lng, lat);

        String adcode = addressBO.getAdcode();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(URL)
                .append("output=json&")
                .append("&key=")
                .append(KEY)
                .append("&extensions=all")
                .append("&city=")
                .append(adcode);

        WeatherResult weatherResult = null;

        weatherResult = httpGet(stringBuilder.toString());
        if (weatherResult != null && weatherResult.getForecasts() != null) {
            ForecastBO forecastBO =  weatherResult.getForecasts().get(0);
            if (forecastBO != null) {
                return forecastBO.getCasts();
            }
        }
        return null;

    }



    /**
     * 发送get请求 根据经纬度获取详情地址
     * @param url    路径
     * @return
     */
    private static WeatherResult httpGet(String url){
        //get请求返回结果
        try {
            String strResult = HttpClientUtil.httpGetRequest(url);
            if (StringUtil.isBlank(strResult)){
                return null;
            }
            return JsonUtil.fromJson(strResult, WeatherResult.class);
//            return new Gson().fromJson(strResult, new TypeToken<WeatherResult>(){}.getType());
        }catch (JsonSyntaxException e){
            log.error(AmapUtil.class.getClass()+"#httpGet,  exception==>{}, url=={}  JSON化异常",e,url);
            return null;
        }catch (BusinessProcessFailException e1){
            log.error(AmapUtil.class.getClass()+"#httpGet,  exception==>{}, url=={}  远程调用异常",e1,url);
            return null;
        }
    }


    public static void main(String[] args) {


        WeatherLiveResult weatherLiveResult = getWeatherByLngLat(119.378042,35.31688);
        System.out.println(weatherLiveResult);


        List<CastResult> castBOList = forecastByLngLat(119.378042,35.31688);
        System.out.println(castBOList.get(2));


    }







}
