package com.cloudm.framework.common.enums;

/**
 * @description: 排序方向
 * @author: Courser
 * @date: 2017/3/23
 * @version: V1.0
 */
public enum Direction {
    /**
     * 降序列
     */
    DESC("DESC"),
    /**
     * 升序列
     */
    ASC("ASC");
    Direction(String code) {
        this.code = code;
    }
    private String code;

    public String getCode() {
        return code;
    }

}