package com.comvee.cdms.bloodmonitor.vo;

public class TodayTaskDetailListVO {

    private String memberId;
    private String memberName;
    private String bedNo;
    private Integer sex;
    private String latestBloodSugar;
    private String paramCode;

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
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

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLatestBloodSugar() {
        return latestBloodSugar;
    }

    public void setLatestBloodSugar(String latestBloodSugar) {
        this.latestBloodSugar = latestBloodSugar;
    }
}
