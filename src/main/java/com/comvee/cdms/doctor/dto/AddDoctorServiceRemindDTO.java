package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotNull;

/**
 * @author wyc
 * @date 2019/12/20 13:31
 */
public class AddDoctorServiceRemindDTO {

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 提示信息
     */
    private String remindInfo;

    /**
     * 开启状态   0 关闭 1 开启
     */
    @NotNull
    private Integer openStatus;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getRemindInfo() {
        return remindInfo;
    }

    public void setRemindInfo(String remindInfo) {
        this.remindInfo = remindInfo;
    }

    public Integer getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }
}
