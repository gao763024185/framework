package com.cloudm.framework.common.helper;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jay
 * @version v1.0
 * @description 请添加类描述
 * @date 2018-06-13 16:22
 */
@Data
public class JpushParam implements Serializable{

    /**
     * 别名（即登录的ID）
     */
    private Long alias;

    /**
     * 消息
     */
    private String msg;

    /**
     * 标题
     */
    private String title;

    /**
     * 持续时间
     */
    private Integer time;

    /**
     * 参数(type属性必须传入，如：map.put("type","***");)
     */
    private Integer type;

    /**
     * IOS需要推送的声音名称
     */
    private String sound;

    /**
     *
     * 业务id
     */
    private Integer bizId;


    /*------------------------------以下三项在需要推送到其他app上的时候 必须要传-------------------------*/

    /**
     * 推送应用的key
     */
    private String appKey;

    /**
     * 推送的应用的密码
     */
    private String appSecret;


    /**
     * 该应用是否推送
     */
    private Boolean status;
}
