


/**
 * @File name:  MediaModel.java  MediaModel  Model信息
 * @Create on:  2018-7-30 15:15:57
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

public class MediaModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 主键id,(字段类型:bigint) **/
	private String sid;
	/** 资源地址,(字段类型:varchar) **/
	private String url; 
	/** 资源描述,(字段类型:varchar) **/
	private String memo; 
	/** 资源类型(1图片2语音),(字段类型:tinyint) **/
	private Integer type; 
	/** 资源名,(字段类型:varchar) **/
	private String name; 
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url){
		 this.url=url;
	}
	public String getMemo(){
		 return this.memo;
	}
	public void setMemo(String memo){
		 this.memo=memo;
	}
	public Integer getType(){
		 return this.type;
	}
	public void setType(Integer type){
		 this.type=type;
	}
	public String getName(){
		 return this.name;
	}
	public void setName(String name){
		 this.name=name;
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