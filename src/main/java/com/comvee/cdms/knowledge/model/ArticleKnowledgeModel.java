


/**
 * @File name:  ArticleKnowledgeModel.java  ArticleKnowledgeModel  Model信息
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


public class ArticleKnowledgeModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** id,(字段类型:bigint) **/
	private Long id; 
	/** 文章id,(字段类型:bigint) **/
	private Long articleId; 
	/** 知识点id,(字段类型:bigint) **/
	private Long knowledgeId; 
	/** 是否有效(1有效0无效),(字段类型:bigint) **/
	private Long isValid; 
	/** 修改时间,(字段类型:timestamp) **/
	private String modifyDt; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 操作者id,(字段类型:varchar) **/
	private String operateId; 
	/** 知识点标题,(字段类型:varchar) **/
    private String name; 
    
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
	public Long getKnowledgeId(){
		 return this.knowledgeId;
	}
	public void setKnowledgeId(Long knowledgeId){
		 this.knowledgeId=knowledgeId;
	}
	public Long getIsValid(){
		 return this.isValid;
	}
	public void setIsValid(Long isValid){
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
	public String getOperateId(){
		 return this.operateId;
	}
	public void setOperateId(String operateId){
		 this.operateId=operateId;
	}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}