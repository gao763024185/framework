package com.cloudm.framework.common.util;

import com.cloudm.framework.common.enums.BaseBizEnum;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @description: 集合工具类
 * @author: Courser
 * @date: 2017/4/6
 * @version: V1.0
 */
public class CollectionUtil {
    /**
     * 判断集合是否为空
     * @param collection 集合类
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断map是否为空
     * @param map 要判断的map
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map){
        return CollectionUtils.isEmpty(map);
    }

    /**
     * 判断集合是否不为空
     * @param collection 集合类
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Collection<?> collection){
        return !CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断map是否不为空
     * @param map 要判断的map
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Map<?, ?> map){
        return !CollectionUtils.isEmpty(map);
    }
    /**
     * 创建List对象
     *
     * @param <E> 泛型，
     * @return
     */
    public static <E> ArrayList<E> newArrayList() {
        return Lists.newArrayList();
    }

    /**
     * 创建List对象
     *
     * @param <E> 泛型，
     * @param size 容器的大小
     * @return list对象
     */
    public static <E> ArrayList<E> newArrayList(int size) {
        return Lists.newArrayListWithCapacity(size);
    }

    /**
     * 获取List集合中第一个对象，前提是自己先判断这个list不会为空
     *
     * @param list
     * @param <E>
     * @return
     */
    public static <E> E firstEntity(List<E> list) {
        return list.get(BaseBizEnum.ZERRO.getCode());
    }


    /**
     *  list 转Map
     * @param values list集合对象
     * @param keyFunction function接口实现
     * @param <K> 就是你的list中需要提取的mapkey的key的类型
     * @param <V> list中的对象
     * @return 返回Map[key value]
     */
    public static  <K, V> Map<K, V> listToMap(Iterable<V> values, Function<? super V, K> keyFunction){
        return Maps.uniqueIndex(values,keyFunction);
    }
    private static <K,V> ImmutableListMultimap<K,V> immutableListMultimap(Iterable<V> values, Function<? super V, K> keyFunction){
        ImmutableListMultimap multimap =  Multimaps.index(values,keyFunction);
        return  multimap ;
    }

    /**
     * list对象 数据分组
     * @param values list 数据对象
     * @param keyFunction 分组属性 值
     * @param <K>
     * @param <V>
     * @return 分组后的map
     */
    public static <K,V> Map<K,List<V>> listToMapGroup(Iterable<V> values, Function<? super V, K> keyFunction){
        Map<K,List<V>> map = Maps.newHashMap();
        ImmutableListMultimap multimap =  Multimaps.index(values,keyFunction);
        if (multimap.isEmpty()){
            return map;
        }
        Iterator<K> iterator =  multimap.keySet().iterator();
        while (iterator.hasNext()){
            K k =  iterator.next() ;
            map.put(k,multimap.get(k));
        }
        return  map ;
    }


    /**
     * 比较两个set集合差 :得到第一个集合中有而第二个集合没有的字符串
     * @param set1 第一个集合
     * @param set2 第二个集合
     * @param <E> 泛型
     * @return 集合差
     */
    public static <E> Iterator<E> difference(  final Set<E> set1, final Set<?> set2){

        Sets.SetView res =Sets.difference( set1,set2);
        return res.iterator() ;
    }

    /**
     *  根据条件过滤指定数据集合
     * @param iterable 原始数据集合
     * @param predicate 条件接口
     * @param <E> 泛型
     * @return 过滤后的数据集合
     */
    public static <E> ArrayList<E> filter(final Iterable<E> iterable,Predicate<? super E> predicate){
        return   newArrayList(FluentIterable.from(iterable).filter(predicate));
    }

    /**
     * 把List数据对象按照指定步长分割， 多用于大批量数据保存，比如10000条数据保存，需要切分成每1000条保存一次
     * @param list 数据集合
     * @param size 大小
     * @param <E> 泛型
     * @return 大区间集合
     */
    public static <E> List<List<E>> partition(List<E> list, int size){
        return Lists.partition(list,size);
    }
    /**
     * 判断一个数组是否会空
     * @param array 数组对象
     * @return {true:不为空，false:空}
     */
    public static boolean isNotEmpty(Object[] array){
        return !ArrayUtils.isEmpty(array);
    }

    /**
     * 判断数组是否为空
     * @param array 数组
     * @return {false:不为空，true:空}
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }

    /**
     *  从一个结合中抽取某一属性的值，组成一个集合
     * @param values 数据集合
     * @param keyFunction  function接口实现
     * @param <K> 就是你的list中需要提取的属性的值
     * @param <V> 对象
     * @return 属性值集合
     */
    public static <K,V> List<K> attrValues(Iterable<V> values,Function<? super V, K> keyFunction){
        List<K> list = newArrayList();
        if(null == values){
            return list;
        }
        Iterator<V> iterator =  values.iterator();
        while (iterator.hasNext()){
            V v =  iterator.next() ;
            list.add(keyFunction.apply(v));
        }
        return list ;
    }

    /**
     * 创建List对象
     *
     * @param <E> 泛型，
     * @param elements Iterb
     * @return list对象
     */
    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
        return Lists.newArrayList(elements);
    }
}
