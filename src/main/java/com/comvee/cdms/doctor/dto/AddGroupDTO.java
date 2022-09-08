package com.comvee.cdms.doctor.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李左河
 * @date 2018/7/25 9:29.
 */
public class AddGroupDTO {

    /**
     * 分组名称
     */
    @NotEmpty(message = "请完善分组名称信息！")
    private String groupName;

    @NotEmpty
    private String doctorId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
