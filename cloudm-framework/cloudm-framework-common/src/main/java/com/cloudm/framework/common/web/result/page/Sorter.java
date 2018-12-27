package com.cloudm.framework.common.web.result.page;

import com.cloudm.framework.common.enums.Direction;

import java.io.Serializable;

/**
 * @description: 排序类
 * @author: Courser
 * @date: 2017/3/23
 * @version: V1.0
 */
public class Sorter implements Serializable {

    private static final long serialVersionUID = 4218565472181683024L;

    private String fieldName;

    private String direction;
    /**
     * 排序字段名
     */
    public String getFieldName() {
        return fieldName;
    }
    /**
     * 排序字段名
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    /**
     * 排序方向
     */
    public String getDirection() {
        return direction;
    }
    /**
     * 排序方向
     */
    public void setDirection(Direction direction) {
        this.direction = direction.getCode();
    }


}