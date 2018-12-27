package com.cloudm.framework.common.enums;


/**
 * @description: redis数据结构
 * @author: Courser
 * @date: 2017/6/29
 * @version: V1.0
 */
public enum  RedisStructureEnum {
    STR("str","字符串结构"),
    LIST("list","list结构"),
    HASH("hash","map结构"),
    ZSET("zset","zset数据结构"),
    SET("set","set数据结构")
    ;

    private String key ;
    private String description ;
    private RedisStructureEnum(String key,String description){
        this.key = key;
        this.description= description;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
