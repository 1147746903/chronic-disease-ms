package com.comvee.cdms.member.tool;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.follow.po.FollowupPO;
import com.comvee.cdms.foot.po.FootPO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyc
 * @date 2019/4/9 16:50
 */
public class MemberHelper {

    public static List<Map<String,Object>> dealRiskResult(Map<String,Object> memberArchivesByMemberIdMap) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> memberArchivesMap = new HashMap<>();
        if (memberArchivesByMemberIdMap.get("memberArchives") != null){
            String memberArchives = JSON.toJSONString(memberArchivesByMemberIdMap.get("memberArchives"));
            if (!StringUtils.isBlank(memberArchives)){
                memberArchivesMap = JsonSerializer.jsonToMap(memberArchives);
            }
        }
        String archivesJson = "";
        if (memberArchivesMap != null && memberArchivesMap.size() > 0 && memberArchivesMap.get("archivesJson")!=null) {
            archivesJson = memberArchivesMap.get("archivesJson").toString();
        }
        Map<String, Object> map = JsonSerializer.jsonToMap(archivesJson);
        if (map != null && map.size() > 0) {
            Map<String, Object> sign = new HashMap<>();
            Map<String, Object> hypoglycemia =new HashMap<>();
            Map<String, Object> history =new HashMap<>();
            Map<String, Object> complication =new HashMap<>();
            Map<String, Object> anamnesis =new HashMap<>();
            if (null != map.get("sign") && !StringUtils.isBlank(map.get("sign").toString())){
                sign = JsonSerializer.jsonToMap(map.get("sign").toString());
            }
            if (null != map.get("hypoglycemia") && !StringUtils.isBlank(map.get("hypoglycemia").toString())){
                hypoglycemia = JsonSerializer.jsonToMap(map.get("hypoglycemia").toString());
            }
            if (null != map.get("history") && !StringUtils.isBlank(map.get("history").toString())){
                history = JsonSerializer.jsonToMap(map.get("history").toString());
            }
            if (null != map.get("complication") && !StringUtils.isBlank(map.get("complication").toString())){
                complication = JsonSerializer.jsonToMap(map.get("complication").toString());
            }
            if (null != map.get("anamnesis") && !StringUtils.isBlank(map.get("anamnesis").toString())){
                anamnesis = JsonSerializer.jsonToMap(map.get("anamnesis").toString());
            }


        if (complication != null && complication.size() > 0) {

            if (null != complication.get("retinal") && !StringUtils.isBlank(complication.get("retinal").toString())) {
                String retinal = complication.get("retinal").toString();
                HashMap<String, Object> retinalMap = new HashMap<>();
                retinalMap.put("order", 1);
                if ("SWM01".equals(retinal)) {
                    retinalMap.put("text", "确认有眼底病变");
                    list.add(retinalMap);
                } else if ("SWM04".equals(retinal)) {
                    retinalMap.put("text", "疑似有眼底病变");
                    list.add(retinalMap);
                }
            }

            if (null != complication.get("nephropathy") && !StringUtils.isBlank(complication.get("nephropathy").toString())) {
                String nephropathy = complication.get("nephropathy").toString();
                HashMap<String, Object> nephropathyMap = new HashMap<>();
                nephropathyMap.put("order", 2);
                if ("SB01".equals(nephropathy)) {
                    nephropathyMap.put("text", "确诊有糖尿病肾病");
                    list.add(nephropathyMap);
                } else if ("SB04".equals(nephropathy)) {
                    nephropathyMap.put("text", "疑似有糖尿病肾病");
                    list.add(nephropathyMap);
                }
            }

            if (null != complication.get("neuropathy") && !StringUtils.isBlank(complication.get("neuropathy").toString())) {
                String neuropathy = complication.get("neuropathy").toString();
                HashMap<String, Object> neuropathyMap = new HashMap<>();
                neuropathyMap.put("order", 3);
                if ("ZWSJ01".equals(neuropathy)) {
                    neuropathyMap.put("text", "确诊有周围神经病变");
                    list.add(neuropathyMap);
                } else if ("ZWSJ04".equals(neuropathy)) {
                    neuropathyMap.put("text", "疑似有周围神经病变");
                    list.add(neuropathyMap);
                }
            }

            if (null != complication.get("diabetic_foot") && !StringUtils.isBlank(complication.get("diabetic_foot").toString())) {
                String diabeticFoot = complication.get("diabetic_foot").toString();
                HashMap<String, Object> diabeticFootMap = new HashMap<>();
                diabeticFootMap.put("order", 4);
                if ("TNBZ01".equals(diabeticFoot)) {
                    diabeticFootMap.put("text", "确诊有糖尿病足");
                    list.add(diabeticFootMap);
                } else if ("TNBZ04".equals(diabeticFoot)) {
                    diabeticFootMap.put("text", "疑似有糖尿病足");
                    list.add(diabeticFootMap);
                }
            }

            if (complication.get("pad") != null && !StringUtils.isBlank(complication.get("pad").toString())) {
                String pad = complication.get("pad").toString();
                HashMap<String, Object> padMap = new HashMap<>();
                padMap.put("order", 5);
                if ("XZXG01".equals(pad)) {
                    padMap.put("text", "确诊有下肢血管病变");
                    list.add(padMap);
                } else if ("XZXG04".equals(pad)) {
                    padMap.put("text", "疑似有下肢血管病变");
                    list.add(padMap);
                }
            }

            if (null != complication.get("dan") && !StringUtils.isBlank(complication.get("dan").toString())) {
                String dan = complication.get("dan").toString();
                HashMap<String, Object> danMap = new HashMap<>();
                danMap.put("order", 6);
                if ("ZZ01".equals(dan)) {
                    danMap.put("text", "确诊有自主神经病变");
                    list.add(danMap);
                } else if ("ZZ04".equals(dan)) {
                    danMap.put("text", "疑似有自主神经病变");
                    list.add(danMap);
                }
            }

            if (null != complication.get("chd") && !StringUtils.isBlank(complication.get("chd").toString())) {
                String chd = complication.get("chd").toString();
                HashMap<String, Object> chdMap = new HashMap<>();
                chdMap.put("order", 7);
                if ("QZ01".equals(chd)) {
                    chdMap.put("text", "确诊有冠心病");
                    list.add(chdMap);
                } else if ("QZ04".equals(chd)) {
                    chdMap.put("text", "疑似有冠心病");
                    list.add(chdMap);
                }
            }

            if (null != anamnesis.get("essential_hyp") && !StringUtils.isBlank(anamnesis.get("essential_hyp").toString())) {
                String essentialHyp = anamnesis.get("essential_hyp").toString();
                HashMap<String, Object> essentialHypMap = new HashMap<>();
                essentialHypMap.put("order", 8);
                if ("1".equals(essentialHyp)) {
                    essentialHypMap.put("text", "确诊有高血压病");
                    list.add(essentialHypMap);
                }
            }

            if (null != history.get("bs_smoke") && !StringUtils.isBlank(history.get("bs_smoke").toString())) {
                String bsSmoke = history.get("bs_smoke").toString();
                HashMap<String, Object> bsSmokeMap = new HashMap<>();
                bsSmokeMap.put("order", 9);
                if ("1".equals(bsSmoke)) {
                    bsSmokeMap.put("text", "患者有吸烟习惯，请督促进行戒烟");
                    list.add(bsSmokeMap);
                }
            }

            if (null != history.get("drink_rate") && !StringUtils.isBlank(history.get("drink_rate").toString())) {
                String drinkRate = history.get("drink_rate").toString();
                HashMap<String, Object> drinkRateMap = new HashMap<>();
                drinkRateMap.put("order", 10);
                if ("YJQK02".equals(drinkRate)) {
                    drinkRateMap.put("text", "患者有饮酒习惯，需要注意控量");
                    list.add(drinkRateMap);
                }
            }

            if (null != hypoglycemia.get("hyp") && null != hypoglycemia.get("hyp_deal") && !StringUtils.isBlank(hypoglycemia.get("hyp").toString())) {
                String hyp = hypoglycemia.get("hyp").toString();
                String hypDeal = hypoglycemia.get("hyp_deal").toString();
                HashMap<String, Object> hypMap = new HashMap<>();
                hypMap.put("order", 11);
                if ("1".equals(hyp) && StringUtils.isBlank(hypDeal)) {
                    hypMap.put("text", "低血糖处理能力未知");
                    list.add(hypMap);
                }
            }

            if (null != history.get("bs_sport") && !StringUtils.isBlank(history.get("bs_sport").toString())) {
                String bs_sport = history.get("bs_sport").toString();
                HashMap<String, Object> labHbaMap = new HashMap<>();
                labHbaMap.put("order", 12);
                if ("0".equals(bs_sport)) {
                    labHbaMap.put("text", "患者没有运动习惯");
                    list.add(labHbaMap);
                }
            }

            if (null != sign.get("bmi") && !StringUtils.isBlank(sign.get("bmi").toString())) {
                String bmiStr = sign.get("bmi").toString();
                double bmi = Double.valueOf(bmiStr);
                HashMap<String, Object> bmiMap = new HashMap<>();
                bmiMap.put("order", 13);
                if (bmi >= 24) {
                    bmiMap.put("text", "患者体重偏高，需建议减重");
                    list.add(bmiMap);
                } else if (bmi < 18.5) {
                    bmiMap.put("text", "患者体重较轻，需要注意营养摄入");
                    list.add(bmiMap);
                }
            }
        }
    }
        return list;
    }
    public static List<Map<String,Object>> dealDetectionDateResult(Map<String,Object> map,FootPO footPO,FollowupPO followDayModel) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> memberArchivesMap = new HashMap<>();
        if (map.get("memberArchives") != null) {
            String memberArchives = JSON.toJSONString(map.get("memberArchives"));
            if (!StringUtils.isBlank(memberArchives)){
                memberArchivesMap = JsonSerializer.jsonToMap(memberArchives);
            }
        }
        String archivesJson = "";
        if (memberArchivesMap != null && memberArchivesMap.size() > 0) {
            archivesJson = memberArchivesMap.get("archivesJson").toString();
        }
        Map<String, Object> archivesJsonMap = JsonSerializer.jsonToMap(archivesJson);

        Map<String, Object> memberMap = new HashMap<>();
        String sex = "";
        int age = 0;
        if (map.get("member") != null ){
            String member = JSON.toJSONString(map.get("member"));
            if (!StringUtils.isBlank(member)){
                memberMap = JsonSerializer.jsonToMap(member);
            }

            if (null != memberMap.get("sex") && !StringUtils.isBlank(memberMap.get("sex").toString())) {
                sex = memberMap.get("sex").toString();  //性别 1:男  2:女
            }
            if (null != memberMap.get("birthday") && !StringUtils.isBlank(memberMap.get("birthday").toString())) {
                String birthday = memberMap.get("birthday").toString();  //年龄
                age = DateHelper.getAge(birthday);
            }

        }


        if (archivesJsonMap != null && archivesJsonMap.size() > 0){
            Map<String, Object> sign = new HashMap<>();
            Map<String, Object> treatment =new HashMap<>();
            Map<String, Object> lab =new HashMap<>();
            Map<String, Object> complication =new HashMap<>();
            if (null != archivesJsonMap.get("sign") && !StringUtils.isBlank(archivesJsonMap.get("sign").toString())){
                sign = JsonSerializer.jsonToMap(archivesJsonMap.get("sign").toString());
            }
            if (null != archivesJsonMap.get("treatment") && !StringUtils.isBlank(archivesJsonMap.get("treatment").toString())){
                treatment = JsonSerializer.jsonToMap(archivesJsonMap.get("treatment").toString());
            }
            if (null != archivesJsonMap.get("lab") && !StringUtils.isBlank(archivesJsonMap.get("lab").toString())){
                lab = JsonSerializer.jsonToMap(archivesJsonMap.get("lab").toString());
            }
            if (null != archivesJsonMap.get("complication") && !StringUtils.isBlank(archivesJsonMap.get("complication").toString())){
                complication = JsonSerializer.jsonToMap(archivesJsonMap.get("complication").toString());
            }
        String chd = "";
        if (null != complication.get("chd") && !StringUtils.isBlank(complication.get("chd").toString())) {
            chd = complication.get("chd").toString();  //是否有冠心病 QZ01:有  QZ02:无
        }

        HashMap<String, Object> whrMap = new HashMap<>();
        whrMap.put("order", 1);
        whrMap.put("status", 1);
        if (null != sign.get("whr") && !StringUtils.isBlank(sign.get("whr").toString())) {
            String whr = sign.get("whr").toString();
            double whrDoub = Double.valueOf(whr);
            if (("1".equals(sex) && whrDoub > 0.90) || ("2".equals(sex) && whrDoub > 0.85)) {
                whrMap.put("text", "腰臀围比:" + whr + "");
                list.add(whrMap);
            }
        }

        HashMap<String, Object> bmiMap = new HashMap<>();
        bmiMap.put("order", 2);
        if (null != memberMap.get("bmi") && !StringUtils.isBlank(memberMap.get("bmi").toString())) {
            String bmiStr = memberMap.get("bmi").toString();
            double bmiDoub = Double.valueOf(bmiStr);
            if (bmiDoub > 23.9) {
                bmiMap.put("status", 1);
                bmiMap.put("text", "BMI:" + bmiStr);
                list.add(bmiMap);
            } else if (bmiDoub < 18.5) {
                bmiMap.put("status", 0);
                bmiMap.put("text", "BMI:" + bmiStr);
                list.add(bmiMap);
            }
        }

        HashMap<String, Object> sbpMap = new HashMap<>();
        sbpMap.put("order", 3);
        if (null != sign.get("sbp") && !StringUtils.isBlank(sign.get("sbp").toString())) {
            String sbpStr = sign.get("sbp").toString();
            double sbpDouble = Double.valueOf(sbpStr);
            if ((age < 60 && sbpDouble > 129) || (age >= 60 && sbpDouble > 149)) {
                sbpMap.put("status", 1);
                sbpMap.put("text", "收缩压:" + sbpStr + "mmHg");
                list.add(sbpMap);
            } else if (sbpDouble < 90) {
                sbpMap.put("status", 0);
                sbpMap.put("text", "收缩压:" + sbpStr + "mmHg");
                list.add(sbpMap);
            }
        }

        HashMap<String, Object> dbpMap = new HashMap<>();
        dbpMap.put("order", 4);
        if (null != sign.get("dbp") && !StringUtils.isBlank(sign.get("dbp").toString())) {
            String dbpStr = sign.get("dbp").toString();
            double dbpDouble = Double.valueOf(dbpStr);
            if ((age < 60 && dbpDouble > 79) || (age >= 60 && dbpDouble > 89)) {
                dbpMap.put("status", 1);
                dbpMap.put("text", "舒张压:" + dbpStr + "mmHg");
                list.add(dbpMap);
            } else if (dbpDouble < 60) {
                dbpMap.put("status", 0);
                dbpMap.put("text", "舒张压:" + dbpStr + "mmHg");
                list.add(dbpMap);
            }
        }

        HashMap<String, Object> mqFbgMap = new HashMap<>();
        mqFbgMap.put("order", 5);
        if (null != treatment.get("mq_fbg") && !StringUtils.isBlank(treatment.get("mq_fbg").toString())) {
            String mqFbgStr = treatment.get("mq_fbg").toString();
            double mqFbgDoub = Double.valueOf(mqFbgStr);
            if (mqFbgDoub > 7) {
                mqFbgMap.put("status", 1);
                mqFbgMap.put("text", "空腹血糖:" + mqFbgStr + "mmol/L");
                list.add(mqFbgMap);
            } else if (mqFbgDoub < 4.4) {
                mqFbgMap.put("status", 0);
                mqFbgMap.put("text", "空腹血糖:" + mqFbgStr + "mmol/L");
                list.add(mqFbgMap);
            }
        }

        HashMap<String, Object> blgMap = new HashMap<>();
        blgMap.put("order", 7);
        if (null != treatment.get("blg") && !StringUtils.isBlank(treatment.get("blg").toString())) {
            String blgStr = treatment.get("blg").toString();
            double blgDoub = Double.valueOf(blgStr);
            if (blgDoub > 9.9) {
                blgMap.put("status", 1);
                blgMap.put("text", "餐后2小时血糖:" + blgStr + "mmol/L");
                list.add(blgMap);
            } else if (blgDoub < 4.4) {
                blgMap.put("status", 0);
                blgMap.put("text", "餐后2小时血糖:" + blgStr + "mmol/L");
                list.add(blgMap);
            }
        }

        HashMap<String, Object> bsgMap = new HashMap<>();
        bsgMap.put("order", 8);
        if (null != treatment.get("bsg") && !StringUtils.isBlank(treatment.get("bsg").toString())) {
            String bsgStr = treatment.get("bsg").toString();
            double bsgDoub = Double.valueOf(bsgStr);
            if (bsgDoub > 9.9) {
                bsgMap.put("status", 1);
                bsgMap.put("text", "睡前血糖:" + bsgStr + "mmol/L");
                list.add(bsgMap);
            } else if (bsgDoub < 4.4) {
                bsgMap.put("status", 0);
                bsgMap.put("text", "睡前血糖:" + bsgStr + "mmol/L");
                list.add(bsgMap);
            }
        }

        HashMap<String, Object> labHbaMap = new HashMap<>();
        labHbaMap.put("order", 10);
        if (null != lab.get("lab_hba") && !StringUtils.isBlank(lab.get("lab_hba").toString())) {
            String labHbaStr = lab.get("lab_hba").toString();
            double labHbaDoub = Double.valueOf(labHbaStr);
            if (labHbaDoub >= 7) {
                labHbaMap.put("status", 1);
                labHbaMap.put("text", "糖化血红蛋白:" + labHbaStr + "%");
                list.add(labHbaMap);
            }
        }

        HashMap<String, Object> heartRateMap = new HashMap<>();
        heartRateMap.put("order", 12);
        if (null != sign.get("tz_heart_rate") && !StringUtils.isBlank(sign.get("tz_heart_rate").toString())) {
            String heartRateStr = sign.get("tz_heart_rate").toString();
            double heartRateDoub = Double.valueOf(heartRateStr);
            if (heartRateDoub > 100) {
                heartRateMap.put("status", 1);
                heartRateMap.put("text", "静态心率:" + heartRateStr + "次/分钟");
                list.add(heartRateMap);
            } else if (heartRateDoub < 60) {
                heartRateMap.put("status", 0);
                heartRateMap.put("text", "静态心率:" + heartRateStr + "次/分钟");
                list.add(heartRateMap);
            }
        }

        HashMap<String, Object> ldlMap = new HashMap<>();
        ldlMap.put("order", 13);
        if (null != lab.get("ldl") && !StringUtils.isBlank(lab.get("ldl").toString())) {
            String ldlStr = lab.get("ldl").toString();
            double ldlDoub = Double.valueOf(ldlStr);
            if("QZ01".equals(chd)){
                if(ldlDoub >= 1.8){
                    ldlMap.put("status", 1);
                    ldlMap.put("text", "低密度脂蛋白胆固醇:" + ldlStr + "mmol/L");
                    list.add(ldlMap);
                }
            }else{
                if(ldlDoub >= 2.6){
                    ldlMap.put("status", 1);
                    ldlMap.put("text", "低密度脂蛋白胆固醇:" + ldlStr + "mmol/L");
                    list.add(ldlMap);
                }
            }
        }

        HashMap<String, Object> hdlMap = new HashMap<>();
        hdlMap.put("order", 14);
        if (null != lab.get("hdl") && !StringUtils.isBlank(lab.get("hdl").toString())) {
            String hdlStr = lab.get("hdl").toString();
            double hdlDoub = Double.valueOf(hdlStr);
            if (("1".equals(sex) && hdlDoub <= 1.0) || ("2".equals(sex) && hdlDoub <= 1.3)) {
                hdlMap.put("status", 0);
                hdlMap.put("text", "高密度脂蛋白胆固醇:" + hdlStr + "mmol/L");
                list.add(hdlMap);
            }
        }

        //甘油三酯	＜1.7mmol/L
        HashMap<String, Object> tgMap = new HashMap<>();
        tgMap.put("order", 15);
        if (null != lab.get("tg") && !StringUtils.isBlank(lab.get("tg").toString())) {
            String tgStr = lab.get("tg").toString();
            double tgDoub = Double.valueOf(tgStr);
            if (tgDoub >= 1.7) {
                tgMap.put("status", 1);
                tgMap.put("text", "甘油三酯:" + tgStr + "mmol/L");
                list.add(tgMap);
            }
        }

        //总胆固醇	＜4.5mmol/L
        HashMap<String, Object> tcMap = new HashMap<>();
        tcMap.put("order", 16);
        if (null != lab.get("tc") && !StringUtils.isBlank(lab.get("tc").toString())) {
            String tcStr = lab.get("tc").toString();
            double tcDoub = Double.valueOf(tcStr);
            if (tcDoub >= 4.5) {
                tcMap.put("status", 1);
                tcMap.put("text", "总胆固醇:" + tcStr + "mmol/L");
                list.add(tcMap);
            }
        }

        //尿素氮	3.6~14.2mmol/L
        HashMap<String, Object> bunMap = new HashMap<>();
        bunMap.put("order", 17);
        if (null != lab.get("bun") && !StringUtils.isBlank(lab.get("bun").toString())) {
            String bunStr = lab.get("bun").toString();
            double bunDoub = Double.valueOf(bunStr);
            if (bunDoub > 14.2) {
                bunMap.put("status", 1);
                bunMap.put("text", "尿素氮:" + bunStr + "mmol/L");
                list.add(bunMap);
            } else if (bunDoub < 3.6) {
                bunMap.put("status", 0);
                bunMap.put("text", "尿素氮:" + bunStr + "mmol/L");
                list.add(bunMap);
            }
        }

        //血肌酐	44~133umol/l
        HashMap<String, Object> crMap = new HashMap<>();
        crMap.put("order", 18);
        if (null != lab.get("cr") && !StringUtils.isBlank(lab.get("cr").toString())) {
            String crStr = lab.get("cr").toString();
            double crDoub = Double.valueOf(crStr);
            if (crDoub > 133) {
                crMap.put("status", 1);
                crMap.put("text", "血肌酐:" + crStr + "umol/L");
                list.add(crMap);
            } else if (crDoub < 44) {
                crMap.put("status", 0);
                crMap.put("text", "血肌酐:" + crStr + "umol/L");
                list.add(crMap);
            }
        }

        //血尿酸	150~420umol/l
        HashMap<String, Object> labXnsMap = new HashMap<>();
        labXnsMap.put("order", 19);
        if (null != lab.get("lab_xns") && !StringUtils.isBlank(lab.get("lab_xns").toString())) {
            String labXnsStr = lab.get("lab_xns").toString();
            double labXnsDoub = Double.valueOf(labXnsStr);
            if (labXnsDoub > 420) {
                labXnsMap.put("status", 1);
                labXnsMap.put("text", "血尿酸:" + labXnsStr + "umol/L");
                list.add(labXnsMap);
            } else if (labXnsDoub < 150) {
                labXnsMap.put("status", 0);
                labXnsMap.put("text", "血尿酸:" + labXnsStr + "umol/L");
                list.add(labXnsMap);
            }
        }

        //21 白蛋白	(35-54) g/L	随访、足部处方 uae
        HashMap<String, Object> uaeMap = new HashMap<>();
        uaeMap.put("order",21);
        if (null != lab.get("uae") && !StringUtils.isBlank(lab.get("uae").toString())){
            String uae = lab.get("uae").toString();
            double uaeDouble = Double.valueOf(uae);
            if (uaeDouble > 54){
                uaeMap.put("status",1);
                uaeMap.put("text","白蛋白:" +uae+ "g/L");
                list.add(uaeMap);
            }
            if (uaeDouble < 35){
                uaeMap.put("status",0);
                uaeMap.put("text","白蛋白:" +uae+ "g/L");
                list.add(uaeMap);
            }
        }

        //谷丙转氨酶	0~40U/L
        HashMap<String, Object> altMap = new HashMap<>();
        altMap.put("order", 22);
        if (null != lab.get("alt") && !StringUtils.isBlank(lab.get("alt").toString())) {
            String altStr = lab.get("alt").toString();
            double altDoub = Double.valueOf(altStr);
            if (altDoub > 40) {
                altMap.put("status", 1);
                altMap.put("text", "谷丙转氨酶:" + altStr + "U/L");
                list.add(altMap);
            }
        }

        //谷草转氨酶	0~40U/L
        HashMap<String, Object> astMap = new HashMap<>();
        astMap.put("order", 23);
        if (null != lab.get("ast") && !StringUtils.isBlank(lab.get("ast").toString())) {
            String astStr = lab.get("ast").toString();
            double astDoub = Double.valueOf(astStr);
            if (astDoub > 40) {
                astMap.put("status", 1);
                astMap.put("text", "谷草转氨酶:" + astStr + "U/L");
                list.add(astMap);
            }
        }

        //尿蛋白	阴性
        HashMap<String, Object> proValueMap = new HashMap<>();
        proValueMap.put("order", 26);
        if (null != complication.get("neph_pro_value") && !StringUtils.isBlank(complication.get("neph_pro_value").toString())) {
            String proValueStr = complication.get("neph_pro_value").toString();
            if ("阳性".equals(proValueStr)) {
                proValueMap.put("status", 1);
                proValueMap.put("text", "尿蛋白:" + proValueStr);
                list.add(proValueMap);
            }
        }

        //尿微量白蛋白	＜20ug/min
        HashMap<String, Object> labNwlbdbMap = new HashMap<>();
        labNwlbdbMap.put("order", 27);
        if (null != lab.get("lab_nwlbdb") && !StringUtils.isBlank(lab.get("lab_nwlbdb").toString())) {
            String labNwlbdbStr = lab.get("lab_nwlbdb").toString();
            double labNwlbdbDouble = Double.valueOf(labNwlbdbStr);
            if (labNwlbdbDouble >= 20) {
                labNwlbdbMap.put("status", 1);
                labNwlbdbMap.put("text", "尿微量白蛋白:" + labNwlbdbStr + "ug/min");
                list.add(labNwlbdbMap);
            }
        }

        //30 尿微量白蛋白/肌酐	＜30mg/g	rc随访、管理处方（控制目标）、足部处方 neph_acr
        HashMap<String, Object> acrMap = new HashMap<>();
        acrMap.put("order", 30);
        if (null != complication.get("neph_acr") && !StringUtils.isBlank(complication.get("neph_acr").toString())) {
            String acr = complication.get("neph_acr").toString();
            double acrDouble = Double.valueOf(acr);
            if (acrDouble >= 30) {
                acrMap.put("status", "1");
                acrMap.put("text", "尿微量白蛋白/肌酐:" + acr + "mg/g");
                list.add(acrMap);
            }
        }

        //34白细胞计数	（4.0～10.0）×10^9/L	bz随访、 yz_leucocyte_count
        HashMap<String, Object> leucocyteCountMap = new HashMap<>();
        leucocyteCountMap.put("order",34);
        if (null != lab.get("lab_xcg_wbc") && !StringUtils.isBlank(lab.get("lab_xcg_wbc").toString())){
            String leucocyteCount = lab.get("lab_xcg_wbc").toString();
            double leucocyteCountDouble = Double.valueOf(leucocyteCount);
            if (leucocyteCountDouble > 10.0){
                leucocyteCountMap.put("status",1);
                leucocyteCountMap.put("text","白细胞计数:" +leucocyteCount+ "×10^9/L");
                list.add(leucocyteCountMap);
            }
            if (leucocyteCountDouble < 4.0){
                leucocyteCountMap.put("status",0);
                leucocyteCountMap.put("text","白细胞计数:" +leucocyteCount+ "×10^9/L");
                list.add(leucocyteCountMap);
            }
        }

            //37血红蛋白	" 男：120～160g/L  女：110-150g/L"
            HashMap<String, Object> qtHgbMap = new HashMap<>();
            qtHgbMap.put("order",37);
            if (null != lab.get("lab_xcg_hgb") && !StringUtils.isBlank(lab.get("lab_xcg_hgb").toString())){
                String qtHgb = lab.get("lab_xcg_hgb").toString();
                double qtHgbDouble = Double.valueOf(qtHgb);
                if("1".equals(sex)){
                    if(qtHgbDouble < 120){
                        qtHgbMap.put("status",0);
                        qtHgbMap.put("text","血红蛋白:" +qtHgb+ "g/L");
                        list.add(qtHgbMap);
                    }
                    if(qtHgbDouble>160){
                        qtHgbMap.put("status",1);
                        qtHgbMap.put("text","血红蛋白:" +qtHgb+ "g/L");
                        list.add(qtHgbMap);
                    }
                }
                if("2".equals(sex)){
                    if(qtHgbDouble < 110){
                        qtHgbMap.put("status",0);
                        qtHgbMap.put("text","血红蛋白:" +qtHgb+ "g/L");
                        list.add(qtHgbMap);
                    }
                    if(qtHgbDouble>150){
                        qtHgbMap.put("status",1);
                        qtHgbMap.put("text","血红蛋白:" +qtHgb+ "g/L");
                        list.add(qtHgbMap);
                    }
                }
            }

    }
    //足部逻辑/////////////////////////////////////
        if (null != footPO){
            String assistCheck = footPO.getAssistCheck();
            if (null != assistCheck){
                Map<String, Object> checkMap = JsonSerializer.jsonToMap(assistCheck);

                //21 白蛋白	(35-54) g/L	足部处方 qt_alb
//                HashMap<String, Object> uaeMap = new HashMap<>();
//                uaeMap.put("order",21);
//                if (null != checkMap.get("qt_alb") && !StringUtils.isBlank(checkMap.get("qt_alb").toString())){
//                    String uae = checkMap.get("qt_alb").toString();
//                    double uaeDouble = Double.valueOf(uae);
//                    if (uaeDouble > 54){
//                        uaeMap.put("status",1);
//                        uaeMap.put("text","白蛋白:" +uae+ "g/L");
//                        list.add(uaeMap);
//                    }
//                    if (uaeDouble < 35){
//                        uaeMap.put("status",0);
//                        uaeMap.put("text","白蛋白:" +uae+ "g/L");
//                        list.add(uaeMap);
//                    }
//                }

                //24尿肌酐	1.3~44.2mmol/L   足部处方  urine_creatinine
                HashMap<String, Object> creatinineMap = new HashMap<>();
                creatinineMap.put("order",24);
                if (null != checkMap.get("urine_creatinine") && !StringUtils.isBlank(checkMap.get("urine_creatinine").toString())){
                    String creatinine = checkMap.get("urine_creatinine").toString();
                    double creatinineDouble = Double.valueOf(creatinine);
                    if (creatinineDouble > 44.2){
                        creatinineMap.put("status",1);
                        creatinineMap.put("text","尿肌酐:" +creatinine+ "mmol/L");
                        list.add(creatinineMap);
                    }else if (creatinineDouble < 1.3){
                        creatinineMap.put("status",0);
                        creatinineMap.put("text","尿肌酐:" +creatinine+ "mmol/L");
                        list.add(creatinineMap);
                    }
                }



                //38中性粒细胞百分比	50～70%	 z足部处方 yz_gra
                HashMap<String, Object> graMap = new HashMap<>();
                graMap.put("order",38);
                if (null != checkMap.get("yz_gra") && !StringUtils.isBlank(checkMap.get("yz_gra").toString())){
                    String gra = checkMap.get("yz_gra").toString();
                    double graDouble = Double.valueOf(gra);
                    if (graDouble > 70){
                        graMap.put("status",1);
                        graMap.put("text","中性粒细胞百分比:" +gra+ "%");
                        list.add(graMap);
                    }else if (graDouble < 50){
                        graMap.put("status",0);
                        graMap.put("text","中性粒细胞百分比:" +gra+ "%");
                        list.add(graMap);
                    }
                }

                //39超敏C反应蛋白	0-0.8mg/dL	z足部处方  yz_hs_crp
                HashMap<String, Object> crpMap = new HashMap<>();
                crpMap.put("order",39);
                if (null != checkMap.get("yz_hs_crp") && !StringUtils.isBlank(checkMap.get("yz_hs_crp").toString())){
                    String crp = checkMap.get("yz_hs_crp").toString();
                    double crpDouble = Double.valueOf(crp);
                    if (crpDouble > 0.8){
                        crpMap.put("status",1);
                        crpMap.put("text","超敏C反应蛋白:" +crp+ "mg/dL");
                        list.add(crpMap);
                    }
                }

                //41 血钾	(3.5-5.5) mmol/L	z足部处方 qt_potassium
                HashMap<String, Object> potassiumMap = new HashMap<>();
                potassiumMap.put("order",41);
                if (null != checkMap.get("qt_potassium") && !StringUtils.isBlank(checkMap.get("qt_potassium").toString())){
                    String potassium = checkMap.get("qt_potassium").toString();
                    double potassiumDouble = Double.valueOf(potassium);
                    if (potassiumDouble > 5.5){
                        potassiumMap.put("status",1);
                        potassiumMap.put("text","血钾:" +potassium+ "mmol/L");
                        list.add(potassiumMap);
                    }else if (potassiumDouble < 3.5){
                        potassiumMap.put("status",0);
                        potassiumMap.put("text","血钾:" +potassium+ "mmol/L");
                        list.add(potassiumMap);
                    }
                }

                //42血钠	（135-145）mmol/L	z足部处方 qt_sodium
                HashMap<String, Object> sodiumMap = new HashMap<>();
                sodiumMap.put("order",42);
                if (null != checkMap.get("qt_sodium") && !StringUtils.isBlank(checkMap.get("qt_sodium").toString())){
                    String sodium = checkMap.get("qt_sodium").toString();
                    double sodiumDouble = Double.valueOf(sodium);
                    if (sodiumDouble > 145){
                        sodiumMap.put("status",1);
                        sodiumMap.put("text","血钠:" +sodium+ "mmol/L");
                        list.add(sodiumMap);
                    }else if (sodiumDouble < 135){
                        sodiumMap.put("status",0);
                        sodiumMap.put("text","血钠:" +sodium+ "mmol/L");
                        list.add(sodiumMap);
                    }
                }

                //43血氯	（96-106）mmol/L	z足部处方  qt_chloride
                HashMap<String, Object> chlorideMap = new HashMap<>();
                chlorideMap.put("order",43);
                if (null != checkMap.get("qt_chloride") && !StringUtils.isBlank(checkMap.get("qt_chloride").toString())){
                    String chloride = checkMap.get("qt_chloride").toString();
                    double chlorideDouble = Double.valueOf(chloride);
                    if (chlorideDouble > 106){
                        chlorideMap.put("status",1);
                        chlorideMap.put("text","血氯:" +chloride+ "mmol/L");
                        list.add(chlorideMap);
                    }else if (chlorideDouble < 96){
                        chlorideMap.put("status",0);
                        chlorideMap.put("text","血氯:" +chloride+ "mmol/L");
                        list.add(chlorideMap);
                    }
                }

                //44血钙	（2.12-2.75）mmol/L	z足部处方  qt_calcium
                HashMap<String, Object> calciumMap = new HashMap<>();
                calciumMap.put("order",44);
                if (null != checkMap.get("qt_calcium") && !StringUtils.isBlank(checkMap.get("qt_calcium").toString())){
                    String calcium = checkMap.get("qt_calcium").toString();
                    double calciumDouble = Double.valueOf(calcium);
                    if (calciumDouble > 2.75){
                        calciumMap.put("status",1);
                        calciumMap.put("text","血钙:" +calcium+ "mmol/L");
                        list.add(calciumMap);
                    }else if (calciumDouble < 2.12){
                        calciumMap.put("status",0);
                        calciumMap.put("text","血钙:" +calcium+ "mmol/L");
                        list.add(calciumMap);
                    }
                }

                //47降钙素	<0.5ug/L	足部处方  yz_ct
                HashMap<String, Object> yzCtMap = new HashMap<>();
                yzCtMap.put("order",47);
                if (null != checkMap.get("yz_ct") && !StringUtils.isBlank(checkMap.get("yz_ct").toString())){
                    String yzCt = checkMap.get("yz_ct").toString();
                    double yzCtDouble = Double.valueOf(yzCt);
                    if (yzCtDouble >= 0.5){
                        yzCtMap.put("status",1);
                        yzCtMap.put("text","降钙素:" +yzCt+ "ug/L");
                        list.add(yzCtMap);
                    }
                }



                //40血酮体（β—羟丁酸）	＜0.27mmol/L	足部处方  qt_ketone_body
                HashMap<String, Object> ketoneBodyMap = new HashMap<>();
                ketoneBodyMap.put("order",40);
                if (null != checkMap.get("qt_ketone_body") && !StringUtils.isBlank(checkMap.get("qt_ketone_body").toString())){
                    String ketoneBody = checkMap.get("qt_ketone_body").toString();
                    double ketoneBodyDouble = Double.valueOf(ketoneBody);
                    if (ketoneBodyDouble >= 0.27){
                        ketoneBodyMap.put("status",1);
                        ketoneBodyMap.put("text","血酮体（β—羟丁酸）:" +ketoneBody+ "mmol/L");
                        list.add(ketoneBodyMap);
                    }
                }
                //45血沉	"男：0-15mm/h  女：0-20mm/h"	足部处方 yz_esr
                HashMap<String, Object> esrMap = new HashMap<>();
                esrMap.put("order",45);
                if (null != checkMap.get("yz_esr") && !StringUtils.isBlank(checkMap.get("yz_esr").toString())){
                    String esr = checkMap.get("yz_esr").toString();
                    double esrDouble = Double.valueOf(esr);
                    if (("1".equals(sex) && esrDouble > 15) || ("2".equals(sex) && esrDouble > 20)){
                        esrMap.put("status",1);
                        esrMap.put("text","血沉:" +esr+ "mm/h");
                        list.add(esrMap);
                    }
                }
                //46白介素-6	<7 pg/mL	足部处方  yz_il_6
                HashMap<String, Object> ilMap = new HashMap<>();
                ilMap.put("order",46);
                if (null != checkMap.get("yz_il_6") && !StringUtils.isBlank(checkMap.get("yz_il_6").toString())){
                    String il = checkMap.get("yz_il_6").toString();
                    double ilDouble = Double.valueOf(il);
                    if (ilDouble >= 7){
                        ilMap.put("status",1);
                        ilMap.put("text","白介素-6:" +il+ "pg/mL");
                        list.add(ilMap);
                    }
                }
                //48乳酸	0.5~2.2mmol/L	足部处方  qt_cac
                HashMap<String, Object> cacMap = new HashMap<>();
                cacMap.put("order",48);
                if (null != checkMap.get("qt_cac") && !StringUtils.isBlank(checkMap.get("qt_cac").toString())){
                    String cac = checkMap.get("qt_cac").toString();
                    double cacDouble = Double.valueOf(cac);
                    if (cacDouble > 2.2){
                        cacMap.put("status",1);
                        cacMap.put("text","乳酸:" +cac+ "mmol/L");
                        list.add(cacMap);
                    }else if (cacDouble < 0.5){
                        cacMap.put("status",0);
                        cacMap.put("text","乳酸:" +cac+ "mmol/L");
                        list.add(cacMap);
                    }
                }

            }
        }
        //日常随访逻辑/////////////////////////////
        if (followDayModel != null) {
            String followInfo = followDayModel.getFollowInfo();
            Map<String, Object> followMap = new HashMap<>();
            if (!StringUtils.isBlank(followInfo)) {
                followMap = JsonSerializer.jsonToMap(followInfo);
            }
            Map<String, Object> lab = new HashMap<>();
            Map<String, Object> dka= new HashMap<>();
            Map<String, Object> complication= new HashMap<>();
            Map<String, Object> treatment= new HashMap<>();
            if (followMap.get("lab") != null){
                lab = JsonSerializer.jsonToMap(followMap.get("lab").toString());
            }
            if (followMap.get("dka")!=null){
                dka = JsonSerializer.jsonToMap(followMap.get("dka").toString());
            }
            if (followMap.get("complication")!=null){
                complication = JsonSerializer.jsonToMap(followMap.get("complication").toString());
            }
            if (followMap.get("treatment") != null){
                treatment = JsonSerializer.jsonToMap(followMap.get("treatment").toString());
            }

            //夜间血糖 order 9    rc随访  4.4~9.9mmol/L glucose
            HashMap<String, Object> glucoseMap = new HashMap<>();
            glucoseMap.put("order", 9);
            if (null != treatment.get("glucose") && !StringUtils.isBlank(treatment.get("glucose").toString())) {
                String glucose = treatment.get("glucose").toString();
                double glucoseDouble = Double.valueOf(glucose);
                if (glucoseDouble > 9.9) {
                    glucoseMap.put("status", "1");
                    glucoseMap.put("text", "夜间血糖:" + glucose + "mmol/L");
                    list.add(glucoseMap);
                }else if (glucoseDouble < 4.4){
                    glucoseMap.put("status", "0");
                    glucoseMap.put("text", "夜间血糖:" + glucose + "mmol/L");
                    list.add(glucoseMap);
                }
            }

/*            //11口服葡萄糖耐量试验  4.4~9.9mmol/L  随访   lab_ogtt_bg
            HashMap<String, Object> ogttBgMap = new HashMap<>();
            ogttBgMap.put("order",21);
            if (null != lab.get("lab_ogtt_bg") && !StringUtils.isBlank(lab.get("lab_ogtt_bg").toString())){
                String ogttBg = lab.get("lab_ogtt_bg").toString();
                double ogttBgDouble = Double.valueOf(ogttBg);
                if (ogttBgDouble > 9.9){
                    ogttBgMap.put("status",1);
                    ogttBgMap.put("text","口服葡萄糖耐量试验:" +ogttBg+ "g/mmol/L");
                    list.add(ogttBgMap);
                }else if (ogttBgDouble < 4.4){
                    ogttBgMap.put("status",0);
                    ogttBgMap.put("text","口服葡萄糖耐量试验:" +ogttBg+ "mmol/L");
                    list.add(ogttBgMap);
                }
            }*/

            //25尿白细胞 	阴性  rc  lab_nbdb
            HashMap<String, Object> nbdbMap = new HashMap<>();
            nbdbMap.put("order", 25);
            if (null != lab.get("lab_nbdb") && !StringUtils.isBlank(lab.get("lab_nbdb").toString())) {
                String nbdb = lab.get("lab_nbdb").toString();
                if ("阳性".equals(nbdb)) {
                    nbdbMap.put("status", "1");
                    nbdbMap.put("text", "尿白细胞:" + nbdb);
                    list.add(nbdbMap);
                }
            }

            //28 尿糖	阴性	rc随访、
            HashMap<String, Object> labMap = new HashMap<>();
            labMap.put("order", 28);
            if (null != lab.get("lab_nt") && !StringUtils.isBlank(lab.get("lab_nt").toString())) {
                String labNt = lab.get("lab_nt").toString();
                if ("阳性".equals(labNt)) {
                    labMap.put("status", "1");
                    labMap.put("text", "尿糖:" + labNt);
                    list.add(labMap);
                }
            }
            //29 尿酮	阴性	rc随访、ket
            HashMap<String, Object> ketMap = new HashMap<>();
            ketMap.put("order", 29);
            if (null != dka.get("ket") && !StringUtils.isBlank(dka.get("ket").toString())) {
                String ket = dka.get("ket").toString();
                if ("阳性".equals(ket)) {
                    ketMap.put("status", "1");
                    ketMap.put("text", "尿酮:" + ket);
                    list.add(ketMap);
                }
            }

            //31 24小时尿蛋白定量	＜30mg/24h	rc随访、 day_uae
            HashMap<String, Object> dayUaeMap = new HashMap<>();
            dayUaeMap.put("order", 31);
            if (null != lab.get("day_uae") && !StringUtils.isBlank(lab.get("day_uae").toString())) {
                String dayUae = lab.get("day_uae").toString();
                double dayUaeDouble = Double.valueOf(dayUae);
                if (dayUaeDouble >= 30) {
                    dayUaeMap.put("status", "1");
                    dayUaeMap.put("text", "24小时尿蛋白定量:" + dayUae + "mg/24h");
                    list.add(dayUaeMap);
                }
            }


/*
            //32   C肽 0.3~1.3nmol/L"	rc随访、 (lab)lab_ct ,
            HashMap<String, Object> ctMap = new HashMap<>();
            ctMap.put("order", 32);
            if (null != lab.get("lab_ct") && !StringUtils.isBlank(lab.get("lab_ct").toString())) {
                String ct = lab.get("lab_ct").toString();
                Double ctDouble = Double.valueOf(ct);
                if (ctDouble > 1.3) {
                    ctMap.put("status", "1");
                    ctMap.put("text", "C肽:" + ct + "nmol/L");
                    list.add(ctMap);
                }else if (ctDouble < 0.3) {
                    ctMap.put("status", "0");
                    ctMap.put("text", "C肽:" + ct + "nmol/L");
                    list.add(ctMap);
                }
            }
            // 33 胰岛素 27.9~83.6pmol/L  lab_yds
            HashMap<String, Object> ydsMap = new HashMap<>();
            ydsMap.put("order", 33);
            if (null != lab.get("lab_yds") && !StringUtils.isBlank(lab.get("lab_yds").toString())) {
                String yds = lab.get("lab_yds").toString();
                Double ydsDouble = Double.valueOf(yds);
                if (ydsDouble > 83.6) {
                    ydsMap.put("status", "1");
                    ydsMap.put("text", "胰岛素:" + yds + "pmol/L");
                    list.add(ydsMap);
                }else if (ydsDouble < 27.9) {
                    ydsMap.put("status", "0");
                    ydsMap.put("text", "胰岛素:" + yds + "pmol/L");
                    list.add(ydsMap);
                }
            }
*/


            //35 红细胞计数	"男(4.0-5.5)×10^12/L  女(3.5-5.0)×10^12/L"	随访、   lab_xcg_rbc
            HashMap<String, Object> rbcMap = new HashMap<>();
            rbcMap.put("order", 35);
            if (null != lab.get("lab_xcg_rbc") && !StringUtils.isBlank(lab.get("lab_xcg_rbc").toString())) {
                String rbc = lab.get("lab_xcg_rbc").toString();
                Double rbcDouble = Double.valueOf(rbc);
                if("1".equals(sex)){
                    if(rbcDouble < 4.0 ){
                        rbcMap.put("status", "0");
                        rbcMap.put("text", "红细胞计数:" + rbc + "×10^12/L");
                        list.add(rbcMap);
                    }
                    if(rbcDouble > 5.5){
                        rbcMap.put("status", "1");
                        rbcMap.put("text", "红细胞计数:" + rbc + "×10^12/L");
                        list.add(rbcMap);
                    }

                }
                if("2".equals(sex)){
                    if(rbcDouble < 3.5){
                        rbcMap.put("status", "0");
                        rbcMap.put("text", "红细胞计数:" + rbc + "×10^12/L");
                        list.add(rbcMap);
                    }
                    if(rbcDouble > 5.0){
                        rbcMap.put("status", "1");
                        rbcMap.put("text", "红细胞计数:" + rbc + "×10^12/L");
                        list.add(rbcMap);
                    }

                }
            }
            //36血小板计数	（100～300）×10^9/L	随访、  lab_xcg_plt
            HashMap<String, Object> pltMap = new HashMap<>();
            pltMap.put("order", 36);
            if (null != lab.get("lab_xcg_plt") && !StringUtils.isBlank(lab.get("lab_xcg_plt").toString())) {
                String plt = lab.get("lab_xcg_plt").toString();
                Double pltDouble = Double.valueOf(plt);
                if (pltDouble > 300){
                    pltMap.put("status", "1");
                    pltMap.put("text", "血小板计数:" + plt + "×10^9/L");
                    list.add(pltMap);
                }
                if (pltDouble < 100) {
                    pltMap.put("status", "0");
                    pltMap.put("text", "血小板计数:" + plt + "×10^9/L");
                    list.add(pltMap);
                }
            }
        }
        return list;
    }

}
