package com.comvee.cdms.defender.model;

/**
 * @File name:  CfgCourseRecommendRuleModel.java   Model信息
 * @Create on:  2018-11-07
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
import java.io.Serializable;
public class CfgCourseRecommendRuleModel implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主键id    ,(字段类型:bigint) **/
	private String sid; 
	/** 标签内容    ,(字段类型:varchar) **/
	private String rule; 
	/** 推荐课程    ,(字段类型:varchar) **/
	private String outid; 
	/** 添加时间    ,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间    ,(字段类型:datetime) **/
	private String modifyDt; 
	/** 是否有效(1有效,0无效)    ,(字段类型:tinyint) **/
	private String isValid; 
	/** 规则描述    ,(字段类型:varchar) **/
	private String con; 
	
	private Integer salience;//权重

	public String getSid(){
		 return this.sid;
	}
 
	public void setSid(String sid){
		 this.sid=sid;
	}
 
	public String getRule(){
		 return this.rule;
	}
 
	public void setRule(String rule){
		 this.rule=rule;
	}
 
	public String getOutid(){
		 return this.outid;
	}
 
	public void setOutid(String outid){
		 this.outid=outid;
	}
	
	public String getCon(){
		 return this.con;
	}
 
	public void setCon(String con){
		 this.con=con;
	}

	public Integer getSalience() {
		return salience;
	}

	public void setSalience(Integer salience) {
		this.salience = salience;
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

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

}
