package com.cloudm.framework.common.sms;

import lombok.Data;

/**
 * @description: log日志解析数据存放类
 * @author: Courser
 * @date: 2017/6/8
 * @version: V1.0
 */
@Data
public class ExectTimeBO {
    /**
     * 方法名
     */
    private String className;
    /**
     * 调用所耗时间
     */
    private String time;
    /**
     * 调用所耗最大时间
     */
    private Integer maxTime;
    /**
     * 调用所耗最小时间
     */
    private Integer minTime;
    /**
     * 调用所耗平均时间
     */
    private Integer avgTime;
    /**
     * 调用次数
     */
    private Integer calls;
    /**
     *调用者所用的系统名
     * 1.公共平台，2.云机械3.0,3.基础资料
     */
    private Integer systemId;

}
