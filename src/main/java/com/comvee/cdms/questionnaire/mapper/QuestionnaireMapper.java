package com.comvee.cdms.questionnaire.mapper;

import com.comvee.cdms.questionnaire.model.dto.ListQuestionnaireDTO;
import com.comvee.cdms.questionnaire.model.po.QuestionnairePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/3/14
 */
public interface QuestionnaireMapper {

    /**
     * 根据id获取问卷内容
     * @param sid
     * @return
     */
    QuestionnairePO getQuestionnaireById(@Param("sid") String sid);

    /**
     * 新增问卷
     * @param questionnairePO
     */
    void addQuestionnaire(QuestionnairePO questionnairePO);

    /**
     * 修改问卷
     * @param questionnairePO
     */
    void updateQuestionnairePO(QuestionnairePO questionnairePO);

    /**
     * 加载问卷
     * @return
     */
    List<QuestionnairePO> listQuestionnaire(ListQuestionnaireDTO listQuestionnaireDTO);

    /**
     * 删除问卷
     * @param sid
     */
    void deleteQuestionnaire(@Param("sid")String sid);

    /**
     * 根据条件获取问卷
     * @return
     */
    QuestionnairePO getQuestionnaire(ListQuestionnaireDTO listQuestionnaireDTO);

    //
    /**
     * 根据自定义随访id,加载随访里的问卷
     * @param followCustomId
     * @return
     */
    List<QuestionnairePO> getQuestionnaireByFollowId(@Param("followContentId") String followContentId);

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
    void updateQuestionnaireByFollowId(@Param("followContentId") String followContentId);

}
