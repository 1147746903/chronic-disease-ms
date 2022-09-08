


/**
 * @File name:  CourseChapterModel.java  CourseChapterModel  Model信息
 * @Create on:  2018-7-28 16:39:45
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

public class CourseChapterModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 主键id,(字段类型:bigint) **/
	private String sid;
	/** 课程id,(字段类型:bigint) **/
	private String courseId;
	/** 章节标题,(字段类型:vachar) **/
	private String chapterName; 
	/** 章节描述,(字段类型:vachar) **/
	private String chapterMemo; 
	/** 是否有效,(字段类型:tinyint) **/
	private Integer isValid; 
	/** 添加时间,(字段类型:datetime) **/
	private String insertDt; 
	/** 修改时间,(字段类型:datetime) **/
	private String modifyDt; 
	/** 排序,(字段类型:sort) **/
	private Integer sort=0;
	/** 图片,(字段类型:vachar) **/
	private String img;
	
	private String thumbnail;//缩略图
	
	private String status;//学习状态  1未学习 2学习中 3已学习 4带解锁
	
	private String score;//分数
	
	private String patientCourseId;
    
	public String getSid(){
		 return this.sid;
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

	public String getChapterName(){
		 return this.chapterName;
	}
	public void setChapterName(String chapterName){
		 this.chapterName=chapterName;
	}
	public String getChapterMemo(){
		 return this.chapterMemo;
	}
	public void setChapterMemo(String chapterMemo){
		 this.chapterMemo=chapterMemo;
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
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	
	public String getPatientCourseId() {
		return patientCourseId;
	}
	public void setPatientCourseId(String patientCourseId) {
		this.patientCourseId = patientCourseId;
	}
	@Override
	public String toString() {
		return "CourseChapterModel [sid=" + sid + ", courseId=" + courseId + ", chapterName=" + chapterName
				+ ", chapterMemo=" + chapterMemo + ", isValid=" + isValid + ", insertDt=" + insertDt + ", modifyDt="
				+ modifyDt + ", sort=" + sort + ", img=" + img + ", thumbnail=" + thumbnail + ", status=" + status
				+ ", score=" + score + "]";
	}
	
}