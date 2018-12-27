package com.cloudm.framework.common.enums;

import com.cloudm.framework.common.core.KeyedNamed;

/**
 * @description: 是否删除标记
 * @author: Courser
 * @date: 2017/4/13
 * @version: V1.0
 */
public enum YnEnum implements KeyedNamed {
    Y(BaseBizEnum.FRIST.getCode(),"已删除"),
    N(BaseBizEnum.ZERRO.getCode(),"未删除");
    private int key ;
    private String name ;
    private YnEnum(int key,String name){
        this.key =  key ;
        this.name = name ;
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
