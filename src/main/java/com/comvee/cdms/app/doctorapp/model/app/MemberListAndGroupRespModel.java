package com.comvee.cdms.app.doctorapp.model.app;

/**
 * 患者分组返回model
 * @author f011509070
 *
 */
public class MemberListAndGroupRespModel {
	
	private String groupId;
	
	/**组名*/
	private String groupName;
	
	/**组 - 患者数*/
	private String num;

	private String doctorId;


	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
}
