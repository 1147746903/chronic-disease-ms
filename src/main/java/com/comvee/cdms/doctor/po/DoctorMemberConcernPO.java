package com.comvee.cdms.doctor.po;

import java.util.Date;
/**
 * 
 * @author 李左河
 *
 */
public class DoctorMemberConcernPO {
    private String sid;
    private String doctorId;
    private String memberId;
    private Integer concernStatus;
    private Integer isValid;
    private Date insertDt;
    private Date modifyDt;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
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
	public Integer getConcernStatus() {
		return concernStatus;
	}
	public void setConcernStatus(Integer concernStatus) {
		this.concernStatus = concernStatus;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
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