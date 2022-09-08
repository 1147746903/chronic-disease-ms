package com.comvee.cdms.sign.dto;

public class ListMembersLatestBloodSugarRecordByRecordDtDTO {

    private String startDt;
    private String endDt;
    private String memberId;
    private Integer inHos;
    private Integer paramLevel;

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getInHos() {
        return inHos;
    }

    public void setInHos(Integer inHos) {
        this.inHos = inHos;
    }

    public Integer getParamLevel() {
        return paramLevel;
    }

    public void setParamLevel(Integer paramLevel) {
        this.paramLevel = paramLevel;
    }
}
