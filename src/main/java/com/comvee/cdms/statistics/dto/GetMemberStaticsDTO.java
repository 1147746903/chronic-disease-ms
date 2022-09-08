package com.comvee.cdms.statistics.dto;


import java.util.List;

/**
 * @Author linr
 * @Date 2022/3/22
 */
public class GetMemberStaticsDTO {
    private String hospitalId;
    private String sex;
    private String startDt;
    private String endDt;
    private String lowBmi;
    private String highBmi;
    private String isDiabetes;
    private String diabetesType;
    private String essentialHyp;
    private String hypType;
    private List<String> committeeIdList;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getLowBmi() {
        return lowBmi;
    }

    public void setLowBmi(String lowBmi) {
        this.lowBmi = lowBmi;
    }

    public String getHighBmi() {
        return highBmi;
    }

    public void setHighBmi(String highBmi) {
        this.highBmi = highBmi;
    }

    public String getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(String isDiabetes) {
        this.isDiabetes = isDiabetes;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getEssentialHyp() {
        return essentialHyp;
    }

    public void setEssentialHyp(String essentialHyp) {
        this.essentialHyp = essentialHyp;
    }

    public String getHypType() {
        return hypType;
    }

    public void setHypType(String hypType) {
        this.hypType = hypType;
    }

    public List<String> getCommitteeIdList() {
        return committeeIdList;
    }

    public void setCommitteeIdList(List<String> committeeIdList) {
        this.committeeIdList = committeeIdList;
    }
}
