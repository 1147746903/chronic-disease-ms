/**
 * @File name:   CfgQuesController.java  题目配置接口控制层
 * @Create on:  2018-7-25 15:24:49
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.defender.model.*;
import com.comvee.cdms.defender.service.*;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.defender.wechat.dto.AddBarrierQuesCfgDTO;
import com.comvee.cdms.defender.wechat.dto.UpdateBarrierQuesCfgDTO;
import com.comvee.cdms.defender.wechat.po.DailyQuestionPO;
import com.comvee.cdms.defender.wechat.service.DailyQuestionServiceI;
import com.comvee.cdms.defender.wechat.service.MemberBarrierServiceI;
import com.comvee.cdms.defender.wechat.vo.BarrierQuesCfgVO;
import com.comvee.cdms.defender.wechat.vo.DailyQuestionVO;
import com.comvee.cdms.defender.wechat.vo.ListCourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/back/cfgQues")
public class CfgQuesController {
	@Autowired
	@Qualifier("cfgQuesService")
	private CfgQuesServiceI cfgQuesService;
	
	@Autowired
	@Qualifier("cfgQuesItemsService")
	private CfgQuesItemsServiceI cfgQuesItemsService;

	@Autowired
	@Qualifier("quesShowService")
	private QuesShowServiceI quesShowService;

	@Autowired
	@Qualifier("cfgGroupQuesService")
	private CfgGroupQuesServiceI cfgGroupQuesService;

	@Autowired
	private MemberBarrierServiceI memberBarrierServiceI;

	@Autowired
	private DailyQuestionServiceI dailyQuestionServiceI;

	@Autowired
	@Qualifier("courseService")
	private CourseServiceI courseService;
	
	/**
	 * @TODO  根据id获取题目配置表信息
	 * @param request
	 * @param response
	 * @param qid　主键id
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8080/comveeframe/web/cfgQues/loadCfgQuesById.do?qid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadCfgQuesById")
	public Result loadCfgQuesById(Long qid)  {
		ValidateTool.checkParameterIsNull("题目配置id(qid)", qid);
		CfgQuesModel cfgQuesModel = this.cfgQuesService.loadCfgQuesById(qid);
        return new Result(cfgQuesModel);
	}
	
	
	/**
	 * @TODO  获取题目配置分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例     http://localhost:8089/comveeframe/web/cfgQues/loadCfgQues.do?page=1&rows=10
	 */
	@RequestMapping("/loadCfgQues")
	public Result loadCfgQues(PageRequest pager, Long label, String param) {
		PageResult<CfgQuesModel> cfgQuesModelList = this.cfgQuesService.loadCfgQues(pager,label,param);
		return new Result(cfgQuesModelList);
	}
	
	/**
	 * @TODO  添加题目配置记录
	 * @param request
	 * @param response
	 * @param CfgQuesModel 题目配置 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgQues/addCfgQues.do?@testparams
	 */
	@RequestMapping("/addCfgQues")
	public Result addCfgQues(CfgQuesModel cfgQuesModel)  {
		ValidateTool.checkParameterIsNull("题目标题(title)", cfgQuesModel.getTitle());
		ValidateTool.checkParameterIsNull("对应的题目编码(code)", cfgQuesModel.getCode());
		ValidateTool.checkParameterIsNull("题目类型(type)", cfgQuesModel.getType());
		ValidateTool.checkParameterIsNull("子类类型扩展(sonType)", cfgQuesModel.getSonType());
		this.cfgQuesService.addCfgQues(cfgQuesModel);
		return new Result("添加成功");
	}
	
	/**
	 * @TODO  修改题目配置记录
	 * @param request
	 * @param response
	 * @param CfgQuesModel 题目配置 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgQues/modifyCfgQues.do?@testparams
	 */
	@RequestMapping("/modifyCfgQues")
	public Result modifyCfgQues(CfgQuesModel cfgQuesModel) {
		ValidateTool.checkParameterIsNull("题目主键id(qid)", cfgQuesModel.getQid());
		ValidateTool.checkParameterIsNull("题目标题(title)", cfgQuesModel.getTitle());
		ValidateTool.checkParameterIsNull("对应的题目编码(code)", cfgQuesModel.getCode());
		ValidateTool.checkParameterIsNull("题目类型(type)", cfgQuesModel.getType());
		ValidateTool.checkParameterIsNull("子类类型扩展(sonType)", cfgQuesModel.getSonType());
		this.cfgQuesService.modifyCfgQues(cfgQuesModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除题目配置记录
	 * @param request
	 * @param response
	 * @param qid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-25
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgQues/delCfgQues.do?qid=xxxxx
	 */
	@RequestMapping("/delCfgQues")
	public Result delCfgQues(Long qid) {
		ValidateTool.checkParameterIsNull("题目主键id(qid)", qid);
		this.cfgQuesService.delCfgQues(qid);
		return new Result("删除成功");
	}
	
	/**
	 * @TODO  删除题库配置记录
	 * @param request
	 * @param response
	 * @param qids　主键id列表,多个用逗号隔开
	 * @return
	 * @author  zhengsw
	 * @date  2016-02-02
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgQues/bacthDelCfgQues.do?qids=xxxxx
	 */
	@RequestMapping("/bacthDelCfgQues")
	public Result bacthDelCfgQues(HttpServletRequest request, HttpServletResponse response, String qids) {
		ValidateTool.checkParameterIsNull("题目主键id(qids)", qids);
		List<String> list = new ArrayList<String>();
		if(qids.indexOf(",") != -1){
			String[] temp = qids.split(",");
			for (int i = 0; i < temp.length; i++) {
				if(!temp[i].trim().equals("")){
					list.add(temp[i].trim());
				}
			}
		}
		this.cfgQuesService.batchDelQues(list);
		return new Result("删除成功");
	}
	
	
	/**
	 * @TODO  移动题库配置记录
	 * @param request
	 * @param response
	 * @param qids　主键id列表,多个用逗号隔开
	 * @return
	 * @author  zhengsw
	 * @date  2016-02-02
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgQues/moveQuesLabel.do?qids=xxxxx&label=xxx
	 */
	@RequestMapping("/moveQuesLabel")
	public Result moveQuesLabel(HttpServletRequest request, HttpServletResponse response, String qids, String label) {
		ValidateTool.checkParameterIsNull("题目主键id(qids)", qids);
			List<String> list = new ArrayList<String>();
			if(qids.indexOf(",") != -1){
				String[] temp = qids.split(",");
				for (int i = 0; i < temp.length; i++) {
					if(!temp[i].trim().equals("")){
						list.add(temp[i].trim());
					}
				}
			}
			this.cfgQuesService.moveQuesLabel(list, label);
		return new Result("移动成功");
	}
	
	
	/**
	 * @TODO  获取题目的选项规则条件
	 * @param request
	 * @param response
	 * @param qid　主键id
	 * @return
	 * @author  zhengsw
	 * @date  2016-02-02
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgQues/loadCfgQuesItems.do?qids=xxxxx,xxxx
	 */
	@RequestMapping("/loadCfgQuesItems")
	public Result loadCfgQuesItems(HttpServletRequest request, HttpServletResponse response,
                                        String qids) {
			String[] qid_temp = null;
			try {
				qid_temp = qids.split(",");
			} catch (Exception e) {
				
			}
			List<Map<String,Object>> listRes = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < qid_temp.length; i++) {
				CfgQuesModel quesModel = this.cfgQuesService.loadCfgQuesById(Long.parseLong(qid_temp[i]));
				PageResult<CfgQuesItemsModel> items = this.cfgQuesItemsService.loadCfgQuesItems(null, Long.parseLong(qid_temp[i]));
				Map<String,Object> mapQues = new HashMap<String,Object>();
				mapQues.put("ques", quesModel);
				mapQues.put("items", items);
				listRes.add(mapQues);
			}

		return new Result(listRes);
	}
	
	
	/**
	 * @TODO  获取题目的选项规则条件
	 * @param request
	 * @param response
	 * @param qid　主键id
	 * @return
	 * @author  zhengsw
	 * @date  2016-02-02
	 * 请求样例  http://localhost:8089/comveeframe/web/cfgQues/loadCfgQuesItems.do?qids=xxxxx,xxxx
	 */
	@RequestMapping("/loadQuesInfo")
	public Result loadQuesInfo(HttpServletRequest request, HttpServletResponse response,
                                    Long qid) {
		 ValidateTool.checkParameterIsNull("题目主键id(qids)", qid);
			CfgQuesModel cfgQuesModel = this.cfgQuesService.loadCfgQuesById(qid);
			//话述列表
			PageResult<QuesShowModel> quesShowModelList = this.quesShowService.loadQuesShow(null,qid);
			
			//选项列表
			PageResult<CfgQuesItemsModel> items = this.cfgQuesItemsService.loadCfgQuesItems(null, qid);
			Map<String ,Object> map = new HashMap<String ,Object>();
			map.put("ques", cfgQuesModel);
		    map.put("shows", quesShowModelList.getRows() == null ? null : quesShowModelList.getRows());
		    map.put("items", items.getRows() == null ? null : items.getRows());
		    return new Result(map);
	}


	/**
	 * @TODO  获取题库配置分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  zhengsw
	 * @date  2016-02-02
	 * 请求样例     http://localhost:8089/comveeframe/web/cfgQues/loadCfgQues.do?page=1&rows=10
	 */
	@RequestMapping("/loadCfgQuesAndItems")
	public Result loadCfgQuesAndItems(PageRequest pager, Long label, String param, Long groupId) {
		PageResult<CfgQuesModel> cfgQuesModelList = this.cfgQuesService.loadCfgQues(null,label,param);
		
		List<Map> resList = new ArrayList<Map>();
		if (cfgQuesModelList.getRows() != null && cfgQuesModelList.getRows().size() > 0){
			for (CfgQuesModel bean:cfgQuesModelList.getRows()) {
				Map map = new HashMap();
				map.put("ques", bean);
				PageResult<CfgQuesItemsModel> result =this.cfgQuesItemsService.loadCfgQuesItems(null, Long.valueOf(bean.getQid())); //InfoManager.getInstance().quesItemMap.get(bean.getQid().toString());
				List<CfgQuesItemsModel> items = result.getRows();
				map.put("items", items==null ?new ArrayList<CfgQuesItemsModel>() : items) ;
				resList.add(map);
			}
		}
		List<CfgGroupQuesModel> groupQues = this.cfgGroupQuesService.loadCfgGroupQues(groupId);
		
//		ResultModel result = new ResultModel(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", resList);
//		map.put("pager", pager);
		map.put("groupQues", groupQues);
//		result.setObj(map);
		return new Result(map);
	}
	
	

	/**
	 * @TODO  获取题库配置分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  zhengsw
	 * @date  2016-02-02
	 * 请求样例     http://localhost:8089/comveeframe/web/cfgQues/loadGroupQuesItem.do?group_id
	 */
	@RequestMapping("/loadGroupQuesItem")
	public Result loadGroupQuesItem(HttpServletRequest request, HttpServletResponse response,
                                         Long groupId) {
			List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
			List<CfgGroupQuesModel> groupQues = this.cfgGroupQuesService.loadCfgGroupQues(groupId);
			
			for (CfgGroupQuesModel bean: groupQues) {
				Map<String,Object> map = new HashMap<String,Object>();
				CfgQuesModel ques = this.cfgQuesService.loadCfgQuesById(bean.getQid());//InfoManager.getInstance().quesMap.get(bean.getQid().toString());
				map.put("ques", ques);
				PageResult<CfgQuesItemsModel> result = this.cfgQuesItemsService.loadCfgQuesItems(null, bean.getQid());//InfoManager.getInstance().quesItemMap.get(bean.getQid().toString());
				List<CfgQuesItemsModel> items = result.getRows();
				map.put("items", items==null ?new ArrayList<CfgQuesItemsModel>() : items) ;
				map.put("groupQues", bean);
				resList.add(map);
			}
		return new Result(resList);
	}


	/**
	 * ----------------------------每日一答后台题库配置----------------------------
	 */

	/**
	 * 添加
	 * @param dailyQuestionPO
	 * @return
	 */
	@RequestMapping("/addDailyQuesCfg")
	public Result addDailyQuesCfg(@Validated DailyQuestionPO dailyQuestionPO) {
		dailyQuestionServiceI.addDailyQuestion(dailyQuestionPO);
		return Result.ok();
	}

	/**
	 * 获取详情
	 * @param sid
	 * @return
	 */
	@RequestMapping("/detailDailyQuesCfg")
	public Result addDailyQuesCfg(String sid) {
		ValidateTool.checkParamIsNull(sid,"sid");
		DailyQuestionVO dailyQuestionVO = dailyQuestionServiceI.loadDailyQuestionById(sid);
		return Result.ok(dailyQuestionVO);
	}

	/**
	 * 删除
	 * @param sid
	 * @return
	 */
	@RequestMapping("/delDailyQuesCfg")
	public Result delDailyQuesCfg(String sid) {
		ValidateTool.checkParamIsNull(sid,"sid");
		dailyQuestionServiceI.delDailyQuestionPO(sid);
		return Result.ok();
	}

	/**
	 * 更新
	 * @param dailyQuestionPO
	 * @return
	 */
	@RequestMapping("/updateDailyQuesCfg")
	public Result updateDailyQuesCfg(@Validated DailyQuestionPO dailyQuestionPO) {
		dailyQuestionServiceI.updatDailyQuestion(dailyQuestionPO);
		return Result.ok();
	}

	/**
	 * 列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/listDailyQuesCfg")
	public Result listDailyQuesCfg(PageRequest page) {
		PageResult<DailyQuestionVO> dailyQuestionPOPageResult = dailyQuestionServiceI.listDailyQuestionPO(page);
		return Result.ok(dailyQuestionPOPageResult);
	}



	/**
	 * ---------------------------知识挑战后台题库配置-------------------------------
	 */

	/**
	 * 题库添加
	 * @param addBarrierQuesCfgDTO
	 * @return
	 */
	@RequestMapping("/addBarrierQuesCfg")
	public Result addBarrierQuesCfg(@Validated AddBarrierQuesCfgDTO addBarrierQuesCfgDTO) {
		memberBarrierServiceI.addBarrierQuesCfg(addBarrierQuesCfgDTO);
		return Result.ok();
	}

	/**
	 * 题库删除
	 * @param sid
	 * @return
	 */
	@RequestMapping("/delBarrierQuesCfg")
	public Result delBarrierQuesCfg(String sid) {
		ValidateTool.checkParamIsNull(sid,"sid");
		memberBarrierServiceI.delBarrierQuesCfg(sid);
		return Result.ok();
	}

	/**
	 * 题库更新
	 * @param updateBarrierQuesCfgDTO
	 * @return
	 */
	@RequestMapping("/updateBarrierQuesCfg")
	public Result updateBarrierQuesCfg(@Validated UpdateBarrierQuesCfgDTO updateBarrierQuesCfgDTO) {
		memberBarrierServiceI.updateBarrierQuesCfg(updateBarrierQuesCfgDTO);
		return Result.ok();
	}

	/**
	 * 列表查询
	 * @param page
	 * @return
	 */
	@RequestMapping("/listBarrierQuesCfg")
	public Result listBarrierQuesCfg(String title,PageRequest page) {
		PageResult<BarrierQuesCfgVO> barrierQuesCfgVOPageResult = memberBarrierServiceI.listBarrierQuesCfgVO(title,page);
		return Result.ok(barrierQuesCfgVOPageResult);
	}

	/**
	 * 详情
	 * @param sid
	 * @return
	 */
	@RequestMapping("/detailBarrierQuesCfg")
	public Result detailBarrierQuesCfgVO(String sid) {
		ValidateTool.checkParamIsNull(sid,"sid");
		BarrierQuesCfgVO barrierQuesCfgVO = memberBarrierServiceI.detailBarrierQuesCfgVO(sid);
		return Result.ok(barrierQuesCfgVO);
	}

	/*搜索课程*/
	@RequestMapping("/searchCourse")
	public Result searchCourse(String keyword) {
		List<ListCourseVO> courseVOS = courseService.searchCourse(keyword,"");
		return Result.ok(courseVOS);
	}
}
