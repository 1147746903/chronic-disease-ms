package com.comvee.cdms.checkresult.dto;

import java.util.List;

public class ListMemberNewestCheckoutDetailDTO {

    private String memberId;
    private List<String> checkoutCodeList;
    private String endCheckoutDt;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<String> getCheckoutCodeList() {
        return checkoutCodeList;
    }

    public void setCheckoutCodeList(List<String> checkoutCodeList) {
        this.checkoutCodeList = checkoutCodeList;
    }

    public String getEndCheckoutDt() {
        return endCheckoutDt;
    }

    public void setEndCheckoutDt(String endCheckoutDt) {
        this.endCheckoutDt = endCheckoutDt;
    }
}
