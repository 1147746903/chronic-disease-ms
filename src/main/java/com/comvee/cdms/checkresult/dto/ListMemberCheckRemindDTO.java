package com.comvee.cdms.checkresult.dto;

import javax.validation.constraints.NotEmpty;

public class ListMemberCheckRemindDTO {

    @NotEmpty(message = "memberId 不能为空")
    private String memberId;
    private String reviewDt;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getReviewDt() {
        return reviewDt;
    }

    public void setReviewDt(String reviewDt) {
        this.reviewDt = reviewDt;
    }
}
