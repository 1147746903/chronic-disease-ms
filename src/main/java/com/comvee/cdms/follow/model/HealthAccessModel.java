package com.comvee.cdms.follow.model;

import java.util.List;

import com.comvee.cdms.member.bo.RangeBO;

public class HealthAccessModel {

	private boolean isFirst;
	
	private String level;//第一次给评估等级
	
	private String suggest;//第一次给评估检验
	
	private String hba1c;//非第一次给糖化血红蛋白值
	
	private List hbalist;//红蛋白走势图list
	
	private List ques13list;//
	
	private boolean ifChd;//是否合并冠心病
	
	private String memberInfo;
	
	private String nowques;
	
	private String gssuggest;
	
	private String yqtarget;
	
	private RangeBO rangebo;

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getHba1c() {
		return hba1c;
	}

	public void setHba1c(String hba1c) {
		this.hba1c = hba1c;
	}

	public List getHbalist() {
		return hbalist;
	}

	public void setHbalist(List hbalist) {
		this.hbalist = hbalist;
	}

	public List getQues13list() {
		return ques13list;
	}

	public void setQues13list(List ques13list) {
		this.ques13list = ques13list;
	}

	public boolean isIfChd() {
		return ifChd;
	}

	public void setIfChd(boolean ifChd) {
		this.ifChd = ifChd;
	}

	public String getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(String memberInfo) {
		this.memberInfo = memberInfo;
	}

	public String getNowques() {
		return nowques;
	}

	public void setNowques(String nowques) {
		this.nowques = nowques;
	}

	public String getGssuggest() {
		return gssuggest;
	}

	public void setGssuggest(String gssuggest) {
		this.gssuggest = gssuggest;
	}

	public String getYqtarget() {
		return yqtarget;
	}

	public void setYqtarget(String yqtarget) {
		this.yqtarget = yqtarget;
	}

	public RangeBO getRangebo() {
		return rangebo;
	}

	public void setRangebo(RangeBO rangebo) {
		this.rangebo = rangebo;
	}
	
	
}
