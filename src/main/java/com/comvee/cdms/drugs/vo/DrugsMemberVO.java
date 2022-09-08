package com.comvee.cdms.drugs.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public class DrugsMemberVO {

    private String sid;
    private String studioId;
    private String memberId;
    private String doctorId;
    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 用药开始时间
     */
    private String startDt;
    /**
     * 用药结束时间
     */
    private String endDt;
    private String isValid;
    /**
     * 用药方案状态 1进行中 2已过期
     */
    private Integer schemeStatus;
    /**
     * 用药详情
     */
    private String drugJson;
    private String schemeName;
    private String studioName;
    /**
     * 用药列表
     */
    private List<DrugSchemeVO> drugList = new ArrayList<DrugSchemeVO>();
    /**
     * 来源 1web 2his 3app
     */
    private String origin;
    /**
     * 备注
     */
    private String remark;
    private String doctorName;

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

    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getStudioId() {
        return studioId;
    }
    public void setStudioId(String studioId) {
        this.studioId = studioId;
    }
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
    public String getInsertDt() {
        return insertDt;
    }
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
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
    public String getIsValid() {
        return isValid;
    }
    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public List<DrugSchemeVO> getDrugList() {
        return drugList;
    }
    public void setDrugList(List<DrugSchemeVO> drugList) {
        this.drugList = drugList;
    }
    public String getDrugJson() {
        return drugJson;
    }
    public void setDrugJson(String drugJson) {
        this.drugJson = drugJson;
    }
    public String getSchemeName() {
        return schemeName;
    }
    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }
    public String getStudioName() {
        return studioName;
    }
    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }
    public Integer getSchemeStatus() {
        return schemeStatus;
    }
    public void setSchemeStatus(Integer schemeStatus) {
        this.schemeStatus = schemeStatus;
    }
}