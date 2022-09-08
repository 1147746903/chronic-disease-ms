/**
 * @File name:   CourseServiceI.java  课程表 service层接口
 * @Create on:  2018-7-28 16:39:45
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.defender.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.comvee.cdms.defender.model.CourseModel;
import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.defender.model.ResultModel;
import com.comvee.cdms.defender.wechat.model.CourseRemindModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.defender.wechat.vo.ListCourseVO;

public interface CourseServiceI {


	/**
	 * @TODO 根据id获取课程表表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	CourseModel loadCourseById(Long sid) ;
	
	 /**
	 * @TODO 获取课程表分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	 List<CourseModel> loadCourse(PageRequestModel pager, String param) ;

	PageResult<CourseModel> loadCourseBack(PageRequest pager, String param) ;

	List<CourseModel> loadCourseByClassifyCode(String classifyCode) ;
	
	/**
	 * @TODO  统计课程表记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	long countCourse() ;
	
	/**
	 * @TODO  添加课程表记录
	 * @param CourseModel 课程表　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	void addCourse(CourseModel courseModel) ;
	
	/**
	 * @TODO  修改课程表记录
	 * @param CourseModel 课程表　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	void modifyCourse(CourseModel courseModel) ;
	
	/**
	 * @TODO  删除课程表记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	void delCourse(Long  sid) ;
	
	/**
	 * 根据类型获取课程
	 * @param pager
	 * @param type
	 * @return
	 */
	List<CourseModel> loadCourse(PageRequestModel pager, String type,String pid);
	
	/**
	 * 添加课程
	 * @param sid
	 * @param pid
	 * @return
	 */
	void addMemberCourse(String sid, String pid,String origin,String isPlan,Long planId);
	
	/**
	 * 
	 * @param pid
	 * @return
	 */
	Map<String, Object> loadMemberCourse(String pid,String allFlag,String isPlan);
	
	/**
	 * 获取课程类型
	 * @param pid
	 * @return
	 */
	Map<String, Object> loadCourseType(String pid,String type);
	
	/**
	 * 根据类型获取课程列表
	 * @param pager
	 * @param type
	 * @param pid
	 * @return
	 */
	Map<String, Object> loadCourseByType(PageRequestModel pager, String type, String pid);
	
	/**
	 * 课程详情
	 * @param sid
	 * @return
	 */
	Map<String, Object> detailCourse(String sid,String pid,String isPlan,String patientCourseId);
	
	/**
	 * 加载用户课程提醒
	 * @param pid
	 * @return
	 */
	Map<String, Object> loadMemberCourseRemind(String pid);
	
	/**
	 * 设置课程提醒
	 * @param pid
	 * @param recordTime 
	 * @param weekIds 
	 * @param isOpen 
	 * @return
	 */
	void setMemberCourseRemind(CourseRemindModel courseRemindModel);
	
	/**
	 * 章节列表
	 * @param pid
	 * @param sid
	 * @return
	 */
	Map<String, Object> courseChapter(String pid, String sid,String patientCourseId);
	
	/**
	 * 章节详情
	 * @param pid
	 * @param chapterId
	 * @return
	 */
	Map<String, Object> detailCourseChapter(String pid, String chapterId,String courseId,String patientCourseId);
	
	/**
	 * 课程学习提交
	 * @param pid
	 * @param chapterId
	 * @param score
	 * @param endDt 
	 * @param startDt 
	 * @param courseAnswerJson 
	 * @param assess 
	 * @return
	 */
	Map<String, Object> submitCourseChapter(String pid,String patientCourseId, String chapterId, String score,String answerJson,String courseId, String startDt, String endDt, String assess, String courseAnswerJson);
	
	/**
	 * 已学课程
	 * @param pid
	 * @param type
	 * @return
	 */
	Map<String, Object> loadMemberFinishCourseByType(String pid,String isPlan,PageRequestModel pager);
	
	/**
	 * 知识点详情
	 * @param pid
	 * @param knowledgeId
	 * @return
	 */
	Map<String, Object> detailKnowledge(String pid, String knowledgeId);
	
	/**
	 * 课程学习问答推荐课程
	 * @param pid
	 * @param courseId
	 * @return
	 */
	List<Map<String, Object>> getRecommend(String pid, String courseId);
	
	/**
	 * 创建学习计划
	 * @param archives
	 * @param memberId
	 * @param drugList
	 * @return
	 */
	public Map<String, Object> createStudyPlan(String archives, String memberId, String drugList ,Integer eohType);

	/**
	 * 查询知识学习的管理处方--提供给学习计划列表
	 * @param pid
	 * @return
	 */
	public Map<String, Object> loadPrescriptionPlans(String pid);
	
	/**
	 * 根据管理处方id查询课程列表
	 * @param prescriptionId
	 * @return
	 */
	public Map<String, Object> loadCoursesByPrescriptionId(String prescriptionId,String knowledgeType ,Integer dataLimit);
	
	
	/**
	 * 删除已选课程
	 * @param patientCourseId
	 * @return
	 */
	public Map<String, Object> delPatientCourse(String patientCourseId);

	/**
	 * 根据id加载患者指定的课程列表
	 * @param memberId
	 * @param courseId
	 * @return
	 */
	List<CourseModel> listMemberCourseById(String memberId ,String courseId);
	/**
	 * 搜索课程
	 * @param keyword
	 * @return
	 */
	List<ListCourseVO> wechatSearchCourse(String keyword,String memberId);

	List<ListCourseVO> searchCourse(String keyword, String memberId);

	/**
	 * 加载课程搜索历史
	 * @param memberId
	 * @return
	 */
	List<String> loadCourseSearchHistory(String memberId);

	/**
	 * 清空搜索历史记录
	 * @param memberId
	 */
	void clearCourseSearchHistory(String memberId);

	/**
	 * 获取推荐课程
	 * @param memberId
	 */
	List<ListCourseVO> loadRecommendCourseList(String memberId);

	/**
	 * 重置生成推荐课程
	 * @param
	 */
	void reSetRecommendCourseList();
	
}
