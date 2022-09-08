/**
 * @File name:   CfgTagController.java  标签接口控制层
 * @Create on:  2018-7-26 19:03:27
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.CfgTagModel;
import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.defender.model.ResultModel;
import com.comvee.cdms.defender.service.CfgTagServiceI;
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
@RequestMapping("/back/cfgTag")
public class CfgTagController {
	@Autowired
	@Qualifier("cfgTagService")
	private CfgTagServiceI cfgTagService;
	
	
	/**
	 * @TODO  根据id获取标签表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-7-26
	 * 请求样例  http://localhost:8080/comveeframe/web/cfgTag/loadCfgTagById.do?sid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadCfgTagById")
	public Result loadCfgTagById(Long sid)  {
		ValidateTool.checkParameterIsNull("标签主键id(sid)", sid);
		CfgTagModel cfgTagModel = this.cfgTagService.loadCfgTagById(sid);
		return new Result(cfgTagModel);
	}
	
	
	/**
	 * @TODO  获取标签分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-26
	 * 请求样例     http://localhost:8089/comveeframe/web/cfgTag/loadCfgTag.do?page=1&rows=10
	 */
	@RequestMapping("/loadCfgTag")
	public Result loadCfgTag(PageRequest pager, String param) {
		PageResult<CfgTagModel> cfgTagModelList = this.cfgTagService.loadCfgTag(pager,param);
		return new Result(cfgTagModelList);
	}
	
	/**
	 * @TODO  添加标签记录
	 * @param request
	 * @param response
	 * @param CfgTagModel 标签 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-26
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgTag/addCfgTag.do?@testparams
	 */
	@RequestMapping("/addCfgTag")
	public Result addCfgTag(CfgTagModel cfgTagModel)  {
		this.cfgTagService.addCfgTag(cfgTagModel);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改标签记录
	 * @param request
	 * @param response
	 * @param CfgTagModel 标签 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-26
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgTag/modifyCfgTag.do?@testparams
	 */
	@RequestMapping("/modifyCfgTag")
	public Result modifyCfgTag(CfgTagModel cfgTagModel) {
		ValidateTool.checkParameterIsNull("标签主键id(sid)", cfgTagModel.getSid());
		this.cfgTagService.modifyCfgTag(cfgTagModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除标签记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-26
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgTag/delCfgTag.do?sid=xxxxx
	 */
	@RequestMapping("/delCfgTag")
	public Result delCfgTag(Long sid) {
		ValidateTool.checkParameterIsNull("标签主键id(sid)", sid);
		this.cfgTagService.delCfgTag(sid);
        return new Result("删除成功");
	}
}
