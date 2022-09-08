


/**
 * @File name:  QuesShowModel.java  QuesShowModel  Model信息
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

public class QuesShowModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 问题话述id,(字段类型:bigint) **/
	private String sid;
	/** 问题id,(字段类型:bigint) **/
	private String qid;
	/** 话述内容,(字段类型:varchar) **/
	private String json; 
	/** 话述标题,(字段类型:varchar) **/
	private String memo; 
	/** 是否有效(1有效0无效),(字段类型:tinyint) **/
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

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getJson(){
		 return this.json;
	}
	public void setJson(String json){
		 this.json=json;
	}
	public String getMemo(){
		 return this.memo;
	}
	public void setMemo(String memo){
		 this.memo=memo;
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