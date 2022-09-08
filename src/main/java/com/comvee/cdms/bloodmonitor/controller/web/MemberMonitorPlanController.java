package com.comvee.cdms.bloodmonitor.controller.web;

import com.comvee.cdms.bloodmonitor.dto.*;
import com.comvee.cdms.bloodmonitor.model.SugarMonitorTemplatePO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.prescription.vo.MemberMonitorVO;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 李左河
 *
 */
@RestController
@RequestMapping("/web/monitor")
public class MemberMonitorPlanController {

	@Autowired
	@Qualifier("memberMonitorPlanService")
	private MemberMonitorPlanServiceI memberMonitorPlanService;
	
	/**
	 * 停止医嘱
	 * @param memberId
	 * @return
	 * 李左河
	 */
	@RequestMapping("stopMonitor")
	public Result stopMonitor(String memberId) {
		ValidateTool.checkParameterIsNull("memberId",memberId);
		this.memberMonitorPlanService.stopMonitor(memberId);
		Result result = new Result("0","获取成功",true);
		return result; 
	}

	/**
	 * 保存监测方案
	 * @return
	 * 李左河
	 */
	@RequestMapping("saveMonitor")
	public Result saveMonitor(MemberMonitorPlanPO memberMonitorPlan) {
		ValidateTool.checkParameterIsNull("memberId",memberMonitorPlan.getMemberId());
		memberMonitorPlan.setOperationType(1);
		MemberMonitorVO str = this.memberMonitorPlanService.saveMonitor(memberMonitorPlan);
		return Result.ok(str);

	}

	/**
	 * 点击是保存监测方案
	 * @return
	 * 李左河
	 */
	@RequestMapping("saveMonitorPlan")
	public Result saveMonitorPlan(MemberMonitorPlanPO memberMonitorPlan) {
		ValidateTool.checkParameterIsNull("memberId",memberMonitorPlan.getMemberId());
		memberMonitorPlan.setOperationType(1);
		this.memberMonitorPlanService.saveMonitorPlan(memberMonitorPlan);
		return Result.ok();

	}

	/**
	 * 根据方案id获取监测方案
	 * @param planId
	 * @return
	 */
	@RequestMapping("getMemberMonitorById")
	public Result getMemberMonitorById(String planId){
		ValidateTool.checkParamIsNull(planId, "planId");
		GetMemberMonitorDTO getMemberMonitorDTO = new GetMemberMonitorDTO();
		getMemberMonitorDTO.setPlanId(planId);
		MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanService.getMemberMonitorPlan(getMemberMonitorDTO);
		return new Result(memberMonitorPlanPO);
	}

	/**
	 * 获取患者的监测方案
	 * @param memberId
	 * @return
	 */
	@RequestMapping("getMemberMonitorPlan")
	public Result getMemberMonitorPlan(String memberId,String doctorId,Integer eohType,String hospitalId){
		ValidateTool.checkParamIsNull(memberId, "memberId");
		boolean switchFlag = false;
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		if (StringUtils.isBlank(hospitalId)){
			hospitalId = doctorSessionBO.getHospitalId();
		}
		if (doctorSessionBO.getSwitchHospital() != null && doctorSessionBO.getSwitchHospital() == 1){  //有切换医院权限
			switchFlag = true;
		}
		Map<String,Object> re = this.memberMonitorPlanService.listMonitorPlanTemplate(1 ,eohType,memberId ,doctorId,hospitalId,switchFlag);
		return new Result(re);
	}

	/**
	 * 添加监测方案模板
	 * @param addMonitorTemplateDTO
	 * @return
	 */
	@RequestMapping("/addMonitorPlanTemplate")
	public Result addMonitorPlanTemplate(@Validated AddMonitorTemplateDTO addMonitorTemplateDTO){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		SugarMonitorTemplatePO sugarMonitorTemplatePO = new SugarMonitorTemplatePO();
		BeanUtils.copyProperties(sugarMonitorTemplatePO ,addMonitorTemplateDTO);
		sugarMonitorTemplatePO.setOperatorId(doctorSessionBO.getDoctorId());
		String sid = this.memberMonitorPlanService.addMonitorPlanTemplate(sugarMonitorTemplatePO);
		return Result.ok(sid);
	}

	/**
	 * 加载自定义监测方案
	 * @param doctorId
	 * @return
	 */
	@RequestMapping("/listCustomMonitorPlanTemplate")
	public Result listCustomMonitorPlanTemplate(String doctorId,Integer eohType){
		ValidateTool.checkParamIsNull(doctorId ,"doctorId");
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		List<SugarMonitorTemplatePO> list = this.memberMonitorPlanService.listCustomMonitorPlanTemplate(doctorId,doctorSessionBO.getHospitalId(),eohType);
		return Result.ok(list);
	}

	/**
	 * 修改自定义监测方案
	 * @param updateSugarMonitorTemplateDTO
	 * @return
	 */
	@RequestMapping("/updateCustomMonitorPlanTemplate")
	public Result updateCustomMonitorPlanTemplate(@Validated UpdateSugarMonitorTemplateDTO updateSugarMonitorTemplateDTO){
		SugarMonitorTemplatePO sugarMonitorTemplatePO = new SugarMonitorTemplatePO();
		BeanUtils.copyProperties(sugarMonitorTemplatePO ,updateSugarMonitorTemplateDTO);
		this.memberMonitorPlanService.updateCustomMonitorPlanTemplate(sugarMonitorTemplatePO);
		return Result.ok();
	}


	/**
	 * @api {post}/web/monitor/listOperateMonitorTemplate.do 加载模板管理医生模板
	 * @author 林雨堆
	 * @time 2020/03/23
	 * @apiName listOperateMonitorTemplate 加载模板管理医生模板
	 * @apiGroup WEB-V6.0.0-C
	 * @apiVersion 6.0.0
	 * @apiParam {String} doctorId 医护人员编号
	 * @apiParam {String} keyword 模版名称
	 * @apiParam {Integer} page 页码
	 * @apiParam {Integer} rows 页数
	 * @apiSampleRequest  http://192.168.7.203:8080/web/monitor/listOperateMonitorTemplate.do
	 *
	 * @apiSuccess {String} data.obj 返回对象
	 * @apiSuccess {Object} data.msg 状态信息
	 * @apiSuccess {Object} data.success
	 * @apiSuccess {Object} data.code 状态代码 0成功
	 */
	@RequestMapping("listOperateMonitorTemplate")
	public Result listOperateMonitorTemplate(String doctorId,PageRequest page,String keyword){
		ValidateTool.checkParamIsNull(doctorId ,"doctorId");
		PageResult<SugarMonitorTemplatePO> list = this.memberMonitorPlanService.listOperateMonitorTemplate(page, doctorId,keyword);
		return new Result(list);
	}


	/**
	 * 删除血糖监测模板
	 * @return
	 */
	@RequestMapping("deleteMonitorTemplate")
	public Result deleteMonitorTemplate(String ids){
		ValidateTool.checkParamIsNull(ids ,"ids");
		this.memberMonitorPlanService.deleteMonitorTemplate(ids);
		return Result.ok("删除成功");
	}

	/**
	 * 根据自定义血糖监测模板id获取模板详情
	 * @return
	 */
	@RequestMapping("getMonitorTemplateById")
	public Result getMonitorTemplateById(String sid){
		ValidateTool.checkParamIsNull(sid ,"sid");
		SugarMonitorTemplatePO po = this.memberMonitorPlanService.getMonitorTemplateById(sid);
		return new Result(po);
	}

}
