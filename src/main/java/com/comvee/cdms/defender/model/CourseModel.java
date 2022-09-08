


/**
 * @File name:  CourseModel.java  CourseModel  Model信息
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
import java.util.List;
import java.util.Map;

public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/** 课程id,(字段类型:bigint) **/
	private String sid; 
	/** 课程类型(1选修课程２必修课程),(字段类型:tinyint) **/
	private Integer type; 
	/** 课程分类，字典表course_type,(字段类型:tinyint) **/
	private Integer courseType; 
	/** ,(字段类型:datetime) **/
	private String insertDt; 
	/** ,(字段类型:datetime) **/
	private String modifyDt; 
	/** 是否有效(1有效0无效),(字段类型:tinyint) **/
	private Integer isValid; 
	/** 课程名字,(字段类型:varchar) **/
	private String name; 
	/** 课程描述,(字段类型:varchar) **/
	private String memo; 
	/** 课程主图,(字段类型:varchar) **/
	private String img; 
	/** 课程等级,(字段类型:varchar) **/
	private String grade; 
	/** 章节数,(字段类型:tinyint) **/
	private Integer chapter; 
	/** 章节长度描述,(字段类型:varchar) **/
	private String chapterMemo; 
	/** 适应人群，字典表apply_crowd,(字段类型) **/
	private String applyCrowd; 
	/** 适应人群(中文) **/
	private String applyCrowdContent; 
	/** 封面图 **/
	private String surfaceImg;
	/** 缩略图 **/
	private String thumbnail;
	/** 已完成的章节数 **/
	private Integer finishChapter;
	
	private List<Map<String,Object>> chapterList;
	
	private Integer isLearn = 0;  //0未学习 1已选 2已学习
	
	private Integer showStatus;//可见状态 1非礼来用户可见2非礼来用户不可见
	
	private Integer checkStatus;//课程审核状态(0未审核，１审核通过，2审核不通过)
	private String publishDt;//课程发布时间
	
	private String classifyId;
	private String classifyName;
	
	private String patientCourseId;
	
	private String prescriptionKnowledgeId;
	
	private String status;

	//计划时间
	private String planPushDt;

	private String planId;

	/**
	 * 是否是学习计划 0:不是  1:是
	 */
	private Integer isPlan;

	public Integer getIsPlan() {
		return isPlan;
	}

	public void setIsPlan(Integer isPlan) {
		this.isPlan = isPlan;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCourseType() {
		return courseType;
	}

	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}

	public String getInsertDt() {
		return insertDt;
	}

	public void setInsertDt(String insertDt) {
		this.insertDt = insertDt;
	}

	public String getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(String modifyDt) {
		this.modifyDt = modifyDt;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getChapter() {
		return chapter;
	}

	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}

	public String getChapterMemo() {
		return chapterMemo;
	}

	public void setChapterMemo(String chapterMemo) {
		this.chapterMemo = chapterMemo;
	}

	public String getApplyCrowd() {
		return applyCrowd;
	}

	public void setApplyCrowd(String applyCrowd) {
		this.applyCrowd = applyCrowd;
	}

	public String getApplyCrowdContent() {
		return applyCrowdContent;
	}

	public void setApplyCrowdContent(String applyCrowdContent) {
		this.applyCrowdContent = applyCrowdContent;
	}

	public String getSurfaceImg() {
		return surfaceImg;
	}

	public void setSurfaceImg(String surfaceImg) {
		this.surfaceImg = surfaceImg;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Integer getFinishChapter() {
		return finishChapter;
	}

	public void setFinishChapter(Integer finishChapter) {
		this.finishChapter = finishChapter;
	}

	public List<Map<String, Object>> getChapterList() {
		return chapterList;
	}

	public void setChapterList(List<Map<String, Object>> chapterList) {
		this.chapterList = chapterList;
	}

	public Integer getIsLearn() {
		return isLearn;
	}

	public void setIsLearn(Integer isLearn) {
		this.isLearn = isLearn;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getPublishDt() {
		return publishDt;
	}

	public void setPublishDt(String publishDt) {
		this.publishDt = publishDt;
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public String getPatientCourseId() {
		return patientCourseId;
	}

	public void setPatientCourseId(String patientCourseId) {
		this.patientCourseId = patientCourseId;
	}

	public String getPrescriptionKnowledgeId() {
		return prescriptionKnowledgeId;
	}

	public void setPrescriptionKnowledgeId(String prescriptionKnowledgeId) {
		this.prescriptionKnowledgeId = prescriptionKnowledgeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlanPushDt() {
		return planPushDt;
	}

	public void setPlanPushDt(String planPushDt) {
		this.planPushDt = planPushDt;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
}