


/**
 * @File name:  CategoryModel.java  CategoryModel  Model信息
 * @Create on:  2016-12-28 16:03:24
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/

package com.comvee.cdms.knowledge.model;
import java.io.Serializable;


public class CategoryModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 规则版本id,(字段类型:bigint) **/
	private Long id; 
	/** 版本说明,(字段类型:char) **/
	private String lable; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:timestamp) **/
	private String modifyDt; 
	/** 是否有效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 操作者id,(字段类型:char) **/
	private String operateId; 
    
	public Long getId(){
		 return this.id;
	}
	public void setId(Long id){
		 this.id=id;
	}
	public String getLable(){
		 return this.lable;
	}
	public void setLable(String lable){
		 this.lable=lable;
	}
	public String getInsertDt(){
		 return this.insertDt;
	}
	public void setInsertDt(String insertDt){
		 this.insertDt=insertDt;
	}
	public String getModifyDt(){
		 return this.modifyDt;
	}
	public void setModifyDt(String modifyDt){
		 this.modifyDt=modifyDt;
	}
	public Integer getIsValid(){
		 return this.isValid;
	}
	public void setIsValid(Integer isValid){
		 this.isValid=isValid;
	}
	public String getOperateId(){
		 return this.operateId;
	}
	public void setOperateId(String operateId){
		 this.operateId=operateId;
	}
}