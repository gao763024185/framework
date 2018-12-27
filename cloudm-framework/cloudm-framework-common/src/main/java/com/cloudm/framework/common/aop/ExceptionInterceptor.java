package com.cloudm.framework.common.aop;

import com.cloudm.framework.common.enums.BaseErrorEnum;
import com.cloudm.framework.common.util.StringUtil;
import com.cloudm.framework.common.web.result.base.BaseResult;
import com.cloudm.framework.common.web.result.base.ServiceError;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.objenesis.ObjenesisStd;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackson on 2017/6/8.
 */
@Component
@Slf4j
public class ExceptionInterceptor {
    private ObjenesisStd generator = new ObjenesisStd();


    /**
     * 异常处理 记录日志并返回result对象
     * @param invocation
     * @param serviceError  {@link BaseErrorEnum}
     * @param e
     * @return
     */
    public BaseResult exceptionProcessor(MethodInvocation invocation, Throwable e,Integer errorCode,String message,ServiceError serviceError) {
        Object[] args = invocation.getArguments();
        List<Object> list =  new ArrayList();

        for (Object arg:args){
            if (arg instanceof BindingResult ||arg instanceof ServletResponse || arg instanceof ServletRequest || arg instanceof Servlet){
                continue;
            }
            list.add(arg);
        }

        Method method = invocation.getMethod();
        String methodName = method.getDeclaringClass().getName() + "." + method.getName();
        if (!list.isEmpty())
            log.error("服务[method=" + methodName + "] params={}" + new Gson().toJson(list) + "异常：", e);
        else
            log.error("服务[method=" + methodName + "] params={}" + new Gson().toJson(null) + "异常：", e);
        BaseResult result = getBaseResult(invocation);
        result.setCode(errorCode!=null?errorCode:serviceError.getCode());
        result.setMessage(StringUtil.isNotEmpty(message)?message:serviceError.getMessage());
        result.setSuccess(false);
        return result;
    }
    public BaseResult exceptionProcessor(MethodInvocation invocation, Throwable e,ServiceError serviceError){
        return exceptionProcessor(invocation,e,null,null,serviceError);
    }

    /**
     * 反射获取返回值对象
     * @param methodInvocation 目标方法
     * @return
     */

    private BaseResult getBaseResult(MethodInvocation methodInvocation) {
        Class<?> returnType = methodInvocation.getMethod().getReturnType();
        BaseResult result = (BaseResult) generator.newInstance(returnType);
        return result;
    }
}
