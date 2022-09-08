package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李左河
 * @date 2018/7/25 9:29.
 */
public class InsertDoctorGroupDTO {

    /**
     * 分组名称
     */
    @NotEmpty(message = "请完善分组名称信息！")
    private String groupName;

    /**
     * 患者id字符串
     */
    private String memberIds;

    /**
     * 医生id
     */
    private String doctorId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String memberIds) {
        this.memberIds = memberIds;
    }

    @Override
    public String toString() {
        return "InsertDoctorGroupDTO{}";
    }
}
