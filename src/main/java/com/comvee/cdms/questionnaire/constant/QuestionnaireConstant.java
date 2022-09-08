package com.comvee.cdms.questionnaire.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2019/3/18
 */
public class QuestionnaireConstant {

    /**
     * 问卷状态 1 未填写 2 草稿  3 完成
     */
    public static final int QUESTIONNAIRE_STATUS_NOT_FILL = 1;
    public static final int QUESTIONNAIRE_STATUS_DRAFT = 2;
    public static final int QUESTIONNAIRE_STATUS_FINISH = 3;

    /**
     * 问卷类型
     * 1 糖尿病知识测试 2 患者自我管理行为评估 3 糖尿病授权评分测试  4 糖尿病足风险评估 5 足部护理评估量表
     */
    public static final int QUESTIONNAIRE_TYPE_DIABETES_KNOWLEDGE = 1;
    public static final int QUESTIONNAIRE_TYPE_SELF_MANAGEMENT = 2;
    public static final int QUESTIONNAIRE_TYPE_DIABETES_AUTHORIZATION = 3;
    public static final int QUESTIONNAIRE_TYPE_DIABETIC_FOOT_RISK = 4;
    public static final int QUESTIONNAIRE_TYPE_FOOT_CARE = 5;


    /**
     * 问卷名称
     */
    public static final Map<Integer ,String> QUESTIONNAIRE_NAME_MAP = new HashMap<>();
    static {
        QUESTIONNAIRE_NAME_MAP.put(QUESTIONNAIRE_TYPE_DIABETES_KNOWLEDGE , "糖尿病知识测试");
        QUESTIONNAIRE_NAME_MAP.put(QUESTIONNAIRE_TYPE_SELF_MANAGEMENT , "患者自我管理行为评估");
        QUESTIONNAIRE_NAME_MAP.put(QUESTIONNAIRE_TYPE_DIABETES_AUTHORIZATION , "糖尿病授权评分测试");
        QUESTIONNAIRE_NAME_MAP.put(QUESTIONNAIRE_TYPE_DIABETIC_FOOT_RISK , "糖尿病足风险评估");
        QUESTIONNAIRE_NAME_MAP.put(QUESTIONNAIRE_TYPE_FOOT_CARE , "足部护理评估量表");
    }

    /**
     * 获取问卷名称
     * @param questionnaireType
     * @return
     */
    public static String questionnaireName(Integer questionnaireType){
        return QUESTIONNAIRE_NAME_MAP.getOrDefault(questionnaireType , "未命名");
    }
}
