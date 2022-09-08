package com.comvee.cdms.prescription.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PrescriptionSportOutVO implements Serializable {

    /**
     *  
     */
    private List<Map<String, Object>> sportOutList;
    /**
     *  
     */
    private String sportRule;
    
    private String sportSuggestText;

	/**
	 * 运动强度 1 低强度运动 2 中强度运动
	 */
	private Integer sportType;

	/**
	 * 心率
	 */
	private String heartRate;

	public Integer getSportType() {
		return sportType;
	}

	public void setSportType(Integer sportType) {
		this.sportType = sportType;
	}

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public List<Map<String, Object>> getSportOutList() {
		return sportOutList;
	}
	public void setSportOutList(List<Map<String, Object>> sportOutList) {
		this.sportOutList = sportOutList;
	}
	public String getSportRule() {
		return sportRule;
	}
	public void setSportRule(String sportRule) {
		this.sportRule = sportRule;
	}
	public String getSportSuggestText() {
		return sportSuggestText;
	}
	public void setSportSuggestText(String sportSuggestText) {
		this.sportSuggestText = sportSuggestText;
	}

     
 
}
