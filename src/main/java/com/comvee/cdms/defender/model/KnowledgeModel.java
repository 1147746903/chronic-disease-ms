


/**
 * @File name:  KnowledgeModel.java  KnowledgeModel  Model信息
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
import java.util.List;
import java.util.Map;

public class KnowledgeModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 文章id,(字段类型:bigint) **/
	private String id; 
	/** 文章标题,(字段类型:varchar) **/
	private String title; 
	/** 是否有效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 1课程文章2卡片知识,(字段类型:tinyint) **/
	private Integer type; 
	
	private String img;//配图
	private String memo;//摘要说明
	
	private Map<String,Object> ques;//题目
	
	private List<Map<String,Object>> content;//知识点的内容
    
	public String getId(){
		 return this.id;
	}
	public void setId(String id){
		 this.id=id;
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
	public Integer getType(){
		 return this.type;
	}
	public void setType(Integer type){
		 this.type=type;
	}
	
	public List<Map<String, Object>> getContent() {
		return content;
	}
	public void setContent(List<Map<String, Object>> content) {
		this.content = content;
	}
	public Map<String,Object> getQues() {
		return ques;
	}
	public void setQues(Map<String,Object> ques) {
		this.ques = ques;
	}
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public String toString() {
		return "KnowledgeModel [id=" + id + ", title=" + title + ", isValid=" + isValid + ", modifyDt=" + modifyDt
				+ ", insertDt=" + insertDt + ", type=" + type + ", ques=" + ques + ", content=" + content + "]";
	}
	
}