package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by : wyc
 * Created time 2020/2/10 14:14
 */
public class FollowTnbAssessHelper {

    public static Map<String, Object> outFollowTnbAssess(Map<String, Object> followBodyMap) {
        Map<String, Object> reMap = new HashMap<>();
        int sumScore = 0;  //总分
        if (null != followBodyMap.get("sex") && !StringUtils.isBlank(followBodyMap.get("sex").toString())){
            String sex = followBodyMap.get("sex").toString();
            if ("1".equals(sex)){  //男
                sumScore += 2;
                //腰围
                if (null != followBodyMap.get("waistline") && !StringUtils.isBlank(followBodyMap.get("waistline").toString())){
                    double waistline = Double.parseDouble(followBodyMap.get("waistline").toString());
                    if (waistline >= 75 && waistline < 80){
                        sumScore += 3;
                    }else if (waistline >= 80 && waistline < 85){
                        sumScore += 5;
                    }else if (waistline >= 85 && waistline < 90){
                        sumScore += 7;
                    }else if (waistline >= 90 && waistline < 95){
                        sumScore += 8;
                    }else if (waistline >= 95){
                        sumScore += 10;
                    }
                }
            }else if ("2".equals(sex)){  //女
                //腰围
                if (null != followBodyMap.get("waistline") && !StringUtils.isBlank(followBodyMap.get("waistline").toString())){
                    double waistline = Double.parseDouble(followBodyMap.get("waistline").toString());
                    if (waistline >= 70 && waistline < 75){
                        sumScore += 3;
                    }else if (waistline >= 75 && waistline < 80){
                        sumScore += 5;
                    }else if (waistline >= 80 && waistline < 85){
                        sumScore += 7;
                    }else if (waistline >= 85 && waistline < 90){
                        sumScore += 8;
                    }else if (waistline >= 90){
                        sumScore += 10;
                    }
                }
            }
        }

        //年龄
        if (null != followBodyMap.get("age") && !StringUtils.isBlank(followBodyMap.get("age").toString())){
            int age = Integer.parseInt(followBodyMap.get("age").toString());
            if (age >= 25 && age <= 34){
                sumScore += 4;
            }else if (age >= 35 && age <= 39){
                sumScore += 8;
            }else if (age >= 40 && age <= 44){
                sumScore += 11;
            }else if (age >= 45 && age <= 49){
                sumScore += 12;
            }else if (age >= 50 && age <= 54){
                sumScore += 13;
            }else if (age >= 55 && age <= 59){
                sumScore += 15;
            }else if (age >= 60 && age <= 64){
                sumScore += 16;
            }else if (age >= 65){
                sumScore += 18;
            }
        }
        //收缩压
        if (null != followBodyMap.get("ques2") && !StringUtils.isBlank(followBodyMap.get("ques2").toString())){
            String ques2 = followBodyMap.get("ques2").toString();
            if ("SBPRANGE02".equals(ques2)){
                sumScore += 1;
            }else if ("SBPRANGE03".equals(ques2)){
                sumScore += 3;
            }else if ("SBPRANGE04".equals(ques2)){
                sumScore += 6;
            }else if ("SBPRANGE05".equals(ques2)){
                sumScore += 7;
            }else if ("SBPRANGE06".equals(ques2)){
                sumScore += 8;
            }else if ("SBPRANGE07".equals(ques2)){
                sumScore += 10;
            }
        }
        //体质指数
        if (null != followBodyMap.get("bmi") && !StringUtils.isBlank(followBodyMap.get("bmi").toString())){
            double bmi = Double.parseDouble(followBodyMap.get("bmi").toString());
            if (bmi >= 22 && bmi < 24){
                sumScore += 1;
            }else if (bmi >= 24 && bmi < 30){
                sumScore += 3;
            }else if (bmi >= 30){
                sumScore += 5;
            }
        }
        //糖尿病家族史
        if (null != followBodyMap.get("ques1") && !StringUtils.isBlank(followBodyMap.get("ques1").toString())){
            String familyHistory = followBodyMap.get("ques1").toString();
            if ("1".equals(familyHistory)){
                sumScore += 6;
            }
        }
            reMap.put("score",sumScore);
        return reMap;
    }
}
