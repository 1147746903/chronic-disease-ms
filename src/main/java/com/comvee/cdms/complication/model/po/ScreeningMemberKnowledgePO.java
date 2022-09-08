package com.comvee.cdms.complication.model.po;

public class ScreeningMemberKnowledgePO {
    private String sid;

    private String memberId;

    private String insertDt;

    private String updateDt;

    private Integer lastSerialNumber;

    private Integer finishStatus;


    public Integer getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(Integer finishStatus) {
        this.finishStatus = finishStatus;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt == null ? null : updateDt.trim();
    }

    public Integer getLastSerialNumber() {
        return lastSerialNumber;
    }

    public void setLastSerialNumber(Integer lastSerialNumber) {
        this.lastSerialNumber = lastSerialNumber;
    }
}