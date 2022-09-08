


/**
 * @File name:  KnowledgeContentModel.java  KnowledgeContentModel  Model信息
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
import java.util.Map;

public class KnowledgeContentModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 内容主键,(字段类型:bigint) **/
	private String sid;
	/** 文章id,(字段类型:bigint) **/
	private String knowledgeId;
	/** 排序,(字段类型:tinyint) **/
	private Integer sort; 
	/** 知识点内容类型 字典表knowledge_type(1图片2语音3大标题4小标题5正文),(字段类型:tinyint) **/
	private Integer type; 
	/** 内容,(字段类型:text) **/
	private String content; 
	/** 是否有效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	
	private Map<String,Object> video;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public Integer getSort(){
		 return this.sort;
	}
	public void setSort(Integer sort){
		 this.sort=sort;
	}
	public Integer getType(){
		 return this.type;
	}
	public void setType(Integer type){
		 this.type=type;
	}
	public String getContent(){
		 return this.content;
	}
	public void setContent(String content){
		 this.content=content;
	}
	public Integer getIsValid(){
		 return this.isValid;
	}
	public void setIsValid(Integer isValid){
		 this.isValid=isValid;
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
	public Map<String, Object> getVideo() {
		return video;
	}
	public void setVideo(Map<String, Object> video) {
		this.video = video;
	}
	
}