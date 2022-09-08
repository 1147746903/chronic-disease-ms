package com.comvee.cdms.member.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/8
 */
public class ListDoctorMemberApplyDTO implements Serializable{

    @NotNull
    private String doctorId;

    private String applyStatus;

    private List<String> doctorIdList;//医生编号列表

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }
}
