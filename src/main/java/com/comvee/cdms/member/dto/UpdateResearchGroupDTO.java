package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

public class UpdateResearchGroupDTO {

    @NotEmpty(message = "groupId 不能为空")
    private String groupId;
    private String researchGroupName;
    private String memberIdStr;
    private String doctorId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getResearchGroupName() {
        return researchGroupName;
    }

    public void setResearchGroupName(String researchGroupName) {
        this.researchGroupName = researchGroupName;
    }

    public String getMemberIdStr() {
        return memberIdStr;
    }

    public void setMemberIdStr(String memberIdStr) {
        this.memberIdStr = memberIdStr;
    }
}
