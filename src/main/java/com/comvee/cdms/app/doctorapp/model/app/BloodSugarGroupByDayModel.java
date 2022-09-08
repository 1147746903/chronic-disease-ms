package com.comvee.cdms.app.doctorapp.model.app;

import java.util.List;

public class BloodSugarGroupByDayModel {

	private String day;
	private List<BloodSugarModel> bloodSugarList;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public List<BloodSugarModel> getBloodSugarList() {
		return bloodSugarList;
	}

	public void setBloodSugarList(List<BloodSugarModel> bloodSugarList) {
		this.bloodSugarList = bloodSugarList;
	}


	@Override
	public String toString() {
		return "BloodSugarGroupByDayModel{" +
				"day='" + day + '\'' +
				", bloodSugarList=" + bloodSugarList +
				'}';
	}
}
