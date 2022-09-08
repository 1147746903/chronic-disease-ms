package com.comvee.cdms.follow.model;

/**
 * @author wyc
 * @date 2019/4/12 14:53
 */
public class FollowDayDTO {
    //患者编号
    private String memberId;
    //医生编号
    private String doctorId;
    //随访类型
    private String type;
    
    private String status;
    
    private String endTime;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
    
}
