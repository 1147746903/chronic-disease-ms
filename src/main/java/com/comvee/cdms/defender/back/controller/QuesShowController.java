/**
 * @File name:   QuesShowController.java  题目话术接口控制层
 * @Create on:  2018-7-25 15:24:49
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.QuesShowModel;
import com.comvee.cdms.defender.service.QuesShowServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/back/quesShow")
public class QuesShowController {
	@Autowired
	@Qualifier("quesShowService")
	private QuesShowServiceI quesShowService;
	
	
	/**
	 * @TODO  根据id获取题目话术表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8080/comveeframe/web/quesShow/loadQuesShowById.do?sid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadQuesShowById")
	public Result loadQuesShowById(Long sid)  {
		ValidateTool.checkParameterIsNull("题目话术id(sid)", sid);
		QuesShowModel quesShowModel = this.quesShowService.loadQuesShowById(sid);
        return new Result(quesShowModel);
	}
	
	
	/**
	 * @TODO  获取题目话术分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例     http://localhost:8089/comveeframe/web/quesShow/loadQuesShow.do?page=1&rows=10
	 */
	@RequestMapping("/loadQuesShow")
	public Result loadQuesShow(PageRequest pager, Long qid) {
		PageResult<QuesShowModel> quesShowModelList = this.quesShowService.loadQuesShow(pager,qid);
		return new Result(quesShowModelList);
	}
	
	/**
	 * @TODO  添加题目话术记录
	 * @param request
	 * @param response
	 * @param QuesShowModel 题目话术 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8089/comveeframe/web/quesShow/addQuesShow.do?@testparams
	 */
	@RequestMapping("/addQuesShow")
	public Result addQuesShow(QuesShowModel quesShowModel)  {
		ValidateTool.checkParameterIsNull("题目主键id(qid)", quesShowModel.getQid());
		this.quesShowService.addQuesShow(quesShowModel);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改题目话术记录
	 * @param request
	 * @param response
	 * @param QuesShowModel 题目话术 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8089/comveeframe/web/quesShow/modifyQuesShow.do?@testparams
	 */
	@RequestMapping("/modifyQuesShow")
	public Result modifyQuesShow(QuesShowModel quesShowModel) {
		ValidateTool.checkParameterIsNull("题目话术id(sid)", quesShowModel.getSid());
		this.quesShowService.modifyQuesShow(quesShowModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除题目话术记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8089/comveeframe/web/quesShow/delQuesShow.do?sid=xxxxx
	 */
	@RequestMapping("/delQuesShow")
	public Result delQuesShow(Long sid) {
		ValidateTool.checkParameterIsNull("题目话术id(sid)", sid);
		this.quesShowService.delQuesShow(sid);
		return new Result("删除成功");
	}
}
