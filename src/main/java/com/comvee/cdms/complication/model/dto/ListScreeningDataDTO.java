package com.comvee.cdms.complication.model.dto;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/3/28
 */
public class ListScreeningDataDTO {

    private String patientName;
    private Integer sex;
    private Float leftVptMin;
    private Float leftVptMax;
    private Float rightVptMin;
    private Float rightVptMax;

    private Float leftAbiMin;
    private Float leftAbiMax;
    private Float rightAbiMin;
    private Float rightAbiMax;

    private Integer ageMin;
    private Integer ageMax;

    private String startDt;
    private String endDt;

    private List<String> doctorIdList;

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Float getLeftVptMin() {
        return leftVptMin;
    }

    public void setLeftVptMin(Float leftVptMin) {
        this.leftVptMin = leftVptMin;
    }

    public Float getLeftVptMax() {
        return leftVptMax;
    }

    public void setLeftVptMax(Float leftVptMax) {
        this.leftVptMax = leftVptMax;
    }

    public Float getRightVptMin() {
        return rightVptMin;
    }

    public void setRightVptMin(Float rightVptMin) {
        this.rightVptMin = rightVptMin;
    }

    public Float getRightVptMax() {
        return rightVptMax;
    }

    public void setRightVptMax(Float rightVptMax) {
        this.rightVptMax = rightVptMax;
    }

    public Float getLeftAbiMin() {
        return leftAbiMin;
    }

    public void setLeftAbiMin(Float leftAbiMin) {
        this.leftAbiMin = leftAbiMin;
    }

    public Float getLeftAbiMax() {
        return leftAbiMax;
    }

    public void setLeftAbiMax(Float leftAbiMax) {
        this.leftAbiMax = leftAbiMax;
    }

    public Float getRightAbiMin() {
        return rightAbiMin;
    }

    public void setRightAbiMin(Float rightAbiMin) {
        this.rightAbiMin = rightAbiMin;
    }

    public Float getRightAbiMax() {
        return rightAbiMax;
    }

    public void setRightAbiMax(Float rightAbiMax) {
        this.rightAbiMax = rightAbiMax;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }

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
}
