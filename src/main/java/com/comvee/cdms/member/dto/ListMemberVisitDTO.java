package com.comvee.cdms.member.dto;


import java.util.List;

/**
 * @Author linr
 * @Date 2022/6/28
 */
public class ListMemberVisitDTO {


    private String doctorId;
    private String memberId;
    private String hospitalId;//就诊医院
    private String joinHospitalId;//建档医院
    private String keyword;
    private Integer attention;//是否关注
    private String begin;//建档日期开始
    private String end;//建档日期结束
    private Integer diseaseType;//疾病类型 1糖尿病 2高血压
    private String visitStartDt;//就诊日期开始
    private String visitEndDt;//就诊日期结束
    private List<String> hospitalIdList;//

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

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

    public Integer getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(Integer diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getVisitStartDt() {
        return visitStartDt;
    }

    public void setVisitStartDt(String visitStartDt) {
        this.visitStartDt = visitStartDt;
    }

    public String getVisitEndDt() {
        return visitEndDt;
    }

    public void setVisitEndDt(String visitEndDt) {
        this.visitEndDt = visitEndDt;
    }

    public List<String> getHospitalIdList() {
        return hospitalIdList;
    }

    public void setHospitalIdList(List<String> hospitalIdList) {
        this.hospitalIdList = hospitalIdList;
    }

    public String getJoinHospitalId() {
        return joinHospitalId;
    }

    public void setJoinHospitalId(String joinHospitalId) {
        this.joinHospitalId = joinHospitalId;
    }
}
