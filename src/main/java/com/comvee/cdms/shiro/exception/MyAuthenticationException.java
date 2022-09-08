package com.comvee.cdms.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

public class MyAuthenticationException extends AuthenticationException {

    private String code;

    public MyAuthenticationException(String code , String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
