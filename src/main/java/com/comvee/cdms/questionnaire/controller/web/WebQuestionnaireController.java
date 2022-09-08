package com.comvee.cdms.questionnaire.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.questionnaire.model.dto.*;
import com.comvee.cdms.questionnaire.model.po.QuestionnairePO;
import com.comvee.cdms.questionnaire.service.QuestionnaireService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/3/14
 * 问卷控制层
 */
@RestController
@RequestMapping("/web/questionnaire")
public class WebQuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    /**
     * 加载患者的问卷
     * @param listMemberQuestionnaireParam
     * @param pr
     * @return
     */
    @RequestMapping("/listMemberQuestionnaire")
    public Result listMemberQuestionnaire(@Validated ListQuestionnaireDTO  listQuestionnaireDTO, PageRequest pr){
        PageResult pageResult = this.questionnaireService.listQuestionnaire(listQuestionnaireDTO , pr);
        return Result.ok(pageResult);
    }

    /**
     * 新增问卷
     * @return
     */
    @RequestMapping("/addQuestionnaire")
    public Result addQuestionnaire(@Validated AddQuestionnaireDTO addQuestionnaireDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addQuestionnaireDTO.setOperatorId(doctorSessionBO.getDoctorId());
        String sid = this.questionnaireService.addQuestionnaire(addQuestionnaireDTO);
        return Result.ok(sid);
    }

    /**
     * 根据问卷id获取问卷内容
     * @param sid
     * @return
     */
    @RequestMapping("/getQuestionnaireById")
    public Result getQuestionnaireById(String sid){
        ValidateTool.checkParamIsNull(sid , "sid");
        QuestionnairePO questionnairePO = this.questionnaireService.getQuestionnaireById(sid);
        return Result.ok(questionnairePO);
    }

    /**
     * 问卷保存草稿
     * @return
     */
    @RequestMapping("/saveQuestionnaire")
    public Result saveQuestionnaire(@Validated SaveQuestionnaireDTO saveQuestionnaireDTO){
        this.questionnaireService.saveQuestionnaire(saveQuestionnaireDTO);
        return Result.ok();
    }

    /**f
     * 提交问卷
     * @return
     */
    @RequestMapping("/commitQuestionnaire")
    public Result commitQuestionnaire(@Validated CommitQuestionnaireDTO commitQuestionnaireDTO){
        this.questionnaireService.commitQuestionnaire(commitQuestionnaireDTO);
        return Result.ok();
    }

    /**
     * 问卷评估
     * @return
     */
    @RequestMapping("/assessQuestionnaire")
    public Result assessQuestionnaire(@Validated AssessQuestionnaireDTO assessQuestionnaireDTO){
        JSONObject jsonObject = this.questionnaireService.assessQuestionnaire(assessQuestionnaireDTO);
        return Result.ok(jsonObject);
    }

    /**
     * 删除问卷
     * @return
     */
    @RequestMapping("/deleteQuestionnaire")
    public Result deleteQuestionnaire(String sid){
        ValidateTool.checkParamIsNull(sid , "sid");
        this.questionnaireService.deleteQuestionnaire(sid);
        return Result.ok();
    }

    /**
     * 获取患者最近一次 问卷得分
     * @param listQuestionnaireDTO
     */
    @RequestMapping("/getMemberQuesScore")
    public Result getMemberQuesScore(ListQuestionnaireDTO listQuestionnaireDT){
        listQuestionnaireDT.setDoctorId(null);
        List<Map<String, Object>> re = this.questionnaireService.getMemberQuesScore(listQuestionnaireDT);
        return Result.ok(re);
    }

    /**
     * 根据自定义随访id,加载随访里的问卷
     * @param followCustomId
     * @return
     */
    @RequestMapping("getQuestionnaireByFollowId")
    public Result getQuestionnaireByFollowId(String type, String doctorId,String memberId,String followContentId){
        ValidateTool.checkParameterIsNull("followContentId",followContentId);
        DoctorSessionBO doctor = SessionTool.getWebSession();
        List<QuestionnairePO> list = this.questionnaireService.getQuestionnaire(type,doctor.getDoctorId(),memberId,followContentId);
        return new Result(list);
    }

}
