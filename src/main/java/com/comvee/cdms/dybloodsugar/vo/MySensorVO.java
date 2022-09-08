package com.comvee.cdms.dybloodsugar.vo;

import java.io.Serializable;

public class MySensorVO implements Serializable {
    private String endDt;
    private String startDt;
    private Byte runStatus;
    private String sensorNo;
    private String sid;
    private String memberId;
    private String memberName;
    private Integer bindType;
    //使用时间
    private Integer userDays;
    //已获取的数据百分比
    private String alreadyGetData;

    public String getAlreadyGetData() {
        return alreadyGetData;
    }

    public void setAlreadyGetData(String alreadyGetData) {
        this.alreadyGetData = alreadyGetData;
    }

    public Integer getUserDays() {
        return userDays;
    }

    public void setUserDays(Integer userDays) {
        this.userDays = userDays;
    }

    public Integer getBindType() {
        return bindType;
    }

    public void setBindType(Integer bindType) {
        this.bindType = bindType;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setRunStatus(Byte runStatus) {
        this.runStatus = runStatus;
    }

    public Byte getRunStatus() {
        return runStatus;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }
}
