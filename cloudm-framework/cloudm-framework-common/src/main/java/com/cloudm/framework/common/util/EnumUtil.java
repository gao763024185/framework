package com.cloudm.framework.common.util;

import com.cloudm.framework.common.core.KeyedNamed;

import java.util.*;

/**
 * @description:  枚举工具类，主要用来根据状态的key获取对应的name以供显示。
 * 使用场景：1.返回给客户端的状态不要在前端转换，在后台直接可以转换掉，让前台直接展示
 * 2.下来框的key-value映射
 * @author: Courser
 * @date: 2017/4/13
 * @version: V1.0
 */
public class EnumUtil {
    private EnumUtil() {
    }

    /**
     * 根据key找到对应的Enum值
     */
    public static <T extends Enum<T> & KeyedNamed> T getEnum(int key, Class<T> clz) {
        T[] values = clz.getEnumConstants();
        for (T value : values) {
            if (value.getKey() == key) {
                return value;
            }
        }
        return null;
    }

    /**
     * 根据key找到对应的name值
     */
    public static <T extends Enum<T> & KeyedNamed> String getName(int key, Class<T> clz) {
        T t = getEnum(key, clz);
        if (t != null) {
            return t.getName();
        }
        return null;
    }

    /**
     * 获取key值列表
     *
     * @param clz enum类
     * @param <T>
     * @return
     */
    public static <T extends Enum<T> & KeyedNamed> List<Integer> getKeys(Class<T> clz) {
        List<Integer> keys = new LinkedList<>();
        T[] values = clz.getEnumConstants();
        for (T value : values) {
            keys.add(value.getKey());
        }
        return keys;
    }

    /**
     * 获取value值列表
     *
     * @param clz enum类
     * @param <T>
     * @return
     */
    public static <T extends Enum<T> & KeyedNamed> List<Integer> getValues(Class<T> clz) {
        List<Integer> keys = new LinkedList<>();
        T[] values = clz.getEnumConstants();
        for (T value : values) {
            keys.add(value.getKey());
        }
        return keys;
    }

    /**
     * 获取enum列表
     *
     * @param clz enum类
     * @param <T>
     * @return
     */
    public static <T extends Enum<T> & KeyedNamed> List<T> getEnumList(Class<T> clz) {
        return new ArrayList<>(Arrays.asList(clz.getEnumConstants()));
    }

    /**
     * @param key     要判断的值
     * @param enumObj 要比较的枚举
     * @param <T>
     * @return 如果key等于enumObj的key值则返回true
     */
    public static <T extends Enum<T> & KeyedNamed> boolean keyEquals(Integer key, T enumObj) {
        return key != null && key == enumObj.getKey();
    }

    /**
     * @param key   要判断的值
     * @param enums 要判断的枚举
     * @return 如果key等于e1的key或者在enums的key值列表里则返回true
     */
    public static <T extends Enum<T> & KeyedNamed> boolean keyIn(Integer key, T... enums) {
        if (key == null) {
            return false;
        }
        for (T enumObj : enums) {
            if (key == enumObj.getKey()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param key   要判断的值
     * @param enums 要判断的枚举
     * @param <T>
     * @return 如果key在enums的key值列表里则返回true
     */
    public static <T extends Enum<T> & KeyedNamed> boolean keyIn(Integer key, Collection<T> enums) {
        if (key == null) {
            return false;
        }
        for (T enumObj : enums) {
            if (key == enumObj.getKey()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回name列表
     *
     * @param enums 枚举集合
     * @param <T>
     * @return 返回name列表
     */
    public static <T extends Enum<T> & KeyedNamed> List<String> getNames(Collection<T> enums) {
        List<String> list = new ArrayList<>();
        for (T enumObj : enums) {
            list.add(enumObj.getName());
        }
        return list;
    }

    /**
     * 返回key列表
     *
     * @param enums
     * @param <T>
     * @return 返回key列表
     */
    public static <T extends Enum<T> & KeyedNamed> List<Integer> getKeys(T... enums) {
        List<Integer> list = new ArrayList<>();
        for (T enumObj : enums) {
            list.add(enumObj.getKey());
        }
        return list;
    }

    /**
     * 返回key列表
     *
     * @param enums 枚举集合
     * @param <T>
     * @return 返回key列表
     */
    public static <T extends Enum<T> & KeyedNamed> List<Integer> getKeys(Collection<T> enums) {
        List<Integer> list = new ArrayList<>();
        for (T enumObj : enums) {
            list.add(enumObj.getKey());
        }
        return list;
    }
}
