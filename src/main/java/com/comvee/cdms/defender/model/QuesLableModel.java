


/**
 * @File name:  QuesLableModel.java  QuesLableModel  Model信息
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

public class QuesLableModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** ,(字段类型:bigint) **/
	private String sid;
	/** 标签内容,(字段类型:varchar) **/
	private String title; 
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