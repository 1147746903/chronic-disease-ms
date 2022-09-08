package com.comvee.cdms.user.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/9/29
 */
public class LoginDTO implements Serializable{

    @NotBlank(message = "账号不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String lastLoginIPAddress;
    private String verifyCodeId;
    private String verifyCodeValue;
    private String smsVerifyCode;

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

    public String getLastLoginIPAddress() {
        return lastLoginIPAddress;
    }

    public void setLastLoginIPAddress(String lastLoginIPAddress) {
        this.lastLoginIPAddress = lastLoginIPAddress;
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


