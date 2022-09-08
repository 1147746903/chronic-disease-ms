/**
 * @File name:   CourseController.java  课程表接口控制层
 * @Create on:  2018-7-28 16:39:45
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.CfgTagModel;
import com.comvee.cdms.defender.model.CourseModel;
import com.comvee.cdms.defender.service.CfgTagServiceI;
import com.comvee.cdms.defender.service.CourseServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/back/course")
public class CourseController {
	@Autowired
	@Qualifier("courseService")
	private CourseServiceI courseService;
	
	@Autowired
	@Qualifier("cfgTagService")
	private CfgTagServiceI cfgTagService;
	
	/**
	 * @TODO  根据id获取课程表表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8080/comveeframe/web/course/loadCourseById.do?sid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadCourseById")
	public Result loadCourseById(Long sid)  {
		ValidateTool.checkParameterIsNull("课程主键id(sid)", sid);
		CourseModel courseModel = this.courseService.loadCourseById(sid);
		return new Result(courseModel);
	}
	
	
	/**
	 * @TODO  获取课程表分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例     http://localhost:8089/comveeframe/web/course/loadCourse.do?page=1&rows=10
	 */
	@RequestMapping("/loadCourse")
	public Result loadCourse(PageRequest pager, String param) {
		PageResult<CourseModel> courseModelList = this.courseService.loadCourseBack(pager,param);
		return new Result(courseModelList);
	}
	
	/**
	 * @TODO  添加课程表记录
	 * @param request
	 * @param response
	 * @param CourseModel 课程表 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/course/addCourse.do?@testparams
	 */
	@RequestMapping("/addCourse")
	public Result addCourse(CourseModel courseModel)  {
		ValidateTool.checkParameterIsNull("课程分类(courseType)", courseModel.getCourseType());
		ValidateTool.checkParameterIsNull("课程类型(type)", courseModel.getType());
		ValidateTool.checkParameterIsNull("classifyId", courseModel.getClassifyId());
		this.courseService.addCourse(courseModel);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改课程表记录
	 * @param request
	 * @param response
	 * @param CourseModel 课程表 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/course/modifyCourse.do?@testparams
	 */
	@RequestMapping("/modifyCourse")
	public Result modifyCourse(CourseModel courseModel) {
		ValidateTool.checkParameterIsNull("课程主键id(sid)", courseModel.getSid());
		this.courseService.modifyCourse(courseModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除课程表记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/course/delCourse.do?sid=xxxxx
	 */
	@RequestMapping("/delCourse")
	public Result delCourse(Long sid) {
		ValidateTool.checkParameterIsNull("课程主键id(sid)", sid);
		this.courseService.delCourse(sid);
		return new Result("删除成功");
	}

	/**
	 * 加载课程下对应的标签以及标签列表
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/loadTagRelation")
	public Result loadTagRelation(Long courseId) {
		ValidateTool.checkParameterIsNull("课程id(courseId)", courseId);
		CourseModel courseModel = this.courseService.loadCourseById(courseId);
		List<CfgTagModel> all = this.cfgTagService.loadCfgTag();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("all", all);
		map.put("select", (courseModel.getApplyCrowd()==null ? "" : courseModel.getApplyCrowd()) );
        return new Result(map);
	}

	/**
	 * 知识管理正文
	 * @param request
	 * @param pid
	 * @param knowledgeId
	 * @return
	 */
	@RequestMapping("/detailKnowledge")
	public Result detailKnowledge(HttpServletRequest request, String pid, String knowledgeId)  {
		ValidateTool.checkParameterIsNull("知识点id", knowledgeId);
		Map<String,Object> map=this.courseService.detailKnowledge(null,knowledgeId);
		return new Result(map);
	}
	
}
