package com.comvee.cdms.statistics.vo;

public class HospitalDataVO {
    private String sid;
    private String hospitalId;
    private String hospitalName;
    private Long memberCount;
    private Long newMemberCount;
    private Long sensorCount;
    private Long newSensorCount;
    private String firstBindTime;
    private Long whiteBoxCount;
    private Long newWhiteBoxCount;
    private Long handheldCount;
    private Long newHandheldCount;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Long memberCount) {
        this.memberCount = memberCount;
    }

    public Long getNewMemberCount() {
        return newMemberCount;
    }

    public void setNewMemberCount(Long newMemberCount) {
        this.newMemberCount = newMemberCount;
    }

    public Long getSensorCount() {
        return sensorCount;
    }

    public void setSensorCount(Long sensorCount) {
        this.sensorCount = sensorCount;
    }

    public Long getNewSensorCount() {
        return newSensorCount;
    }

    public void setNewSensorCount(Long newSensorCount) {
        this.newSensorCount = newSensorCount;
    }

    public String getFirstBindTime() {
        return firstBindTime;
    }

    public void setFirstBindTime(String firstBindTime) {
        this.firstBindTime = firstBindTime;
    }

    public Long getWhiteBoxCount() {
        return whiteBoxCount;
    }

    public void setWhiteBoxCount(Long whiteBoxCount) {
        this.whiteBoxCount = whiteBoxCount;
    }

    public Long getNewWhiteBoxCount() {
        return newWhiteBoxCount;
    }

    public void setNewWhiteBoxCount(Long newWhiteBoxCount) {
        this.newWhiteBoxCount = newWhiteBoxCount;
    }

    public Long getHandheldCount() {
        return handheldCount;
    }

    public void setHandheldCount(Long handheldCount) {
        this.handheldCount = handheldCount;
    }

    public Long getNewHandheldCount() {
        return newHandheldCount;
    }

    public void setNewHandheldCount(Long newHandheldCount) {
        this.newHandheldCount = newHandheldCount;
    }
}
