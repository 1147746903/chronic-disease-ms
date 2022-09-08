package com.comvee.cdms.prescription.dto;

import java.io.Serializable;
import java.util.List;

public class GetMemberPerDTO implements Serializable {
    private String memberName;
    private String memberNamePy;
    private String mobilePhone;
    private List<String> memberIds;
    private String memberId;

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberNamePy(String memberNamePy) {
        this.memberNamePy = memberNamePy;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberNamePy() {
        return memberNamePy;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
    }

    public List<String> getMemberIds() {
        return memberIds;
    }

    @Override
    public String toString() {
        return "GetMemberPerDTO{" +
                "memberName='" + memberName + '\'' +
                ", memberNamePy='" + memberNamePy + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", memberIds=" + memberIds +
                '}';
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }
}
