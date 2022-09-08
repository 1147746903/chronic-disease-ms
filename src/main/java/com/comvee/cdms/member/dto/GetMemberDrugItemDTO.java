package com.comvee.cdms.member.dto;

import java.io.Serializable;

/**
 * @author: wangxy
 * @date: 2018/10/23
 */
public class GetMemberDrugItemDTO implements Serializable{

    private String recordId;
    private String memberId;
    private String followId;
    private String doctorId; // 操作医生id
    private String teamId; // 团队id
    private String id;// 用药方案id

    private String startDt;
    private String endDt;

    private Integer dType; //用药记录来源1 通用 2南京鼓楼
    private String hospitalId;
    private String origin;
    private String hasDrugType;
    private String cacheKey;

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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getdType() {
        return dType;
    }

    public void setdType(Integer dType) {
        this.dType = dType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setHasDrugType(String hasDrugType) {
        this.hasDrugType = hasDrugType;
    }

    public String getHasDrugType() {
        return hasDrugType;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getCacheKey() {
        return cacheKey;
    }
}
