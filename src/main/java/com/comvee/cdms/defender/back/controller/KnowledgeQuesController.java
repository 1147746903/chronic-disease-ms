/**
 * @File name:   KnowledgeQuesController.java  文章课后习题接口控制层
 * @Create on:  2018-7-28 16:52:22
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.CfgQuesModel;
import com.comvee.cdms.defender.model.KnowledgeQuesModel;
import com.comvee.cdms.defender.model.QuesLableModel;
import com.comvee.cdms.defender.model.ResultModel;
import com.comvee.cdms.defender.service.CfgQuesServiceI;
import com.comvee.cdms.defender.service.QuesLableServiceI;
import com.comvee.cdms.defender.service.TiqKnowledgeQuesServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.knowledge.model.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/back/knowledgeQues")
public class KnowledgeQuesController {
	@Autowired
	@Qualifier("tiqKnowledgeQuesService")
	private TiqKnowledgeQuesServiceI tiqKnowledgeQuesService;
	
	@Autowired
	@Qualifier("cfgQuesService")
	private CfgQuesServiceI cfgQuesService;
	
	@Autowired
	@Qualifier("quesLableService")
	private QuesLableServiceI quesLableService;
	
	
	/**
	 * @TODO  根据id获取文章课后习题表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8080/comveeframe/web/knowledgeQues/loadKnowledgeQuesById.do?sid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadKnowledgeQuesById")
	public Result loadKnowledgeQuesById(Long sid)  {
		KnowledgeQuesModel knowledgeQuesModel = this.tiqKnowledgeQuesService.loadKnowledgeQuesById(sid);
		return new Result(knowledgeQuesModel);
	}
	
	
	/**
	 * @TODO  获取文章课后习题分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例     http://localhost:8089/comveeframe/web/knowledgeQues/loadKnowledgeQues.do?page=1&rows=10
	 */
	@RequestMapping("/loadKnowledgeQues")
	public Result loadKnowledgeQues(Long knowledgeId) {
		List<KnowledgeQuesModel> knowledgeQuesModelList = this.tiqKnowledgeQuesService.loadKnowledgeQues(knowledgeId);
		return new Result(knowledgeQuesModelList);
	}
	
	/**
	 * @TODO  添加文章课后习题记录
	 * @param request
	 * @param response
	 * @param KnowledgeQuesModel 文章课后习题 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/knowledgeQues/addKnowledgeQues.do?@testparams
	 */
	@RequestMapping("/addKnowledgeQues")
	public Result addKnowledgeQues(KnowledgeQuesModel knowledgeQuesModel)  {
		this.tiqKnowledgeQuesService.addKnowledgeQues(knowledgeQuesModel);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改文章课后习题记录
	 * @param request
	 * @param response
	 * @param KnowledgeQuesModel 文章课后习题 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/knowledgeQues/modifyKnowledgeQues.do?@testparams
	 */
	@RequestMapping("/modifyKnowledgeQues")
	public Result modifyKnowledgeQues(KnowledgeQuesModel knowledgeQuesModel) {
		this.tiqKnowledgeQuesService.modifyKnowledgeQues(knowledgeQuesModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除文章课后习题记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/knowledgeQues/delKnowledgeQues.do?sid=xxxxx
	 */
	@RequestMapping("/delKnowledgeQues")
	public Result delKnowledgeQues(Long sid) {
		this.tiqKnowledgeQuesService.delKnowledgeQues(sid);
		return new Result("删除成功");
	}

	/**
	 * 获取知识点课后习题关联信息以及习题列表
	 * @param knowledgeId
	 * @return
	 */
	@RequestMapping("/loadKnowledgeQuesRelation")
	public Result loadKnowledgeQuesRelation(Long knowledgeId){
		ValidateTool.checkParameterIsNull("知识文章id(knowledgeId)", knowledgeId);
		List<CfgQuesModel> all =  this.cfgQuesService.loadAllCfgQues();
		Map<String,QuesLableModel> quesLables = this.quesLableService.quesLable();
		List<KnowledgeQuesModel> kq = this.tiqKnowledgeQuesService.loadKnowledgeQues(knowledgeId);
		
		for (CfgQuesModel cfgQuesModel : all) {
			QuesLableModel label = quesLables.get(cfgQuesModel.getLabel());
			if(label == null){
				cfgQuesModel.setLabelName("默认分组");
			} else {
				cfgQuesModel.setLabelName(label.getTitle());
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("all", all);
		map.put("select", kq);
		
		return new Result(map);
	}
	
	@RequestMapping("/batchAddKnowledgeQues")
	public Result batchAddKnowledgeQues(String qs ,Long knowledgeId){
		ValidateTool.checkParameterIsNull("知识文章id(knowledgeId)", knowledgeId);
		String[] temp = qs.split(",");
		List<Long> ids = new ArrayList<Long>();
		for (String str : temp) {
			Long id  = Long.parseLong(str);
			ids.add(id);
		}
		if(ids.size() > 0){
			this.tiqKnowledgeQuesService.batchAddKnowledgeQues(ids, knowledgeId);
		}
        return new Result("保存成功");
	}
}
