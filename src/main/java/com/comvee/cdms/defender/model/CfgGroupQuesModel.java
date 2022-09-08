


/**
 * @File name:  CfgGroupQuesModel.java  CfgGroupQuesModel  Model信息
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

public class CfgGroupQuesModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 题目与分组关联表主键id,(字段类型:bigint) **/
	private Long tid;
	/** 题目id,(字段类型:bigint) **/
	private Long qid;
	/** 分组id,(字段类型:bigint) **/
	private Long groupId;
	/** 是否有效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 排序,(字段类型:int) **/
	private Integer sort; 
	/** 题目标题    ,(字段类型:int) **/
	private String title; 
	/** 题目code    ,(字段类型:varchar) **/
	private String code;

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
	public Long getGroupId(){
		 return this.groupId;
	}
	public void setGroupId(Long groupId){
		 this.groupId=groupId;
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
	public Integer getSort(){
		 return this.sort;
	}
	public void setSort(Integer sort){
		 this.sort=sort;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}