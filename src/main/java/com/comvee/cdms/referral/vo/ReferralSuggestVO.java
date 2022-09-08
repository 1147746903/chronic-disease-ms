package com.comvee.cdms.referral.vo;

import java.io.Serializable;

public class ReferralSuggestVO implements Serializable {

    private String sid;
    private String reason;
    private String memberId;
    private String memberName;
    private String mobileNo;
    private Integer status;
    /**
     * 建议转诊原因类型 1001 上转 原因a 1002上转原因b 1003上转原因c 1004上转原因d
     * 1005上转原因e 1006上转原因f 1007上转原因g 2001下转原因a
     * (规则 1开头为上转 00#为原因编号  2开头为下转 00#为原因编号)
     */
    private Integer type;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
