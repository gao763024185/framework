package com.cloudm.framework.common.enums;

import com.cloudm.framework.common.web.result.base.ServiceError;

/**
 * @description: 基础的异常异常枚举，用于基层封装从底层抛出捕获
 * @author: Courser
 * @date: 2017/3/20
 * @version: V1.0
 */
public enum BaseErrorEnum implements ServiceError {
    VALIDATE_ERROR(-10,"认证失败"),
    /**
     * 用于有和数据库交互更新，一般在manager层出现
     */
    BNS_PRS_ERROR(-4,"业务处理异常"),
    /**
     * 一般用于service，用于用户逻辑判断
     */
    BNS_CHK_ERROR(-3,"业务校验异常"),
    SYS_ERROR(-2,"系统异常"),
    SAVE_ERROR(-5,"保存失败"),
    UPDATE_ERROR(-6,"更新失败"),
    PARAM_FAILD(-7,"参数异常"),
    PARAM_ERROR(BaseErrorEnum.PARAM_FAILD.getCode(),BaseErrorEnum.PARAM_FAILD.getMessage()),
    DATA_NOT_EXISTS(-8,"数据不存在"),
    RPC_ERROR(-9,"远程调用异常"),
    UNKNOWN_ERROR(-1,"未知异常");

    private final int code;
    private final String message ;

    BaseErrorEnum(int code ,String message){
        this.code = code;
        this.message = message ;
    }

    /**
     * 错误码
     *
     * @return 返回错误码
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * 返回错误信息
     *
     * @return 错误信息
     */
    @Override
    public String getMessage() {
        return message;
    }
}
