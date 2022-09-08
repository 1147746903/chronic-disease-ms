package com.comvee.cdms.app.doctorapp.contorller;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.app.doctorapp.model.app.DialogueLatestForDoctorModel;
import com.comvee.cdms.app.doctorapp.service.DialogueServiceI;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.dto.*;
import com.comvee.cdms.dialogue.po.DialogueGroupPO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.dialogue.vo.AddDialogueReturnVO;
import com.comvee.cdms.dialogue.vo.ListDialogueLatestVO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/docapp/dialogue")
public class DocAppDialogueController {

	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DialogueService dialogueService;//dialogueService web端的service
	
	@Autowired
	private DialogueServiceI dialogueServiceI;	//dialogueService app端的service
	
	
	/**
	 * 首次加载对话列表 + 刷新也包含进去
	 * @param page
	 * @param refreshTimeStamp
	 * @return
	 */
	@RequestMapping("/doctorHomePage")
	@ResponseBody
	public Result doctorHomePage(PageRequest page , Long refreshTimeStamp){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ListDialogueLatestMapperDTO listDialogueLatestMapperDTO = new ListDialogueLatestMapperDTO();
		listDialogueLatestMapperDTO.setBeDelete(0);
		listDialogueLatestMapperDTO.setDoctorRefreshTime(refreshTimeStamp);//包含刷新 第一次传空。
		listDialogueLatestMapperDTO.setDoctorList(doctorModel.getRelateDoctorList());
		ListDialogueLatestVO<DialogueLatestForDoctorModel> list = this.dialogueServiceI.listDialogueLatest( page , listDialogueLatestMapperDTO);
		logger.info("[listDialogueLatest]加载消息列表:"+ JSON.toJSON(list));
		return Result.ok(list);
	}
	
	@RequestMapping("/refreshHomePage")
	@ResponseBody
	public Result refreshHomePage(PageRequest page,Long returnDate){
		DoctorSessionBO ddModel = SessionTool.getWebSession();
		Map<String,Object> map = this.dialogueServiceI.refreshHomePage(page, ddModel.getRelateDoctorList(),returnDate);
		return Result.ok(map);
	}	

	
	/**
	 * 删除医生患者对话
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/deleteHomeNews")
	@ResponseBody		
	public Result deleteHomeNews(String memberId){
		ValidateTool.checkParamIsNull(memberId, "memberId");
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		this.dialogueServiceI.deleteHomeNews(doctorModel.getDoctorId(), memberId);
		return Result.ok();
	}	
	
	/**
	 * 清除消息小红点
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/removeHomeCount")
	@ResponseBody	
	public Result removeHomeCount(String memberId){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		this.dialogueServiceI.removeHomeCount(doctorModel.getDoctorId(), memberId);
		return Result.ok();
	}	
	
	/**
	 * 添加消息对话
	 * @param mobileDefualtVO
	 * @param request
     * @apiParam {String} answerContent 内容
     * @apiParam {Integer} attachType 内容类型 1 文本 2 图片 3 语音
     * app端传来的这三种附件类型  存字段msg_type = 1   
     * 血糖监测发送 通知卡片 存msg_type = 6监测方案
     * @apiParam {String} attachUrl URL
     * @apiParam {String} memberId 患者编号
     * @apiParam {Long} voiceMins 语音消息时长
     * @apiParam {String} msgType  消息类型 1普通对话 2 体征异常 3管理处方 4随访消息  5控制目标 6监测方案 11系统消息(普通)13 患教知识
     * @apiSuccess {DialogueReturnModel} body 返回结构体
     * @apiSuccess {Integer} body.sid 消息编号
     * @apiSuccess {Integer} body.returnDate 消息刷新时间
	 * @return
	 */
	@RequestMapping("/addDocDialogue")
	@ResponseBody
	public Result addDocDialogue(String answerContent,Integer attachType,
			String attachUrl,String memberId,
            Long voiceMins, String doctorId){

		ValidateTool.checkParamIsNull(attachType, "attachType");
		ValidateTool.checkParamIsNull(attachType, "memberId");
		ValidateTool.checkParamIsNull(answerContent, "answerContent");

		DoctorSessionBO doctorModel = SessionTool.getWebSession();

		//将app 旧的参数转换成本系统的参数
		AddDialogueServiceDTO addDialogueServiceDTO = new AddDialogueServiceDTO();
		addDialogueServiceDTO.setMemberId(memberId);
		//消息内容
		addDialogueServiceDTO.setMsg(answerContent);
		//默认文本1  消息类型  1 文本 2 图片 3 语音
		addDialogueServiceDTO.setTextType(attachType);
		addDialogueServiceDTO.setAttachUrl(attachUrl);
		addDialogueServiceDTO.setVoiceLength(voiceMins);
		addDialogueServiceDTO.setDoctorId(doctorId);
		addDialogueServiceDTO.setSenderId(doctorModel.getDoctorId());
		addDialogueServiceDTO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
		AddDialogueReturnVO addDialogueReturnVO = this.dialogueService.sendDialogue(addDialogueServiceDTO);

		return Result.ok(addDialogueReturnVO);
	}

	/**
	 * 医生撤回消息
	 * @apiParam {String} sid 消息id
	 * @apiParam {String} memberId 患者编号
	 * @apiParam {String} doctorId 医生id
	 * @apiSuccess {DialogueReturnModel} body 返回结构体
	 * @apiSuccess {Integer} body.sid 消息编号
	 * @apiSuccess {Integer} body.returnDate 消息刷新时间
	 * @return
	 */
	@RequestMapping("/recallDocDialogue")
	@ResponseBody
	public Result recallDocDialogue(String sid,String memberId, String doctorId){
		ValidateTool.checkParamIsNull(sid, "sid");
		ValidateTool.checkParamIsNull(memberId, "memberId");
		ValidateTool.checkParamIsNull(doctorId, "doctorId");

		AddDialogueReturnVO addDialogueReturnVO = this.dialogueServiceI.recallDocDialogue(sid, memberId, doctorId);
		return Result.ok(addDialogueReturnVO);
	}
	
	/**
	 * 	加载最新通知 - 置顶的消息   -- 通知
	 * @param mobileDefualtVO
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadLatestNotification")
	@ResponseBody
	public Result loadLatestNotification(){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		Map<String , Object> map = this.dialogueServiceI.loadLatestNotification(doctorModel.getDoctorId());
		return Result.ok(map);
	}		
	
	/**
	 * 
	 * 加载医生 - 患者对话
	 * @param mobileDefualtVO
	 * @param request
	 * @param page
	 * @param memberId
	 * @param upTimeStamp 加载旧的数据 <
	 * @param downTimeStamp > 加载新对话
	 * @return
	 */
	@RequestMapping("/docMemberDialogue")
	@ResponseBody
	public Result docMemberDialogue( PageRequest page, String memberId
			,String upTimeStamp, String downTimeStamp, String doctorId){
		ValidateTool.checkParamIsNull(memberId, "memberId");
		DoctorSessionBO doctorModel = SessionTool.getWebSession();

		ListDialogueServiceDTO listDialogueServiceDTO = new ListDialogueServiceDTO();
		listDialogueServiceDTO.setDoctorId(doctorId);
		listDialogueServiceDTO.setMemberId(memberId);
		listDialogueServiceDTO.setBeDelete(0);
		List<Integer> showClientList = new ArrayList<>();
		showClientList.add(DialogueConstant.DIALOGUE_SHOW_CLIENT_DOCTOR);
		showClientList.add(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
		listDialogueServiceDTO.setShowClientList(showClientList);

		listDialogueServiceDTO.setUpTimeStamp(stringTimeParseLong(upTimeStamp));
		listDialogueServiceDTO.setDownTimeStamp(stringTimeParseLong(downTimeStamp));
		Map<String,Object> map = this.dialogueServiceI.listDialogue(page, listDialogueServiceDTO);
		//清除小红点
		this.dialogueService.clearUnread(memberId, doctorModel.getDoctorId(), DialogueConstant.DIALOGUE_VISIT_OWNER_DOCTOR);

		return Result.ok(map);
	}

	/**
	 * 处理ios时间戳可能带有小数点的问题
	 * @param timestamp
	 * @return
	 */
	private Long stringTimeParseLong(String timestamp){
		if(StringUtils.isBlank(timestamp)){
			return null;
		}
		int index = timestamp.indexOf(".");
		if(index > 0){
			timestamp = timestamp.substring(0, index);
		}
		return Long.parseLong(timestamp);
	}

	/**
	 * 载最新的对话消息
	 * @param mobileDefualtVO
	 * @param request
	 * @param memberId
	 * @param returnDate  //本次项目改成时间戳
	 * @return
	 */
	@RequestMapping("/refreshDialogue")
	@ResponseBody
	public Result refreshDialogue(PageRequest page,
			String memberId,String returnDate){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ValidateTool.checkParamIsNull(memberId, "memberId");

		ListDialogueServiceDTO listDialogueServiceDTO = new ListDialogueServiceDTO();
		listDialogueServiceDTO.setDoctorId(doctorModel.getDoctorId());
		listDialogueServiceDTO.setMemberId(memberId);
		listDialogueServiceDTO.setBeDelete(0);
		listDialogueServiceDTO.setUpTimeStamp(stringTimeParseLong(returnDate));
		Map<String,Object> map = this.dialogueService.listDialogue(page, listDialogueServiceDTO);

		return Result.ok(map);
	}

	/**
	 * 添加群发消息
	 * @param addDialogueGroupDTO
	 * @return
	 */
	@RequestMapping("addDialogueGroup")
	@ResponseBody
	public Result addDialogueGroup(AddDialogueGroupDTO addDialogueGroupDTO){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		this.dialogueService.addDialogueGroup(addDialogueGroupDTO, doctorModel.getDoctorId());
		return Result.ok();
	}

	/**
	 * 加载消息群发列表
	 * @param pr
	 * @return
	 */
	@RequestMapping("listDialogueGroup")
	@ResponseBody
	public Result listDialogueGroup(PageRequest pr){
		DoctorSessionBO doctorModel = SessionTool.getWebSession();
		ListDialogueGroupDTO listDialogueGroupDTO = new ListDialogueGroupDTO();
		listDialogueGroupDTO.setDoctorList(doctorModel.getRelateDoctorList());
		PageResult<DialogueGroupPO> pageResult = this.dialogueService.listDialogueGroup(pr, listDialogueGroupDTO);
		return Result.ok(pageResult);
	}
}
