package com.comvee.cdms.prescription.vo;

import java.io.Serializable;

public class PrescriptionForSportVO implements Serializable {

    /**
     * 模块主键
     */
    private String sid;
    /**
     * 运动治疗信息
     */
    private String sportInfo;
    /**
     * 保存状态 0未保存，1保存
     */
    private Integer saveState = 0;
    

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

	public String getSportInfo() {
		return sportInfo;
	}

	public void setSportInfo(String sportInfo) {
		this.sportInfo = sportInfo;
	}

	public Integer getSaveState() {
		return saveState;
	}

	public void setSaveState(Integer saveState) {
		this.saveState = saveState;
	}

 
}
