/**
 * @File name:   CourseQuesServiceI.java  课程题目关联 service层接口
 * @Create on:  2018-9-14 16:41:07
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.defender.service;


import com.comvee.cdms.defender.model.CourseQuesModel;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;

import java.util.List;

public interface CourseQuesServiceI {


	/**
	 * @TODO 根据id获取课程题目关联表信息
	 * @param sid　主键id
	 * @return
	 * @
	 * @author admin
	 * @date 2018-9-14 16:41:07
	 */
	CourseQuesModel loadCourseQuesById(Long sid) ;
	
	 /**
	 * @TODO 获取课程题目关联分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-9-14 16:41:07
	 */
	PageResult<CourseQuesModel> loadCourseQues(PageRequest pager) ;
	
	 /**
	 * @TODO 获取课程题目关联分页信息
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @
	 * @author admin
	 * @date 2018-9-14 16:41:07
	 */
	List<CourseQuesModel> loadCourseQues(Long courseId) ;
	
	/**
	 * @TODO  统计课程题目关联记录数
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return 返回统计数
	 * @
	 * @author admin
	 * @date 2018-9-14 16:41:07
	 */
	long countCourseQues() ;
	
	/**
	 * @TODO  添加课程题目关联记录
	 * @param CourseQuesModel 课程题目关联　bean
	 * @return true 添加成功 false 添加失败
	 * @
	 * @author admin
	 * @date 2018-9-14 16:41:07
	 */
	void addCourseQues(CourseQuesModel courseQuesModel) ;
	
	/**
	 * @TODO  修改课程题目关联记录
	 * @param CourseQuesModel 课程题目关联　bean
	 * @return true 修改成功 false 修改失败
	 * @
	 * @author admin
	 * @date 2018-9-14 16:41:07
	 */
	void modifyCourseQues(CourseQuesModel courseQuesModel) ;
	
	/**
	 * @TODO  删除课程题目关联记录
	 * @param sid 主键id
	 * @return true 删除成功 false 删除失败
	 * @
	 * @author admin
	 * @date 2018-9-14 16:41:07
	 */
	void delCourseQues(Long sid) ;
	
	/**
	 * 批量添加课后习题
	 * @param ids
	 * @param knowledgeId
	 */
	public void batchAddCourseQues(List<Long> ids, Long courseId);
	
}
