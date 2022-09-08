package com.comvee.cdms.member.bo;

import java.io.Serializable;

public class MemberDrugItemBO implements Serializable {
    private String id;
    /**
     * 方案名称
     */
    private String name;
    /**
     * 用药方案详情
     */
    private String drugJson;
    /**
     * 1 进行中 2已过期
     */
    private String schemeStatus;
    /**
     * 开始时间
     */
    private String startDt;
    /**
     * 结束时间
     */
    private String endDt;
    /**
     * 方案用药类型  0-单纯生活方式 1-降糖药，2-降脂药，3-降压药，4-胰岛素，5-常用药（用户添加）
     */
    private String drugType;
    /**
     * 添加时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;
    /**
     * 是否有效
     */
    private String isValid;
    /**
     * 方案组编号
     */
    private String recordId;
    /**
     * 首诊id 默认为空-1
     */
    private String followId;
    /**
     * 来源 1web手动添加  2 his同步 3app添加
     */
    private String origin;
    /**
     * 备注
     */
    private String remark;
    private String doctorId;
    private String doctorName;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrugJson() {
        return drugJson;
    }

    public void setDrugJson(String drugJson) {
        this.drugJson = drugJson;
    }

    public String getSchemeStatus() {
        return schemeStatus;
    }

    public void setSchemeStatus(String schemeStatus) {
        this.schemeStatus = schemeStatus;
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }
}
