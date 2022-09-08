package com.comvee.cdms.healthrecord.model.dto;

public class CountHealthRecordDTO {

    private String hospitalId;
    private String checkupDate;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCheckupDate() {
        return checkupDate;
    }

    public void setCheckupDate(String checkupDate) {
        this.checkupDate = checkupDate;
    }
}
