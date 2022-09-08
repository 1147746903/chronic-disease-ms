/**
 * 文件名：MyExceptionHandler.java
 *
 * 版本信息：
 * 日期：2017-12-20
 * Copyright 足下 Corporation 2017
 * 版权所有
 *
 */
package com.comvee.cdms.common.exception.handler;


import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.exception.SessionTimeOutException;
import com.comvee.cdms.common.utils.RequestTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.shiro.exception.MyAuthenticationException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


/**
 *
 * @author: 苏友智
 * @time：2017-12-20 上午11:21:22
 * @version:
 */
@RestControllerAdvice
public class MyExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * 参数绑定异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException e){
        return validateExceptionHandler(e.getBindingResult());
    }

    /**
     * 参数绑定异常处理(@RequestBody + @Validated 抛出的是这个异常)
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidHandler(MethodArgumentNotValidException e){
        return validateExceptionHandler(e.getBindingResult());
    }

    /**
     * 校验异常处理
     * @param bindingResult
     * @return
     */
    private Result validateExceptionHandler(BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //只输出第一个校验异常信息
        String errorMsg = fieldErrors.get(0).getDefaultMessage();
        log.warn("参数校验错误，字段名：{}，错误信息{}" , fieldErrors.get(0).getField(), errorMsg);
        Result Result = new Result();
        Result.setCode("5");
        Result.setMessage(errorMsg);
        return Result;
    }

    /**
     * shiro 登录失败异常
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result authenticationExceptionHandler(AuthenticationException e){
        log.warn("shiro认证失败,错误内容:{}", e.getMessage());
        Result result = new Result();
        result.setCode("3");
        result.setMessage(e.getMessage());
        if(e instanceof MyAuthenticationException){
            result.setCode(((MyAuthenticationException) e).getCode());
        }
        return result;
    }

    /**
     * shiro认证失败异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Result unAuthenticateExceptionHandler(UnauthenticatedException e){
        log.warn("shiro认证失败,message:{}", e.getMessage());
        Result Result = new Result();
        Result.setCode("-1");
        Result.setMessage("无请求权限");
        return Result;
    }

    /**
     * shiro授权判断失败异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Result s(UnauthorizedException e){
        log.warn("无接口访问权限!接口:{},异常信息:{}" ,RequestTool.getRequestInterface() ,e.getMessage());
        Result result = new Result();
        result.setMessage("无访问权限");
        result.setCode("403");
        return result;
    }

    @ExceptionHandler(value = SessionTimeOutException.class)
    public Result sessionTimeOutExceptionHandler(SessionTimeOutException e){
        log.warn("session无效!错误描述:{}" ,e.getErrMsg());
        Result result = new Result();
        result.setMessage("登录超时，请重新登录!");
        result.setCode("-1");
        return result;
    }


    @ExceptionHandler(value = DeadlockLoserDataAccessException.class)
    public Result deadlockLoserDataAccessExceptionHandler(DeadlockLoserDataAccessException e){
        log.warn("事务锁异常！" + e);
        Result result = new Result();
        result.setCode("5555");
        result.setMessage("操作频繁，请稍后再试！");
        return result;
    }

    @ExceptionHandler(value = BusinessException.class)
    public Result businessExceptionHandler(BusinessException e){
        log.warn("捕获自定义异常！code:{},message:{}\n接口地址:{}", e.getCode() , e.getMessage() , RequestTool.getRequestInterface() ,e.getCause());
        Result result = new Result();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        return result;
    }

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e){
        log.error("捕获异常！\n请求接口:{}\n请求参数:{}", RequestTool.getRequestInterface(), RequestTool.getRequestInterfaceParams(), e);
        Result result = new Result();
        result.setCode("8888");
        result.setMessage("系统错误");
        return result;
    }

}
