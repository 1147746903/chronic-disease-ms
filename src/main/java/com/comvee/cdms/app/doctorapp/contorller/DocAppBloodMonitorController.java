package com.comvee.cdms.app.doctorapp.contorller;

import com.comvee.cdms.app.doctorapp.model.app.MemberMonitorPlanModel;
import com.comvee.cdms.app.doctorapp.service.BloodMonitorServiceI;
import com.comvee.cdms.app.doctorapp.vo.MobileDefualtVO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/docapp/bloodmonitor")
public class DocAppBloodMonitorController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BloodMonitorServiceI bloodMonitorService;
	@Autowired
	@Qualifier("memberMonitorPlanService")
	private MemberMonitorPlanServiceI memberMonitorPlanService;
	/**
	 * 加载患者血糖监测模板
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/loadSugarMonitorTemplates")
	@ResponseBody
	public Result loadSugarMonitorTemplates(MobileDefualtVO mobileDefualtVO,
											HttpServletRequest request,
											String memberId){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		//结果是分页
		MemberMonitorPlanModel resultModel = this.bloodMonitorService.loadSugarMonitorTemplates(memberId);

		return Result.ok(resultModel);
	}	
	
	/**
	 * 加载患者当前血糖方案 - 最新 - 在用的血糖方案
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/loadMemberSugarmonitorById")
	@ResponseBody
	public Result loadMemberSugarmonitorById(MobileDefualtVO mobileDefualtVO,
			HttpServletRequest request,
			String memberId){
		MemberMonitorPlanModel resultModel = this.bloodMonitorService.loadMemberSugarmonitorById(memberId);
		return Result.ok(resultModel);
	}	
	
	/**
	 *
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @param schemeName 方案名
	 * @param monitorScheme 方案 明细
	 * @param applicability
	 * @return
	 */
	@RequestMapping("/addSugarMonitor")
	@ResponseBody
	public Result addSugarMonitor(MobileDefualtVO mobileDefualtVO,
			String doctorId,
			String memberId,String schemeName,String monitorScheme,String applicability,
			String startMonitorDt,String endMonitorDt){
		ValidateTool.checkParamIsNull(doctorId, "doctorId");
		ValidateTool.checkParamIsNull(memberId, "memberId");
		ValidateTool.checkParamIsNull(schemeName, "schemeName");
		ValidateTool.checkParamIsNull(monitorScheme, "monitorScheme");

		DoctorSessionBO doctorModel  = SessionTool.getWebSession();

		MemberMonitorPlanPO memberMonitorPlan = new MemberMonitorPlanPO();
		memberMonitorPlan.setMemberId(memberId);
		memberMonitorPlan.setDoctorId(doctorId);
		memberMonitorPlan.setSenderId(doctorModel.getDoctorId());
		memberMonitorPlan.setPlanType(1);
		memberMonitorPlan.setPlanName(schemeName);
		memberMonitorPlan.setPlanDetail(monitorScheme);
		memberMonitorPlan.setApplyExplain(applicability);
		memberMonitorPlan.setInProgress(2);
		memberMonitorPlan.setIllnessType(1);
		memberMonitorPlan.setEohType(0);
		memberMonitorPlan.setStartMonitorDt(startMonitorDt);
		memberMonitorPlan.setEndMonitorDt(endMonitorDt);
		memberMonitorPlan.setOperationType(2);
		return Result.ok(this.bloodMonitorService.addSugarMonitor(memberMonitorPlan));
	}


	/**
	 * 点击是保存
	 * @param mobileDefualtVO
	 * @param doctorId
	 * @param memberId
	 * @param schemeName
	 * @param monitorScheme
	 * @param applicability
	 * @param startMonitorDt
	 * @param endMonitorDt
	 * @return
	 */
	@RequestMapping("/addSugarMonitorPlan")
	@ResponseBody
	public Result addSugarMonitorPlan(MobileDefualtVO mobileDefualtVO,
								  String doctorId,
								  String memberId,String schemeName,String monitorScheme,String applicability,
								  String startMonitorDt,String endMonitorDt,String planId,Integer type){
		ValidateTool.checkParamIsNull(doctorId, "doctorId");
		ValidateTool.checkParamIsNull(memberId, "memberId");
		ValidateTool.checkParamIsNull(schemeName, "schemeName");
		ValidateTool.checkParamIsNull(monitorScheme, "monitorScheme");

		DoctorSessionBO doctorModel  = SessionTool.getWebSession();

		MemberMonitorPlanPO memberMonitorPlan = new MemberMonitorPlanPO();
		memberMonitorPlan.setMemberId(memberId);
		memberMonitorPlan.setDoctorId(doctorId);
		memberMonitorPlan.setSenderId(doctorModel.getDoctorId());
		memberMonitorPlan.setPlanType(1);
		memberMonitorPlan.setPlanName(schemeName);
		memberMonitorPlan.setPlanDetail(monitorScheme);
		memberMonitorPlan.setApplyExplain(applicability);
		memberMonitorPlan.setInProgress(2);
		memberMonitorPlan.setIllnessType(1);
		memberMonitorPlan.setEohType(0);
		memberMonitorPlan.setStartMonitorDt(startMonitorDt);
		memberMonitorPlan.setEndMonitorDt(endMonitorDt);
		memberMonitorPlan.setOperationType(2);
		memberMonitorPlan.setPlanId(planId);
		memberMonitorPlan.setType(type);
		this.memberMonitorPlanService.saveMonitorPlan(memberMonitorPlan);
		return Result.ok();
	}	
	
	
}
