package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

public class ListMemberBySmsVerifyMobilePhoneDTO {

    @NotEmpty(message = "mobilePhone 不能为空")
    private String mobilePhone;
    @NotEmpty(message = "verifyCode 不能为空")
    private String verifyCode;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
