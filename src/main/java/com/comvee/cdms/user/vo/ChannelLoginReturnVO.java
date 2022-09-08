package com.comvee.cdms.user.vo;

import java.io.Serializable;

/**
 * @author: chenhb
 * @description: 描述
 * @data: 2021/2/2 11:22
 **/
public class ChannelLoginReturnVO implements Serializable {
    private static final long serialVersionUID = -96443930190656267L;

    private Object info;
    private String sessionId;
    //登陆类型：1代理，2销售
    private Integer loginType;
    //0成功，1未绑定(就跳转登陆界面)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }
}
