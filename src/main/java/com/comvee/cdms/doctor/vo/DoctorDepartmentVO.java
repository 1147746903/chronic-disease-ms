package com.comvee.cdms.doctor.vo;

/**
 * @author wyc
 * @date 2020/3/4 15:22
 */
public class DoctorDepartmentVO {
    /**
     * 主键id
     */
    private String sid;

    /**
     * 用户id
     */
    private String doctorId;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 是否有效 0-无效 1有效
     */
    private Integer isValid;

    /**
     * 添加时间
     */
    private String insertDt;

    /**
     * 修改时间
     */
    private String modifyDt;

    /**
     * 操作类型 0-不可操作 1-可操作
     */
    private Integer optionType;

    private String departmentName;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public Integer getOptionType() {
        return optionType;
    }

    public void setOptionType(Integer optionType) {
        this.optionType = optionType;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
