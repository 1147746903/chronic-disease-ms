/**
 * @File name:   CfgQuesItemsController.java  题目选项接口控制层
 * @Create on:  2018-7-25 15:24:49
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.CfgQuesItemsModel;
import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.defender.model.ResultModel;
import com.comvee.cdms.defender.service.CfgQuesItemsServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.knowledge.model.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/back/cfgQuesItems")
public class CfgQuesItemsController {
	@Autowired
	@Qualifier("cfgQuesItemsService")
	private CfgQuesItemsServiceI cfgQuesItemsService;
	
	/**
	 * @TODO  根据id获取题目选项表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8080/comveeframe/web/cfgQuesItems/loadCfgQuesItemsById.do?sid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadCfgQuesItemsById")
	public Result loadCfgQuesItemsById(Long sid)  {
		ValidateTool.checkParameterIsNull("选项主键id(sid)", sid);
		CfgQuesItemsModel cfgQuesItemsModel = this.cfgQuesItemsService.loadCfgQuesItemsById(sid);
        return new Result(cfgQuesItemsModel);
	}
	
	
	/**
	 * @TODO  获取题目选项分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例     http://localhost:8089/comveeframe/web/cfgQuesItems/loadCfgQuesItems.do?page=1&rows=10
	 */
	@RequestMapping("/loadCfgQuesItems")
	public Result loadCfgQuesItems(PageRequest pager, Long qid) {
		ValidateTool.checkParameterIsNull("题目id(qid)", qid);
		PageResult<CfgQuesItemsModel> cfgQuesItemsModelList = this.cfgQuesItemsService.loadCfgQuesItems(pager,qid);
		return new Result(cfgQuesItemsModelList);
	}
	
	/**
	 * @TODO  添加题目选项记录
	 * @param request
	 * @param response
	 * @param CfgQuesItemsModel 题目选项 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgQuesItems/addCfgQuesItems.do?@testparams
	 */
	@RequestMapping("/addCfgQuesItems")
	public Result addCfgQuesItems(CfgQuesItemsModel cfgQuesItemsModel)  {
		ValidateTool.checkParameterIsNull("题目id(qid)", cfgQuesItemsModel.getQid());
		this.cfgQuesItemsService.addCfgQuesItems(cfgQuesItemsModel);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改题目选项记录
	 * @param request
	 * @param response
	 * @param CfgQuesItemsModel 题目选项 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgQuesItems/modifyCfgQuesItems.do?@testparams
	 */
	@RequestMapping("/modifyCfgQuesItems")
	public Result modifyCfgQuesItems(CfgQuesItemsModel cfgQuesItemsModel) {
		ValidateTool.checkParameterIsNull("选项主键id(sid)", cfgQuesItemsModel.getSid());
		this.cfgQuesItemsService.modifyCfgQuesItems(cfgQuesItemsModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除题目选项记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgQuesItems/delCfgQuesItems.do?sid=xxxxx
	 */
	@RequestMapping("/delCfgQuesItems")
	public Result delCfgQuesItems(Long sid) {
		ValidateTool.checkParameterIsNull("选项主键id(sid)", sid);
		this.cfgQuesItemsService.delCfgQuesItems(sid);
		return new Result("删除成功");
	}
}
