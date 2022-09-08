package com.comvee.cdms.defender.model;

import java.io.Serializable;
import java.util.Date;

public class PrescriptionPlansForMemberModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -429335673375409971L;

	private String prescriptionId;
	
	private String insertDt;
	
	private String courseCnt;

	public String getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(String prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public String getInsertDt() {
		return insertDt;
	}

	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}

	public String getCourseCnt() {
		return courseCnt;
	}

	public void setCourseCnt(String courseCnt) {
		this.courseCnt = courseCnt;
	}

}
