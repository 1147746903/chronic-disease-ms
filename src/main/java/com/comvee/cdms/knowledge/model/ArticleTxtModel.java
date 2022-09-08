


/**
 * @File name:  ArticleTxtModel.java  ArticleTxtModel  Model信息
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


public class ArticleTxtModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 文章正文主键id,(字段类型:bigint) **/
	private Long id; 
	/** 文单id,(字段类型:bigint) **/
	private Long articleId; 
	/** 正文内容,(字段类型:text) **/
	private String content; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:timestamp) **/
	private String modifyDt; 
	/** 是否有效(1有效0无效),(字段类型:tinyint) **/
	private Integer isValid; 
	/** 操作者id,(字段类型:varchar) **/
	private String opearateId; 
    
	public Long getId(){
		 return this.id;
	}
	public void setId(Long id){
		 this.id=id;
	}
	public Long getArticleId(){
		 return this.articleId;
	}
	public void setArticleId(Long articleId){
		 this.articleId=articleId;
	}
	public String getContent(){
		 return this.content;
	}
	public void setContent(String content){
		 this.content=content;
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
	public String getOpearateId(){
		 return this.opearateId;
	}
	public void setOpearateId(String opearateId){
		 this.opearateId=opearateId;
	}
}