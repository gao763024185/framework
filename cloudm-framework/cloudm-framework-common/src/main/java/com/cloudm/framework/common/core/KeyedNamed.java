package com.cloudm.framework.common.core;

/**
 * @description:  用于枚举key-value映射描述，方便统一。
 * 多用于enum 的枚举类实现这个接口，并且个enum是非异常枚举
 * 可以参考{@link com.cloudm.framework.common.enums.YnEnum}
 * @author: Courser
 * @date: 2017/4/13
 * @version: V1.0
 */
public interface KeyedNamed {
    /**
     * 状态值
     */
    int getKey();

    /**
     * 状态描述
     */
    String getName();
}
