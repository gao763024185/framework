package com.cloudm.framework.common.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @description: 数组工具类
 * @author: Courser
 * @date:  2017/3/15
 * @version: V1.0
 */
public final class ArrayUtil {
    /**
     * 判断数据是否非空
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array){
        return !ArrayUtils.isEmpty(array);
    }

    /**
     * 判断数组是否为空
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }
}
