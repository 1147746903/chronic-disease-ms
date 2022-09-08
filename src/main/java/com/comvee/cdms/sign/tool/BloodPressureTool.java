package com.comvee.cdms.sign.tool;

import com.comvee.cdms.sign.constant.SignConstant;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: suyz
 * @date: 2018/12/28
 */
public class BloodPressureTool {



    /**
     * 获取血压智能建议
     * @param sdp
     * @param dbp
     * @return
     */
    public static String getIntelligentSuggestion(int sbpLevel, int dbpLevel){
        String msg = "";
        if(sbpLevel == SignConstant.SIGN_LEVEL_NORMAL
                && dbpLevel == SignConstant.SIGN_LEVEL_HIGH){
            msg = getRandomSuggest(DBP_HIGH);
        }else if(sbpLevel == SignConstant.SIGN_LEVEL_HIGH
                && dbpLevel == SignConstant.SIGN_LEVEL_NORMAL){
            msg = getRandomSuggest(SBP_HIGH);
        }else if(sbpLevel == SignConstant.SIGN_LEVEL_HIGH
                && dbpLevel == SignConstant.SIGN_LEVEL_HIGH){
            msg = getRandomSuggest(BLOOD_PRESSURE_HIGH);
        }else if(sbpLevel == SignConstant.SIGN_LEVEL_LOW
                || dbpLevel == SignConstant.SIGN_LEVEL_LOW){
            msg = getRandomSuggest(BLOOD_PRESSURE_LOW);
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
     * 舒张压升高
     */
    private final static String[] DBP_HIGH = new String[]{
        "您这次舒张压高了，建议心血管内科诊治明确病因，日常生活中多关注血压变化，加强预防，如保持规律良好的饮食、运动和作息习惯，保持乐观的心情，避免工作、精神压力过大，并戒烟限酒，按照医嘱定期复查等，有利于血压和整体血糖的控制。",
        "舒张压高了，建议专科复诊。高血压的发生一般常见于有高血压家族史者，以及高盐饮食、长期大量饮酒、抽烟、肥胖、晚睡等不健康生活习惯者，这些均属于高血压的易患人群。建议就医复诊，日常控制好血糖的同时，积极采取预防措施，避免高血压病的发生和发展。"
    };

    /**
     * 收缩压升高
     */
    private final static String[] SBP_HIGH = new String[]{
            "您这次收缩压高于目标值，建议心血管内科诊治明确病因，日常生活中多关注血压变化，加强预防，如保持规律良好的饮食、运动和作息习惯，保持乐观的心情，避免工作、精神压力过大，并戒烟限酒，按照医嘱定期复查等，有利于血压和整体血糖的控制。"
            ,"收缩压高了，建议专科复诊。日常注意积极控制好血糖的同时，也关注血压、血脂等相关联指标的变化，在生活中注意减盐限盐、平衡膳食、戒烟限酒、控制体重、坚持科学运动并保持心态平和，这都有助于您血压的控制。"
            ,"您收缩压超过了目标值，建议专科复诊。高血压是大家最熟悉的慢性病，但是多数人都存在误解，一是多数人对这种病的认知并不够，容易忽略预防和控制，而使病情加重；二是有些人一听到得了高血压就非常紧张和焦虑；其实只要日常积极依照医嘱，从生活干预和科学用药上来进行综合管控，并定期复查观察病情变化，使血压维持在平稳、理想的范围，那么相当一部分人的寿命都不受影响。"
    };

    /**
     * 血压升高
     */
    private final static String[] BLOOD_PRESSURE_HIGH = new String[]{
            "血压高了，建议心血管内科诊治。日常注意可以适宜科学的锻炼，辅以合理的饮食控制，并配合良好的作息习惯及乐观的心情，积极控制好血糖、血脂等，均有助于良好的控制血压。"
            ,"血压高了，高血压的预防和控制的好坏，日常饮食管理是关键的一环，建议您可以参考这几点进行：1、减盐限盐：正常人每日食盐摄入量不应超过6g，而高血压患者不应超过3g。 2、平衡膳食：多吃新鲜蔬菜、鱼类、粗粮、奶制品及其他富含钾、钙、膳食纤维、不饱和脂肪酸的食物，尤其是钾离子，可帮助调节血压。3、控制总热卡的摄入：按照您的理想体重和工作强度计算出每日所需的总热量来合理的安排三餐饮食。"
            ,"您血压超过医嘱的控制目标。高血压和高血糖又称为姐妹病，两者相辅相成会对身体造成严重危害，二者均是临床上引发心脑血管、肾脏病变等疾病的主要病因，建议专科复诊，日常关注变化，积极控制好血压、血糖和血脂。"
    };

    /***
     * 血压偏低
     */
    private final static String[] BLOOD_PRESSURE_LOW = new String[]{
            "您血压偏低，建议专科诊治。日常适当增加体育运动，增强体质、监测血压变化，平时下蹲位时不宜猛起身，防止发生体位性低血压而造成外伤。"
            ,"血压偏低，由于很多因素均会造成血压的偏低，建议近期注意观察下，如有正在使用容易引起低血压的药物，或身体有出现不适的症状表现，如头晕、乏力、恶心、呕吐、心慌、昏蒙、昏倒等等，那么就需要就医看下，明确病因在进行适宜的处理。"
            ,"您血压偏低，低血压可以分为生理性和病理性。生理性的低血压，不会造成组织脏器的缺血缺氧，在日常生活中也无明显不适症状表现，日常以观察为主，并预防体位性低血压就行；如病理性因素引发的低血压，常常伴有不同程度的症状和疾病，则需在医生指导下进行针对性治疗；所以建议您可就医明确下具体病因。"
    };
}
