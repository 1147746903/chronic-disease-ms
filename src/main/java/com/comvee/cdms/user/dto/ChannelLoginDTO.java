package com.comvee.cdms.user.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author: chenhb
 * @description: 描述
 * @data: 2021/2/2 11:27
 **/
public class ChannelLoginDTO {
    @NotBlank(message = "手机号不能为空")
    private String telPhoneNum;

    @NotBlank(message = "验证码不能为空")
    private String verificationCode;

    /**
     * 渠道小程序登陆类型：1代理登陆，2销售登陆
     */
    private Integer loginType;

    private String password;

    private String inviteCode;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getTelPhoneNum() {
        return telPhoneNum;
    }

    public void setTelPhoneNum(String telPhoneNum) {
        this.telPhoneNum = telPhoneNum;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
