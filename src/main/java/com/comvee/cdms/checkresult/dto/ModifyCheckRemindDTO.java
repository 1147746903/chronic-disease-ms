package com.comvee.cdms.checkresult.dto;

import javax.validation.constraints.NotEmpty;

public class ModifyCheckRemindDTO {

    @NotEmpty(message = "主键不能为空")
    private String sid;
    //@NotEmpty(message = "reviewDt 不能为空")
    private String reviewDt;
    private String isIgnore;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getReviewDt() {
        return reviewDt;
    }

    public void setReviewDt(String reviewDt) {
        this.reviewDt = reviewDt;
    }

    public String getIsIgnore() {
        return isIgnore;
    }

    public void setIsIgnore(String isIgnore) {
        this.isIgnore = isIgnore;
    }
}
