package com.cloudm.framework.common.enums;

/**
 * @description: 业务需求的常量
 * @author: Courser
 * @date: 2017/3/21
 * @version: V1.0
 */
public enum  BaseBizEnum {
    ZERRO(0, "零"),
    FRIST(1, "一"),
    SECOND(2, "二"),
    THREE(3, "三"),
    FOUR(4, "四"),
    FIVE(5, "五"),
    SIX(6, "六"),
    SEVEN(7, "七"),
    EIGHT(8, "八"),
    NINE(9, "九"),
    TEN(10, "十"),
    OK(800,"调用成功"),
    YN_Y(BaseBizEnum.FRIST.getCode(), "已删除"),
    YN_N(BaseBizEnum.ZERRO.getCode(), "未删除"),
    DEFAULT_USER(BaseBizEnum.ZERRO.getCode(),"系统管理员"),
    SAVE_SUCCESS(BaseBizEnum.OK.getCode(),"保存成功！"),
    UPDATE_SUCCESS(BaseBizEnum.OK.getCode(),"更新成功"),
    DELETE_SUCCESS(BaseBizEnum.OK.getCode(),"删除成功");


    private int code;
    private String message;

    private BaseBizEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}