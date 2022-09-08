package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

public class ListMemberByWxMobilePhoneDTO {

    @NotEmpty
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
