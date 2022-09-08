package com.comvee.cdms.app.doctorapp.contorller;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.app.doctorapp.model.app.PrescriptionModel;
import com.comvee.cdms.app.doctorapp.service.PrescriptionServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/docapp/prescription")
public class DocAppPrescriptionController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PrescriptionServiceI prescriptionService;
	/**
	 * 加载患者管理处方列表
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @param page
	 * @return
	 */
	@RequestMapping("/loadDoctorMemberPrescriptionList")
	@ResponseBody
	public Result loadDoctorMemberPrescriptionList(String memberId, PageRequest page){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(memberId, "memberId");

		//结果是分页
		PageResult<PrescriptionModel> pageResult = this.prescriptionService.loadDoctorMemberPrescriptionList(page, memberId , doctorModel.getDoctorId());
		logger.info("[loadDoctorMemberPrescriptionList]加载患者管理处方列表："+ JSON.toJSON(pageResult));

		return Result.ok(pageResult);
	}
}
