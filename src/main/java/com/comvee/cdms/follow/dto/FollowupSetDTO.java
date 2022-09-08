package com.comvee.cdms.follow.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author wyc
 * @date 2019/12/20 13:21
 */
public class FollowupSetDTO {

    /**
     * 患者id
     */
    @NotEmpty
    private String memberId;

    /**
     *医生id
     */
    @NotEmpty
    private String doctorId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
