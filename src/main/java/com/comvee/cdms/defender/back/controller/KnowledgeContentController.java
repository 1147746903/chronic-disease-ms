/**
 * @File name:   KnowledgeContentController.java  文章内容正文接口控制层
 * @Create on:  2018-7-28 18:59:05
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.alibaba.fastjson.JSON;
import com.comvee.cdms.defender.model.KnowledgeContentModel;
import com.comvee.cdms.defender.service.TiqKnowledgeContentServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/back/knowledgeContent")
public class KnowledgeContentController {
	@Autowired
	@Qualifier("tiqKnowledgeContentService")
	private TiqKnowledgeContentServiceI tiqKnowledgeContentService;
	
	
	/**
	 * @TODO  根据id获取文章内容正文表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8080/comveeframe/web/knowledgeContent/loadKnowledgeContentById.do?sid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadKnowledgeContentById")
	public Result loadKnowledgeContentById(Long sid)  {
		ValidateTool.checkParameterIsNull("内容主键id(sid)", sid);
		KnowledgeContentModel knowledgeContentModel = this.tiqKnowledgeContentService.loadKnowledgeContentById(sid);
		return new Result(knowledgeContentModel);
	}
	
	
	/**
	 * @TODO  获取文章内容正文分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例     http://localhost:8089/comveeframe/web/knowledgeContent/loadKnowledgeContent.do?page=1&rows=10
	 */
	@RequestMapping("/loadKnowledgeContent")
	public Result loadKnowledgeContent(PageRequest pager) {
		PageResult<KnowledgeContentModel> knowledgeContentModelList = this.tiqKnowledgeContentService.loadKnowledgeContent(pager);
		return new Result(knowledgeContentModelList);
	}
	
	/**
	 * @TODO  获取文章内容正文信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例     http://localhost:8089/comveeframe/web/knowledgeContent/loadKnowledgeContent.do?page=1&rows=10
	 */
	@RequestMapping("/loadContentByKnowledgeId")
	public Result loadContentByKnowledgeId(Long knowledgeId) {
		ValidateTool.checkParameterIsNull("文章知识id(knowledgeId)", knowledgeId);
		List<KnowledgeContentModel> knowledgeContentModelList = this.tiqKnowledgeContentService.loadContentByKnowledgeId(knowledgeId);
		return new Result(knowledgeContentModelList);
	}
	
	/**
	 * @TODO  添加文章内容正文记录
	 * @param request
	 * @param response
	 * @param KnowledgeContentModel 文章内容正文 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/knowledgeContent/addKnowledgeContent.do?@testparams
	 */
	@RequestMapping("/addKnowledgeContent")
	public Result addKnowledgeContent(KnowledgeContentModel knowledgeContentModel)  {
		this.tiqKnowledgeContentService.addKnowledgeContent(knowledgeContentModel);
		return new Result("添加成功");
	}
	
	
	/**
	 * @TODO  添加文章内容正文记录
	 * @param request
	 * @param response
	 * @param KnowledgeContentModel 文章内容正文 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/knowledgeContent/addKnowledgeContent.do?@testparams
	 */
	@RequestMapping("/batchAddKnowledgeContent")
	public Result batchAddKnowledgeContent(Long knowledgeId,Integer type,String content)  {
		ValidateTool.checkParameterIsNull("文章知识id(knowledgeId)", knowledgeId);
		ValidateTool.checkParameterIsNull("知识点内容类型(type)", type);
		List<String> contents = JSON.parseArray(content,String.class);
		List<KnowledgeContentModel> list  =  new ArrayList<KnowledgeContentModel>();
		for (String string : contents) {
			KnowledgeContentModel knowledgeContentModel = new KnowledgeContentModel();
			knowledgeContentModel.setContent(string);
			knowledgeContentModel.setType(type);
			knowledgeContentModel.setKnowledgeId(knowledgeId.toString());
			list.add(knowledgeContentModel);
		}
		this.tiqKnowledgeContentService.addKnowledgeContent(list, knowledgeId);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改文章内容正文记录
	 * @param request
	 * @param response
	 * @param KnowledgeContentModel 文章内容正文 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/knowledgeContent/modifyKnowledgeContent.do?@testparams
	 */
	@RequestMapping("/modifyKnowledgeContent")
	public Result modifyKnowledgeContent(KnowledgeContentModel knowledgeContentModel) {
		ValidateTool.checkParameterIsNull("内容主键id(sid)", knowledgeContentModel.getSid());
		ValidateTool.checkParameterIsNull("知识点内容类型(type)", knowledgeContentModel.getType());
		this.tiqKnowledgeContentService.modifyKnowledgeContent(knowledgeContentModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除文章内容正文记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/knowledgeContent/delKnowledgeContent.do?sid=xxxxx
	 */
	@RequestMapping("/delKnowledgeContent")
	public Result delKnowledgeContent(Long sid) {
		ValidateTool.checkParameterIsNull("内容主键id(sid)", sid);
		this.tiqKnowledgeContentService.delKnowledgeContent(sid);
        return new Result("删除成功");
	}
	/**
	 * 文章内容排序
	 * @param ids
	 * @return
	 */
	@RequestMapping("/sortKnowledgeContent")
	public Result sortKnowledgeContent(String ids){
		this.tiqKnowledgeContentService.sortKnowledgeContent(ids);
		return new Result("");
	}
}
