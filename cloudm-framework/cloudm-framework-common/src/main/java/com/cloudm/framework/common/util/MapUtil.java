package com.cloudm.framework.common.util;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @description: Map 工具类
 * @author: Courser
 * @date: 2017/8/1
 * @version: V1.0
 */

public class MapUtil {
    /**
     * 将主键当作Map的Key
     * @param values 是一个迭代器
     * @param keyFunction
     * @param <K> 主键
     * @param <V> 对应的实体
     * @return
     */
   public static  <K, V> Map<K,V> uniqueIndex(Iterable<V> values, Function<? super V, K> keyFunction){
     return Maps.uniqueIndex(values,keyFunction);
  }

}
