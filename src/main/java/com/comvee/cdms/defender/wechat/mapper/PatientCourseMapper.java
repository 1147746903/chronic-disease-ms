package com.comvee.cdms.defender.wechat.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comvee.cdms.defender.model.CourseModel;
import com.comvee.cdms.defender.wechat.model.CourseRemindModel;
import com.comvee.cdms.defender.wechat.model.PatientChapterAnswerModel;
import com.comvee.cdms.defender.wechat.model.PatientChapterModel;
import com.comvee.cdms.defender.wechat.model.PatientCourseModel;

public interface PatientCourseMapper {
	
	//统计患者未完成课程数量
	Long countNoFinishCourseNum(String pid);
	
	//根据患者和课程id获取课程
	PatientCourseModel getPatientCourseById(Map<String, Object> param);
	
	//添加用户课程
	void addPatientCourse(Map<String, Object> param);
	
	//必修课程
	List<CourseModel> loadRequiredCourse(Map<String, Object> param);
	
	//选修课程
	List<CourseModel> loadOptionalCourse(Map<String, Object> param);
	
	//课程提醒
	CourseRemindModel getCourseRemindByPid(@Param("pid") String pid);
	
	//修改课程提醒
	void updateCourseRemind(CourseRemindModel courseRemindModel);
	
	//添加课程提醒
	void addCourseRemind(CourseRemindModel courseRemindModel);
	
	//获取患者已完成的章节数
	Long countCourseChapterNum(Map<String,Object>  param);
	
	//获取患者某章节的详情
	PatientChapterModel getPatiChapter(Map<String, Object> param);
	
	//添加患者章节记录
	void addPatientChapter(PatientChapterModel patiChapterModel);
	
	//添加患者答题记录
	void addPatientChapterAnswer(PatientChapterAnswerModel patientChapterAnswerModel);
	
	//修改患者章节记录
	void updatePatientChapter(PatientChapterModel patientChapterModel);
	
	//修改用户课程状态
	void updatePatientCourse(PatientCourseModel patientCourseModel);
	
	//获取用户课程
	List<CourseModel> loadMemberCourse(Map<String, Object> param);
	
	
	List<Map<String, Object>> loadPrescriptionPlansForMember(Map<String, Object> param);
	
	List<CourseModel> loadCoursesByPrescriptionId(@Param("prescriptionId") String prescriptionId,@Param("followId") String followId ,@Param("dataLimit") Integer dataLimit);
	
	int updateByPrimaryKeySelective(PatientCourseModel model);
	
	List<Map<String, Object>> listMemberKnowledgeCourseForLearne(Map<String, Object> param);

	/**
	 * 统计一个学习计划中某个类型的课程数量
	 * @param prescriptionId
	 * @param followId
	 * @return
	 */
	long countCourseInPlan(@Param("prescriptionId") String prescriptionId ,@Param("followId") String followId ,@Param("countType") Integer countType);

}
