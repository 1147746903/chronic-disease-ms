package com.comvee.cdms.checkresult.dto;

public class GetNewestCheckoutDetailDTO {

    private String memberId;
    private String checkoutCode;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCheckoutCode() {
        return checkoutCode;
    }

    public void setCheckoutCode(String checkoutCode) {
        this.checkoutCode = checkoutCode;
    }
}
