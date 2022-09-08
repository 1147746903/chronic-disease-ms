package com.comvee.cdms.common.wrapper;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class MobileRequest implements Serializable {

    /**
     * session 授权key
     */
    private String sessionToken;
    /**
     * 设备号
     */
    private String dev;
    /**
     * 系统版本号
     */
    private String sys;
    /**
     * 设备型号
     */
    private String devType;
    /**
     * 分辨率
     */
    private String reso;
    /**
     * 客户端版本号
     */
    private String ver;
    /**
     * 推送key
     */
    private String pushTokenKey;
    /**
     * 装载渠道
     */
    private String loadFrom;
    /**
     * 用户ip地址
     */
    private String ip;


    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getReso() {
        return reso;
    }

    public void setReso(String reso) {
        this.reso = reso;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getPushTokenKey() {
        return pushTokenKey;
    }

    public void setPushTokenKey(String pushTokenKey) {
        this.pushTokenKey = pushTokenKey;
    }

    public String getLoadFrom() {
        return loadFrom;
    }

    public void setLoadFrom(String loadFrom) {
        this.loadFrom = loadFrom;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
