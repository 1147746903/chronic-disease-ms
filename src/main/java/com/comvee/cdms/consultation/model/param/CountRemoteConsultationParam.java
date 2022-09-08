package com.comvee.cdms.consultation.model.param;

import java.util.List;

public class CountRemoteConsultationParam {

    private String toDepartId;
    private String fromDepartId;
    private List<Integer> consultationStatusList;
    private Integer fromRemindStatus;
    private Integer toRemindStatus;
    private Boolean fromRemindDtCheck;
    private Boolean toRemindDtCheck;

    public String getToDepartId() {
        return toDepartId;
    }

    public void setToDepartId(String toDepartId) {
        this.toDepartId = toDepartId;
    }

    public String getFromDepartId() {
        return fromDepartId;
    }

    public void setFromDepartId(String fromDepartId) {
        this.fromDepartId = fromDepartId;
    }

    public List<Integer> getConsultationStatusList() {
        return consultationStatusList;
    }

    public void setConsultationStatusList(List<Integer> consultationStatusList) {
        this.consultationStatusList = consultationStatusList;
    }

    public Integer getFromRemindStatus() {
        return fromRemindStatus;
    }

    public void setFromRemindStatus(Integer fromRemindStatus) {
        this.fromRemindStatus = fromRemindStatus;
    }

    public Integer getToRemindStatus() {
        return toRemindStatus;
    }

    public void setToRemindStatus(Integer toRemindStatus) {
        this.toRemindStatus = toRemindStatus;
    }

    public Boolean getFromRemindDtCheck() {
        return fromRemindDtCheck;
    }

    public void setFromRemindDtCheck(Boolean fromRemindDtCheck) {
        this.fromRemindDtCheck = fromRemindDtCheck;
    }

    public Boolean getToRemindDtCheck() {
        return toRemindDtCheck;
    }

    public void setToRemindDtCheck(Boolean toRemindDtCheck) {
        this.toRemindDtCheck = toRemindDtCheck;
    }
}
