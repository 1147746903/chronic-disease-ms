package com.comvee.cdms.dialogue.dto;

/**
 * @author wyc
 * @date 2019/6/24 17:06
 */
public class ListReplyTextDTO {
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
     * 是否切换医院 true为是
     */
    private Boolean switchFlag;

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
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

    public Boolean getSwitchFlag() {
        return switchFlag;
    }

    public void setSwitchFlag(Boolean switchFlag) {
        this.switchFlag = switchFlag;
    }
}
