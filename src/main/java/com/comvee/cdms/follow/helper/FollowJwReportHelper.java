package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.follow.po.FollowDiabetesPO;
import com.comvee.cdms.member.bo.MemberControlTargetBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.constant.ControlTargetConstant;

import java.util.HashMap;
import java.util.Map;

/**2型糖尿病随访报告
 * @author wyc
 * @date 2019/9/20 9:15
 */
public class FollowJwReportHelper {
    //2型糖尿病随访报告
    public static String getTwoDiabetesReportJson(FollowDiabetesPO diabetesPO,RangeBO range) {
        Map<String, Object> resultMap = new HashMap<>();
        String essentialHyp = ""; //是否有高血压病史  1:有   2:无
        Integer sex = 1;  //性别 1男2女
        String birthday = "";
        //获取报告数据信息
        if (null != diabetesPO.getFollowInfo() && !StringUtils.isBlank(diabetesPO.getFollowInfo().toString())) {
            Map<String, Object> followMap = JsonSerializer.jsonToMap(diabetesPO.getFollowInfo().toString());
            resultMap.put("member_name", followMap.get("member_name"));
            resultMap.put("sex", followMap.get("sex"));
            resultMap.put("height", followMap.get("height"));
            resultMap.put("weight", followMap.get("weight"));
            resultMap.put("bmi", followMap.get("bmi"));
            resultMap.put("birthday", followMap.get("birthday"));
            resultMap.put("sbp", followMap.get("sbp"));  //收缩压
            resultMap.put("dbp", followMap.get("dbp"));  //舒张压
            resultMap.put("mq_fbg", followMap.get("mq_fbg")); //空腹血糖
            resultMap.put("lab_hba", followMap.get("lab_hba")); //糖化血红蛋白
            resultMap.put("mqzywt", diabetesPO.getMqzywt()); //目前主要问题
            resultMap.put("zygjcs", diabetesPO.getZygjcs()); //主要改进措施
            resultMap.put("yqddmb", diabetesPO.getYqddmb()); //预期达到目标
            if (null != followMap.get("essential_hyp") && !StringUtils.isBlank(followMap.get("essential_hyp").toString())) {
                essentialHyp = followMap.get("essential_hyp").toString();
            }
            if (null != followMap.get("sex") && !StringUtils.isBlank(followMap.get("sex").toString())) {
                sex = Integer.parseInt(followMap.get("sex").toString());
            }
            if (null != followMap.get("birthday") && !StringUtils.isBlank(followMap.get("birthday").toString())) {
                birthday = followMap.get("birthday").toString();
            }
        }
        //控制目标
        resultMap.put("range", range);
        String jsonString = JsonSerializer.objectToJson(resultMap);
        return jsonString;
    }

}
