package com.comvee.cdms.app.doctorapp.contorller;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.questionnaire.model.dto.AssessQuestionnaireDTO;
import com.comvee.cdms.questionnaire.model.dto.SaveQuestionnaireDTO;
import com.comvee.cdms.questionnaire.model.po.QuestionnairePO;
import com.comvee.cdms.questionnaire.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**问卷控制层
 * @author wyc
 * @date 2019/5/20 10:27
 */
@RestController
@RequestMapping("/docapp/questionnaire")
public class DocAppQuestionnaireConntroller {

    @Autowired
    private QuestionnaireService questionnaireService;
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
     * 问卷保存
     * @return
     */
    @RequestMapping("/saveQuestionnaire")
    public Result saveQuestionnaire(@Validated SaveQuestionnaireDTO saveQuestionnaireDTO){
        this.questionnaireService.saveQuestionnaire(saveQuestionnaireDTO);
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
     * 根据自定义随访id,加载随访里的问卷
     * @param followCustomId
     * @return
     */
    @RequestMapping("getQuestionnaireByFollowId")
    public Result getQuestionnaireByFollowId(String followContentId){
        ValidateTool.checkParameterIsNull("followContentId",followContentId);
        List<QuestionnairePO> list = this.questionnaireService.getQuestionnaireByFollowId(followContentId);
        return new Result(list);
    }
}
