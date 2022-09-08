package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

public class AddResearchGroupDTO {

    @NotEmpty(message = "researchGroupName 不能为空")
    private String researchGroupName;
    private String memberIdStr;

    /**
     * 以下参数系统填充
     */
    private String doctorId;
    private String hospitalId;

    public String getResearchGroupName() {
        return researchGroupName;
    }

    public void setResearchGroupName(String researchGroupName) {
        this.researchGroupName = researchGroupName;
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

    public String getMemberIdStr() {
        return memberIdStr;
    }

    public void setMemberIdStr(String memberIdStr) {
        this.memberIdStr = memberIdStr;
    }
}
