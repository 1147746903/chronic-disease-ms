package com.comvee.cdms.member.po;

import java.io.Serializable;

/**
 * 患者用药记录
 * @author wangxy
 * @date 2018-10-22 11：00
 */
public class MemberDrugRecordPO implements Serializable {
    private String id;
    private String insertDt; // 添加时间
    private String modifyDt; // 修改时间
    private String isValid; // 是否有效
    private String memberId; // 患者id
    private String followId; // 首诊id 默认为空-1
    private String doctorId; // 操作医生id
    private String teamId; // 团队id
    private Integer dType; //用药类型 1通用 2南京


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
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

    public Integer getdType() {
        return dType;
    }

    public void setdType(Integer dType) {
        this.dType = dType;
    }
}