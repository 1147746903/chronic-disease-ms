package com.comvee.cdms.shiro.token;

import com.comvee.cdms.shiro.cfg.AuthenticationType;

public class DoctorHisToken extends AuthenticationTypeToken{

    private String doctorId;

    public DoctorHisToken(String doctorId){
        this.doctorId = doctorId;
        setAuthenticationType(AuthenticationType.DOCTOR_HIS);
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
        return getDoctorId();
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
