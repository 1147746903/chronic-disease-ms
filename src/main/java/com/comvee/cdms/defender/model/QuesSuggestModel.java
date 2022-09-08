


/**
 * @File name:  QuesSuggestModel.java  QuesSuggestModel  Model信息
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

public class QuesSuggestModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 题目建议主键id,(字段类型:bigint) **/
	private Long sid; 
	/** 分组题目id,(字段类型:bigint) **/
	private Long tid; 
	/** 题目id,(字段类型:bigint) **/
	private Long qid; 
	/** 规则描述,(字段类型:varchar) **/
	private String con; 
	/** 规则逻辑,(字段类型:varchar) **/
	private String rule; 
	/** 规则html(方便展示),(字段类型:varchar) **/
	private String conHtml; 
	/** ,(字段类型:varchar) **/
	private String suggest; 
	/** ,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
    
	public Long getSid(){
		 return this.sid;
	}
	public void setSid(Long sid){
		 this.sid=sid;
	}
	public Long getTid(){
		 return this.tid;
	}
	public void setTid(Long tid){
		 this.tid=tid;
	}
	public Long getQid(){
		 return this.qid;
	}
	public void setQid(Long qid){
		 this.qid=qid;
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
	public String getSuggest(){
		 return this.suggest;
	}
	public void setSuggest(String suggest){
		 this.suggest=suggest;
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
}