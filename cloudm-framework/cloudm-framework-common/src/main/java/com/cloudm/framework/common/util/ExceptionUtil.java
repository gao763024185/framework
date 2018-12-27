package com.cloudm.framework.common.util;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @description: 异常服务工具类，主要用于把堆栈信息转换成String类型
 * @author: Courser
 * @date: 2017/3/21
 * @version: V1.0
 */
public class ExceptionUtil {

    /**
     * 返回堆栈字符串
     * @param e
     * @return
     */
    public static String getStackTrace(Throwable e){
        return  ExceptionUtils.getStackTrace(e);
    }


}
