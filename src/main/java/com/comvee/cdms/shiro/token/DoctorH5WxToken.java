package com.comvee.cdms.shiro.token;

import com.comvee.cdms.shiro.cfg.AuthenticationType;

public class DoctorH5WxToken extends AuthenticationTypeToken {

    private String openId;

    public DoctorH5WxToken(String openId){
        this.openId = openId;
        setAuthenticationType(AuthenticationType.DOCTOR_H5_WX);
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return getOpenId();
    }

    @Override
    public Object getCredentials() {
        return "";
    }
}
