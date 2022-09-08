package com.comvee.cdms.common.wrapper;

import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/9/27
 */
public class Result implements Serializable{

    private String code;
    private String message;
    private Object obj;
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static Result ok(){
        return new Result("");
    }

    public static Result ok(Object obj){
        return new Result(obj);
    }



    public Result(){

    }

    public Result(Object obj){
        this.message = "成功";
        this.code = "0";
        this.obj = obj;
        this.success = true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Result(String code,String msg,boolean success){
        this.code = code;
        this.message = msg;
        this.obj = success;
        this.success = success;
    }
}
