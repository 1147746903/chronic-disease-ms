package com.comvee.cdms.sign.vo;

public class BloodMemberVO {
    private String memberId;
    private String recordDt;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    @Override
    public String toString() {
        return "BloodMemberVO{" +
                "memberId='" + memberId + '\'' +
                ", recordDt='" + recordDt + '\'' +
                '}';
    }

}
