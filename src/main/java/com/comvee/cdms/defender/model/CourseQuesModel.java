


/**
 * @File name:  CourseQuesModel.java  CourseQuesModel  Model信息
 * @Create on:  2018-9-14 16:41:07
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

public class CourseQuesModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 主键id,(字段类型:bigint) **/
	private String sid;
	/** 课程id,(字段类型:bigint) **/
	private String courseId;
	/** 课程id,(字段类型:bigint) **/
	private String qid;
	/** 是否有效1有效0无效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 排序,(字段类型:smallint) **/
	private Integer sort;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
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
	public Integer getSort(){
		 return this.sort;
	}
	public void setSort(Integer sort){
		 this.sort=sort;
	}
}