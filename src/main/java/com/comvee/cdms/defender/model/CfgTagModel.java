


/**
 * @File name:  CfgTagModel.java  CfgTagModel  Model信息
 * @Create on:  2018-7-26 19:03:27
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

public class CfgTagModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 主键id,(字段类型:bigint) **/
	private String sid;
	/** 标签标题,(字段类型:varchar) **/
	private String title; 
	/** 标签类型,(字段类型:tinyint) **/
	private String type;
	/** 编码,(字段类型:varchar) **/
	private String code; 
	/** 是否有效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getTitle(){
		 return this.title;
	}
	public void setTitle(String title){
		 this.title=title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode(){
		 return this.code;
	}
	public void setCode(String code){
		 this.code=code;
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