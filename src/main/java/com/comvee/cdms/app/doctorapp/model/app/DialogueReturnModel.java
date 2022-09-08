package com.comvee.cdms.app.doctorapp.model.app;

public class DialogueReturnModel {
	private static final long serialVersionUID = 6415711905789309525L;
	

	private Long sid;
	private String returnDate;
	
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
}
