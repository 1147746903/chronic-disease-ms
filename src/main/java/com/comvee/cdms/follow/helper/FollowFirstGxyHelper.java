package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.constant.MemberConstant;
import com.comvee.cdms.member.tool.EighteenRangeHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/** 高血压 分层分级
 * v5.0.0
 * @author wyc
 * @date 2019/11/18 9:08
 */
public class FollowFirstGxyHelper {

    public static Map<String,Object> outFollowFirstGxy(Map<String,Object> map){
        Map<String, Object> resultMap = new HashMap<>();

        int score = 0 ;  //分层评估分数

        String birthday = map.get("birthday").toString();  //生日
        String sex = map.get("sex").toString();  //性别 1:男 2:女
        String bmi = map.get("bmi").toString();  //BMI
        String sbp = map.get("sbp").toString();  //收缩压
        String dbp = map.get("dbp").toString();  //舒张压
        String smoke = map.get("smoke").toString();  //是否吸烟 1:是
        String tc = map.get("tc").toString();  //总胆固醇
        String diabetes = map.get("diabetes").toString();  //是否有糖尿病 1:是 2:否
        String ldl = "";   //低密度脂蛋白
        String height = map.get("height") == null ? "" : map.get("height").toString();  //身高

        boolean ageAffect = false;
        boolean sexAffect = false;
        boolean bmiAffect = false;
        boolean smokeAffect = false;
        boolean tcAffect = false;
        boolean diabetesAffect = false;
        boolean ldlAffect = false;
        boolean heightAffect = false;

        if (null != map.get("ldl") && !StringUtils.isBlank(map.get("ldl").toString())){
            ldl = map.get("ldl").toString();
        }

        //高血压分级逻辑 start
        String level = ""; //分级级别 0:其他 1:一级 2:二级 3:三级
        double sbpD = 0.0; //收缩压
        if (!StringUtils.isBlank(sbp)){
            sbpD = Double.parseDouble(sbp);
        }
        double dbpD = 0.0;  //舒张压
        if (!StringUtils.isBlank(dbp)){
            dbpD = Double.parseDouble(dbp);
        }
        if ((sbpD >= 140 && sbpD <= 159) || (dbpD >= 90 && dbpD <= 99)){
            level = "1";
        }
        if (sbpD >= 140 && sbpD <= 159 && dbpD < 90){
            level = "1";
        }
        if (sbpD < 140 && dbpD >= 90 && dbpD <= 99){
            level = "1";
        }
        if ((sbpD >= 160 && sbpD <= 179) || (dbpD >= 100 && dbpD <=109)){
            level = "2";
        }
        if (sbpD >= 160 && sbpD <= 179 && dbpD < 90){
            level = "2";
        }
        if (sbpD < 140 && dbpD >= 100 && dbpD <= 109){
            level = "2";
        }
        if (sbpD >= 180 || dbpD >= 110){
            level = "3";
        }
        if (sbpD >= 180 && dbpD < 90){
            level = "3";
        }
        if (sbpD < 140 && dbpD >= 110){
            level = "3";
        }
        //高血压分级逻辑 end
        level = StringUtils.isBlank(level) ? "0" : level;
        //高血压分层逻辑 start
        int age = 0; //年龄
        if (!StringUtils.isBlank(birthday)){
            age = DateHelper.getAge(birthday);
        }
        if (age >= 40 && age < 45){
            score += 1;
            ageAffect = true;
        }else if (age >= 45 && age < 50){
            score += 2;
            ageAffect = true;
        }else if (age  >= 50 && age < 55){
            score +=3;
            ageAffect = true;
        }else if (age >= 55 && age <60){
            score += 4;
            ageAffect = true;
        }else if (age >= 60){
            int differAge = age - 60;
            int addScore = 4;
            if (differAge > 0){
                for (int i = 1; i <= differAge; i++) {
                    if (i % 5 == 0){
                        addScore ++;
                    }
                }
            }
            score += addScore;
            ageAffect = true;
        }


        if(sbpD < 120){
            score -= 2;
        }else if (sbpD >= 130 && sbpD <140){
            score += 1;
        }else if (sbpD >= 140 && sbpD < 160){
            score += 2;
        }else if (sbpD >= 160 && sbpD < 180){
            if ("1".equals(sex)){
                score += 5;
                sexAffect = true;
            }else if ("2".equals(sex)){
                score += 3;
                sexAffect = true;
            }
        }else if (sbpD >= 180){
            if ("1".equals(sex)){
                score += 8;
                sexAffect = true;
            }else if ("2".equals(sex)){
                score += 4;
                sexAffect = true;
            }
        }

        double bmiD = 0.0; //BMI
        if (!StringUtils.isBlank(bmi)){
            bmiD = Double.parseDouble(bmi);
        }
        if (bmiD >= 24 && bmiD < 28){
            score += 1;
            bmiAffect = true;
        }else if (bmiD >= 28){
            score += 2;
            bmiAffect = true;
        }
        if ("1".equals(smoke)){
            if ("1".equals(sex)){
                score += 2;
                sexAffect = true;
                smokeAffect = true;
            }else if ("2".equals(sex)){
                score += 1;
                sexAffect = true;
                smokeAffect = true;
            }
        }

        double tcD = 0.0; //总胆固醇
        if (!StringUtils.isBlank(tc)){
            tcD = Double.parseDouble(tc);
        }
        if (tcD >= 5.2){
            score += 1;
            tcAffect = true;
        }
        if ("1".equals(diabetes)){
            if ("1".equals(sex)){
                score += 1;
                diabetesAffect = true;
                sexAffect = true;
            }else if ("2".equals(sex)){
                score += 2;
                diabetesAffect = true;
                sexAffect = true;
            }
        }
        String layer = "";  //分层级别 1:低危 2:中危 3:高危
        if (score <= 10){
            layer = "1";
        }else if (score > 10 && score < 13){
            layer = "2";
        }else if (score >=13){
            layer = "3";
        }
        double ldlD = 0.0;
        if (!StringUtils.isBlank(ldl)){
            ldlD = Double.parseDouble(ldl);
        }
        //糖尿病填写有，直接列为高危 或 低密度脂蛋白≥4.9  或总胆固醇≥7.2 直接列为高危
        if ("1".equals(diabetes)){
            layer = "3";
            diabetesAffect = true;
        }
        if(ldlD >= 4.9){
            layer = "3";
            ldlAffect = true;
        }
        if(tcD >=7.2){
            layer = "3";
            tcAffect = true;
        }

        //获取小于18岁的分级
        if (!StringUtils.isBlank(height) && age < 18){
            Double heightD = Double.parseDouble(height);
            level = getEighteenLevel(age,heightD,Integer.parseInt(sex),sbpD, dbpD);
            heightAffect = true;
            ageAffect = true;
            sexAffect = true;
        }

        //当高血压水平分级为 2级、3级时，对应的危险分层评估为 中危、高危
        if ("2".equals(level) && "1".equals(layer)){
                layer = "2";
        }
        if ("3".equals(level)){
            layer = "3";
        }

        //高血压分层逻辑 end
        if(layer.compareTo("2") >= 0 && level.compareTo("2") >= 0){
            StringJoiner factorText = new StringJoiner("、");
            if(sexAffect){
                factorText.add("性别:" + ("1".equals(sex) ? "男":"女"));
            }
            if(ageAffect){
                factorText.add("年龄:" + age + "岁");
            }
            if(heightAffect){
                factorText.add("身高:" + height + "cm");
            }
            factorText.add("收缩压:" + sbp + "mmhg");
            factorText.add("舒张压:" + dbp + "mmhg");
            if(bmiAffect){
                factorText.add("BMI:" + bmi);
            }
            if(smokeAffect){
                factorText.add("吸烟:是");
            }
            if(tcAffect){
                factorText.add("总胆固醇:" + tc + "mmol/l");
            }
            if(diabetesAffect){
                factorText.add("有糖尿病");
            }
            if(ldlAffect){
                factorText.add("低密度脂蛋白:" + ldl + "mmol/l");
            }
            resultMap.put("factorText" ,factorText.toString());
        }
        resultMap.put("layer",layer);  //高血压分层
        resultMap.put("level",level);  //高血压分级
        return resultMap;

    }

    /**
     * 获取小于18岁的分级
     * @param age
     * @param height
     * @param sex
     * @param sbp
     * @param dbp
     * @return
     */
    private static String getEighteenLevel(Integer age,Double height,Integer sex,Double sbp,Double dbp){
        String level = "0"; //其他
        if (age >= 3){
            Map<String, Double> levelRange = EighteenRangeHelper.getEighteenLevelRange(age, height, sex);
            Double sbpLow = levelRange.get("sbpLow") + 5d;  //收缩压低值
            Double sbpHigh = levelRange.get("sbpHigh") + 5d;  //收缩压高值
            Double dbpLow = levelRange.get("dbpLow") + 5d;  //舒张压低值
            Double dbpHigh = levelRange.get("dbpHigh") + 5d;  //舒张压高值
            if (sbp >= sbpLow && sbp < sbpHigh && dbp >= dbpLow && dbp < dbpHigh){
                level = "1";
            }else if (sbp >= sbpHigh && dbp >= dbpHigh){
                level = "2";
            }
        }

        return level;
    }
}
