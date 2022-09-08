


/**
 * @File name:  QuesRuleModel.java  QuesRuleModel  Model信息
 * @Create on:  2018-7-25 15:24:49
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/

package com.comvee.cdms.defender.model;
import java.io.Serializable;

public class QuesRuleModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 主健,(字段类型:bigint) **/
	private Long sid; 
	/** 题目id,(字段类型:bigint) **/
	private Long qid; 
	/** 分组内的题目id,(字段类型:bigint) **/
	private Long tid; 
	/** 规则描述,(字段类型:varchar) **/
	private String con; 
	/** 规则逻辑,(字段类型:varchar) **/
	private String rule; 
	/** 规则html(方便展示),(字段类型:varchar) **/
	private String conHtml; 
	/** 要出的题目的编码,(字段类型:varchar) **/
	private String outCode; 
	/** ,(字段类型:bigint) **/
	private Long outQid; 
	/** ,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	
	private String groupId;
	private Integer interveneType;
	
	private String code;
	private String keyword;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Integer getInterveneType() {
		return interveneType;
	}
	public void setInterveneType(Integer interveneType) {
		this.interveneType = interveneType;
	}
	public Long getSid(){
		 return this.sid;
	}
	public void setSid(Long sid){
		 this.sid=sid;
	}
	public Long getQid(){
		 return this.qid;
	}
	public void setQid(Long qid){
		 this.qid=qid;
	}
	public Long getTid(){
		 return this.tid;
	}
	public void setTid(Long tid){
		 this.tid=tid;
	}
	public String getCon(){
		 return this.con;
	}
	public void setCon(String con){
		 this.con=con;
	}
	public String getRule(){
		 return this.rule;
	}
	public void setRule(String rule){
		 this.rule=rule;
	}
	public String getConHtml(){
		 return this.conHtml;
	}
	public void setConHtml(String conHtml){
		 this.conHtml=conHtml;
	}
	public String getOutCode(){
		 return this.outCode;
	}
	public void setOutCode(String outCode){
		 this.outCode=outCode;
	}
	public Long getOutQid(){
		 return this.outQid;
	}
	public void setOutQid(Long outQid){
		 this.outQid=outQid;
	}
	public Integer getIsValid(){
		 return this.isValid;
	}
	public void setIsValid(Integer isValid){
		 this.isValid=isValid;
	}
	public String getModifyDt(){
		 return this.modifyDt;
	}
	public void setModifyDt(String modifyDt){
		 this.modifyDt=modifyDt;
	}
	public String getInsertDt(){
		 return this.insertDt;
	}
	public void setInsertDt(String insertDt){
		 this.insertDt=insertDt;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
}