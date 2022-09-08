package com.comvee.cdms.glu.model;

import java.io.Serializable;
import java.util.Map;
/**
 * 
 * @author 李左河
 *
 */
public class MemberEverydaySugarModel implements Serializable {
	private static final long serialVersionUID = -6346662863570416880L;
	
	private String sex;
	private String sid;
	private String memberId;
	private String concernStatus;
	private String departmentName;
	private String bedId;
	private String bedNo;
	private String paramListJson;
	private String paramDt;
	private String isValid;
	private String departmentId;
	private String smbgScheme;
	private String sugarListJson;
	private Map<String,Object> newSugarMap;
	private String memberName;
	/**
	 * 监测类型    1 长期  2临时 0 没有 
	 */
	private Integer planType;  
	/**
	 * 登记号
	 */
	private String patPatientId;

	private String inHospitalDate;
	private Integer age;

	/**
	 * 住院卡号
	 */
	private String hospitalNo;
	/**
	 * 住院流水号
	 */
	private String admNo;

	public String getInHospitalDate() {
		return inHospitalDate;
	}

	public void setInHospitalDate(String inHospitalDate) {
		this.inHospitalDate = inHospitalDate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getConcernStatus() {
		return concernStatus;
	}
	public void setConcernStatus(String concernStatus) {
		this.concernStatus = concernStatus;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getBedId() {
		return bedId;
	}
	public void setBedId(String bedId) {
		this.bedId = bedId;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getParamListJson() {
		return paramListJson;
	}
	public void setParamListJson(String paramListJson) {
		this.paramListJson = paramListJson;
	}
	public String getParamDt() {
		return paramDt;
	}
	public void setParamDt(String paramDt) {
		this.paramDt = paramDt;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getSmbgScheme() {
		return smbgScheme;
	}
	public void setSmbgScheme(String smbgScheme) {
		this.smbgScheme = smbgScheme;
	}
    public String getSugarListJson() {
        return sugarListJson;
    }
    public void setSugarListJson(String sugarListJson) {
        this.sugarListJson = sugarListJson;
    }
    public Map<String, Object> getNewSugarMap() {
        return newSugarMap;
    }
    public void setNewSugarMap(Map<String, Object> newSugarMap) {
        this.newSugarMap = newSugarMap;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public Integer getPlanType() {
        return planType;
    }
    public void setPlanType(Integer planType) {
        this.planType = planType;
    }
	public String getPatPatientId() {
		return patPatientId;
	}
	public void setPatPatientId(String patPatientId) {
		this.patPatientId = patPatientId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getHospitalNo() {
		return hospitalNo;
	}

	public void setHospitalNo(String hospitalNo) {
		this.hospitalNo = hospitalNo;
	}

	public String getAdmNo() {
		return admNo;
	}

	public void setAdmNo(String admNo) {
		this.admNo = admNo;
	}
}
