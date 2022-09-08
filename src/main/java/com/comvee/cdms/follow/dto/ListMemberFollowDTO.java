package com.comvee.cdms.follow.dto;

import java.util.List;

/**
 * @author wyc
 * @date 2020/1/17 10:27
 */
public class ListMemberFollowDTO {

    private String memberId;
    private String doctorId;
    private Integer status;
    private Integer type;
    private List<Integer> typeList;
    private List<String> committeeList;
    private String startDt;
    private String endDt;
    private String diseaseType;//1糖尿病2高血压
    private String keyword;
    private String mobilePhone;
    private String xltz;//2型糖尿病 基卫高血压  心理调整
    private String zyxw;//2型糖尿病 基卫高血压  遵医行为
    private String hospitalId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Integer> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Integer> typeList) {
        this.typeList = typeList;
    }

    public List<String> getCommitteeList() {
        return committeeList;
    }

    public void setCommitteeList(List<String> committeeList) {
        this.committeeList = committeeList;
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

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getXltz() {
        return xltz;
    }

    public void setXltz(String xltz) {
        this.xltz = xltz;
    }

    public String getZyxw() {
        return zyxw;
    }

    public void setZyxw(String zyxw) {
        this.zyxw = zyxw;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
