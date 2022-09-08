package com.comvee.cdms.prescription.bo;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class BmiRangeSetBO implements Serializable{
	
    private static final long serialVersionUID = 1L;
    /**
     * MEMBER_ID NUMBER(15)  Y           成员id
     */
    private String memberId;
    /**
     * IS_VALID  NUMBER(1)   Y   1       1有效0无效
     */
    private Integer isValid;
    /**
     * INSERT_DT DATE    Y   sysdate     添加时间
     */
    private String insertDt;
    /**
     * LOW_BMI VARCHAR2(32)    Y           Bmi指数下限
     */
    private String lowBmi;
    /**
     * HIGH_BMI   VARCHAR2(32)    Y           Bmi指数上限
     */
    private String highBmi;
    /**
     * 修改时间
     */
    private String modifyDt;

	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public String getInsertDt() {
		return insertDt;
	}
	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}
	public String getLowBmi() {
		return lowBmi;
	}
	public void setLowBmi(String lowBmi) {
		this.lowBmi = lowBmi;
	}
	public String getHighBmi() {
		return highBmi;
	}
	public void setHighBmi(String highBmi) {
		this.highBmi = highBmi;
	}
	public String getModifyDt() {
		return modifyDt;
	}
	public void setModifyDt(String modifyDt) {
		this.modifyDt = modifyDt;
	}

	@Override
	public String toString() {
		return "BmiRangeSetBO{" +
				"memberId='" + memberId + '\'' +
				", isValid=" + isValid +
				", insertDt='" + insertDt + '\'' +
				", lowBmi='" + lowBmi + '\'' +
				", highBmi='" + highBmi + '\'' +
				", modifyDt='" + modifyDt + '\'' +
				'}';
	}
}
