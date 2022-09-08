/**    
 * 文件名：GlucometerRequest.java    
 *    
 * 版本信息：    
 * 日期：2018-1-12    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.comvee.cdms.glu.model;

import java.io.Serializable;

/**  
 *   
 * @author: 苏友智
 * @time：2018-1-12 上午9:08:40     
 * @version:       
 */
public class GlucometerRequest implements Serializable{
    
    /**    
     *
     */    
    private static final long serialVersionUID = 5152058958302463916L;
    
    /**
     * 版本号
     */
    private String verNo;
    /**
     * 设备号
     */
    private String machineNo;
    /**
     * 产品序列号
     */
    private String sn;
    /**
     * session票
     */
    private String sessionToken;
    /**
     * app类型 1 血糖仪 2 血压计
     */
    private Integer appType;
    
    public String getVerNo() {
        return verNo;
    }
    public void setVerNo(String verNo) {
        this.verNo = verNo;
    }
    public String getMachineNo() {
        return machineNo;
    }
    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }
    public String getSn() {
        return sn;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }
    public String getSessionToken() {
        return sessionToken;
    }
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    public Integer getAppType() {
        return appType;
    }
    public void setAppType(Integer appType) {
        this.appType = appType;
    }
    
    
    
    
}
