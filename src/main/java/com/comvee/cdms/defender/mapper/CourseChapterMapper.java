/**
 * @File name:   CourseChapterMapper.java  课程章节 mapper层接口
 * @Create on:  2018-7-28 16:39:45
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/



package com.comvee.cdms.defender.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.comvee.cdms.defender.model.CourseChapterModel;

public interface CourseChapterMapper {

	/**
	 * @TODO 根据id获取课程章节表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	CourseChapterModel loadCourseChapterById(@Param("sid") Long sid) ;
	
	 /**
	 * @TODO 获取课程章节分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	List<CourseChapterModel> loadCourseChapter(Map<String, Object> param) ;
	
	/**
	 * @TODO  统计课程章节记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	long countCourseChapter() ;
	

	/**
	 * @TODO  添加课程章节记录
	 * @param CourseChapterModel 课程章节　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	void addCourseChapter(CourseChapterModel courseChapterModel) ;
	
	/**
	 * @TODO  修改课程章节记录
	 * @param CourseChapterModel 课程章节　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	void modifyCourseChapter(CourseChapterModel courseChapterModel) ;
	
	/**
	 * @TODO  删除课程章节记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-7-28 16:39:45
	 */
	void delCourseChapter(@Param("sid") Long  sid) ;

	List<CourseChapterModel> listMemberCourseChapter(@Param("pid")String pid, @Param("courseId")String courseId,@Param("patientCourseId")String patientCourseId);
	
	/**
	 * 
	 * @param patientCourseId
	 * @return
	 */
	List<CourseChapterModel> listMemberCourseChapterByPatientCourseId(@Param("courseId")String courseId,@Param("patientCourseId")String patientCourseId);
	
	
}
