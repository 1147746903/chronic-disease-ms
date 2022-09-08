package com.comvee.cdms.member.dto;

import java.util.List;

public class ListMemberJoinHospitalDTO {
    private String doctorId;
    private String memberId;
    private String hospitalId;
    private String keyword;
    private Integer status;
    private Integer attention;
    private String begin;
    private String end;
    private Integer essentialHyp;
    private Integer isDiabetes;
    private List<String> members;
    private List<String> hospitalIdList;
    private List<String> committeeIdList;
    private Integer diseaseType;//疾病类型 1糖尿病 2高血压
    private String mobilePhone;
    private Integer doctorLevel;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getEssentialHyp() {
        return essentialHyp;
    }

    public void setEssentialHyp(Integer essentialHyp) {
        this.essentialHyp = essentialHyp;
    }

    public Integer getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(Integer isDiabetes) {
        this.isDiabetes = isDiabetes;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getHospitalIdList() {
        return hospitalIdList;
    }

    public void setHospitalIdList(List<String> hospitalIdList) {
        this.hospitalIdList = hospitalIdList;
    }

    public Integer getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(Integer diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(Integer doctorLevel) {
        this.doctorLevel = doctorLevel;
    }

    public List<String> getCommitteeIdList() {
        return committeeIdList;
    }

    public void setCommitteeIdList(List<String> committeeIdList) {
        this.committeeIdList = committeeIdList;
    }
}
