package com.comvee.cdms.member.dto;

import java.io.Serializable;
import java.util.List;

public class ListMemberWarmDTO implements Serializable {
    private String doctorIds;
    /**
     * 推送状态: 1 未推送 2 已推送
     */
    private Integer pushStatus;
    /**
     * 下发类型：1立即下发、2定时下发
     */
    private Integer pushType;
    private String startDt;
    private String endDt;
    private List<String> doctorIdList;
    private String hospitalId;

    public String getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(String doctorIds) {
        this.doctorIds = doctorIds;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public void setDoctorIdList(List<String> doctorIdList) {
        this.doctorIdList = doctorIdList;
    }

    public List<String> getDoctorIdList() {
        return doctorIdList;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
