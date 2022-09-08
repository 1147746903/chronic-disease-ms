package com.comvee.cdms.sign.tool;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: suyz
 * @date: 2018/11/7
 */
public class BmiTool {

    /**
     * THIN_VALUE 偏瘦标准
     * OVER_WEIGHT_MIN 超重最小值
     * OVER_WEIGHT_MAX 超重最大值
     * FAT_VALUE 肥胖标准
     */
    private final static float THIN_VALUE = 18.5f;
    private final static float OVER_WEIGHT_MIN = 24.0f;
    private final static float OVER_WEIGHT_MAX = 27.9f;
    private final static float FAT_VALUE = 28.0f;

    /**
     * 获取BMI智能建议
     * @return
     */
    public static String getBMIIntelligentSuggestion(float bmiValue){
        String msg = "";
        if(bmiValue < THIN_VALUE){
            msg = getRandomSuggest(THIN_SUGGESTION);
        }else if(bmiValue >= OVER_WEIGHT_MIN && bmiValue <= OVER_WEIGHT_MAX){
            msg = getRandomSuggest(OVER_WEIGHT_SUGGESTION);
        }else if(bmiValue >= FAT_VALUE){
            msg = getRandomSuggest(FAT_SUGGESTION);
        }
        return msg;
    }

    /**
     * 随机获取建议
     * @param suggestion
     * @return
     */
    private static String getRandomSuggest(String[] suggestion){
        int random = ThreadLocalRandom.current().nextInt(suggestion.length);
        return suggestion[random];
    }

    /**
     * 偏瘦建议
     */
    private static final String[] THIN_SUGGESTION = {
            "您目前的体重低于正常水平，建议在营养师指导下适当加强营养，平衡膳食，并同时在运动治疗师指导下配合科学的运动，比如制定每周运动总时长不少于150分钟的运动计划，避免运动方式不对、运动强度和时长不科学而影响血糖的控制和体重的恢复。"
            ,"您的体重已经属于偏瘦的范围了，这不利于您整体血糖的良好控制，建议及时在医生和营养师指导下进行治疗方案的调整，比如饮食方面，按照您的理想体重和工作强度计算出每日所需总热卡，然后按照三大营养素的比例，来合理的分配日常的饮食，并配合科学的运动，和适宜的药物调整，相信您的体重会逐渐恢复。"
            ,"您的体重偏瘦，是日常饮食控制太过严格，还是运动和用药不科学呢？建议日常参加糖尿病的相关知识学习，多跟内分泌专科医护人员和营养师沟通，交流，熟悉和掌握科学控糖的方法，避免进入糖尿病治疗的误区，这将有利于您整体血糖的控制理想。"
    };

    /**
     * 超重建议
     */
    private static final String[] OVER_WEIGHT_SUGGESTION = {
            "您的体重已属于超重范围，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例；坚持合理健身运动，控制体重。"
            ,"您目前体重超重，为了更好地防治糖尿病并发症的发生与发展，您需要在3-6个月内减重5%-10%。建议您遵循科学的运动方案，并搭配合理饮食。注意减重速度不宜过快，建议以0.5～1kg/周的速率进行。"
            ,"您的体重已经属于超重的范围了，这不利于您整体血糖的良好控制，建议及时在医生和营养师指导下进行治疗方案的调整，比如饮食方面，按照您的理想体重和工作强度计算出每日所需总热卡，然后按照三大营养素的比例，来合理的分配日常的饮食，并配合科学的运动，和适宜的药物调整，相信您的体重会逐渐恢复。"
    };

    /**
     * 肥胖建议
     */
    private static final String[] FAT_SUGGESTION = {
            "您的体重已属于肥胖范围，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例；坚持合理健身运动，控制体重。"
            ,"您的体重已经属于肥胖的范围了，这不利于您整体血糖的良好控制，建议及时在医生和营养师指导下进行治疗方案的调整，比如饮食方面，按照您的理想体重和工作强度计算出每日所需总热卡，然后按照三大营养素的比例，来合理的分配日常的饮食，并配合科学的运动，和适宜的药物调整，相信您的体重会逐渐恢复。"
            ,"您目前体重肥胖，为了更好地防治糖尿病并发症的发生与发展，您需要在3-6个月内减重5%-10%。建议您遵循科学的饮食方案与运动治疗方案。注意减重速度不宜过快，建议以0.5～1kg/周的速率进行。"
    };
}
