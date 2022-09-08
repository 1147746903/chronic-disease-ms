package com.comvee.cdms.follow.model;

import java.io.Serializable;

/**
 * @author wyc
 * @date 2019/4/12 14:43
 */
public class FollowDayModel implements Serializable{
	
	private String foreignId;
    //随访类型：1首诊  2日常随访 3自我行为问卷 4糖尿病足随访表 5糖尿病随访表
    private Integer type;
    //状态 0 未完成 1已完成 2患者已填写(未完成)
    private Integer status;
    //医生编号
    private String doctorId;
    //患者编号
    private String memberId;
    //随访信息JSON
    private String followInfo;
  //随访信息JSON
    private String memberInfo;
    //是否有效数据 1有效 0无效
    private Integer isValid;
    //添加时间
    private String insertDt;
    //更新时间
    private String modifyDt;

    private String drugListJson;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(String followInfo) {
        this.followInfo = followInfo;
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

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getDrugListJson() {
        return drugListJson;
    }

    public void setDrugListJson(String drugListJson) {
        this.drugListJson = drugListJson;
    }

	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	public String getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(String memberInfo) {
		this.memberInfo = memberInfo;
	}

}
