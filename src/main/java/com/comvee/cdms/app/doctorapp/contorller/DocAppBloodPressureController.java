package com.comvee.cdms.app.doctorapp.contorller;

import com.comvee.cdms.app.doctorapp.service.BloodPressureServiceI;
import com.comvee.cdms.app.doctorapp.vo.MobileDefualtVO;
import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBloodPressureDTO;
import com.comvee.cdms.sign.dto.AddBloodPressureServiceDTO;
import com.comvee.cdms.sign.dto.ListBmiDTO;
import com.comvee.cdms.sign.service.BloodPressureService;
import com.comvee.cdms.sign.service.BmiService;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/docapp/bloodpressure")
public class DocAppBloodPressureController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BloodPressureServiceI bloodPressureService;

	@Autowired
	private BloodPressureService bloodPressureServiceV2;
	
	@Autowired
	private BmiService bmiService;
	
	/**
	 * 获取血压记录（分页） + BMI + 糖化
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * BMI=1 糖化=2   血压=3
	 * @param page
	 * @return
	 */
	@RequestMapping("/getGraphsByPage")
	@ResponseBody
	public Result getGraphsByPage(MobileDefualtVO mobileDefualtVO,
								  HttpServletRequest request, PageRequest page,
								  String memberId, String paramKey){
		Object result = null;
		//BMI
		if(paramKey.equals("1")) {
			ListBmiDTO listBmiDTO = new ListBmiDTO();
			listBmiDTO.setMemberId(memberId);
			result = this.bloodPressureService.getPagientBmiList(page, listBmiDTO);
			// 糖化
		}else if(paramKey.equals("2")){
			//血压
		}else if(paramKey.equals("3")){
			result = this.bloodPressureService.getPatientBloodPressure(page, memberId);
		}

		return Result.ok(result);
	}

	@RequestMapping("addBloodPressure")
	@ResponseBody
	public Result addBloodPressure( AddBloodPressureServiceDTO addBloodSugarServiceDTO){
		DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getMemberId(), "memberId");
		ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getMemberId(), "dbp");
		ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getMemberId(), "sbp");
		ValidateTool.checkParamIsNull(addBloodSugarServiceDTO.getMemberId(), "recordTime");
		addBloodSugarServiceDTO.setOperatorId(doctorSessionBO.getDoctorId());
		addBloodSugarServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
		if (null == addBloodSugarServiceDTO.getOrigin()){
			addBloodSugarServiceDTO.setOrigin(CheckoutConstant.RECORD_ORIGIN_PATIENT_WECHAT);
		}
		String sid = this.bloodPressureServiceV2.addBloodPressureForWechat(addBloodSugarServiceDTO);
		return new Result(sid);
	}
}
