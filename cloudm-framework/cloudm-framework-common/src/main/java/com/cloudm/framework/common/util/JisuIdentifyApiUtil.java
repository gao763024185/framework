package com.cloudm.framework.common.util;

import com.cloudm.framework.common.consts.JisuConfigConstant;
import com.cloudm.framework.common.enums.BaseErrorEnum;
import com.cloudm.framework.common.ex.BusinessCheckFailException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @description: http://api.jisuapi.com 信息认证
 * @author: gaobh
 * @date: 2018/6/8 10:58
 * @version: v2.1
 */
@Slf4j
public class JisuIdentifyApiUtil {

    /**
     * 身份证验证
     *
     * @param realName 真是姓名
     * @param idCard   身份证号码
     * @return
     */
    public static String idCardValidate(String realName, String idCard) {
        StringBuilder sbUrl = new StringBuilder(JisuConfigConstant.ID_CARD_VERIFY_URL);
        try {
            sbUrl.append("?appkey=");
            sbUrl.append(JisuConfigConstant.APP_KEY);
            sbUrl.append("&idcard=");
            sbUrl.append(idCard);
            sbUrl.append("&realname=");
            sbUrl.append(URLEncoder.encode(realName, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("#idCardValidate error", e);
            throw new BusinessCheckFailException(BaseErrorEnum.BNS_PRS_ERROR.getCode(), e.getMessage());
        }
        return sendPostRequest(sbUrl.toString(), JisuConfigConstant.TIME_OUT, JisuConfigConstant.TIME_OUT, JisuConfigConstant.TIME_OUT);
    }


    /**
     * 发送http post请求返回响应json
     *
     * @param url                      请求接口,请求参数直接在url
     * @param connectTimeout           连接超时时间 ms
     * @param connectionRequestTimeout 发送请求超时时间 ms
     * @param socketTimeout            请求返回数据超时时间 ms
     * @return
     */
    private static String sendPostRequest(String url, int connectTimeout, int connectionRequestTimeout, int socketTimeout) {
        log.info("request url : " + url);
        String result;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout).setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            //http响应非200
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.error("#postSendRequest response error, url = {} , httpStatusCode = {}",
                        url, httpResponse.getStatusLine().getStatusCode());
                throw new BusinessCheckFailException(BaseErrorEnum.BNS_PRS_ERROR.getCode(), "http响应异常");
            }
            result = EntityUtils.toString(httpResponse.getEntity());
            httpResponse.close();
            httpClient.close();
            return result;
        } catch (Exception e) {
            log.error("#postSendRequest error", e);
            throw new BusinessCheckFailException(BaseErrorEnum.BNS_PRS_ERROR.getCode(), e.getMessage());
        }
    }


    public static void main(String[] args) {
        String strr = idCardValidate("高保红1", "411527199309305263");
        System.out.println(strr);

    }
}
