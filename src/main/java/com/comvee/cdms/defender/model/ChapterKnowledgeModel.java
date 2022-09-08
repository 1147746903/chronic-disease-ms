


/**
 * @File name:  ChapterKnowledgeModel.java  ChapterKnowledgeModel  Model信息
 * @Create on:  2018-7-28 18:59:05
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

public class ChapterKnowledgeModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** id,(字段类型:bigint) **/
	private String sid;
	/** 章节id,(字段类型:bigint) **/
	private String chapterId;
	/** 文章id,(字段类型:bigint) **/
	private String knowledgeId;
	/** 是否有效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 排序,(字段类型:tinyint) **/
	private Integer sort;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
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
}