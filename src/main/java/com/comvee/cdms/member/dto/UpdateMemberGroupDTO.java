package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: suyz
 * @date: 2018/10/8
 */
public class UpdateMemberGroupDTO {

    @NotEmpty
    private String memberId;
    @NotEmpty
    private String groupId;
    @NotEmpty
    private String doctorId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
