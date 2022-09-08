package com.comvee.cdms.machine.model;

import javax.validation.constraints.NotEmpty;

import java.util.Date;

public class ThpMemberMachine {

    private String sid;
    @NotEmpty(message = "machineId参数不可为空")
    private String machineId;
    private String machineSn;
    private String machineCode;
    private String machineType;
    private String doctorId;
    private String memberId;
    private Byte isValid;
    private Date insertDt;
    private Date modifyDt;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
    
    public String getMachineSn() {
		return machineSn;
	}

	public void setMachineSn(String machineSn) {
		this.machineSn = machineSn;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

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

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    public Date getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(Date insertDt) {
        this.insertDt = insertDt;
    }

	public Date getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

    @Override
    public String toString() {
        return "ThpMemberMachine{" +
                "sid='" + sid + '\'' +
                ", machineId='" + machineId + '\'' +
                ", machineSn='" + machineSn + '\'' +
                ", machineCode='" + machineCode + '\'' +
                ", machineType='" + machineType + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", isValid=" + isValid +
                ", insertDt=" + insertDt +
                ", modifyDt=" + modifyDt +
                '}';
    }
}