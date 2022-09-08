package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.follow.po.FollowupPO;
import com.comvee.cdms.member.bo.RangeBO;

import java.util.HashMap;
import java.util.Map;

/** 糖尿病随访表报告
 * @author wyc
 * @date 2019/10/9 10:52
 */
public class FollowTnbReportHelper {

    public static String getFollowTnbReportReportJson(FollowupPO followupPO, RangeBO range){
        Map<String, Object> resultMap = new HashMap<>();
        if (null != followupPO.getFollowInfo() && !StringUtils.isBlank(followupPO.getFollowInfo().toString())){
            Map<String, Object> followupMap = JsonSerializer.jsonToMap(followupPO.getFollowInfo().toString());
            resultMap.put("member_name",followupMap.get("member_name"));  //姓名
            resultMap.put("sex",followupMap.get("sex"));  //性别  男 女
            resultMap.put("height",followupMap.get("height2"));  //身高
            resultMap.put("weight",followupMap.get("weight2"));  //体重
            resultMap.put("bmi",followupMap.get("bmi2"));  //BMI
            resultMap.put("waistline",followupMap.get("waistline2"));  //腰围
            resultMap.put("hipline",followupMap.get("hipline2"));  //臀围
            resultMap.put("sbp",followupMap.get("sbp"));  //收缩压
            resultMap.put("dbp",followupMap.get("dbp"));  //舒张压
            resultMap.put("mq_fbg",followupMap.get("mq_fbg2"));  //空腹血糖
            resultMap.put("lab_hba",followupMap.get("thxhdb2"));  //糖化血红蛋白
            resultMap.put("mqzywt",followupMap.get("mqzywt"));   //目前主要问题
            resultMap.put("zygjcs",followupMap.get("zygjcs"));   //主要改进措施
            resultMap.put("yqddmb",followupMap.get("yqddmb"));   //预期达到目标
        }
        //控制目标
        resultMap.put("range", range);
        String jsonString = JsonSerializer.objectToJson(resultMap);
        return jsonString;
    }
}
