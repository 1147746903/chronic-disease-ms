package com.comvee.cdms.app.doctorapp.contorller;

import com.comvee.cdms.app.doctorapp.service.FollowServiceI;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.follow.model.FollowListModel;
import com.comvee.cdms.follow.po.FollowCustomerTemplatePO;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/docapp/follow")
public class DocAppFollowController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FollowServiceI followServiceI;

	@Autowired
	private com.comvee.cdms.follow.service.FollowServiceI followService;

	
	/**
	 * 加载患者管理处方列表
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @param page
	 * @return
	 */
	@RequestMapping("/selectFollowUpList")
	@ResponseBody
	public Result selectFollowUpList(String memberId, String type, String isDeal, PageRequest page){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
	    	//结果是分页
		PageResult<FollowListModel> pageResult = this.followServiceI.selectFollowUpList(memberId, type, isDeal, page ,doctorModel.getDoctorId());
		return Result.ok(pageResult);
	}


	//
	/**
	 * 获取回显信息
	 * @param memberId
	 * @param doctorId
	 * @return
	 */
	@RequestMapping("/getCustomerEchoInfo")
	@ResponseBody
	public Result getCustomerEchoInfo(String doctorId,String memberId,String followId){
		ValidateTool.checkParamIsNull(memberId ,"memberId");
		ValidateTool.checkParamIsNull(doctorId ,"doctorId");
		Map<String, Object> map = this.followService.getCustomerEchoInfo(doctorId, memberId,followId);
		return new Result(map);
	}

	/**
	 * 根据模板id查询模板信息
	 * @param sid
	 * @return
	 */
	@RequestMapping("/getTemplateById")
	@ResponseBody
	public Result getTemplateById(String sid) {
		ValidateTool.checkParameterIsNull(sid,"sid");
		FollowCustomerTemplatePO templatePO = this.followService.getTemplateById(sid);
		return new Result(templatePO);
	}


	@RequestMapping("/getFollowByLogId")
	@ResponseBody
	public Result getFollowByLogId(String followId,Integer type){
		return new Result(this.followService.getFollowById(followId,type));
	}



}
