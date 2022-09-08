package com.comvee.cdms.common.exception;

/**
 * @author: suyz
 * @date: 2018/9/27
 */
public class BusinessException extends RuntimeException{

    private String code;
    private String message;

    public BusinessException(String code, String message){
        super();
        this.code = code;
        this.message = message;
    }

    public BusinessException(String code, String message,Throwable e){
        super(e);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message,Throwable e){
        super(e);
        this.code = code;
        this.message = message;
    }


    public BusinessException(String message){
        super(message);
        this.code = "1";
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
