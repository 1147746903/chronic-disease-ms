package com.comvee.cdms.virtualward.model.vo;

import com.comvee.cdms.virtualward.model.po.VirtualWardListPO;
import com.comvee.cdms.virtualward.support.ListBloodSugarInfoHandler;

public class VirtualWardListVO extends VirtualWardListPO implements ListBloodSugarInfoHandler {

    private String allowIntoDoctorName;
    private String bloodSugarValue;
    private Integer bloodSugarLevel;
    private String bloodSugarRecordTime;
    private String bloodSugarCode;
    private String useMachineInfo;
    private String applyIntoDoctorName;
    private Integer status; //1:未转出 2:已转出 3:直接出院转入未确认 4:直接出院转出未确认
    private String applyDoctorId; //申请转出医生id
    private String applyDoctorName; //申请医生名称

    public String getAllowIntoDoctorName() {
        return allowIntoDoctorName;
    }

    public void setAllowIntoDoctorName(String allowIntoDoctorName) {
        this.allowIntoDoctorName = allowIntoDoctorName;
    }

    public String getBloodSugarValue() {
        return bloodSugarValue;
    }

    public void setBloodSugarValue(String bloodSugarValue) {
        this.bloodSugarValue = bloodSugarValue;
    }

    public Integer getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(Integer bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }


    public String getBloodSugarRecordTime() {
        return bloodSugarRecordTime;
    }

    public void setBloodSugarRecordTime(String bloodSugarRecordTime) {
        this.bloodSugarRecordTime = bloodSugarRecordTime;
    }

    public String getBloodSugarCode() {
        return bloodSugarCode;
    }

    public void setBloodSugarCode(String bloodSugarCode) {
        this.bloodSugarCode = bloodSugarCode;
    }

    public String getUseMachineInfo() {
        return useMachineInfo;
    }

    public void setUseMachineInfo(String useMachineInfo) {
        this.useMachineInfo = useMachineInfo;
    }

    public String getApplyIntoDoctorName() {
        return applyIntoDoctorName;
    }

    public void setApplyIntoDoctorName(String applyIntoDoctorName) {
        this.applyIntoDoctorName = applyIntoDoctorName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApplyDoctorId() {
        return applyDoctorId;
    }

    public void setApplyDoctorId(String applyDoctorId) {
        this.applyDoctorId = applyDoctorId;
    }

    public String getApplyDoctorName() {
        return applyDoctorName;
    }

    public void setApplyDoctorName(String applyDoctorName) {
        this.applyDoctorName = applyDoctorName;
    }

    @Override
    public String toString() {
        return "VirtualWardListVO{" +
                "allowIntoDoctorName='" + allowIntoDoctorName + '\'' +
                ", bloodSugarValue='" + bloodSugarValue + '\'' +
                ", bloodSugarLevel=" + bloodSugarLevel +
                ", bloodSugarRecordTime='" + bloodSugarRecordTime + '\'' +
                ", bloodSugarCode='" + bloodSugarCode + '\'' +
                ", useMachineInfo='" + useMachineInfo + '\'' +
                ", applyIntoDoctorName='" + applyIntoDoctorName + '\'' +
                ", status=" + status +
                ", applyDoctorId='" + applyDoctorId + '\'' +
                '}';
    }
}
