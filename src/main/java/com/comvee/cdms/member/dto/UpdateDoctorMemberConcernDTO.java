package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: suyz
 * @date: 2018/10/15
 */
public class UpdateDoctorMemberConcernDTO implements Serializable{

    @NotEmpty
    private String doctorId;
    @NotEmpty
    private String memberId;
    @NotNull
    private Integer concernStatus;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getConcernStatus() {
        return concernStatus;
    }

    public void setConcernStatus(Integer concernStatus) {
        this.concernStatus = concernStatus;
    }
}
