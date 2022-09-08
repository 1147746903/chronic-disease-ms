/**    
 * 文件名：MemberSugarCodeNumberPO.java    
 *    
 * 版本信息：    
 * 日期：2018-1-13    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.comvee.cdms.bloodmonitor.po;

import java.io.Serializable;

/**  
 *   
 * @author: 苏友智
 * @time：2018-1-13 下午1:46:44     
 * @version:       
 */
public class MemberSugarCodeNumberPO implements Serializable{
    
    /**    
     *
     */    
    private static final long serialVersionUID = 5494936690892482969L;
    
    private String paramCode;
    private Long number;
    
    public String getParamCode() {
        return paramCode;
    }
    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }
    public Long getNumber() {
        return number;
    }
    public void setNumber(Long number) {
        this.number = number;
    }
    
    
}
