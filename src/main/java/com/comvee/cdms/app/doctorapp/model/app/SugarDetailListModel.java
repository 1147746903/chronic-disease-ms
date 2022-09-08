package com.comvee.cdms.app.doctorapp.model.app;

import java.util.Objects;

public class SugarDetailListModel {

	private String sid;
	
	private String paramCode;
	private String paramValue;
	private String paramLevel;
	private String recordDt;
	private String memberId;
	private String memberName;
	private String num;
	private String picUrl;
	private String text;
	
	//正常血糖添加
//	private String

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamLevel() {
		return paramLevel;
	}
	public void setParamLevel(String paramLevel) {
		this.paramLevel = paramLevel;
	}
	public String getRecordDt() {
		return recordDt;
	}
	public void setRecordDt(String recordDt) {
		this.recordDt = recordDt;
	}
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
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Override
	public String toString() {
		return "SugarDetailListModel{" +
				", memberName='" + memberName + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;  //先判断o是否为本对象，this 指向当前的对象
		if (o == null || getClass() != o.getClass())
			return false; //再判断o是否为null，和o.类对象和本类对象是否一致
		//再把o对象强制转化为SugarDetailListModel类对象
		SugarDetailListModel sugarDetailListModel = (SugarDetailListModel) o;
		//查看两个对象的name和sex属性值是否相等
		return  Objects.equals(memberId, sugarDetailListModel.memberId);
	}

}
