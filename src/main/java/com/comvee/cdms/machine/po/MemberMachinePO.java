package com.comvee.cdms.machine.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author landd
 *
 */
public class MemberMachinePO implements Serializable {
   
    private static final long serialVersionUID = -2422192221328893428L;
    
    private Long sid;
    private String machineId;
    private String machineSn;
    private String machineType;
    private Long doctorId;
    private Long memberId;
    private Byte isValid;
    private Date insertDt;
    private Date modifyDt;
    
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
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
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
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

}
