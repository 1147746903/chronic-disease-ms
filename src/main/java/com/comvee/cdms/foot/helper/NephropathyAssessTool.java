package com.comvee.cdms.foot.helper;



import com.comvee.cdms.foot.constant.NephropathyConstant;
import com.comvee.cdms.foot.vo.NephropathyAssessResultVO;
import com.comvee.cdms.foot.vo.NurseAdviceVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: suyz
 * @date: 2019/6/13
 */
public class NephropathyAssessTool {

    /**
     * 输出糖尿病肾病评估结果
     * @param eGFR 肾小球滤过率
     * @param uACR 尿白蛋白/肌酐
     * @return
     */
    public static NephropathyAssessResultVO assessNephropathyResult(Float eGFR , Float uACR){
        NephropathyAssessResultVO nephropathyAssessResultVO = new NephropathyAssessResultVO();
        //分期判断
        Integer eGFRStages = eGFRStagesHandler(eGFR);
        Integer uACRStages = uACRStagesHandler(uACR);
        nephropathyAssessResultVO.setEgfrStages(eGFRStages);
        nephropathyAssessResultVO.setUacrStages(uACRStages);
        //肾损伤程度 处理
        String renalInjuryDegree = renalInjuryDegreeHandler(eGFRStages ,uACRStages);
        nephropathyAssessResultVO.setRenalInjuryDegree(renalInjuryDegree);
        //风险程度处理
        String riskDegree = riskDegreeHandler(eGFRStages ,uACRStages);
        nephropathyAssessResultVO.setRiskDegree(riskDegree);
        //转诊随访建议
        String advice = referralAdviceHandler(eGFRStages ,uACRStages);
        nephropathyAssessResultVO.setReferralAdvice(advice);
        //护理建议
        nephropathyAssessResultVO.setAdviceList(adviceHandler(eGFRStages ,uACRStages));
        return nephropathyAssessResultVO;
    }

    /**
     * eGFR 肾小球滤过率 分期判断
     * @param eGFR
     * @return
     */
    public static Integer eGFRStagesHandler(Float eGFR){
        Integer eGFRStages = null;
        if(eGFR != null){
            if(eGFR >= 90){
                eGFRStages = NephropathyConstant.eGFR_STAGES_G1;
            }else if(eGFR >= 60 && eGFR <= 89){
                eGFRStages = NephropathyConstant.eGFR_STAGES_G2;
            }else if(eGFR >= 45 && eGFR <= 59){
                eGFRStages = NephropathyConstant.eGFR_STAGES_G3a;
            }else if(eGFR >= 30 && eGFR <= 44){
                eGFRStages = NephropathyConstant.eGFR_STAGES_G3b;
            }else if(eGFR >= 15 && eGFR <= 29){
                eGFRStages = NephropathyConstant.eGFR_STAGES_G4;
            }else if(eGFR < 15){
                eGFRStages = NephropathyConstant.eGFR_STAGES_G5;
            }
        }
        return eGFRStages;
    }

    /**
     * 白蛋白尿分期 判断
     * @param uACR
     * @return
     */
    public static Integer uACRStagesHandler(Float uACR){
        Integer uACRStages = null;
        if(uACR != null){
            if(uACR < 30){
                uACRStages = NephropathyConstant.uACR_STAGES_A1;
            }else if(uACR >= 30 && uACR <= 300){
                uACRStages = NephropathyConstant.uACR_STAGES_A2;
            }else{
                uACRStages = NephropathyConstant.uACR_STAGES_A3;
            }
        }
        return uACRStages;
    }

    /**
     * 肾损伤程度 文案处理
     * @param eGFRStages
     * @param uACRStages
     * @return
     */
    public static String renalInjuryDegreeHandler(Integer eGFRStages ,Integer uACRStages){
        StringBuilder stringBuilder = new StringBuilder();
        if(eGFRStages != null){
            switch (eGFRStages){
                case NephropathyConstant.eGFR_STAGES_G1:
                    stringBuilder.append("eGFR正常或增高");
                    break;
                case NephropathyConstant.eGFR_STAGES_G2:
                    stringBuilder.append("eGFR轻度下降");
                    break;
                case NephropathyConstant.eGFR_STAGES_G3a:
                    stringBuilder.append("eGFR轻中度下降");
                    break;
                case NephropathyConstant.eGFR_STAGES_G3b:
                    stringBuilder.append("eGFR中重度下降");
                    break;
                case NephropathyConstant.eGFR_STAGES_G4:
                    stringBuilder.append("eGFR重度下降");
                    break;
                case NephropathyConstant.eGFR_STAGES_G5:
                    stringBuilder.append("肾功能衰竭");
                    break;
                default:
                    break;
            }
        }
        if(uACRStages != null && uACRStages != 0){
            //如果eGFR分期为g5,则不输出白蛋白尿分期内容
            if(eGFRStages != null && NephropathyConstant.eGFR_STAGES_G5 == eGFRStages){
            }else {
                if(stringBuilder.length() > 0){
                    stringBuilder.append(",");
                }
                switch (uACRStages){
                    case NephropathyConstant.uACR_STAGES_A1:
                        stringBuilder.append("uACR正常");
                        break;
                    case NephropathyConstant.uACR_STAGES_A2:
                        stringBuilder.append("uACR中度升高");
                        break;
                    case NephropathyConstant.uACR_STAGES_A3:
                        stringBuilder.append("uACR重度升高");
                        break;
                    default:
                        break;
                }
            }
        }
        //当eGFR分期 或 白蛋白尿分期 一个为空时，不进行风险评估的判断,直接返回现在的结果
        if(eGFRStages == null || uACRStages == null){
            return stringBuilder.toString();
        }
/*        if(NephropathyConstant.eGFR_STAGES_G1 == eGFRStages
                || NephropathyConstant.eGFR_STAGES_G2 == eGFRStages){
            if(NephropathyConstant.uACR_STAGES_A1 == uACRStages){
                stringBuilder.append(",低风险");
            }else if(NephropathyConstant.uACR_STAGES_A2 == uACRStages){
                stringBuilder.append(",中风险");
            }else if(NephropathyConstant.uACR_STAGES_A3 == uACRStages){
                stringBuilder.append(",高风险");
            }
        }else if(NephropathyConstant.eGFR_STAGES_G3a == eGFRStages){
            if(NephropathyConstant.uACR_STAGES_A1 == uACRStages){
                stringBuilder.append(",中风险");
            }else if(NephropathyConstant.uACR_STAGES_A2 == uACRStages){
                stringBuilder.append(",高风险");
            }else if(NephropathyConstant.uACR_STAGES_A3 == uACRStages){
                stringBuilder.append(",极高风险");
            }
        }else if(NephropathyConstant.eGFR_STAGES_G3b == eGFRStages){
            if(NephropathyConstant.uACR_STAGES_A1 == uACRStages){
                stringBuilder.append(",高风险");
            }else if(NephropathyConstant.uACR_STAGES_A2 == uACRStages
                    || NephropathyConstant.uACR_STAGES_A3 == uACRStages){
                stringBuilder.append(",极高风险");
            }
        }else{
            stringBuilder.append(",极高风险");
        }*/
        return stringBuilder.toString();
    }

    /**
     * 风险程度处理
     * @param eGFRStages
     * @param uACRStages
     * @return
     */
    public static String riskDegreeHandler(Integer eGFRStages ,Integer uACRStages){
        StringBuilder stringBuilder = new StringBuilder();
        if(eGFRStages == null || uACRStages == null
                || eGFRStages == 0 || uACRStages == 0){
            return stringBuilder.toString();
        }
        if(NephropathyConstant.eGFR_STAGES_G1 == eGFRStages
                || NephropathyConstant.eGFR_STAGES_G2 == eGFRStages){
            if(NephropathyConstant.uACR_STAGES_A1 == uACRStages){
                stringBuilder.append("低风险");
            }else if(NephropathyConstant.uACR_STAGES_A2 == uACRStages){
                stringBuilder.append("中风险");
            }else if(NephropathyConstant.uACR_STAGES_A3 == uACRStages){
                stringBuilder.append("高风险");
            }
        }else if(NephropathyConstant.eGFR_STAGES_G3a == eGFRStages){
            if(NephropathyConstant.uACR_STAGES_A1 == uACRStages){
                stringBuilder.append("中风险");
            }else if(NephropathyConstant.uACR_STAGES_A2 == uACRStages){
                stringBuilder.append("高风险");
            }else if(NephropathyConstant.uACR_STAGES_A3 == uACRStages){
                stringBuilder.append("极高风险");
            }
        }else if(NephropathyConstant.eGFR_STAGES_G3b == eGFRStages){
            if(NephropathyConstant.uACR_STAGES_A1 == uACRStages){
                stringBuilder.append("高风险");
            }else if(NephropathyConstant.uACR_STAGES_A2 == uACRStages
                    || NephropathyConstant.uACR_STAGES_A3 == uACRStages){
                stringBuilder.append("极高风险");
            }
        }else{
            stringBuilder.append("极高风险");
        }
        return stringBuilder.toString();
    }

    /**
     * 随访转诊建议 处理
     * @param eGFRStages
     * @param uACRStages
     * @return
     */
    public static String referralAdviceHandler(Integer eGFRStages ,Integer uACRStages){
        if(eGFRStages == null || uACRStages == null
                || eGFRStages == 0 || uACRStages == 0){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if(eGFRStages == NephropathyConstant.eGFR_STAGES_G1){
            if(uACRStages == NephropathyConstant.uACR_STAGES_A1
                    || uACRStages == NephropathyConstant.uACR_STAGES_A2){
                stringBuilder.append("在日常门诊随访，观察血压、尿常规、血常规、肝肾功能、血脂、血糖、电解质、甲状腺旁腺激素、血清铁等相关变化，其中GFR和尿白蛋白需遵医嘱持续监测，一般每年至少检测一次。");
            }else{
                stringBuilder.append("1、在日常专科随访，观察血压、尿常规、血常规、肝肾功能、血脂、血糖、电解质、甲状腺旁腺激素、血清铁等相关变化，每半年至少检测一次GFR和尿白蛋白；");
                stringBuilder.append("\n2、根据当地肾脏病专科的安排，与专科医师讨论后决定继续监测或者转诊");
            }
        }else if(eGFRStages == NephropathyConstant.eGFR_STAGES_G2){
            if(uACRStages == NephropathyConstant.uACR_STAGES_A1
                    || uACRStages == NephropathyConstant.uACR_STAGES_A2){
                stringBuilder.append("在日常专科随访，观察血压、尿常规、血常规、肝肾功能、血脂、血糖、电解质、甲状腺旁腺激素、血清铁等相关变化，其中GFR和尿白蛋白需遵医嘱持续监测，一般每年至少检测一次GFR和尿白蛋白 ");
            }else{
                stringBuilder.append("1、在日常专科随访，观察血压、尿常规、血常规、肝肾功能、血脂、血糖、电解质、甲状腺旁腺激素、血清铁等相关变化，每半年至少检测一次GFR和尿白蛋白；");
                stringBuilder.append("\n2、根据当地肾脏病专科的安排，与专科医师讨论后决定继续监测或者转诊");
            }
        }else if(eGFRStages == NephropathyConstant.eGFR_STAGES_G3a){
            if(uACRStages == NephropathyConstant.uACR_STAGES_A1){
                stringBuilder.append("在日常专科随访，观察血压、尿常规、血常规、肝肾功能、血脂、血糖、电解质、甲状腺旁腺激素、血清铁等相关变化， 其中GFR和尿白蛋白需遵医嘱持续监测，一般每年至少检测一次GFR和尿白蛋白");
            }else if(uACRStages == NephropathyConstant.uACR_STAGES_A2){
                stringBuilder.append("在日常专科随访，观察血压、尿常规、血常规、肝肾功能、血脂、血糖、电解质、甲状腺旁腺激素、血清铁等相关变化，其中GFR和尿白蛋白需遵医嘱持续监测，一般每半年至少检测一次GFR和尿白蛋白");
            }else{
                stringBuilder.append("1、遵医嘱定期复查，密切观察CKD相关的代谢紊乱，如：维生素 D、血红蛋白、碳酸氢盐、钙磷代谢、甲状旁腺激素等指标变化，每季度至少检测一次GFR和尿白蛋白");
                stringBuilder.append("\n2、建议相应患者需转诊至肾脏专科治疗");
            }
        }else if(eGFRStages == NephropathyConstant.eGFR_STAGES_G3b){
            if(uACRStages == NephropathyConstant.uACR_STAGES_A1){
                stringBuilder.append("日常专科随访，观察血压、尿常规、血常规、肝肾功能、血脂、血糖、电解质、甲状腺旁腺激素、血清铁等相关变化，其中GFR和尿白蛋白需遵医嘱持续监测，一般每半年至少检测一次GFR和尿白蛋白");
            }else if(uACRStages == NephropathyConstant.uACR_STAGES_A2){
                stringBuilder.append("遵医嘱定期复查，密切观察CKD相关的代谢紊乱，如：维生素 D、血红蛋白、碳酸氢盐、钙磷代谢、甲状旁腺激素等指标变化，其中GFR和尿白蛋白需遵医嘱持续监测， 每季度至少检测一次GFR和尿白蛋白");
            }else{
                stringBuilder.append("1、遵医嘱定期复查，密切观察CKD相关的代谢紊乱，如：维生素 D、血红蛋白、碳酸氢盐、钙磷代谢、甲状旁腺激素等指标变化，每季度至少检测一次GFR和尿白蛋白");
                stringBuilder.append("\n2、建议相应患者转诊至肾脏专科治疗");
            }
        }else if(eGFRStages == NephropathyConstant.eGFR_STAGES_G4){
            if(uACRStages == NephropathyConstant.uACR_STAGES_A1
                    || uACRStages == NephropathyConstant.uACR_STAGES_A2){
                stringBuilder.append("1、遵医嘱定期复查，密切观察CKD相关的代谢紊乱，如：维生素 D、血红蛋白、碳酸氢盐、钙磷代谢、甲状旁腺激素等指标变化， 每季度至少检测一次GFR和尿白蛋白");
                stringBuilder.append("\n2、根据当地肾脏病专科的安排，与专科医师讨论后决定继续监测或者转诊");
            }else{
                stringBuilder.append("1、谨遵医嘱定期复查，继续监测GFR和尿白蛋白，并密切观察CKD相关的代谢紊乱，如：维生素 D、血红蛋白、碳酸氢盐、钙磷代谢、甲状旁腺激素、血糖等相关指标变化");
                stringBuilder.append("\n2、建议相应患者需转诊至肾脏专科治疗");
            }
        }else if(eGFRStages == NephropathyConstant.eGFR_STAGES_G5){
            stringBuilder.append("1、谨遵医嘱定期复查，继续监测GFR和尿白蛋白，并密切观察CKD相关的代谢紊乱，如：维生素 D、血红蛋白、碳酸氢盐、钙磷代谢、甲状旁腺激素、血糖等相关指标变化");
            stringBuilder.append("\n2、建议相应患者需转诊至肾脏专科治疗");
        }
        return stringBuilder.toString();
    }

    /**
     * 护理建议
     * @param eGFRStages
     * @param uACRStages
     * @return
     */
    public static List<NurseAdviceVO> adviceHandler(Integer eGFRStages , Integer uACRStages){
        List<NurseAdviceVO> adviceList = new ArrayList<>();
        if(eGFRStages == null && uACRStages == null){
            return adviceList;
        }
        //饮食护理建议
        adviceList.add(new NurseAdviceVO(1,"营养治疗，控制或补充相应物质摄入" ,1 ));
        adviceList.add(new NurseAdviceVO(2,"注意饮食结构" ,0 ));
        adviceList.add(new NurseAdviceVO(3,"注意低蛋白饮食" ,0 ));
        adviceList.add(new NurseAdviceVO(4,"食盐不超过6克" ,0 ));
        adviceList.add(new NurseAdviceVO(5,"低嘌呤、低脂饮食" ,0 ));
        adviceList.add(new NurseAdviceVO(6,"适当高纤维素饮食" ,0 ));
        //运动护理建议
        adviceList.add(new NurseAdviceVO(7,"科学运动，改善机体功能" ,1 ));
        adviceList.add(new NurseAdviceVO(8,"选择易操作，有大肌群参与的有氧运动" ,0 ));
        adviceList.add(new NurseAdviceVO(9,"优先低强度运动，循序渐进" ,0 ));
        adviceList.add(new NurseAdviceVO(10,"保持一定运动频率" ,0 ));
        adviceList.add(new NurseAdviceVO(11,"避免运动" ,0 ));
        //相关风险的防控、教育
        adviceList.add(new NurseAdviceVO(12,"控制好血糖、血压和血脂" ,1 ));
        adviceList.add(new NurseAdviceVO(13,"纠正不良生活习惯" ,1 ));
        adviceList.add(new NurseAdviceVO(14,"健康教育学习" ,1 ));
        return adviceList;
    }
}
