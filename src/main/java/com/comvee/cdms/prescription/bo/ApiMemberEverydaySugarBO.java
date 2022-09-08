package com.comvee.cdms.prescription.bo;

import java.io.Serializable;

public class ApiMemberEverydaySugarBO implements Serializable {
    private String sid;

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 插入时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String modifyDt;

    /**
     * 是否有效：0无效，1有效
     */
    private Integer isValid;

    /**
     * 监测日期
     */
    private String paramDt;

    /**
     * 偏高率
     */
    private String highRate;

    /**
     * 偏低率
     */
    private String lowRate;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 血糖情况 3正常。大于3偏高，小于3偏低
     */
    private Integer level;

    /**
     * 血糖记录类型，1住院(住院血糖) 2院外(日常血糖)
     */
    private Integer paramType;

    /**
     * 血糖值
     */
    private String paramValueJson;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getParamDt() {
        return paramDt;
    }

    public void setParamDt(String paramDt) {
        this.paramDt = paramDt;
    }

    public String getHighRate() {
        return highRate;
    }

    public void setHighRate(String highRate) {
        this.highRate = highRate;
    }

    public String getLowRate() {
        return lowRate;
    }

    public void setLowRate(String lowRate) {
        this.lowRate = lowRate;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public String getParamValueJson() {
        return paramValueJson;
    }

    public void setParamValueJson(String paramValueJson) {
        this.paramValueJson = paramValueJson;
    }
}
