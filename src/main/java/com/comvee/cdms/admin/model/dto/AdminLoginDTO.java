package com.comvee.cdms.admin.model.dto;

import javax.validation.constraints.NotEmpty;

public class AdminLoginDTO {

    @NotEmpty(message = "accountNo 不能为空")
    private String accountNo;
    @NotEmpty(message = "password 不能为空")
    private String password;
    @NotEmpty(message = "verifyCodeId 不能为空")
    private String verifyCodeId;
    @NotEmpty(message = "verifyCodeValue 不能为空")
    private String verifyCodeValue;
    private String smsVerifyCode;

    private String loginIp;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
