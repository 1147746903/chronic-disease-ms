package com.comvee.cdms.app.doctorapp.contorller;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.app.doctorapp.service.DrugsMemberServiceI;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.drugs.po.DrugsDepotPO;
import com.comvee.cdms.drugs.vo.DrugsMemberVO;
import com.comvee.cdms.member.po.MemberDrugItemPO;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/docapp/drug")
public class DocAppDrugController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DrugsMemberServiceI drugsMemberServiceI;


	/**
	 * 分页加载药品库
	 * @param mobileDefualtVO
	 * @param request
	 * @param type
	 * @param page
	 * @return
	 */
	@RequestMapping("/loadDrugsList")
	@ResponseBody
	public Result loadDrugsList(String drugType, String drugName, String doctorId, PageRequest page){
			//结果是分页
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		if(StringUtils.isBlank(doctorId)){
			doctorId=doctorModel.getDoctorId();
		}
		PageResult<DrugsDepotPO> pageResult = this.drugsMemberServiceI.listDrugsDepotPageByHosForDefault(doctorModel.getHospitalId(),doctorId, drugType, drugName, page);
		return Result.ok(pageResult);
	}

	/**
	 * 新增药品记录[关闭-linyd-20190909]
	 * @param mobileDefualtVO
	 * @param request
	 * @param type
	 * @param page
	 * @return
	 */
	//@RequestMapping("/addDrugsDepot")
	//@ResponseBody
	public Result addDrugsDepotOfDefault(String drugName,String unit,String doctorId){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		if(StringUtils.isBlank(doctorId)){
			doctorId=doctorModel.getDoctorId();
		}
		ValidateTool.checkParamIsNull(drugName, "drugName");
		ValidateTool.checkParamIsNull(unit, "unit");
		DrugsDepotPO drugsDepotPO = this.drugsMemberServiceI.addDrugsDepotOfDefault(drugName, unit, doctorId);
		logger.info("[addDrugsDepot]新增药品记录:"+ JSON.toJSON(drugsDepotPO));
		return Result.ok();
	}

	/**
	 * 获取患者最新一条用药方案
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @param doctorId
	 * @param page
	 * @return
	 */
	@RequestMapping("/getDrugsMemberNew")
	@ResponseBody
	public Result getDrugsMemberNew(String memberId,String doctorId){
		ValidateTool.checkParamIsNull(memberId, "memberId");
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		if(StringUtils.isBlank(doctorId)){
			doctorId=doctorModel.getDoctorId();
		}
		//结果是分页
		MemberDrugItemPO drugsMemberPO = this.drugsMemberServiceI.getDrugsMemberNew(memberId, doctorId);
		logger.info("[getDrugsMemberNew]获取患者最新一条用药方案:"+ JSON.toJSON(drugsMemberPO));
		return Result.ok(drugsMemberPO);
	}

	/**
	 * 获取患者用药方案
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @param doctorId
	 * @param page
	 * @return
	 */
	@RequestMapping("/getMemberDrugItem")
	@ResponseBody
	public Result getMemberDrugItem(String id){
		ValidateTool.checkParamIsNull(id, "id");
			//结果是分页
		MemberDrugItemPO drugsMemberPO = this.drugsMemberServiceI.getMemberDrugItem(id);
		logger.info("[getMemberDrugItem]分页加载患者用药列表:"+ JSON.toJSON(drugsMemberPO));
		return Result.ok(drugsMemberPO);
	}


	/**
	 * 分页加载患者用药方案列表
	 * @param memberId 患者编号
	 * @param doctorId 团队编号
	 * @param page
	 * @return
	 */
	@RequestMapping("/loadMemberDrugsList")
	@ResponseBody
	public Result loadMemberDrugsList(String memberId,String doctorId,Integer dType, PageRequest page){
		ValidateTool.checkParamIsNull(memberId, "memberId");
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		String teamId = doctorId;
		if(StringUtils.isBlank(doctorId)){
			teamId=doctorModel.getDoctorId();
		}
		//结果是分页
		PageResult<DrugsMemberVO> pageResult = this.drugsMemberServiceI.listDrugsMemberPage(memberId, null, teamId,dType, page,null,null);
		logger.info("[loadMemberDrugsList]分页加载患者用药列表:"+ JSON.toJSON(pageResult));
		return Result.ok(pageResult);
	}


	/**
	 * 新增患者用药方案
	 * @apiDescription 下发用药方案，由于修改用药方药的操作也会产生一个新的用药方案，所以修改用药方案也使用该接口，传oldSchemeId用于修改旧的方案
	 * @apiParam {string} memberId 患者id
	 * @apiParam {string} startDt 开始时间
	 * @apiParam {string} endDt 结束时间
	 * @apiParam {string} drugJson 用药详细情况 [{"cycle":0,"dayTime":2,"drugName":"阿司匹林","endDt":"2017-05-06","remark":"我是备注","startDt":"2017-04-12","timeList":[{"num":1,"timeCode":"breakfast","timeNodes":"1"},{"num":2,"timeCode":"sleep","timeNodes":"1"}],"unit":"片"}]
	 * @apiParam {string} oldSchemeId 旧的用药方案id(当修改时传)
	 * @return
	 */
	@RequestMapping("/addDrugsMember")
	@ResponseBody
	public Result addDrugsMember(String memberId,String doctorId, String drugType,String schemeName
			,String drugJson,String startDt,String endDt,String oldSchemeId){

		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(memberId, "memberId");
		ValidateTool.checkParamIsNull(drugJson, "drugJson");
		ValidateTool.checkParamIsNull(startDt, "startDt");
		ValidateTool.checkParamIsNull(endDt, "endDt");
		this.drugsMemberServiceI.addDrugsMember(memberId, doctorModel.getDoctorId() ,drugType, schemeName, drugJson,
				startDt, endDt, doctorId, oldSchemeId);
		return Result.ok();
	}

}
