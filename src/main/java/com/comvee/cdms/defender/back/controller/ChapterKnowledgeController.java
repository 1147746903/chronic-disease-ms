/**
 * @File name:   ChapterKnowledgeController.java  章节文章关联表接口控制层
 * @Create on:  2018-7-28 18:59:05
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.ChapterKnowledgeModel;
import com.comvee.cdms.defender.model.KnowledgeModel;
import com.comvee.cdms.defender.service.ChapterKnowledgeServiceI;
import com.comvee.cdms.defender.service.TiqKnowledgeServiceI;
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
@RequestMapping("/back/chapterKnowledge")
public class ChapterKnowledgeController {
	@Autowired
	@Qualifier("chapterKnowledgeService")
	private ChapterKnowledgeServiceI chapterKnowledgeService;
	
	
	@Autowired
	@Qualifier("tiqKnowledgeService")
	private TiqKnowledgeServiceI tiqKnowledgeService;
	
	
	/**
	 * @TODO  根据id获取章节文章关联表表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8080/comveeframe/web/chapterKnowledge/loadChapterKnowledgeById.do?sid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadChapterKnowledgeById")
	public Result loadChapterKnowledgeById(Long sid)  {
		ChapterKnowledgeModel chapterKnowledgeModel = this.chapterKnowledgeService.loadChapterKnowledgeById(sid);
		return new Result(chapterKnowledgeModel);
	}
	
	
	/**
	 * @TODO  获取章节文章关联表分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例     http://localhost:8089/comveeframe/web/chapterKnowledge/loadChapterKnowledge.do?page=1&rows=10
	 */
	@RequestMapping("/loadChapterKnowledge")
	public Result loadChapterKnowledge(PageRequest pager) {
		PageResult<ChapterKnowledgeModel> chapterKnowledgeModelList = this.chapterKnowledgeService.loadChapterKnowledge(pager);
		return new Result(chapterKnowledgeModelList);
	}
	
	/**
	 * @TODO  获取章节文章关联表信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例     http://localhost:8089/comveeframe/web/chapterKnowledge/loadKnowledgeRelation.do
	 */
	@RequestMapping("/loadKnowledgeRelation")
	public Result loadKnowledgeRelation(Long chapterId) {
		ValidateTool.checkParameterIsNull("章节id(chapterId)", chapterId);
		List<KnowledgeModel> chapterKnowledges = this.chapterKnowledgeService.loadKnowledgeRelation(chapterId);
		List<KnowledgeModel> all = this.tiqKnowledgeService.loadAllKnowledge();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("all",  all);
		map.put("select",  chapterKnowledges);
		return new Result(map);
	}
	
	/**
	 * @TODO  保存章节文章关联表信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例     http://localhost:8089/comveeframe/web/chapterKnowledge/loadKnowledgeRelation.do
	 */
	@RequestMapping("/batchAddChapterKnowledge")
	public Result batchAddChapterKnowledge(String knowledgeIds,Long chapterId ) {
		ValidateTool.checkParameterIsNull("章节id(chapterId)", chapterId);
		String[] temp = knowledgeIds.split(",");
		
		List<Long> ids = new ArrayList<Long>();
		for (String str : temp) {
			Long id  = Long.parseLong(str);
			ids.add(id);
			
		}
		if(ids.size() > 0){
			this.chapterKnowledgeService.batchAddChapterKnowledge(ids, chapterId);
		}
		return new Result("保存成功");
	}
	
	/**
	 * @TODO  添加章节文章关联表记录
	 * @param request
	 * @param response
	 * @param ChapterKnowledgeModel 章节文章关联表 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/chapterKnowledge/addChapterKnowledge.do?@testparams
	 */
	@RequestMapping("/addChapterKnowledge")
	public Result addChapterKnowledge(ChapterKnowledgeModel chapterKnowledgeModel)  {
		this.chapterKnowledgeService.addChapterKnowledge(chapterKnowledgeModel);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改章节文章关联表记录
	 * @param request
	 * @param response
	 * @param ChapterKnowledgeModel 章节文章关联表 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/chapterKnowledge/modifyChapterKnowledge.do?@testparams
	 */
	@RequestMapping("/modifyChapterKnowledge")
	public Result modifyChapterKnowledge(ChapterKnowledgeModel chapterKnowledgeModel) {
		this.chapterKnowledgeService.modifyChapterKnowledge(chapterKnowledgeModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除章节文章关联表记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-28
	 * 请求样例  http://localhost:8089/comveeframe/web/chapterKnowledge/delChapterKnowledge.do?sid=xxxxx
	 */
	@RequestMapping("/delChapterKnowledge")
	public Result delChapterKnowledge(Long sid) {
		this.chapterKnowledgeService.delChapterKnowledge(sid);
        return new Result("删除成功");
	}
}
