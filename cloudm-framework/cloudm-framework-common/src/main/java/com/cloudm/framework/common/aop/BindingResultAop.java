package com.cloudm.framework.common.aop;

import com.cloudm.framework.common.enums.BaseErrorEnum;
import com.cloudm.framework.common.util.StringUtil;
import com.cloudm.framework.common.web.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @description: 主要针对于controller 方法验证。
 * 使用方法： 参数中添加@Vaild注解与BindingResult。然后自动验证。
 * 如果验证失败：然后json字符串 {result:false,message:'java bean中验证message'}
 * @author: Courser
 * @date: 2017/3/20
 * @version: V1.0
 */
@Slf4j
public class BindingResultAop {
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        BindingResult bindingResult = null;
        for(Object arg:joinPoint.getArgs()){
            if(arg instanceof BindingResult){
                bindingResult = (BindingResult) arg;
            }
        }

        if(bindingResult != null){
            List<ObjectError> errors = bindingResult.getAllErrors();
            if(errors.size()>0){
                StringBuilder msg = new StringBuilder();
                for(ObjectError error :errors){
                    msg.append(error.getDefaultMessage());

                    msg.append(";");
                }
                if (StringUtil.isEmpty(msg.toString())){
                    msg.append(BaseErrorEnum.VALIDATE_ERROR.getMessage()+";");
                }
//                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//                Method method = signature.getMethod();
                log.error(joinPoint.getTarget().getClass().getName()+"#"+joinPoint.getSignature().getName() +" is error ==>{}",msg.toString());
               return Result.wrapErrorResult(BaseErrorEnum.VALIDATE_ERROR.getCode(),msg.toString().substring(0,msg.toString().length()-1));
            }
        }
        return joinPoint.proceed();
    }
}
