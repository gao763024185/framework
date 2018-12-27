package com.cloudm.framework.common.util;

/**
 * @description: Class 工具类
 * @author: Courser
 * @date: 2017/7/1
 * @version: V1.0
 */
public class ClassUtil {
    /**
     * 获取正在执行的方法的类型名、方法名、以及行号。
     * 用于在log.error()输出时，要求类名#方法名 line：12 is faild
     * @param c
     * @return
     */
    public static String getExcCodeError(Class<?> c){
        return c.getName()+"#"+
                Thread.currentThread().getStackTrace()[2].getMethodName()
                +" line:"+
                Thread.currentThread().getStackTrace()[2].getLineNumber()
                +" is faild! Params is ==>{} ";
    }
}
