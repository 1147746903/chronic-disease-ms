package com.comvee.cdms.questionnaire.tool;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.questionnaire.constant.QuestionnaireConstant;
import com.comvee.cdms.questionnaire.model.bo.QuestionnaireContentBO;

import java.util.*;

/**
 * @author: suyz
 * @date: 2019/3/18
 * 问卷评估工具
 */
public class AssessQuestionnaireTool {

    /**
     * 问卷评估
     * @param contentJson
     * @param questionType
     * @return
     */
    public static JSONObject assessQuestionnaire(String contentJson ,int questionType){
        List<QuestionnaireContentBO> contentList = JSONObject.parseArray(contentJson ,QuestionnaireContentBO.class);
        JSONObject accessResult = null;
        switch (questionType){
            case QuestionnaireConstant.QUESTIONNAIRE_TYPE_DIABETES_KNOWLEDGE:
                accessResult = assessDiabetesKnowledgeTestQuestionnaire(contentList);
                break;
            case QuestionnaireConstant.QUESTIONNAIRE_TYPE_SELF_MANAGEMENT:
                accessResult = assessSelfManagement(contentList);
                break;
            case QuestionnaireConstant.QUESTIONNAIRE_TYPE_DIABETES_AUTHORIZATION:
                accessResult = assessDiabetesAuthorization(contentList);
                break;
            case QuestionnaireConstant.QUESTIONNAIRE_TYPE_DIABETIC_FOOT_RISK:
                accessResult = assessDiabeticFootRisk(contentList);
                break;
            case QuestionnaireConstant.QUESTIONNAIRE_TYPE_FOOT_CARE:
                accessResult = assessFootCare(contentList);
                break;
            default:
                break;
        };
        return accessResult;
    }

    /**
     * 足部护理评估量表
     * @param contentList
     * @return
     */
    private static JSONObject assessFootCare(List<QuestionnaireContentBO> contentList){
        int totalScore = 0;
        for(QuestionnaireContentBO questionnaireContentBO : contentList){
            if(FOOT_CARE_REVERSE_ENTRY.contains(questionnaireContentBO.getOrder())){
                totalScore += Math.abs(Integer.parseInt(questionnaireContentBO.getOption()) - 3);
            }else{
                totalScore += Integer.parseInt(questionnaireContentBO.getOption());
            }
        }
        float standardScore = Math.round((totalScore * 1.0f / 72) * 100);
        Integer level;
        if(standardScore >= 80f){
            level = ASSESS_LEVEl_GOOD;
        }else if(standardScore < 60f){
            level = ASSESS_LEVEl_BAD;
        }else{
            level = ASSESS_LEVEl_NORMAL;
        }
        JSONObject result = new JSONObject();
        result.put("assessLevel" , level);
        result.put("totalScore" , totalScore);
        result.put("standardScore" , standardScore);
        return result;
    }

    /**
     * 足部护理 - 反向条目
     */
    private final static Set<String> FOOT_CARE_REVERSE_ENTRY = new HashSet<>();
    static {
        FOOT_CARE_REVERSE_ENTRY.add("8");
        FOOT_CARE_REVERSE_ENTRY.add("10");
        FOOT_CARE_REVERSE_ENTRY.add("11");
        FOOT_CARE_REVERSE_ENTRY.add("13");
        FOOT_CARE_REVERSE_ENTRY.add("17");
        FOOT_CARE_REVERSE_ENTRY.add("18");
        FOOT_CARE_REVERSE_ENTRY.add("19");
        FOOT_CARE_REVERSE_ENTRY.add("20");
    }

    /**
     * 糖尿病足风险评估
     * @param contentList
     * @return
     */
    private static JSONObject assessDiabeticFootRisk(List<QuestionnaireContentBO> contentList){
        if(contentList == null || contentList.size() == 0){
            return null;
        }
        int riskLevel = 0;
        String options = contentList.get(0).getOption();
        List list = Arrays.asList(options.split("\\^"));
        Set<String> optionSet = new HashSet<>(list);
        if(optionSet.contains("4")|| optionSet.contains("5")
                || optionSet.contains("6")){
            riskLevel = FOOT_RISK_LEVEL_3;
        }else if(optionSet.contains("2")){
            riskLevel = FOOT_RISK_LEVEL_2;
        }else if(optionSet.contains("1")
                || optionSet.contains("3")){
            riskLevel = FOOT_RISK_LEVEL_1;
        }else  if(!optionSet.contains("1")
                && !optionSet.contains("2") && !optionSet.contains("3") ){
            riskLevel = FOOT_RISK_LEVEL_0;
        }
        FootAdvice footAdvice = FOOT_ADVICE_MAP.get(riskLevel);
        JSONObject result = new JSONObject();
        result.put("riskLevel" ,riskLevel);
        result.put("dealAdvice" , footAdvice.getDealAdvice());
        result.put("followAdvice" , footAdvice.getFollowAdvice());
        return result;
    }

    /**
     * 糖尿病授权评分
     * @param contentList
     * @return
     */
    private static JSONObject assessDiabetesAuthorization(List<QuestionnaireContentBO> contentList){
        int totalScore = 0;
        for(QuestionnaireContentBO questionnaireContentBO : contentList){
            totalScore += Integer.parseInt(questionnaireContentBO.getOption());
        }
        JSONObject result = new JSONObject();
        result.put("totalScore" ,totalScore * 4);
        return result;
    }

    /**
     * 患者自我管理行为评估
     * @param contentList
     * @return
     */
    private static JSONObject assessSelfManagement(List<QuestionnaireContentBO> contentList){
        int totalScore = 0;
        QuestionnaireContentBO questionnaireContentBO;
        for(int i = 1,length = contentList.size(); i <= length ; i++){
            questionnaireContentBO = contentList.get(i - 1);
            //第四题反向计分
            if(i == 4){
                totalScore += Math.abs(Integer.parseInt(questionnaireContentBO.getOption()) - 7);
            }else{
                totalScore += Integer.parseInt(questionnaireContentBO.getOption());
            }
        }
        float avg = totalScore * 1.0f / contentList.size() ;
        Integer level = null;
        if(avg >= 5.6f){
            level = ASSESS_LEVEl_GOOD;
        }else if(avg <= 4.1f){
            level = ASSESS_LEVEl_BAD;
        }else{
            level = ASSESS_LEVEl_NORMAL;
        }
        JSONObject result = new JSONObject();
        result.put("assessLevel" , level);
        result.put("totalScore" , totalScore);
        double reNum= (double) Math.round(avg * 10) / 10;
        result.put("avgScore" , reNum);
        return result;
    }

    /**
     * 糖尿病知识测试问卷
     * @param contentJsonObject
     * @return
     */
    private static JSONObject assessDiabetesKnowledgeTestQuestionnaire(List<QuestionnaireContentBO> contentList){
        int totalScore = 0;
        for(QuestionnaireContentBO contentBO : contentList){
            if(DIABETES_KNOWLEDGE_ANSWER.getOrDefault(contentBO.getOrder() , "empty")
                    .equals(contentBO.getOption())){
                totalScore ++;
            }
        }
        Integer level = null;
        if(totalScore > 20){
            level = ASSESS_LEVEl_GOOD;
        }else if(totalScore < 15){
            level = ASSESS_LEVEl_BAD;
        }else{
            level = ASSESS_LEVEl_NORMAL;
        }
        JSONObject result = new JSONObject();
        result.put("assessLevel" , level);
        result.put("totalScore" , totalScore);
        result.put("standardScore" , Math.round((totalScore * 1.0f / contentList.size()) * 100));
        return result;
    }

    /**
     * 糖尿病知识测试问卷 - 答案
     */
    private static final Map<String,String> DIABETES_KNOWLEDGE_ANSWER = new HashMap(23);
    static {
        DIABETES_KNOWLEDGE_ANSWER.put("1", "3");
        DIABETES_KNOWLEDGE_ANSWER.put("2", "2");
        DIABETES_KNOWLEDGE_ANSWER.put("3", "2");
        DIABETES_KNOWLEDGE_ANSWER.put("4", "3");
        DIABETES_KNOWLEDGE_ANSWER.put("5", "1");
        DIABETES_KNOWLEDGE_ANSWER.put("6", "1");
        DIABETES_KNOWLEDGE_ANSWER.put("7", "2");
        DIABETES_KNOWLEDGE_ANSWER.put("8", "3");
        DIABETES_KNOWLEDGE_ANSWER.put("9", "1");
        DIABETES_KNOWLEDGE_ANSWER.put("10", "4");
        DIABETES_KNOWLEDGE_ANSWER.put("11", "1");
        DIABETES_KNOWLEDGE_ANSWER.put("12", "3");
        DIABETES_KNOWLEDGE_ANSWER.put("13", "2");
        DIABETES_KNOWLEDGE_ANSWER.put("14", "4");
        DIABETES_KNOWLEDGE_ANSWER.put("15", "3");
        DIABETES_KNOWLEDGE_ANSWER.put("16", "4");
        DIABETES_KNOWLEDGE_ANSWER.put("17", "2");
        DIABETES_KNOWLEDGE_ANSWER.put("18", "4");
        DIABETES_KNOWLEDGE_ANSWER.put("19", "3");
        DIABETES_KNOWLEDGE_ANSWER.put("20", "1");
        DIABETES_KNOWLEDGE_ANSWER.put("21", "2");
        DIABETES_KNOWLEDGE_ANSWER.put("22", "1");
        DIABETES_KNOWLEDGE_ANSWER.put("23", "1");
    }

    /**
     * 评估级别  1 好 2 一般 3 差
     */
    public static final int ASSESS_LEVEl_GOOD = 1;
    public static final int ASSESS_LEVEl_NORMAL = 2;
    public static final int ASSESS_LEVEl_BAD = 3;

    /**
     * 病足风险评估等级
     */
    public static final int FOOT_RISK_LEVEL_0 = 0;
    public static final int FOOT_RISK_LEVEL_1 = 1;
    public static final int FOOT_RISK_LEVEL_2 = 2;
    public static final int FOOT_RISK_LEVEL_3 = 3;
    /**
     * 病足建议
     */
    public static final Map<Integer ,FootAdvice> FOOT_ADVICE_MAP = new HashMap<>();
    static {
        FOOT_ADVICE_MAP.put(FOOT_RISK_LEVEL_0 , new FootAdvice("1）继续控制好血糖、血脂、血压，并了解糖尿病足的相关危险因素及预防方法，如戒烟限酒、控制体重等等；\n2）日常注意足部的防护，在糖尿病教育者指导下，进行日常足部保健；", "日常专科门诊随访，每年到普通医生或者专科进行至少一次足部检查评估"));
        FOOT_ADVICE_MAP.put(FOOT_RISK_LEVEL_1 , new FootAdvice("1）继续控制好血糖、血脂、血压，并了解糖尿病足的相关危险因素及预防方法，如戒烟限酒、控制体重等等；\n2）日常注意足部的防护，在糖尿病教育者指导下，进行日常足部保健；", "日常专科门诊随访，另外至少3-6个月到普通医生或专科进行足部全面检查评估"));
        FOOT_ADVICE_MAP.put(FOOT_RISK_LEVEL_2 , new FootAdvice("1）积极控制好血糖、血脂、血压，并了解糖尿病足的相关危险因素及预防方法，如戒烟限酒、控制体重等等；\n2）日常注意足部的防护，在糖尿病教育者指导下，进行日常足部保健；\n3）日常运动需在医生指导下科学进行，注意运动前、后做好足部的防护，如有下肢开放性伤口情况，需暂停，以适宜的上肢运动为主；", "日常专科门诊随访，并至少2-3个月到专科进行足部评估，可咨询血管专家并结合随访"));
        FOOT_ADVICE_MAP.put(FOOT_RISK_LEVEL_3 , new FootAdvice("1）积极控制好血糖、血脂、血压，并了解糖尿病足的相关危险因素及预防方法，如控制体重、戒烟、避免剧烈、负重运动，预防外伤、防止感染等等；\n2） 日常注意足部的防护，在糖尿病教育者指导下，进行日常足部保健；\n3） 请专科医生综合评估，对相关的风险因素进行针对性的干预，如必要时的畸形预防性手术、系统的药物干预治疗等等；", "每隔1-2个月到专科进行足部复查，如果目前有外周动脉疾病可考虑咨询血管专家并联合随访"));
    }


    /**
     * 足部建议
     */
    private static class FootAdvice{
        private String dealAdvice;
        private String followAdvice;

        private FootAdvice(String dealAdvice ,String followAdvice){
            this.dealAdvice = dealAdvice;
            this.followAdvice = followAdvice;
        }

        public String getDealAdvice() {
            return dealAdvice;
        }

        public void setDealAdvice(String dealAdvice) {
            this.dealAdvice = dealAdvice;
        }

        public String getFollowAdvice() {
            return followAdvice;
        }

        public void setFollowAdvice(String followAdvice) {
            this.followAdvice = followAdvice;
        }
    }
}
