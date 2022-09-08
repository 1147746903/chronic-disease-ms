package com.comvee.cdms.dialogue.controller.web;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.dto.*;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.po.ReplyTextPO;
import com.comvee.cdms.dialogue.po.UnReadDialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.dialogue.vo.AddDialogueReturnVO;
import com.comvee.cdms.dialogue.vo.ListDialogueLatestVO;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodsugar.mapper.DyBloodSugarReportMapper;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author: suyz
 * @date: 2018/10/25
 */
@RestController
@RequestMapping("/web/dialogue")
@RequiresUser
public class WebDialogueController {

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private DyBloodSugarReportMapper reportMapper;

    /**
     * 加载医生最新对话列表
     * @param pr
     * @param doctorDialogueLatestSearchDTO
     * @return
     */
    @RequestMapping("listDialogueLatest")
    public Result listDialogueLatest(PageRequest pr, DoctorDialogueLatestSearchDTO doctorDialogueLatestSearchDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        doctorDialogueLatestSearchDTO.setDoctorList(doctorSessionBO.getRelateDoctorList());
        ListDialogueLatestVO listDialogueLatestDoctorVO = this.dialogueService.listDoctorDialogueLatestWithSearch(doctorDialogueLatestSearchDTO, pr);
        return new Result(listDialogueLatestDoctorVO);
    }

    /**
     * 加载对话
     * @param pr
     * @param listDoctorDialogueDTO
     * @return
     */
    @RequestMapping("listDialogue")
    public Result listDialogue(PageRequest pr, @Validated ListDoctorDialogueDTO listDoctorDialogueDTO){
        DoctorSessionBO sessionBO = SessionTool.getWebSession();
        ListDialogueServiceDTO listDialogueServiceDTO = new ListDialogueServiceDTO();
        BeanUtils.copyProperties(listDialogueServiceDTO, listDoctorDialogueDTO);
        listDialogueServiceDTO.setDoctorId(listDoctorDialogueDTO.getDoctorId());
        listDialogueServiceDTO.setBeDelete(0);
        List<Integer> showClientList = new ArrayList<>();
        showClientList.add(DialogueConstant.DIALOGUE_SHOW_CLIENT_DOCTOR);
        showClientList.add(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        listDialogueServiceDTO.setShowClientList(showClientList);
        Map<String,Object> map = this.dialogueService.listDialogue(pr, listDialogueServiceDTO);
        PageResult pageResult = (PageResult) map.get("pageResult");
        if(pageResult.getTotalRows() > 0){
            //清除小红点
            this.dialogueService.clearUnread(listDialogueServiceDTO.getMemberId(), listDialogueServiceDTO.getDoctorId(), DialogueConstant.DIALOGUE_VISIT_OWNER_DOCTOR);
        }
        return new Result(map);
    }

    /**
     * 添加咨询
     * @param addDialogueDoctorDTO
     * @return
     */
    @RequestMapping("addDialogue")
    public Result addDialogue(@Validated AddDialogueDoctorDTO addDialogueDoctorDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        AddDialogueServiceDTO addDialogueServiceDTO = new AddDialogueServiceDTO();
        BeanUtils.copyProperties(addDialogueServiceDTO, addDialogueDoctorDTO);
        addDialogueServiceDTO.setDoctorId(addDialogueDoctorDTO.getDoctorId());
        addDialogueServiceDTO.setSenderId(doctorSessionBO.getDoctorId());
        addDialogueServiceDTO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        AddDialogueReturnVO addDialogueReturnVO = this.dialogueService.sendDialogue(addDialogueServiceDTO);
        return new Result(addDialogueReturnVO);
    }

    /**
     * 统计医生未读消息数
     * @return
     */
    @RequestMapping("countUnRead")
    public Result countUnRead(){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        UnReadDialogueDTO unReadDialogueDTO = new UnReadDialogueDTO();
        unReadDialogueDTO.setDoctorId(doctorSessionBO.getDoctorId());
        UnReadDialoguePO unReadDialoguePO = this.dialogueService.countUnRead(unReadDialogueDTO);
        return new Result(unReadDialoguePO.getMemberUnRead());
    }


    /**
     * 医生撤回消息
     * @param  sid 消息id
     * @param  memberId 患者编号
     * @param  doctorId 医生id
     * @return
     */
    @RequestMapping("recallDocDialogue")
    public Result recallDocDialogue(String sid,String memberId, String doctorId){
        ValidateTool.checkParamIsNull(sid, "sid");
        ValidateTool.checkParamIsNull(memberId, "memberId");
        ValidateTool.checkParamIsNull(doctorId, "doctorId");

        AddDialogueReturnVO addDialogueReturnVO = this.dialogueService.recallDocDialogue(sid, memberId, doctorId);
        return Result.ok(addDialogueReturnVO);
    }

    /**
     * 加载医生回复文案列表
     * @param doctorId
     * @param hospitalId
     * @param departmentId
     * @param pr
     * @return
     */
    @RequestMapping("listReplyText")
    public Result listReplyText(ListReplyTextDTO listReplyTextDTO){
        DoctorSessionBO bo = SessionTool.getWebSession();
        if (!StringUtils.isBlank(listReplyTextDTO.getHospitalId())){
            listReplyTextDTO.setHospitalId(listReplyTextDTO.getHospitalId());
            if (!listReplyTextDTO.getHospitalId().equals(bo.getHospitalId())){
                listReplyTextDTO.setSwitchFlag(true);
            }
        }else{
            listReplyTextDTO.setHospitalId(bo.getHospitalId());
        }
        List<ReplyTextPO> result = this.dialogueService.listReplyText(listReplyTextDTO);
        return new Result(result);
    }

    /**
     * 添加医生自定义回复文案
     * @param dto
     * @return
     */
    @RequestMapping("addReplyText")
    public Result addReplyText(ReplyTextDTO dto){
        ValidateTool.checkParameterIsNull("doctorId",dto.getDoctorId());
        ValidateTool.checkParameterIsNull("hospitalId",dto.getHospitalId());
        ValidateTool.checkParameterIsNull("departmentId",dto.getDepartmentId());
        ValidateTool.checkParameterIsNull("textType",dto.getTextType());
        DoctorSessionBO bo = SessionTool.getWebSession();
        dto.setCreatorId(bo.getDoctorId());
        String sid = this.dialogueService.addReplyText(dto);
        return new Result(sid);
    }

    /**
     * 修改医生自定义回复文案
     * @param dto
     * @return
     */
    @RequestMapping("modifyReplyText")
    public Result modifyReplyText(ReplyTextDTO dto){
        ValidateTool.checkParameterIsNull("sid",dto.getSid());
        ValidateTool.checkParameterIsNull("textType",dto.getTextType());
        this.dialogueService.modifyReplyText(dto);
        return new Result("修改成功");
    }

    /**
     * 删除医生自定义回复文案
     * @param sid
     * @return
     */
    @RequestMapping("delReplayText")
    public Result delReplayText(String sid) {
        ValidateTool.checkParameterIsNull("sid",sid);
        this.dialogueService.delReplayText(sid);
        return new Result("删除成功");
    }

    /**
     * 根据医生自定义回复文案id获取文案信息
     * @param sid
     * @return
     */
    @RequestMapping("getReplyTextById")
    public Result getReplyTextById(String sid) {
        ValidateTool.checkParameterIsNull("sid",sid);
        ReplyTextPO textPO = this.dialogueService.getReplyTextById(sid);
        return new Result(textPO);
    }


    /**
     * path /web/dialogue/sendDyReportDialogue.do
     * 动态血糖图谱解读报告下发通知
     * @param memberId 患者id
     * @param reportId 报告id
     * @return
     */
    @RequestMapping("sendDyReportDialogue")
    public Result sendReportMsg(String memberId, String reportId){
        DialoguePO dialoguePO = new DialoguePO();
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        String msg = "动态血糖图谱解读报告下发通知";
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("title","动态血糖图谱解读报告下发通知");
        dataMap.put("content","医生为您下发了图谱解读报告");
        dataMap.put("reportId", reportId);
        dataMap.put("date", DateHelper.dateToString(new Date()));
        dialoguePO.setDoctorId(doctorSessionBO.getDoctorId());
        dialoguePO.setDataStr(JSON.toJSONString(dataMap));
        dialoguePO.setMemberId(memberId);
        dialoguePO.setMsgType(DialogueConstant.DIALOGUE_DY_REPORT_MSG_TYPE);
        dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        dialoguePO.setPatientMsg(msg);
        dialoguePO.setDoctorMsg(msg);
        dialoguePO.setSenderId(doctorSessionBO.getDoctorId());
        dialoguePO.setSendTimestamp(System.currentTimeMillis());
        dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        dialoguePO.setUpdateTimestamp(System.currentTimeMillis());
        dialoguePO.setForeignId(Constant.DEFAULT_FOREIGN_ID);
        reportMapper.setReportValid(reportId);
        return Result.ok(dialogueService.addDialogue(dialoguePO));
    }


}
