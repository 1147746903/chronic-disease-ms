package com.comvee.cdms.app.doctorapp.model.app;

public class BloodNumByStatusResqModel {

	private String memberId;
	
	private String num;
	
	private String paramLevel;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getParamLevel() {
		return paramLevel;
	}

	public void setParamLevel(String paramLevel) {
		this.paramLevel = paramLevel;
	}

	@Override
	public String toString() {
		return "BloodNumByStatusResqModel{" +
				"memberId='" + memberId + '\'' +
				", num='" + num + '\'' +
				", paramLevel='" + paramLevel + '\'' +
				'}';
	}
}
