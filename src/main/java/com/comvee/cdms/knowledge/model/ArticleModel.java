


/**
 * @File name:  ArticleModel.java  ArticleModel  Model信息
 * @Create on:  2016-12-28 15:47:25
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


public class ArticleModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 文章id,(字段类型:bigint) **/
	private Long id; 
	/** 摘要,(字段类型:varchar) **/
	private String summary; 
	/** 标题,(字段类型:varchar) **/
	private String title; 
	/** 平台,(字段类型:varchar) **/
	private String platform; 
	/** 内容出处,(字段类型:varchar) **/
	private String reference; 
	/** 文章图片,(字段类型:varchar) **/
	private String img; 
	/** 干预目的,(字段类型:varchar) **/
	private String purposes; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:timestamp) **/
	private String modifyDt; 
	/** 是否有效(1有效 0无效),(字段类型:tinyint) **/
	private Integer isValid; 
	/** 标签id,多个标签逗号隔开,(字段类型:varchar) **/
	private String tagId; 
	/** 关键字id,多个关键字逗号隔开,(字段类型:varchar) **/
	private String keyId; 
	/** 所属栏目id,(字段类型:bigint) **/
	private Long barId; 
	/** 操作者id,(字段类型:varchar) **/
	private String operateId; 
	
    /** 所属栏目名字 **/
	private String barName;
	
	public Long getId(){
		 return this.id;
	}
	public void setId(Long id){
		 this.id=id;
	}
	public String getSummary(){
		 return this.summary;
	}
	public void setSummary(String summary){
		 this.summary=summary;
	}
	public String getTitle(){
		 return this.title;
	}
	public void setTitle(String title){
		 this.title=title;
	}
	public String getPlatform(){
		 return this.platform;
	}
	public void setPlatform(String platform){
		 this.platform=platform;
	}
	public String getReference(){
		 return this.reference;
	}
	public void setReference(String reference){
		 this.reference=reference;
	}
	public String getImg(){
		 return this.img;
	}
	public void setImg(String img){
		 this.img=img;
	}
	public String getPurposes(){
		 return this.purposes;
	}
	public void setPurposes(String purposes){
		 this.purposes=purposes;
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
	public String getTagId(){
		 return this.tagId;
	}
	public void setTagId(String tagId){
		 this.tagId=tagId;
	}
	public String getKeyId(){
		 return this.keyId;
	}
	public void setKeyId(String keyId){
		 this.keyId=keyId;
	}
	public Long getBarId(){
		 return this.barId;
	}
	public void setBarId(Long barId){
		 this.barId=barId;
	}
	public String getOperateId(){
		 return this.operateId;
	}
	public void setOperateId(String operateId){
		 this.operateId=operateId;
	}
    public String getBarName() {
        return barName;
    }
    public void setBarName(String barName) {
        this.barName = barName;
    }
	
	
}