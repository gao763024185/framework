package com.cloudm.framework.common.util;


import com.cloudm.framework.common.enums.BaseErrorEnum;
import com.cloudm.framework.common.ex.BusinessProcessFailException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @description:
 * @author: Courser
 * @date: 2017/7/1
 * @version: V1.0
 */
@Slf4j
public class HttpUtil {

    private String url ;
    public HttpUtil(){}
    public HttpUtil(String url){
        this.url =  url ;
    }

    public String post(){
       return post(url,null) ;
    }

    /**
     *  post 请求
     * @param url 地址
     * @param t 传送的数据
     * @param <T>
     * @return
     */
    public <T> String post(String url,T t){
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("content-type", "application/json");
        httpPost.addHeader("Accept", "application/json");
        String response =null;
        StringEntity   entity;
        String json = JsonUtil.toJson(t);
        try {
            entity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(entity);

            HttpResponse httpResponse;
            //post请求
            httpResponse = closeableHttpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() !=200) {
                throw new BusinessProcessFailException(BaseErrorEnum.SYS_ERROR);
            }

            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                response = EntityUtils.toString(httpEntity);
            }

            //释放资源
            closeableHttpClient.close();
        }catch (BusinessProcessFailException bpfe){
            log.error(ClassUtil.getExcCodeError(HttpUtil.class)+",{}！远程处理异常",url,json);
            throw bpfe;
        }catch (IOException ioe){
            log.error(ClassUtil.getExcCodeError(HttpUtil.class)+",{}！连接出错，可能远程服务器挂掉了",url,json);
            throw  new BusinessProcessFailException(BaseErrorEnum.SYS_ERROR);
        }
        catch (Exception e) {
            log.error(ClassUtil.getExcCodeError(HttpUtil.class)+",{}",url,json);
            throw  new BusinessProcessFailException(BaseErrorEnum.SYS_ERROR);
        }
        return  response;
    }

    /**
     * url请求
     *
     * @param url
     * @return String
     */
    public static String getUrlRespStr(String url) {

        try {
            HttpGet http = new HttpGet(url);
            CloseableHttpResponse response = HttpClients.createDefault().execute(http);
            final StatusLine statusLine = response.getStatusLine();
            final HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            return entity == null ? null : EntityUtils.toString(entity, Consts.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
