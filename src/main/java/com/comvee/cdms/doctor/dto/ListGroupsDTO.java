package com.comvee.cdms.doctor.dto;

public class ListGroupsDTO {

    private String doctorId;
    private String keyWord;
    private String entityType;
    private String hospitalId;
    /**
     * 是否需要统计患者数量
     */
    private boolean countPeopleNumber = false;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public boolean isCountPeopleNumber() {
        return countPeopleNumber;
    }

    public void setCountPeopleNumber(boolean countPeopleNumber) {
        this.countPeopleNumber = countPeopleNumber;
    }
}
