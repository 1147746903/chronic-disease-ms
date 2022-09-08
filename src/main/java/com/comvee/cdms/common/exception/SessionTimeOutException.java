/**    
 * 文件名：SessionTimeOutException.java    
 *    
 * 版本信息：    
 * 日期：2017-12-25    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.comvee.cdms.common.exception;

/**  
 *   
 * @author: 苏友智
 * @time：2017-12-25 下午9:15:13     
 * @version:       
 */
public class SessionTimeOutException extends RuntimeException{

    /**    
     *
     */    
    private static final long serialVersionUID = -813824355691672980L;

    public SessionTimeOutException(){}

    public SessionTimeOutException(String errMsg){
        super();
        this.errMsg = errMsg;
    }

    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
