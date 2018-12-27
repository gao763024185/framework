package com.cloudm.framework.common.enums;

import com.cloudm.framework.common.core.KeyedNamed;

/**
 * @description: 是否启用枚举
 * @author: Courser
 * @date: 2017/4/13
 * @version: V1.0
 */
public enum EnabledStateEnum implements KeyedNamed {
    ENABLED(BaseBizEnum.FRIST.getCode(), "启用"),
    NOT_ENABLED(BaseBizEnum.ZERRO.getCode(), "禁用");
    private int key;
    private String name;

    private EnabledStateEnum(int key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }
}
