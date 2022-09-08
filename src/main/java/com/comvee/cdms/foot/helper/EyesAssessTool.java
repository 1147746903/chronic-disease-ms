package com.comvee.cdms.foot.helper;


import com.comvee.cdms.foot.constant.EyesConstant;
import com.comvee.cdms.foot.vo.EyesAssessResultVO;
import com.comvee.cdms.foot.vo.NurseAdviceVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: suyz
 * @date: 2019/6/13
 */
public class EyesAssessTool {

    /**
     * 输出眼底评估 结果
     * @return
     */
    public static EyesAssessResultVO assessEyesResult(Integer leftEyeLevel , Integer rightEyeLevel){
        if(leftEyeLevel == null && rightEyeLevel == null){
            return null;
        }
        //判断获取最严重的糖网等级
        Integer eyesLevel = getMaxLevel(leftEyeLevel ,rightEyeLevel);
        //获取随访建议
        String followAdvice = followAdviceHandler(eyesLevel);
        //获取护理建议
        List list = adviceHandler();
        EyesAssessResultVO eyesAssessResultVO = new EyesAssessResultVO();
        eyesAssessResultVO.setFollowAdvice(followAdvice);
        eyesAssessResultVO.setAdviceList(list);
        eyesAssessResultVO.setEyesLevel(eyesLevel);
        return eyesAssessResultVO;
    }

    /**
     * 获取较严重的糖网等级
     * @param leftEyeLevel
     * @param rightEyeLevel
     * @return
     */
    private static Integer getMaxLevel(Integer leftEyeLevel ,Integer rightEyeLevel){
        if(leftEyeLevel != null && rightEyeLevel != null){
            if(leftEyeLevel > rightEyeLevel){
                return leftEyeLevel;
            }else{
                return rightEyeLevel;
            }
        }else if(leftEyeLevel != null){
            return leftEyeLevel;
        }else{
            return rightEyeLevel;
        }
    }

    /**
     * 获取随访建议
     * @param eyesLevel
     * @return
     */
    private static String followAdviceHandler(Integer eyesLevel){
        String followAdvice = "";
        switch (eyesLevel){
            case EyesConstant.EYES_LEVEL_NO:
                followAdvice = "1年随访1次";
                break;
            case EyesConstant.EYES_LEVEL_LIGHT:
                followAdvice = "遵医嘱进行系统治疗，半年随访一次（如果突然视力下降可提早回访），由眼科医师检查密切观察病情变化";
                break;
            case EyesConstant.EYES_LEVEL_MEDIUM:
                followAdvice = "遵医嘱进行系统治疗，3-6个月随访一次（如果突然视力下降可提早回访），由眼科医师检查密切观察病情变化";
                break;
            case EyesConstant.EYES_LEVEL_SEVERE:
                followAdvice = "遵医嘱进行联合治疗，至少3个月随访一次，由眼底病科医师检查密切观察病情变化，必要时考虑行全网视网膜光凝治疗";
                break;
            case EyesConstant.EYES_LEVEL_PDR:
                followAdvice = "遵医嘱定期眼科复诊，至少1-2个月一次，由眼科医师密切观察病变变化，必要时考虑行全网视网膜光凝等针对性治疗";
                break;
            default:
                break;
        }
        return followAdvice;
    }

    /**
     * 护理建议处理
     * @return
     */
    private static List<NurseAdviceVO> adviceHandler(){
        List<NurseAdviceVO> adviceVOS = new ArrayList<>();
        adviceVOS.add(new NurseAdviceVO(1,"避免引起眼压升高的运动" ,1));
        adviceVOS.add(new NurseAdviceVO(2,"避免过度用眼和剧烈运动" ,0));
        adviceVOS.add(new NurseAdviceVO(3,"注意眼睛防护" ,1));
        adviceVOS.add(new NurseAdviceVO(4,"严格执行糖尿病饮食" ,1));
        adviceVOS.add(new NurseAdviceVO(5,"注意定时定量进餐" ,0));
        adviceVOS.add(new NurseAdviceVO(6,"适当食用富含维生素食物" ,0));
        adviceVOS.add(new NurseAdviceVO(7,"注意眼压检测，积极复查" ,0));
        adviceVOS.add(new NurseAdviceVO(8,"术后饮食清淡，避免重体力劳动" ,1));
        adviceVOS.add(new NurseAdviceVO(9,"术后遵医嘱定期复查" ,0));
        adviceVOS.add(new NurseAdviceVO(10,"进行血糖、血压、血脂的防控" ,1));
        adviceVOS.add(new NurseAdviceVO(11,"戒烟戒酒，保持理想体重指数" ,0));
        adviceVOS.add(new NurseAdviceVO(12,"早发现，早诊断，早治疗" ,0));
        adviceVOS.add(new NurseAdviceVO(13,"注意生活规律，规范用药" ,1));
        adviceVOS.add(new NurseAdviceVO(14,"保持良好心理状态，积极配合治疗" ,0));
        return adviceVOS;
    }
}
