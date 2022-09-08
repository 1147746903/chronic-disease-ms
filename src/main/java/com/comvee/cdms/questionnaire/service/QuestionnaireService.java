package com.comvee.cdms.questionnaire.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.questionnaire.model.dto.*;
import com.comvee.cdms.questionnaire.model.po.QuestionnairePO;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/3/14
 */
public interface QuestionnaireService {

    /**
     * 加载问卷列表
     * @param listQuestionnaireDTO
     * @param pr
     * @return
     */
    PageResult<QuestionnairePO> listQuestionnaire(ListQuestionnaireDTO listQuestionnaireDTO , PageRequest pr);

    /**
     * 根据问卷id获取问卷
     * @param sid
     * @return
     */
    QuestionnairePO getQuestionnaireById(String sid);

    /**
     * 新增问卷
     * @param addQuestionnaireDTO
     * @return
     */
    String addQuestionnaire(AddQuestionnaireDTO addQuestionnaireDTO);

    /**
     * 保存问卷
     * @param saveQuestionnaireDTO
     */
    void saveQuestionnaire(SaveQuestionnaireDTO saveQuestionnaireDTO);

    /**
     * 提交问卷
     * @param commitQuestionnaireDTO
     */
    void commitQuestionnaire(CommitQuestionnaireDTO commitQuestionnaireDTO);

    /**
     * 问卷评估
     * @param assessQuestionnaireDTO
     * @return
     */
    JSONObject assessQuestionnaire(AssessQuestionnaireDTO assessQuestionnaireDTO);

    /**
     * 删除问卷
     * @param sid
     */
    void deleteQuestionnaire(String sid);

    /**
     * 修改问卷
     * @param questionnairePO
     */
    void updateQuestionnaire(QuestionnairePO questionnairePO);

    /**
     * 获取患者最近一次 问卷得分
     * @param listQuestionnaireDTO
     */
    List<Map<String, Object>> getMemberQuesScore(ListQuestionnaireDTO listQuestionnaireDTO);

    //
    /**
     * 加载随访里的问卷,并且将已有的数据填充
     * @param followCustomId
     * @return
     */
    List<QuestionnairePO> getQuestionnaire(String type, String doctorId,String memberId,String followId);

    /**
     * 根据自定义随访id,加载随访里的问卷
     * @param followId
     * @return
     */
    List<QuestionnairePO> getQuestionnaireByFollowId(String followId);



    /**
     * 获取患者的最新问卷
     * @param questionnaireDTO
     * @return
     */
    QuestionnairePO getNewQuestionnaire(ListQuestionnaireDTO questionnaireDTO);

    /**
     * 修改自定义随访中提交后问卷的状态
     * @param followContentId
     */
    void updateQuestionnaireByFollowId(String followContentId);


}
