package com.comvee.cdms.member.dto;

public class ChangeHospitalBedDTO {

    private String oldSid;
    private String newSid;
    private String hospitalNo;
    private String inHospitalDate;

    public String getOldSid() {
        return oldSid;
    }

    public void setOldSid(String oldSid) {
        this.oldSid = oldSid;
    }

    public String getNewSid() {
        return newSid;
    }

    public void setNewSid(String newSid) {
        this.newSid = newSid;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }
}
