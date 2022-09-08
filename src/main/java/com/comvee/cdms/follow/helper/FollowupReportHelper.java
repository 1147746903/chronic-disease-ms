package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.follow.po.FollowupPO;
import com.comvee.cdms.member.bo.RangeBO;

import java.util.HashMap;
import java.util.Map;

/** 日常随访报告
 * @author wyc
 * @date 2019/10/8 10:39
 */
public class FollowupReportHelper {

    public static String getFollowupReportJson(FollowupPO followupPO, RangeBO range){
        Map<String, Object> resultMap = new HashMap<>();
        if (null != followupPO.getFollowInfo() && !StringUtils.isBlank(followupPO.getFollowInfo().toString())){
            Map<String, Object> followupMap = JsonSerializer.jsonToMap(followupPO.getFollowInfo().toString());
            if (null != followupMap.get("basic") && !StringUtils.isBlank(followupMap.get("basic").toString())){
                String basic = followupMap.get("basic").toString();
                Map<String, Object> basicMap = JsonSerializer.jsonToMap(basic);
                resultMap.put("member_name",basicMap.get("member_real_name"));  //姓名
                resultMap.put("sex",basicMap.get("sex"));  //性别  1: 男 2 :女
            }
            if (null != followupMap.get("sign") && !StringUtils.isBlank(followupMap.get("sign").toString())){
                String sign = followupMap.get("sign").toString();
                Map<String, Object> signMap = JsonSerializer.jsonToMap(sign);
                resultMap.put("height",signMap.get("height"));  //身高
                resultMap.put("weight",signMap.get("weight"));  //体重
                resultMap.put("bmi",signMap.get("bmi"));  //BMI
                resultMap.put("sbp",signMap.get("sbp"));  //收缩压
                resultMap.put("dbp",signMap.get("dbp"));  //舒张压
                resultMap.put("waistline",signMap.get("waistline"));  //腰围
                resultMap.put("hipline",signMap.get("hipline"));  //臀围
                resultMap.put("whr",signMap.get("whr"));  //腰臀比
                resultMap.put("tz_heart_rate",signMap.get("tz_heart_rate"));  //静息心率
            }
            if (null != followupMap.get("treatment") && !StringUtils.isBlank(followupMap.get("treatment").toString())){
                String treatment = followupMap.get("treatment").toString();
                Map<String, Object> treatmentMap = JsonSerializer.jsonToMap(treatment);
                resultMap.put("mq_fbg",treatmentMap.get("mq_fbg"));  //空腹血糖
                resultMap.put("blg",treatmentMap.get("blg"));  //餐后血糖
                resultMap.put("bsg",treatmentMap.get("bsg"));  //睡前血糖
                resultMap.put("glucose",treatmentMap.get("glucose"));  //夜间血糖
            }
            if (null != followupMap.get("lab") && !StringUtils.isBlank(followupMap.get("lab").toString())){
                String lab = followupMap.get("lab").toString();
                Map<String, Object> labMap = JsonSerializer.jsonToMap(lab);
                resultMap.put("lab_hba",labMap.get("lab_hba"));  //糖化血红蛋白
            }
            if (null != followupMap.get("assess") && !StringUtils.isBlank(followupMap.get("assess").toString())){
                String assess = followupMap.get("assess").toString();
                Map<String, Object> assessMap = JsonSerializer.jsonToMap(assess);
                resultMap.put("mqzywt",assessMap.get("pg_hzmqczzywt"));
                resultMap.put("zygjcs",assessMap.get("pg_zygjcs"));
                resultMap.put("yqddmb",assessMap.get("pg_yqddmb"));
            }
        }
        //控制目标
        resultMap.put("range", range);
        String jsonString = JsonSerializer.objectToJson(resultMap);
        return jsonString;
    }
}
