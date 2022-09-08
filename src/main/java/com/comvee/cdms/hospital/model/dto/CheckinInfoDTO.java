package com.comvee.cdms.hospital.model.dto;

import java.io.Serializable;

public class CheckinInfoDTO implements Serializable {
    private String sid;
    private String memberId;
    private String checkinStatus;
    private String departmentId;
    private String departmentName;
    private String roomNo;
    private String bedId;
    private String bedNo;
    private String isValid;
    private String insertDt;
    private String modifyDt;
    private String inHospitalDate;
    private String outHospitalDate;
    private String hospitalNo;
    private String patientCard;
    private String patPatientId;
    private String doctorZgCode;
    private String admNo;
    private String mainNurseDesc;
    private String doctorZg;
    private String nurLevel;
    private String eatModel;
    private String chargeClass;
    private String hospitalId;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(String checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }

    public String getOutHospitalDate() {
        return outHospitalDate;
    }

    public void setOutHospitalDate(String outHospitalDate) {
        this.outHospitalDate = outHospitalDate;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getPatPatientId() {
        return patPatientId;
    }

    public void setPatPatientId(String patPatientId) {
        this.patPatientId = patPatientId;
    }

    public String getDoctorZgCode() {
        return doctorZgCode;
    }

    public void setDoctorZgCode(String doctorZgCode) {
        this.doctorZgCode = doctorZgCode;
    }

    public String getAdmNo() {
        return admNo;
    }

    public void setAdmNo(String admNo) {
        this.admNo = admNo;
    }

    public String getMainNurseDesc() {
        return mainNurseDesc;
    }

    public void setMainNurseDesc(String mainNurseDesc) {
        this.mainNurseDesc = mainNurseDesc;
    }

    public String getDoctorZg() {
        return doctorZg;
    }

    public void setDoctorZg(String doctorZg) {
        this.doctorZg = doctorZg;
    }

    public String getNurLevel() {
        return nurLevel;
    }

    public void setNurLevel(String nurLevel) {
        this.nurLevel = nurLevel;
    }

    public String getEatModel() {
        return eatModel;
    }

    public void setEatModel(String eatModel) {
        this.eatModel = eatModel;
    }

    public String getChargeClass() {
        return chargeClass;
    }

    public void setChargeClass(String chargeClass) {
        this.chargeClass = chargeClass;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Override
    public String toString() {
        return "CheckinInfoDTO{" +
                "sid='" + sid + '\'' +
                ", memberId='" + memberId + '\'' +
                ", checkinStatus='" + checkinStatus + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", roomNo='" + roomNo + '\'' +
                ", bedId='" + bedId + '\'' +
                ", bedNo='" + bedNo + '\'' +
                ", isValid='" + isValid + '\'' +
                ", insertDt='" + insertDt + '\'' +
                ", modifyDt='" + modifyDt + '\'' +
                ", inHospitalDate='" + inHospitalDate + '\'' +
                ", outHospitalDate='" + outHospitalDate + '\'' +
                ", hospitalNo='" + hospitalNo + '\'' +
                ", patientCard='" + patientCard + '\'' +
                ", patPatientId='" + patPatientId + '\'' +
                ", doctorZgCode='" + doctorZgCode + '\'' +
                ", admNo='" + admNo + '\'' +
                ", mainNurseDesc='" + mainNurseDesc + '\'' +
                ", doctorZg='" + doctorZg + '\'' +
                ", nurLevel='" + nurLevel + '\'' +
                ", eatModel='" + eatModel + '\'' +
                ", chargeClass='" + chargeClass + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                '}';
    }
}
