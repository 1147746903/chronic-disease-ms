


/**
 * @File name:  KnowledgeClassifyModel.java  KnowledgeClassifyModel  Model信息
 * @Create on:  2017-1-4 16:09:47
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


public class KnowledgeClassifyModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 知识点层级分类id,(字段类型:bigint) **/
	private Long id; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:timestamp) **/
	private String modifyDt; 
	/** 是否有效(1有效0无效),(字段类型:tinyint) **/
	private Integer isValid; 
	/** 知识点层级分类标题,(字段类型:varchar) **/
	private String name; 
	/** 知识点层级分类父级id,(字段类型:bigint) **/
	private Long pid; 
	/** 是否有子级(1有子级0无子级),(字段类型:tinyint) **/
	private Integer hasChild; 
	/** 知识点层级分类排序,(字段类型:int) **/
	private Integer sort; 
	/** 层级,(字段类型:tinyint) **/
	private Integer zindex; 
	/** 操作者id,(字段类型:varchar) **/
	private String operateId; 
    
	public Long getId(){
		 return this.id;
	}
	public void setId(Long id){
		 this.id=id;
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
	public String getName(){
		 return this.name;
	}
	public void setName(String name){
		 this.name=name;
	}
	public Long getPid(){
		 return this.pid;
	}
	public void setPid(Long pid){
		 this.pid=pid;
	}
	public Integer getHasChild(){
		 return this.hasChild;
	}
	public void setHasChild(Integer hasChild){
		 this.hasChild=hasChild;
	}
	public Integer getSort(){
		 return this.sort;
	}
	public void setSort(Integer sort){
		 this.sort=sort;
	}
	public Integer getZindex(){
		 return this.zindex;
	}
	public void setZindex(Integer zindex){
		 this.zindex=zindex;
	}
	public String getOperateId(){
		 return this.operateId;
	}
	public void setOperateId(String operateId){
		 this.operateId=operateId;
	}
}