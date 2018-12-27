package com.cloudm.framework.common.util.amap;

import com.cloudm.framework.common.ex.BusinessProcessFailException;
import com.cloudm.framework.common.util.*;
import com.cloudm.framework.common.util.amap.bo.geo.GeoAmapResult;
import com.cloudm.framework.common.util.amap.bo.regeo.AddressBO;
import com.cloudm.framework.common.util.amap.bo.geo.LngLatBO;
import com.cloudm.framework.common.util.amap.bo.regeo.AMAPResult;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @description: 高德地图解析
 * @author: sunyinhui
 * @date: 2018/3/20
 * @version: V1.0
 */
@Slf4j
public class AmapUtil {

    /**
     * 地球半径
     */
    private static double EARTH_RADIUS = 6378.137;

    /**
     *  调试用
     *  stringBuilder.append("http://restapi.amap.com/v3/geocode/regeo?output=json&location=").append(location).append("&key=631b59229f1638b02e51d9155dbef10f&radius=100&extensions=base");
     */
    /**
     * amap.url
     */
    private static String AMAP_URL = "http://restapi.amap.com/v3/geocode/regeo?";

    /**
     * amap.key
     */
    private static String AMAP_KEY = "631b59229f1638b02e51d9155dbef10f";

    /**
     * 经纬度 获取地址详情
     * @param lng
     * @param lat
     * @return
     */
    public static AddressBO getAddress(double lng, double lat) {

        if (lat < 0.5 || lat > 57 || lng  < 70 || lng > 140){
            return null;
        }

        StringBuffer stringBuilder = new StringBuffer();
        String location = String .format("%.6f",lng)+","+String .format("%.6f",lat);
        stringBuilder.append(AMAP_URL)
                .append("output=json&").append("location=").append(location).append("&key=")
                .append(AMAP_KEY)
                .append("&radius=1000&extensions=base");

        AddressBO addressBO = null;
        try {
            AMAPResult amapResult = httpGet(stringBuilder.toString());
            if (amapResult ==null|| amapResult.getRegeocode()==null){
                log.info(AmapUtil.class.getClass()+"#getPosition   lng={} ,lat={}",lng,lat);
                return null;
            }
            addressBO = new AddressBO();
            if (amapResult.getRegeocode().getAddressComponent() != null && !("[]").equals(amapResult.getRegeocode().getAddressComponent().toString())) {
                addressBO.setProvince(amapResult.getRegeocode().getAddressComponent().getProvince().toString());
            } else {
                addressBO.setProvince("中国");
                return addressBO;
            }
            //如果城市为空，取省份，例如 北京市 city=[],则设置city=北京市
            if ("[]".equals(amapResult.getRegeocode().getAddressComponent().getCity().toString())) {
                addressBO.setCity(addressBO.getProvince());
            } else {
                addressBO.setCity(amapResult.getRegeocode().getAddressComponent().getCity().toString());
            }

            if (amapResult.getRegeocode().getAddressComponent().getDistrict() != null && !("[]").equals(amapResult.getRegeocode().getAddressComponent().getDistrict().toString())) {
                addressBO.setDistrict(amapResult.getRegeocode().getAddressComponent().getDistrict().toString());
            }

            if (amapResult.getRegeocode().getFormatted_address() != null && !("[]").equals(amapResult.getRegeocode().getFormatted_address().toString()) ) {
                addressBO.setDetail(amapResult.getRegeocode().getFormatted_address().toString());
            } else {
                addressBO.setDetail("中国");
            }

            if (amapResult.getRegeocode().getAddressComponent().getAdcode() != null) {
                addressBO.setAdcode(amapResult.getRegeocode().getAddressComponent().getAdcode().toString());
            }

        } catch (Exception e) {
            log.error(AmapUtil.class.getSimpleName() + "#getAddress failed  lng:{}, lat:{}", lng, lat + ",e:" + e);
        }


        return addressBO;
    }

    /**
     * 发送get请求 根据经纬度获取详情地址
     * @param url    路径
     * @return
     */
    private static AMAPResult httpGet(String url){
        //get请求返回结果
        try {
            String strResult = HttpClientUtil.httpGetRequest(url);
            if (StringUtil.isBlank(strResult)){
                return null;
            }
            return JsonUtil.fromJson(strResult, AMAPResult.class);
        }catch (JsonSyntaxException e){
            log.error(AmapUtil.class.getClass()+"#httpGet,  exception==>{}, url=={}  JSON化异常",e,url);
            return null;
        }catch (BusinessProcessFailException e1){
            log.error(AmapUtil.class.getClass()+"#httpGet,  exception==>{}, url=={}  远程调用异常",e1,url);
            return null;
        }
    }



    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s*1000;
        return s;
    }


    /**
     * 通过高德API获取经纬度信息
     *
     * @param address
     * @return
     */
    public static LngLatBO addressToGPS(String address) {
        if(address == null || StringUtil.isBlank(address) || StringUtil.isBlank(address.trim())) {
            return null;
        }
        // 过滤特殊字符
        address = FilterString.filterStr(address);
        StringBuffer url = new StringBuffer();
        url.append("http://restapi.amap.com/v3/geocode/geo?")
                .append("output=json&").append("address=").append(address).append("&key=")
                .append(AMAP_KEY);

        GeoAmapResult geoAmapResult = geoHttpGet(url.toString());

        if (geoAmapResult ==null || CollectionUtil.isEmpty(geoAmapResult.getGeocodes())){
            log.info(AmapUtil.class.getClass()+"#addressToGPS amapResult is null or ... address={}", address);
            return null;
        }

        String location = geoAmapResult.getGeocodes().get(0).getLocation();
        if (StringUtil.isBlank(location)) {
            log.info(AmapUtil.class.getClass()+"#addressToGPS location is blank ... address={}", address);
            return null;
        }
        LngLatBO lngLatBO = new LngLatBO();
        String[] lngLatArr = location.split(",");
        if (lngLatArr != null) {
            lngLatBO.setLng(new BigDecimal(lngLatArr[0]));
            lngLatBO.setLat(new BigDecimal(lngLatArr[1]));
        }

        return lngLatBO;
    }


    /**
     * 根据地址解析经纬度
     * @param url
     * @return
     */
    private static GeoAmapResult geoHttpGet(String url){
        //get请求返回结果
        try {
            String strResult = HttpClientUtil.httpGetRequest(url);
            if (StringUtil.isBlank(strResult)){
                return null;
            }
            return JsonUtil.fromJson(strResult, GeoAmapResult.class);
        }catch (JsonSyntaxException e){
            log.error(AmapUtil.class.getClass()+"#geoHttpGet,  exception==>{}, url=={}  JSON化异常",e,url);
            return null;
        }catch (BusinessProcessFailException e1){
            log.error(AmapUtil.class.getClass()+"#geoHttpGet,  exception==>{}, url=={}  远程调用异常",e1,url);
            return null;
        }
    }


    public static void main(String[] args) {
        getAddress(115.001779,35.80651);
    }


}
