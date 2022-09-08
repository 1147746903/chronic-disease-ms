/**
 * @File name:   KnowledgeController.java  知识点表接口控制层
 * @Create on:  2018-7-28 18:59:05
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.KnowledgeModel;
import com.comvee.cdms.defender.service.TiqKnowledgeServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/back/knowledge")
public class KnowledgeController {
	@Autowired
	@Qualifier("tiqKnowledgeService")
	private TiqKnowledgeServiceI tiqKnowledgeService;
	
	
	/**
	 * @TODO  根据id获取知识点表表信息
	 * @param request
	 * @param response
	 * @param id　主键id
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8080/comveeframe/web/knowledge/loadKnowledgeById.do?id=xxxxx
	 * @return
	 */
	@RequestMapping("/loadKnowledgeById")
	public Result loadKnowledgeById(Long id)  {
		ValidateTool.checkParameterIsNull("知识点主键id(id)", id);
		KnowledgeModel knowledgeModel = this.tiqKnowledgeService.loadKnowledgeById(id);
		return new Result(knowledgeModel);
	}
	
	
	/**
	 * @TODO  获取知识点表分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例     http://localhost:8089/comveeframe/web/knowledge/loadKnowledge.do?page=1&rows=10
	 */
	@RequestMapping("/loadKnowledge")
	public Result loadKnowledge(PageRequest pager, Integer type, String param) {
		PageResult<KnowledgeModel> knowledgeModelList = this.tiqKnowledgeService.loadKnowledge(pager,type,param);
		return new Result(knowledgeModelList);
	}
	
	/**
	 * @TODO  添加知识点表记录
	 * @param request
	 * @param response
	 * @param KnowledgeModel 知识点表 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/knowledge/addKnowledge.do?@testparams
	 */
	@RequestMapping("/addKnowledge")
	public Result addKnowledge(KnowledgeModel knowledgeModel)  {
		ValidateTool.checkParameterIsNull("知识点类型(type)", knowledgeModel.getType());
		this.tiqKnowledgeService.addKnowledge(knowledgeModel);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改知识点表记录
	 * @param request
	 * @param response
	 * @param KnowledgeModel 知识点表 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/knowledge/modifyKnowledge.do?@testparams
	 */
	@RequestMapping("/modifyKnowledge")
	public Result modifyKnowledge(KnowledgeModel knowledgeModel) {
		ValidateTool.checkParameterIsNull("知识点主键id(id)", knowledgeModel.getId());
		ValidateTool.checkParameterIsNull("知识点类型(type)", knowledgeModel.getType());
		this.tiqKnowledgeService.modifyKnowledge(knowledgeModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除知识点表记录
	 * @param request
	 * @param response
	 * @param id　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/knowledge/delKnowledge.do?id=xxxxx
	 */
	@RequestMapping("/delKnowledge")
	public Result delKnowledge(Long id) {
		ValidateTool.checkParameterIsNull("知识点主键id(id)", id);
		this.tiqKnowledgeService.delKnowledge(id);
        return new Result("删除成功");
	}
}
