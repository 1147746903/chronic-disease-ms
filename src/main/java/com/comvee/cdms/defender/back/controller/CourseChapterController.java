/**
 * @File name:   CourseChapterController.java  课程章节接口控制层
 * @Create on:  2018-7-28 16:39:45
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.CourseChapterModel;
import com.comvee.cdms.defender.service.CourseChapterServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/back/courseChapter")
public class CourseChapterController {
	@Autowired
	@Qualifier("courseChapterService")
	private CourseChapterServiceI courseChapterService;
	
	
	/**
	 * @TODO  根据id获取课程章节表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8080/comveeframe/web/courseChapter/loadCourseChapterById.do?sid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadCourseChapterById")
	public Result loadCourseChapterById(Long sid)  {
		ValidateTool.checkParameterIsNull("课程章节id(sid)", sid);
		CourseChapterModel courseChapterModel = this.courseChapterService.loadCourseChapterById(sid);
		return new Result(courseChapterModel);
	}
	
	
	/**
	 * @TODO  获取课程章节分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例     http://localhost:8089/comveeframe/web/courseChapter/loadCourseChapter.do?page=1&rows=10
	 */
	@RequestMapping("/loadCourseChapter")
	public Result loadCourseChapter(PageRequest pager, Long courseId, String param) {
		ValidateTool.checkParameterIsNull("课程id(courseId)", courseId);
		PageResult<CourseChapterModel> courseChapterModelList = this.courseChapterService.loadCourseChapter(pager,courseId,param);
		return new Result(courseChapterModelList);
	}
	
	/**
	 * @TODO  添加课程章节记录
	 * @param request
	 * @param response
	 * @param CourseChapterModel 课程章节 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/courseChapter/addCourseChapter.do?@testparams
	 */
	@RequestMapping("/addCourseChapter")
	public Result addCourseChapter(CourseChapterModel courseChapterModel)  {
		ValidateTool.checkParameterIsNull("课程id(courseId)", courseChapterModel.getCourseId());
		ValidateTool.checkParameterIsNull("章节标题(chapterName)", courseChapterModel.getChapterName());
		this.courseChapterService.addCourseChapter(courseChapterModel);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改课程章节记录
	 * @param request
	 * @param response
	 * @param CourseChapterModel 课程章节 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/courseChapter/modifyCourseChapter.do?@testparams
	 */
	@RequestMapping("/modifyCourseChapter")
	public Result modifyCourseChapter(CourseChapterModel courseChapterModel) {
		ValidateTool.checkParameterIsNull("课程章节id(sid)", courseChapterModel.getSid());
		ValidateTool.checkParameterIsNull("课程id(courseId)", courseChapterModel.getCourseId());
		ValidateTool.checkParameterIsNull("章节标题(chapterName)", courseChapterModel.getChapterName());
		this.courseChapterService.modifyCourseChapter(courseChapterModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除课程章节记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/courseChapter/delCourseChapter.do?sid=xxxxx
	 */
	@RequestMapping("/delCourseChapter")
	public Result delCourseChapter(Long sid) {
		ValidateTool.checkParameterIsNull("课程章节id(sid)", sid);
		this.courseChapterService.delCourseChapter(sid);
        return new Result("删除成功");
	}
}
