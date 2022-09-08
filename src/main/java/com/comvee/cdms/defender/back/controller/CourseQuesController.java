/**
 * @File name:   CourseQuesController.java  课程题目关联接口控制层
 * @Create on:  2018-9-14 16:41:07
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.CfgQuesModel;
import com.comvee.cdms.defender.model.CourseQuesModel;
import com.comvee.cdms.defender.service.CfgQuesServiceI;
import com.comvee.cdms.defender.service.CourseQuesServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/back/courseQues")
public class CourseQuesController {
	@Autowired
	@Qualifier("courseQuesService")
	private CourseQuesServiceI courseQuesService;
	
	@Autowired
	@Qualifier("cfgQuesService")
	private CfgQuesServiceI cfgQuesService;
	
	/**
	 * @TODO  根据id获取课程题目关联表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-9-14
	 * 请求样例  http://localhost:8080/comveeframe/web/courseQues/loadCourseQuesById.do?sid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadCourseQuesById")
	public Result loadCourseQuesById(Long sid)  {
		CourseQuesModel courseQuesModel = this.courseQuesService.loadCourseQuesById(sid);
		return new Result(courseQuesModel);
	}
	
	
	/**
	 * @TODO  获取课程题目关联分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-9-14
	 * 请求样例     http://localhost:8089/comveeframe/web/courseQues/loadCourseQues.do?page=1&rows=10
	 */
	@RequestMapping("/loadCourseQues")
	public Result loadCourseQues(PageRequest pager) {
		PageResult<CourseQuesModel> courseQuesModelList = this.courseQuesService.loadCourseQues(pager);
		return new Result(courseQuesModelList);
	}
	
	
	@RequestMapping("/loadCourseQuesRelation")
	public Result loadCourseQuesRelation(Long courseId){
		ValidateTool.checkParameterIsNull("课程id(courseId)", courseId);
		List<CfgQuesModel> all =  this.cfgQuesService.loadAllCfgQues();
		//Map<String,QuesLableModel> quesLables = this.quesLableService.quesLable();
		List<CourseQuesModel> kq = this.courseQuesService.loadCourseQues(courseId);
		
//		for (CfgQuesModel cfgQuesModel : all) {
//			QuesLableModel label = quesLables.get(cfgQuesModel.getLabel());
//			if(label == null){
//				cfgQuesModel.setLabelName("默认分组");
//			} else {
//				cfgQuesModel.setLabelName(label.getTitle());
//			}
//		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("all", all);
		map.put("select", kq);
		
		return new Result(map);
	}
	
	/**
	 * @TODO  添加课程题目关联记录
	 * @param request
	 * @param response
	 * @param CourseQuesModel 课程题目关联 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-9-14
	 * 请求样例  http://localhost:8089/comveeframe/web/courseQues/addCourseQues.do?@testparams
	 */
	@RequestMapping("/addCourseQues")
	public Result addCourseQues(CourseQuesModel courseQuesModel)  {
		this.courseQuesService.addCourseQues(courseQuesModel);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改课程题目关联记录
	 * @param request
	 * @param response
	 * @param CourseQuesModel 课程题目关联 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-9-14
	 * 请求样例  http://localhost:8089/comveeframe/web/courseQues/modifyCourseQues.do?@testparams
	 */
	@RequestMapping("/modifyCourseQues")
	public Result modifyCourseQues(CourseQuesModel courseQuesModel) {
		this.courseQuesService.modifyCourseQues(courseQuesModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除课程题目关联记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-9-14
	 * 请求样例  http://localhost:8089/comveeframe/web/courseQues/delCourseQues.do?sid=xxxxx
	 */
	@RequestMapping("/delCourseQues")
	public Result delCourseQues(Long sid) {
		this.courseQuesService.delCourseQues(sid);
		return new Result("删除成功");
	}

	/**
	 * 保存课程课后习题关联信息
	 * @param qs
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/batchAddCourseQues")
	public Result batchAddCourseQues(String qs ,Long courseId){
		ValidateTool.checkParameterIsNull("课程id(courseId)", courseId);
		String[] temp = qs.split(",");
		List<Long> ids = new ArrayList<Long>();
		for (String str : temp) {
			Long id  = Long.parseLong(str);
			ids.add(id);
		}
		if(ids.size() > 0){
			this.courseQuesService.batchAddCourseQues(ids, courseId);
		}
        return new Result("保存成功");
	}
}
