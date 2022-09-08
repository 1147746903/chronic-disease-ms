package com.comvee.cdms.dialogue.dto;

/**
 * table name:  t_doctor_reply_text
 * author name: wyc
 * create time: 2019-05-23 13:52:04
 */
public class ReplyTextDTO {
    /**
     * 主键id
     */
    private String sid;
    /**
     * 文案内容
     */
    private String textContent;
    /**
     * 创建人id
     */
    private String creatorId;
    /**
     * 文案类型1:全院 2:科室 3:个人 4:系统预设
     */
    private Integer textType;
    /**
     * 医生id
     */
    private String doctorId;
    /**
     * 医院id
     */
    private String hospitalId;
    /**
     * 科室id
     */
    private String departmentId;

    /**
     * 是否有效1:有效 0:无效
     */
    private Integer isValid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getTextType() {
        return textType;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}

