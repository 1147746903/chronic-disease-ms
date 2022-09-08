package com.comvee.cdms.virtualward.model.vo;

import com.comvee.cdms.virtualward.model.po.InHospitalPatientListPO;
import com.comvee.cdms.virtualward.support.ListBloodSugarInfoHandler;

public class InHospitalPatientListVO extends InHospitalPatientListPO implements ListBloodSugarInfoHandler {

    private String bloodSugarValue;
    private Integer bloodSugarLevel;
    private String bloodSugarRecordTime;
    private String bloodSugarCode;

    private String virtualWardId;
    private Integer virtualWardStatus;
    private String applyDoctorName;

    public String getApplyDoctorName() {
        return applyDoctorName;
    }

    public void setApplyDoctorName(String applyDoctorName) {
        this.applyDoctorName = applyDoctorName;
    }

    public String getVirtualWardId() {
        return virtualWardId;
    }

    public void setVirtualWardId(String virtualWardId) {
        this.virtualWardId = virtualWardId;
    }

    public Integer getVirtualWardStatus() {
        return virtualWardStatus;
    }

    public void setVirtualWardStatus(Integer virtualWardStatus) {
        this.virtualWardStatus = virtualWardStatus;
    }

    public String getBloodSugarValue() {
        return bloodSugarValue;
    }

    @Override
    public void setBloodSugarValue(String bloodSugarValue) {
        this.bloodSugarValue = bloodSugarValue;
    }

    public Integer getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    @Override
    public void setBloodSugarLevel(Integer bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public String getBloodSugarRecordTime() {
        return bloodSugarRecordTime;
    }

    @Override
    public void setBloodSugarRecordTime(String bloodSugarRecordTime) {
        this.bloodSugarRecordTime = bloodSugarRecordTime;
    }

    public String getBloodSugarCode() {
        return bloodSugarCode;
    }

    @Override
    public void setBloodSugarCode(String bloodSugarCode) {
        this.bloodSugarCode = bloodSugarCode;
    }
}
