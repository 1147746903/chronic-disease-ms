package com.comvee.cdms.member.vo;

/**
 * @Author linr
 * @Date 2021/8/6
 */
public class ListMemberBloodStaticsVO {
    private String memberId;
    private String memberName;
    private String departmentName;
    private String doctorName;
    private String monitorNum;//监测次数
    private String highSugarNum;//高血糖次数
    private String lowSugarNum;//低血糖次数
    private String normalSugarNum;//正常血糖次数
    private String rate;//达标率
    private String memberNum;//患者人数

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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMonitorNum() {
        return monitorNum;
    }

    public void setMonitorNum(String monitorNum) {
        this.monitorNum = monitorNum;
    }

    public String getHighSugarNum() {
        return highSugarNum;
    }

    public void setHighSugarNum(String highSugarNum) {
        this.highSugarNum = highSugarNum;
    }

    public String getLowSugarNum() {
        return lowSugarNum;
    }

    public void setLowSugarNum(String lowSugarNum) {
        this.lowSugarNum = lowSugarNum;
    }

    public String getNormalSugarNum() {
        return normalSugarNum;
    }

    public void setNormalSugarNum(String normalSugarNum) {
        this.normalSugarNum = normalSugarNum;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(String memberNum) {
        this.memberNum = memberNum;
    }
}
