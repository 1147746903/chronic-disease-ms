package com.comvee.cdms.shiro.token;

import com.comvee.cdms.shiro.cfg.AuthenticationType;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
public class UserNamePasswordToken extends AuthenticationTypeToken {

    private String userName;
    private String password;
    private String host;
    private Integer userType;
    private String verifyCodeId;
    private String verifyCodeValue;
    private String smsVerifyCode;

    public UserNamePasswordToken(String userName, String password, AuthenticationType authenticationType){
        this.userName = userName;
        this.password = password;
        setAuthenticationType(authenticationType);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return getUserName();
    }

    @Override
    public Object getCredentials() {
        return getPassword();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getVerifyCodeId() {
        return verifyCodeId;
    }

    public void setVerifyCodeId(String verifyCodeId) {
        this.verifyCodeId = verifyCodeId;
    }

    public String getVerifyCodeValue() {
        return verifyCodeValue;
    }

    public void setVerifyCodeValue(String verifyCodeValue) {
        this.verifyCodeValue = verifyCodeValue;
    }

    public String getSmsVerifyCode() {
        return smsVerifyCode;
    }

    public void setSmsVerifyCode(String smsVerifyCode) {
        this.smsVerifyCode = smsVerifyCode;
    }
}
