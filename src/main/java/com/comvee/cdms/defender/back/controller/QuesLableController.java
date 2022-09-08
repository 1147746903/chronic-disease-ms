/**
 * @File name:   QuesLableController.java  题目分组接口控制层
 * @Create on:  2018-7-25 15:24:49
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.QuesLableModel;
import com.comvee.cdms.defender.service.QuesLableServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/back/quesLable")
public class QuesLableController {
	@Autowired
	@Qualifier("quesLableService")
	private QuesLableServiceI quesLableService;
	
	
	/**
	 * @TODO  根据id获取题目分组表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-7-25
	 * @return
	 */
	@RequestMapping("/loadQuesLableById")
	public Result loadQuesLableById(Long sid)  {
		ValidateTool.checkParameterIsNull("分组id(sid)", sid);
		QuesLableModel quesLableModel = this.quesLableService.loadQuesLableById(sid);
        return new Result(quesLableModel);
	}
	
	
	/**
	 * @TODO  获取题目分组分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 */
	@RequestMapping("/loadQuesLable")
	public Result loadQuesLable(PageRequest page) {
		PageResult<QuesLableModel> quesLableModelList = this.quesLableService.loadQuesLable(page);
        return new Result(quesLableModelList);
	}
	
	/**
	 * @TODO  添加题目分组记录
	 * @param request
	 * @param response
	 * @param QuesLableModel 题目分组 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 */
	@RequestMapping("/addQuesLable")
	public Result addQuesLable(QuesLableModel quesLableModel)  {
		ValidateTool.checkParameterIsNull("标签内容(title)", quesLableModel.getTitle());
		this.quesLableService.addQuesLable(quesLableModel);
        return Result.ok("添加成功");
	}
	
	/**
	 * @TODO  修改题目分组记录
	 * @param request
	 * @param response
	 * @param QuesLableModel 题目分组 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 */
	@RequestMapping("/modifyQuesLable")
	public Result modifyQuesLable(QuesLableModel quesLableModel) {
		ValidateTool.checkParameterIsNull("标签内容(title)", quesLableModel.getTitle());
		ValidateTool.checkParameterIsNull("分组id(sid)", quesLableModel.getSid());
		this.quesLableService.modifyQuesLable(quesLableModel);
        return Result.ok("修改成功");
	}
	
	/**
	 * @TODO  删除题目分组记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 */
	@RequestMapping("/delQuesLable")
	public Result delQuesLable(Long sid) {
		ValidateTool.checkParameterIsNull("分组id(sid)", sid);
		this.quesLableService.delQuesLable(sid);
		return Result.ok("删除成功");
	}
}
