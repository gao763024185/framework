package com.cloudm.framework.common.util;

import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @description: bean的工具类
 * @author: Courser
 * @date: 2017/3/17
 * @version: V1.0
 */
public class BeanUtil {

    /**
     * 对象属性值复制，这个是浅复制
     * @param source 有值对象，即源
     * @param target 没有值得对象，即目标对象
     */
    public static void copyProperties(Object source, Object target){
        BeanUtils.copyProperties(source,target);
    }


    /**
     * 集合复制，传入源list集合 和目标实体类型
     *
     * @param sourceList
     * @param targetType
     * @param <D>        源集合数据类型
     * @param <T>        目标集合数据类型
     * @return 转换后的集合
     */
    public static <D, T> List<T> copyList(List<D> sourceList, Class<T> targetType) {
        List<T> list = CollectionUtil.newArrayList(sourceList.size());
        for (D d : sourceList) {
            T t = BeanUtils.instantiate(targetType);
            copyProperties(d, t);
            list.add(t);
        }
        return list;
    }

}
