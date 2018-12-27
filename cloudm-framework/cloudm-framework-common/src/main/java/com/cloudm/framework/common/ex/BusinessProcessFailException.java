package com.cloudm.framework.common.ex;

import com.cloudm.framework.common.enums.BaseErrorEnum;
import com.cloudm.framework.common.web.result.base.ServiceError;

/**
 * @description: 业务运行时异常
 * <ul>
 *     <li>比如说，写入数据库后判断返回条数，不符合预期就用此异常</li>
 *     <li>如果是在业务层做业务判断请参考{@link BusinessCheckFailException} </li>
 * <ul/>
 * @author: Courser
 * @date: 2017/3/17
 * @version: V1.0
 */
public class BusinessProcessFailException extends RuntimeException{
    private static final long serialVersionUID = -5649952725445092524L;
    /**
     * 错误码
     */
    private final Integer errorCode;

    /**
     * 通过ServiceError接口构造
     * @param errors
     */
    public BusinessProcessFailException(final ServiceError errors) {
        super(errors.getMessage());
        this.errorCode = errors.getCode();
    }
    /**
     * 通过错误信息接口构造
     * @param message
     */
    public BusinessProcessFailException(final String message) {
        super(message);
        this.errorCode = BaseErrorEnum.BNS_PRS_ERROR.getCode();
    }


    /**
     * 用错误码和错误信息构造
     * @param message 错误信息
     * @param errorCode 错误编码
     */
    public BusinessProcessFailException(final String message , final Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 用错误码 异常 错误信息一起构造
     * @param message
     * @param cause
     * @param errorCode
     */
    public BusinessProcessFailException(final String message, final Throwable cause, final Integer errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * 异常和错误码构造
     * @param cause
     * @param errorCode
     */
    public BusinessProcessFailException(final Throwable cause, final Integer errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    /**
     * 错误码构造
     * @param errorCode
     */
    public BusinessProcessFailException(final Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
