package com.cloudm.framework.common.ex;
import com.cloudm.framework.common.web.result.base.ServiceError;
/**
 * @description: 业务校验异常
 * @author: Courser
 * @date: 2017/3/17
 * @version: V1.0
 * @see BusinessProcessFailException
 */
public class BusinessCheckFailException extends RuntimeException {

    private static final long serialVersionUID = -346427066798778452L;
    private final int errorCode;

    public BusinessCheckFailException(final ServiceError errors) {
        super(errors.getMessage());
        this.errorCode = errors.getCode();
    }

    public BusinessCheckFailException(final Integer errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}