package com.comvee.cdms.exportexcle.tool;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.sign.po.BloodSugarPO;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author wyc
 * @date 2019/4/22 10:13
 */
public class ExportDataHelper {

    public static Map<String, Object> dealExportData(Map<String, Object> map) {
        HashMap<String, Object> resultMap = new HashMap<>();
        Map<String, Object> memberMap = new HashMap<>();
        Map<String, Object> archivesJsonMap = new HashMap<>();
        Map<String, Object> signMap = new HashMap<>();
        Map<String, Object> basicMap = new HashMap<>();
        Map<String, Object> treatmentMap = new HashMap<>();
        Map<String, Object> labMap = new HashMap<>();
        Map<String, Object> historyMap = new HashMap<>();
        Map<String, Object> hypoglycemiaMap = new HashMap<>();
        Map<String, Object> complicationMap = new HashMap<>();
        Map<String, Object> anamnesisMap = new HashMap<>();
        if (null != map.get("member") && !StringUtils.isBlank(map.get("member").toString())) {
            String member = JSON.toJSONString(map.get("member"));
            memberMap = JsonSerializer.jsonToMap(member);
        }
        if (null != map.get("memberArchives") && !StringUtils.isBlank(map.get("memberArchives").toString())) {
            String memberArchives = JSON.toJSONString(map.get("memberArchives"));
            Map<String, Object> map1 = JsonSerializer.jsonToMap(memberArchives);
            if (null != map1.get("archivesJson") && !StringUtils.isBlank(map1.get("archivesJson").toString())) {
                archivesJsonMap = JsonSerializer.jsonToMap(map1.get("archivesJson").toString());
                System.out.println(archivesJsonMap);
            }
        }
        if (null != map.get("drugListJson") && !StringUtils.isBlank(map.get("drugListJson").toString())) {
            String drugListJson = map.get("drugListJson").toString();
            StringBuffer sb = new StringBuffer();
            List<Map<String, Object>> list = JsonSerializer.jsonToMapList(drugListJson);
            for (Map<String, Object> objectMap : list) {
                if (objectMap != null && objectMap.size() > 0) {
                    if (null != objectMap.get("drugJson") && !StringUtils.isBlank(objectMap.get("drugJson").toString())) {
                        String drugJson = objectMap.get("drugJson").toString();
                        List<Map<String, Object>> list1 = JsonSerializer.jsonToMapList(drugJson);
                        for (Map<String, Object> drugMap : list1) {
                            if (drugMap != null && drugMap.size() > 0) {
                                if (null != drugMap.get("drugName") && !StringUtils.isBlank(drugMap.get("drugName").toString())) {
                                    if (!sb.toString().contains(drugMap.get("drugName").toString())){
                                        sb.append(drugMap.get("drugName").toString() + ";");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            String str = sb.toString();
            if (str.length() > 0){
                resultMap.put("drugCondition", str.substring(0,str.length()-1));
            }
        }
        if (null != archivesJsonMap.get("sign") && !StringUtils.isBlank(archivesJsonMap.get("sign").toString())) {
            String signStr = JSON.toJSONString(archivesJsonMap.get("sign"));
            signMap = JsonSerializer.jsonToMap(signStr);
        }
        if (null != archivesJsonMap.get("basic") && !StringUtils.isBlank(archivesJsonMap.get("basic").toString())) {
            String basicStr = JSON.toJSONString(archivesJsonMap.get("basic"));
            basicMap = JsonSerializer.jsonToMap(basicStr);
        }
        if (null != archivesJsonMap.get("treatment") && !StringUtils.isBlank(archivesJsonMap.get("treatment").toString())) {
            String treatmentStr = JSON.toJSONString(archivesJsonMap.get("treatment"));
            treatmentMap = JsonSerializer.jsonToMap(treatmentStr);
        }
        if (null != archivesJsonMap.get("lab") && !StringUtils.isBlank(archivesJsonMap.get("lab").toString())) {
            String labStr = JSON.toJSONString(archivesJsonMap.get("lab"));
            labMap = JsonSerializer.jsonToMap(labStr);
        }
        if (null != archivesJsonMap.get("history") && !StringUtils.isBlank(archivesJsonMap.get("history").toString())) {
            String historyStr = JSON.toJSONString(archivesJsonMap.get("history"));
            historyMap = JsonSerializer.jsonToMap(historyStr);
        }
        if (null != archivesJsonMap.get("hypoglycemia") && !StringUtils.isBlank(archivesJsonMap.get("hypoglycemia").toString())) {
            String hypoglycemiaStr = JSON.toJSONString(archivesJsonMap.get("hypoglycemia"));
            hypoglycemiaMap = JsonSerializer.jsonToMap(hypoglycemiaStr);
        }
        if (null != archivesJsonMap.get("complication") && !StringUtils.isBlank(archivesJsonMap.get("complication").toString())) {
            String complicationStr = JSON.toJSONString(archivesJsonMap.get("complication"));
            complicationMap = JsonSerializer.jsonToMap(complicationStr);
        }
        if (null != archivesJsonMap.get("anamnesis") && !StringUtils.isBlank(archivesJsonMap.get("anamnesis").toString())) {
            String anamnesisStr = JSON.toJSONString(archivesJsonMap.get("anamnesis"));
            anamnesisMap = JsonSerializer.jsonToMap(anamnesisStr);
        }


        if (memberMap != null && memberMap.size() > 0) {
            //姓名
            if (null != memberMap.get("memberName") && !StringUtils.isBlank(memberMap.get("memberName").toString())) {
                resultMap.put("memberName", memberMap.get("memberName"));
            }
            //性别
            if (null != memberMap.get("sex") && !StringUtils.isBlank(memberMap.get("sex").toString())) {
                String sex = memberMap.get("sex").toString();
                if ("1".equals(sex)) {
                    resultMap.put("sex", "男");
                } else if ("2".equals(sex)) {
                    resultMap.put("sex", "女");
                }
            }
            //年龄
            if (null != memberMap.get("birthday") && !StringUtils.isBlank(memberMap.get("birthday").toString())) {
                String birthday = memberMap.get("birthday").toString();
                int age = DateHelper.getAge(birthday);
                resultMap.put("age", age);
            }
            //身高
            if (null != memberMap.get("height") && !StringUtils.isBlank(memberMap.get("height").toString())) {
                resultMap.put("height", memberMap.get("height") + "cm");
            }
            //体重
            if (null != memberMap.get("weight") && !StringUtils.isBlank(memberMap.get("weight").toString())) {
                resultMap.put("weight", memberMap.get("weight") + "kg");
            }
            //BMI
            if (null != memberMap.get("bmi") && !StringUtils.isBlank(memberMap.get("bmi").toString())) {
                resultMap.put("bmi", memberMap.get("bmi"));
            }
            //手机号 mobilePhone
            if (null != memberMap.get("mobilePhone") && !StringUtils.isBlank(memberMap.get("mobilePhone").toString())) {
                resultMap.put("mobilePhone", memberMap.get("mobilePhone"));
            }
            //身份证号 idCard
            if (null != memberMap.get("idCard") && !StringUtils.isBlank(memberMap.get("idCard").toString())) {
                resultMap.put("idCard", memberMap.get("idCard"));
            }
        }

        if (signMap != null && signMap.size() > 0) {
            //腰围
            if (null != signMap.get("waistline") && !StringUtils.isBlank(signMap.get("waistline").toString())) {
                resultMap.put("waistline", signMap.get("waistline") + "cm");
            }
            //臀围  hipline
            if (null != signMap.get("hipline") && !StringUtils.isBlank(signMap.get("hipline").toString())) {
                resultMap.put("hipline", signMap.get("hipline") + "cm");
            }
            //腰臀比 whr
            if (null != signMap.get("whr") && !StringUtils.isBlank(signMap.get("whr").toString())) {
                resultMap.put("whr", signMap.get("whr"));
            }
            //收缩压   sbp
            if (null != signMap.get("sbp") && !StringUtils.isBlank(signMap.get("sbp").toString())) {
                resultMap.put("sbp", signMap.get("sbp") + "mmHg");
            }
            //舒张压 dbp
            if (null != signMap.get("dbp") && !StringUtils.isBlank(signMap.get("dbp").toString())) {
                resultMap.put("dbp", signMap.get("dbp") + "mmHg");
            }
        }


        if (basicMap != null && basicMap.size() > 0) {
            //确诊时间 diabetes_date
            if (null != basicMap.get("diabetes_date") && !StringUtils.isBlank(basicMap.get("diabetes_date").toString())) {
                resultMap.put("diabetesDate", basicMap.get("diabetes_date"));
            }

            //糖尿病类型 diabetes_type
            if (null != basicMap.get("diabetes_type") && !StringUtils.isBlank(basicMap.get("diabetes_type").toString())) {
                String diabetesType = basicMap.get("diabetes_type").toString();
                if ("SUGAR_TYPE_001".equals(diabetesType)) {
                    resultMap.put("diabetesType", "1型");
                } else if ("SUGAR_TYPE_002".equals(diabetesType)) {
                    resultMap.put("diabetesType", "2型");
                } else if ("SUGAR_TYPE_003".equals(diabetesType)) {
                    resultMap.put("diabetesType", "妊娠");
                } else if ("SUGAR_TYPE_004".equals(diabetesType)) {
                    resultMap.put("diabetesType", "其他");
                }
            }
            //劳动强度  profession
            if (null != basicMap.get("profession") && !StringUtils.isBlank(basicMap.get("profession").toString())) {
                String profession = basicMap.get("profession").toString();
                if ("ZY01".equals(profession)) {
                    resultMap.put("profession", "轻体力劳动：办公室职员、教师...");
                } else if ("ZY02".equals(profession)) {
                    resultMap.put("profession", "中体力：学生、司机、外科医生...");
                } else if ("ZY03".equals(profession)) {
                    resultMap.put("profession", "重体力劳动：建筑工、搬运工...");
                }
            }
            //目前血糖监测频率 jbxx_mujcxtpl_week
            if (null != basicMap.get("jbxx_mujcxtpl_week") && !StringUtils.isBlank(basicMap.get("jbxx_mujcxtpl_week").toString())) {
                String mujcxtplWeek = basicMap.get("jbxx_mujcxtpl_week").toString();
                if ("JCXTPV01".equals(mujcxtplWeek)) {
                    resultMap.put("jbxxMujcxtplWeek", "<1次/周");
                } else if ("JCXTPV02".equals(mujcxtplWeek)) {
                    resultMap.put("jbxxMujcxtplWeek", "1次/周");
                } else if ("JCXTPV03".equals(mujcxtplWeek)) {
                    resultMap.put("jbxxMujcxtplWeek", ">1-3次/周");
                } else if ("JCXTPV04".equals(mujcxtplWeek)) {
                    resultMap.put("jbxxMujcxtplWeek", "3-5次/周");
                } else if ("JCXTPV05".equals(mujcxtplWeek)) {
                    resultMap.put("jbxxMujcxtplWeek", ">5次/周");
                } else if ("JCXTPV06".equals(mujcxtplWeek)) {
                    if (null != basicMap.get("jbxx_mujcxtpl") && !StringUtils.isBlank(basicMap.get("jbxx_mujcxtpl").toString())) {
                        resultMap.put("jbxxMujcxtplWeek", basicMap.get("jbxx_mujcxtpl").toString());
                    }
                }
            }
        }


        if (treatmentMap != null && treatmentMap.size() > 0) {
            //空腹血糖 mq_fbg
            if (null != treatmentMap.get("mq_fbg") && !StringUtils.isBlank(treatmentMap.get("mq_fbg").toString())) {
                resultMap.put("mqFbg", treatmentMap.get("mq_fbg") + "mmol/l");
            }
            //餐后2小时血糖  blg
            if (null != treatmentMap.get("blg") && !StringUtils.isBlank(treatmentMap.get("blg").toString())) {
                resultMap.put("blg", treatmentMap.get("blg") + "mmol/l");
            }
            //睡前血糖 bsg
            if (null != treatmentMap.get("bsg") && !StringUtils.isBlank(treatmentMap.get("bsg").toString())) {
                resultMap.put("bsg", treatmentMap.get("bsg") + "mmol/l");
            }
        }


        if (labMap != null && labMap.size() > 0) {
            //糖化血红蛋白  lab_hba
            if (null != labMap.get("lab_hba") && !StringUtils.isBlank(labMap.get("lab_hba").toString())) {
                resultMap.put("labHba", labMap.get("lab_hba") + "%");
            }
            //低密度脂蛋白   ldl
            if (null != labMap.get("ldl") && !StringUtils.isBlank(labMap.get("ldl").toString())) {
                resultMap.put("ldl", labMap.get("ldl") + "mmol/l");
            }
            //hdl        高低度脂蛋白
            if (null != labMap.get("hdl") && !StringUtils.isBlank(labMap.get("hdl").toString())) {
                resultMap.put("hdl", labMap.get("hdl") + "mmol/l");
            }
            //tg         甘油三脂
            if (null != labMap.get("tg") && !StringUtils.isBlank(labMap.get("tg").toString())) {
                resultMap.put("tg", labMap.get("tg") + "mmol/l");
            }
            //tc         总胆固醇
            if (null != labMap.get("tc") && !StringUtils.isBlank(labMap.get("tc").toString())) {
                resultMap.put("tc", labMap.get("tc") + "mmol/l");
            }
            //cr         血肌酐
            if (null != labMap.get("cr") && !StringUtils.isBlank(labMap.get("cr").toString())) {
                resultMap.put("cr", labMap.get("cr") + "umol/l");
            }
            //lab_xns     血尿酸
            if (null != labMap.get("lab_xns") && !StringUtils.isBlank(labMap.get("lab_xns").toString())) {
                resultMap.put("xns", labMap.get("lab_xns") + "umol/l");
            }
            //alt      谷丙转氨酶
            if (null != labMap.get("alt") && !StringUtils.isBlank(labMap.get("alt").toString())) {
                resultMap.put("alt", labMap.get("alt") + "U/L");
            }
        }


        if (historyMap != null && historyMap.size() > 0) {
            //是否接受过糖尿病教育  diabetes_edu
            if (null != historyMap.get("diabetes_edu") && !StringUtils.isBlank(historyMap.get("diabetes_edu").toString())) {
                String diabetesEdu = historyMap.get("diabetes_edu").toString();
                if ("TNBJY02".equals(diabetesEdu)) {
                    resultMap.put("diabetesEdu", "没有");
                } else{
                    String replace = diabetesEdu.replace("TNBJY04", "糖尿病基础知识")
                            .replace("TNBJY05", "饮食")
                            .replace("TNBJY06", "运动")
                            .replace("TNBJY07", "药物")
                            .replace("TNBJY08", "自我监测")
                            .replace("TNBJY09", "糖尿病并发症");
                    resultMap.put("diabetesEdu", replace);
                }
                if ("空".equals(diabetesEdu)){
                    resultMap.put("diabetesEdu"," -- ");
                }
            }
            //目前运动方式  bs_sport_type
            if (null != historyMap.get("bs_sport_type") && !StringUtils.isBlank(historyMap.get("bs_sport_type").toString())) {
                String sportType = historyMap.get("bs_sport_type").toString();
                String replace = reportTypeReplace(sportType);
                resultMap.put("sportType", replace);
                if ("空".equals(sportType)){
                    resultMap.put("sportType", " -- ");
                }
            }
            double energy = 0;
            //bs_dinner_jc   睡前加餐
            if (null != historyMap.get("bs_dinner_jc") && !StringUtils.isBlank(historyMap.get("bs_dinner_jc").toString())) {
                String dinnerJc = historyMap.get("bs_dinner_jc").toString();
                List<Map<String, Object>> list = JsonSerializer.jsonToMapList(dinnerJc);
                if (list != null && list.size() > 0) {
                    for (Map<String, Object> map1 : list) {
                        if (null != map1.get("num") && !StringUtils.isBlank(map1.get("num").toString()) && null != map1.get("heat") && !StringUtils.isBlank(map1.get("heat").toString())) {
                            double num = Double.valueOf(map1.get("num").toString());
                            double heat = Double.valueOf(map1.get("heat").toString());
                            //new DecimalFormat("#.00");
                            energy += num * heat;
//                            energy += new DecimalFormat("#.00").format((num * heat));
                        }
                    }
                }
            }
            //bs_jcnr  早午间加餐
            if (null != historyMap.get("bs_jcnr") && !StringUtils.isBlank(historyMap.get("bs_jcnr").toString())) {
                String jcnr = historyMap.get("bs_jcnr").toString();
                List<Map<String, Object>> list = JsonSerializer.jsonToMapList(jcnr);
                if (list != null && list.size() > 0) {
                    for (Map<String, Object> map1 : list) {
                        if (null != map1.get("num") && !StringUtils.isBlank(map1.get("num").toString()) && null != map1.get("heat") && !StringUtils.isBlank(map1.get("heat").toString())) {
                            double num = Double.valueOf(map1.get("num").toString());
                            double heat = Double.valueOf(map1.get("heat").toString());
                            energy += num * heat;
                        }
                    }
                }
            }
            //bs_lunch_jc 午晚间
            if (null != historyMap.get("bs_lunch_jc") && !StringUtils.isBlank(historyMap.get("bs_lunch_jc").toString())) {
                String lunchJc = historyMap.get("bs_lunch_jc").toString();
                List<Map<String, Object>> list = JsonSerializer.jsonToMapList(lunchJc);
                if (list != null && list.size() > 0) {
                    for (Map<String, Object> map1 : list) {
                        if (null != map1.get("num") && !StringUtils.isBlank(map1.get("num").toString()) && null != map1.get("heat") && !StringUtils.isBlank(map1.get("heat").toString())) {
                            double num = Double.valueOf(map1.get("num").toString());
                            double heat = Double.valueOf(map1.get("heat").toString());
                            energy += num * heat;
                        }
                    }
                }
            }
            //bs_zczslx
            if (null != historyMap.get("bs_zczslx") && !StringUtils.isBlank(historyMap.get("bs_zczslx").toString())) {
                String zczslx = historyMap.get("bs_zczslx").toString();
                List<Map<String, Object>> list = JsonSerializer.jsonToMapList(zczslx);
                if (list != null && list.size() > 0) {
                    for (Map<String, Object> map1 : list) {
                        if (null != map1.get("num") && !StringUtils.isBlank(map1.get("num").toString()) && null != map1.get("heat") && !StringUtils.isBlank(map1.get("heat").toString())) {
                            double num = Double.valueOf(map1.get("num").toString());
                            double heat = Double.valueOf(map1.get("heat").toString());
                            energy += num * heat;
                        }
                    }
                }
            }
            //bs_wuczslx
            if (null != historyMap.get("bs_wuczslx") && !StringUtils.isBlank(historyMap.get("bs_wuczslx").toString())) {
                String wuczslx = historyMap.get("bs_wuczslx").toString();
                List<Map<String, Object>> list = JsonSerializer.jsonToMapList(wuczslx);
                if (list != null && list.size() > 0) {
                    for (Map<String, Object> map1 : list) {
                        if (null != map1.get("num") && !StringUtils.isBlank(map1.get("num").toString()) && null != map1.get("heat") && !StringUtils.isBlank(map1.get("heat").toString())) {
                            double num = Double.valueOf(map1.get("num").toString());
                            double heat = Double.valueOf(map1.get("heat").toString());
                            energy += num * heat;
                        }
                    }
                }
            }
            //bs_wanczslx
            if (null != historyMap.get("bs_wanczslx") && !StringUtils.isBlank(historyMap.get("bs_wanczslx").toString())) {
                String wanczslx = historyMap.get("bs_wanczslx").toString();
                List<Map<String, Object>> list = JsonSerializer.jsonToMapList(wanczslx);
                if (list != null && list.size() > 0) {
                    for (Map<String, Object> map1 : list) {
                        if (null != map1.get("num") && !StringUtils.isBlank(map1.get("num").toString()) && null != map1.get("heat") && !StringUtils.isBlank(map1.get("heat").toString())) {
                            double num = Double.valueOf(map1.get("num").toString());
                            double heat = Double.valueOf(map1.get("heat").toString());
                            energy += num * heat;
                        }
                    }
                }
            }
            if (energy == 0){
                resultMap.put("energy","--");
            }else{
                resultMap.put("energy", new DecimalFormat("0.00").format(energy) + "千卡");
            }
        }

        if (hypoglycemiaMap != null && hypoglycemiaMap.size() > 0) {
            //是否发生过低血糖 hyp
            if (null != hypoglycemiaMap.get("hyp") && !StringUtils.isBlank(hypoglycemiaMap.get("hyp").toString())) {
                String hypStr = hypoglycemiaMap.get("hyp").toString();
                if ("1".equals(hypStr)) {
                    resultMap.put("hyp", "是");
                    //近一个月内发生低血糖次数 hyp_frequency
                    if (null != hypoglycemiaMap.get("hyp_frequency") && !StringUtils.isBlank(hypoglycemiaMap.get("hyp_frequency").toString())) {
                        if ("0".equals(hypoglycemiaMap.get("hyp_frequency").toString())){
                            resultMap.put("hypFrequency", " -- ");
                        }else {
                            resultMap.put("hypFrequency", hypoglycemiaMap.get("hyp_frequency") + "次");
                        }
                    }
                } else if ("2".equals(hypStr)) {
                    resultMap.put("hyp", "否");
                }
            }

        }


        if (complicationMap != null && complicationMap.size() > 0) {
            //neph_pro_value     尿蛋白
            if (null != complicationMap.get("neph_pro_value") && !StringUtils.isBlank(complicationMap.get("neph_pro_value").toString())) {
                resultMap.put("nephPro", complicationMap.get("neph_pro_value"));
            }
            //retinal    是否有糖尿病眼底病变  SWM01
            if (null != complicationMap.get("retinal") && !StringUtils.isBlank(complicationMap.get("retinal").toString())) {
                String retinal = complicationMap.get("retinal").toString();
                if ("SWM01".equals(retinal)) {
                    resultMap.put("retinal", "确诊有");
                    //retinal_type_treat    治疗情况  QK01
                    if (null != complicationMap.get("retinal_type_treat") && !StringUtils.isBlank(complicationMap.get("retinal_type_treat").toString())) {
                        String typeTreat = complicationMap.get("retinal_type_treat").toString();
                        if ("QK01".equals(typeTreat)) {
                            resultMap.put("retinalTreat", "已治疗");
                        } else if ("QK02".equals(typeTreat)) {
                            resultMap.put("retinalTreat", "未治疗");
                        } else if ("QK03".equals(typeTreat)) {
                            resultMap.put("retinalTreat", "已用药");
                        } else if ("QK04".equals(typeTreat)) {
                            resultMap.put("retinalTreat", "未用药");
                        } else if ("QK05".equals(typeTreat)) {
                            resultMap.put("retinalTreat", "正常");
                        }
                        //retinal_type_cms    疾病类型
                        if (null != complicationMap.get("retinal_type_cms") && !StringUtils.isBlank(complicationMap.get("retinal_type_cms").toString())) {
                            String retinalCms = complicationMap.get("retinal_type_cms").toString();
                            if ("LX01".equals(retinalCms)) {
                                resultMap.put("retinalCms", "视网膜病变单纯型");
                            } else if ("LX02".equals(retinalCms)) {
                                resultMap.put("retinalCms", "视网膜病变增殖型");
                            } else if ("LX03".equals(retinalCms)) {
                                if (null != complicationMap.get("retinal_type_cms_remark") && !StringUtils.isBlank(complicationMap.get("retinal_type_cms_remark").toString())) {
                                    resultMap.put("retinalCms", complicationMap.get("retinal_type_cms_remark").toString());
                                } else {
                                    resultMap.put("retinalCms", "");
                                }
                            }
                        }
                    }
                } else if ("SWM02".equals(retinal)) {
                    resultMap.put("retinal", "确诊无");
                } else if ("SWM03".equals(retinal)) {
                    resultMap.put("retinal", "未评估");
                } else if ("SWM04".equals(retinal)) {
                    resultMap.put("retinal", "疑似");
                }
                if ("SWM01".equals(retinal) || "SWM02".equals(retinal) || "SWM04".equals(retinal)) {
                    //retinal_date    检查时间
                    if (null != complicationMap.get("retinal_date") && !StringUtils.isBlank(complicationMap.get("retinal_date").toString())) {
                        resultMap.put("retinalDate", complicationMap.get("retinal_date"));
                    }
                }
                if ("SWM01".equals(retinal) || "SWM03".equals(retinal)) {
                    //                        ret_symptom_cms   表现症状  ZZ01
                    if (null != complicationMap.get("ret_symptom_cms") && !StringUtils.isBlank(complicationMap.get("ret_symptom_cms").toString())) {
                        String symptomCms = complicationMap.get("ret_symptom_cms").toString();
                        String symptomCmsRemark = "";
                        if (symptomCms.contains("ZZ09")) {
                            if (null != complicationMap.get("ret_symptom_cms_remark") && !StringUtils.isBlank(complicationMap.get("ret_symptom_cms_remark").toString())) {
                                symptomCmsRemark = complicationMap.get("ret_symptom_cms_remark").toString();
                            }
                        }
                        if ("ZZ01".equals(symptomCms)) {
                            resultMap.put("symptomCms", "无症状");
                        } else {
                            String replace = symptomCms.replace("ZZ02", "视野模糊").replace("ZZ03", "眼睛发红且长久不退").replace("ZZ04", "眼部有压力感")
                                    .replace("ZZ05", "眼花").replace("ZZ06", "视野重影").replace("ZZ07", "看见光斑或漂浮物")
                                    .replace("ZZ08", "眼前有飞虫感").replace("ZZ09", symptomCmsRemark);
                            resultMap.put("symptomCms", replace);
                        }
                        if ("空".equals(symptomCms)){
                            resultMap.put("symptomCms", " -- ");
                        }
                    }
                }
            }

            //nephropathy   是否有糖尿病肾病  SB01
            if (null != complicationMap.get("nephropathy") && !StringUtils.isBlank(complicationMap.get("nephropathy").toString())) {
                String nephropathy = complicationMap.get("nephropathy").toString();
                if ("SB01".equals(nephropathy)) {
                    resultMap.put("nephropathy", "确诊有");
                    //neph_treat   治疗情况
                    if (null != complicationMap.get("neph_treat") && !StringUtils.isBlank(complicationMap.get("neph_treat").toString())) {
                        String nephTreat = complicationMap.get("neph_treat").toString();
                        if ("QK01".equals(nephTreat)) {
                            resultMap.put("nephTreat", "已治疗");
                        } else if ("QK02".equals(nephTreat)) {
                            resultMap.put("nephTreat", "未治疗");
                        } else if ("QK03".equals(nephTreat)) {
                            resultMap.put("nephTreat", "已用药");
                        } else if ("QK04".equals(nephTreat)) {
                            resultMap.put("nephTreat", "未用药");
                        } else if ("QK05".equals(nephTreat)) {
                            resultMap.put("nephTreat", "正常");
                        }
                    }
                    //neph_type_cms   疾病类型  LX01
                    if (null != complicationMap.get("neph_type_cms") && !StringUtils.isBlank(complicationMap.get("neph_type_cms").toString())) {
                        String nephCms = complicationMap.get("neph_type_cms").toString();
                        if (null != complicationMap.get("retinal_type_cms") && !StringUtils.isBlank(complicationMap.get("retinal_type_cms").toString())) {
                            if ("LX01".equals(nephCms)) {
                                resultMap.put("nephCms", "肾小球高滤过和肾脏肥大");
                            } else if ("LX02".equals(nephCms)) {
                                resultMap.put("nephCms", "无临床表现的肾损害期");
                            } else if ("LX03".equals(nephCms)) {
                                resultMap.put("nephCms", "早期糖尿病肾病期");
                            } else if ("LX04".equals(nephCms)) {
                                resultMap.put("nephCms", "临床糖尿病肾病期");
                            } else if ("LX05".equals(nephCms)) {
                                resultMap.put("nephCms", "肾衰竭期");
                            } else if ("LX06".equals(nephCms)) {
                                if (null != complicationMap.get("neph_type_remark") && !StringUtils.isBlank(complicationMap.get("neph_type_remark").toString())) {
                                    resultMap.put("nephCms", complicationMap.get("neph_type_remark").toString());
                                } else {
                                    resultMap.put("nephCms", "");
                                }

                            }
                        }
                    }
                } else if ("SB02".equals(nephropathy)) {
                    resultMap.put("nephropathy", "确诊无");
                } else if ("SB03".equals(nephropathy)) {
                    resultMap.put("nephropathy", "未评估");
                } else if ("SB04".equals(nephropathy)) {
                    resultMap.put("nephropathy", "疑似");
                }
                if ("SB01".equals(nephropathy) || "SB02".equals(nephropathy) || "SB04".equals(nephropathy)) {
                    //neph_date   检查时间 检查时间
                    if (null != complicationMap.get("neph_date") && !StringUtils.isBlank(complicationMap.get("neph_date").toString())) {
                        resultMap.put("nephDate", complicationMap.get("neph_date"));
                    }
                }
                if ("SB01".equals(nephropathy) || "SB03".equals(nephropathy)) {
                    //neph_symptom_cms   表现症状
                    if (null != complicationMap.get("neph_symptom_cms") && !StringUtils.isBlank(complicationMap.get("neph_symptom_cms").toString())) {
                        String nephSymptom = complicationMap.get("neph_symptom_cms").toString();
                        String nephSymptomRemark = "";
                        if (nephSymptom.contains("ZZ07")) {
                            if (null != complicationMap.get("neph_symptom_cms_remark") && !StringUtils.isBlank(complicationMap.get("neph_symptom_cms_remark").toString())) {
                                nephSymptomRemark = complicationMap.get("neph_symptom_cms_remark").toString();
                            }
                        }
                        if ("ZZ01".equals(nephSymptom)) {
                            resultMap.put("nephSymptom", "无症状");
                        } else {
                            String replace = nephSymptom.replace("ZZ02", "全身浮肿").replace("ZZ03", "持续性血压增高（表现为头痛、记忆力减退、睡眠不佳症状等）").replace("ZZ04", "血尿")
                                    .replace("ZZ05", "夜尿次数多").replace("ZZ06", "蛋白尿（尿中泡沫增多）").replace("ZZ07", nephSymptomRemark);
                            resultMap.put("nephSymptom", replace);
                        }
                        if ("空".equals(nephSymptom)){
                            resultMap.put("nephSymptom"," -- ");
                        }
                    }
                }

            }
            //neuropathy   是否有糖尿病周围神经病变
            if (null != complicationMap.get("neuropathy") && !StringUtils.isBlank(complicationMap.get("neuropathy").toString())) {
                String neuropathy = complicationMap.get("neuropathy").toString();
                if ("ZWSJ01".equals(neuropathy)) {
                    resultMap.put("neuropathy", "确诊有");
                } else if ("ZWSJ02".equals(neuropathy)) {
                    resultMap.put("neuropathy", "确诊无");
                } else if ("ZWSJ03".equals(neuropathy)) {
                    resultMap.put("neuropathy", "未评估");

                } else if ("ZWSJ04".equals(neuropathy)) {
                    resultMap.put("neuropathy", "疑似");
                }
                if ("ZWSJ01".equals(neuropathy) || "ZWSJ03".equals(neuropathy)) {
                    //neu_symptom_cms   表现症状
                    if (null != complicationMap.get("neu_symptom_cms") && !StringUtils.isBlank(complicationMap.get("neu_symptom_cms").toString())) {
                        String neuSymptom = complicationMap.get("neu_symptom_cms").toString();
                        String neuSymptomRemark = "";
                        if (neuSymptom.contains("ZZ12")) {
                            if (null != complicationMap.get("neu_symptom_cms_remark") && !StringUtils.isBlank(complicationMap.get("neu_symptom_cms_remark").toString())) {
                                neuSymptomRemark = complicationMap.get("neu_symptom_cms_remark").toString();
                            }
                        }

                        if ("ZZ01".equals(neuSymptom)) {
                            resultMap.put("neuSymptom", "无症状");
                        } else {
                            String replace = neuSymptom.replace("ZZ02", " 袜套样感觉").replace("ZZ03", "灼热感").replace("ZZ04", "感觉减退甚至消失")
                                    .replace("ZZ05", " 麻木").replace("ZZ06", "异常出汗").replace("ZZ07", "腹胀")
                                    .replace("ZZ08", "疼痛(如电击样疼痛、针刺、火烤、撕裂样疼痛)").replace("ZZ11", "撕裂样疼痛").replace("ZZ12", neuSymptomRemark);
                            resultMap.put("neuSymptom", replace);
                        }
                        if ("空".equals(neuSymptom)){
                            resultMap.put("neuSymptom", " -- ");
                        }
                    }
                }
            }

            //diabetic_foot     是否有糖尿病足
            if (null != complicationMap.get("diabetic_foot") && !StringUtils.isBlank(complicationMap.get("diabetic_foot").toString())) {
                String diabeticFoot = complicationMap.get("diabetic_foot").toString();
                if ("TNBZ01".equals(diabeticFoot)) {
                    resultMap.put("diabeticFoot", "确诊有");
                    //df_level   表现症状  CD01
                    if (null != complicationMap.get("df_level") && !StringUtils.isBlank(complicationMap.get("df_level").toString())) {
                        String dfLevel = complicationMap.get("df_level").toString();
                        if ("CD01".equals(dfLevel)) {
                            resultMap.put("dfLevel", "0级（有发生足溃疡危险因素）");
                        } else if ("CD02".equals(dfLevel)) {
                            resultMap.put("dfLevel", "1级（表面溃疡，临床无感染）");
                        } else if ("CD03".equals(dfLevel)) {
                            resultMap.put("dfLevel", "2级（较深的感染，无脓肿或骨的感染）");
                        } else if ("CD04".equals(dfLevel)) {
                            resultMap.put("dfLevel", "3级（深度感染，伴有骨组织病变或脓肿)");
                        } else if ("CD05".equals(dfLevel)) {
                            resultMap.put("dfLevel", " 4级（趾、足跟或前足背局限性坏疽）");
                        } else if ("CD06".equals(dfLevel)) {
                            resultMap.put("dfLevel", "5级（全足坏疽)");
                        } else if ("CD07".equals(dfLevel)) {
                            //df_level_remark
                            if (null != complicationMap.get("df_level_remark") && !StringUtils.isBlank(complicationMap.get("df_level_remark").toString())) {
                                resultMap.put("dfLevel", complicationMap.get("df_level_remark").toString());
                            } else {
                                resultMap.put("dfLevel", "");
                            }

                        }
                    }
                } else if ("TNBZ02".equals(diabeticFoot)) {
                    resultMap.put("diabeticFoot", "确诊无");
                }
                if ("TNBZ03".equals(diabeticFoot)) {
                    resultMap.put("diabeticFoot", "未评估");
                }
                if ("TNBZ04".equals(diabeticFoot)) {
                    resultMap.put("diabeticFoot", "疑似");
                }
            }

            //dan      是否有糖尿病自主神经病变
            if (null != complicationMap.get("dan") && !StringUtils.isBlank(complicationMap.get("dan").toString())) {
                String dan = complicationMap.get("dan").toString();
                if ("ZZ01".equals(dan)) {
                    resultMap.put("dan", "确诊有");
                    //dan_type   疾病类型
                    if (null != complicationMap.get("dan_type") && !StringUtils.isBlank(complicationMap.get("dan_type").toString())) {
                        String danType = complicationMap.get("dan_type").toString();
                        //dan_type_remark
                        String danTypeRemark = "";
                        if (danType.contains("LX07")) {
                            if (null != complicationMap.get("dan_type_remark") && !StringUtils.isBlank(complicationMap.get("dan_type_remark").toString())) {
                                danTypeRemark = complicationMap.get("dan_type_remark").toString();
                            }
                        }
                        String replace = danType.replace("LX01", "体位低血压").replace("LX02", "胃轻瘫(进食后食物不能往下走)").replace("LX03", "汗腺神经病变(下肢无汗、上肢或局部多汗)")
                                .replace("LX04", "男性阳痿").replace("LX05", "尿潴留（排尿困难）").replace("LX06", "心脏自主神经改变(心率变化大)")
                                .replace("LX07", danTypeRemark);
                        resultMap.put("danType", replace);
                    }
                } else if ("ZZ02".equals(dan)) {
                    resultMap.put("dan", "确诊无");
                } else if ("ZZ03".equals(dan)) {
                    resultMap.put("dan", "未评估");
                } else if ("ZZ04".equals(dan)) {
                    resultMap.put("dan", "疑似");
                }
                if ("ZZ01".equals(dan) || "ZZ01".equals(dan)) {
                    //dan_symptom   表现症状
                    if (null != complicationMap.get("dan_symptom") && !StringUtils.isBlank(complicationMap.get("dan_symptom").toString())) {
                        String danSymptom = complicationMap.get("dan_symptom").toString();
                        //dan_symptom_remark
                        String danSymptomRemark = "";
                        if (danSymptom.contains("ZZ06")) {
                            if (null != complicationMap.get("dan_symptom_remark") && !StringUtils.isBlank(complicationMap.get("dan_symptom_remark").toString())) {
                                danSymptomRemark = complicationMap.get("dan_symptom_remark").toString();
                            }
                        }
                        if ("ZZ01".equals(danSymptom)) {
                            resultMap.put("danSymptom", "无症状");
                        } else {
                            String replace = danSymptom.replace("ZZ02", " 尿潴留").replace("ZZ03", " 顽固的便秘或腹泻").replace("ZZ04", "出汗异常")
                                    .replace("ZZ05", "心悸或心动过缓").replace("ZZ06", danSymptomRemark);
                            resultMap.put("danSymptom", replace);
                        }
                        if ("空".equals(danSymptom)){
                            resultMap.put("danSymptom", " -- ");
                        }
                    }
                }
            }

            //chd     是否有冠心病
            if (null != complicationMap.get("chd") && !StringUtils.isBlank(complicationMap.get("chd").toString())) {
                String chd = complicationMap.get("chd").toString();
                if ("QZ01".equals(chd)) {
                    resultMap.put("chd", "确诊有");
                    //cbd_treat   治疗情况
                    if (null != complicationMap.get("cbd_treat") && !StringUtils.isBlank(complicationMap.get("cbd_treat").toString())) {
                        String cbdTreat = complicationMap.get("cbd_treat").toString();
                        if ("ZL01".equals(cbdTreat)) {
                            resultMap.put("cbdTreat", "已治疗");
                        } else if ("ZL02".equals(cbdTreat)) {
                            resultMap.put("cbdTreat", "未治疗");
                        } else if ("ZL03".equals(cbdTreat)) {
                            resultMap.put("cbdTreat", "已用药");
                        } else if ("ZL04".equals(cbdTreat)) {
                            resultMap.put("cbdTreat", "未用药");
                        } else if ("ZL05".equals(cbdTreat)) {
                            resultMap.put("cbdTreat", "正常");
                        }
                    }
                    //chd_type     疾病类型
                    if (null != complicationMap.get("chd_type") && !StringUtils.isBlank(complicationMap.get("chd_type").toString())) {
                        String chdType = complicationMap.get("chd_type").toString();
                        if ("LX01".equals(chdType)) {
                            resultMap.put("chdType", "无症状心肌缺血（隐匿性冠心病）");
                        } else if ("LX02".equals(chdType)) {
                            resultMap.put("chdType", "心绞痛");
                        } else if ("LX03".equals(chdType)) {
                            resultMap.put("chdType", "心肌梗死");
                        } else if ("LX04".equals(chdType)) {
                            resultMap.put("chdType", " 缺血性心力衰竭（缺血性心脏病）");
                        } else if ("LX05".equals(chdType)) {
                            //chd_type_remark
                            if (null != complicationMap.get("chd_type_remark") && !StringUtils.isBlank(complicationMap.get("chd_type_remark").toString())) {
                                resultMap.put("chdType", complicationMap.get("chd_type_remark").toString());
                            } else {
                                resultMap.put("chdType", "");
                            }
                        }
                    }
                } else if ("QZ02".equals(chd)) {
                    resultMap.put("chd", "确诊无");
                } else if ("QZ03".equals(chd)) {
                    resultMap.put("chd", "未评估");
                } else if ("QZ04".equals(chd)) {
                    resultMap.put("chd", "疑似");
                }
                if ("QZ01".equals(chd) || "QZ02".equals(chd) || "QZ04".equals(chd)) {
                    //chd_date    检查时间
                    if (null != complicationMap.get("chd_date") && !StringUtils.isBlank(complicationMap.get("chd_date").toString())) {
                        resultMap.put("chdDate", complicationMap.get("chd_date"));
                    }
                }
                if ("QZ01".equals(chd) || "QZ03".equals(chd)) {
                    //chd_symptom   表现症状
                    if (null != complicationMap.get("chd_symptom") && !StringUtils.isBlank(complicationMap.get("chd_symptom").toString())) {
                        String chdSymptom = complicationMap.get("chd_symptom").toString();
                        //chd_symptom_remark
                        String chdSymptomRemark = "";
                        if (chdSymptom.contains("ZZ13")) {
                            if (null != complicationMap.get("chd_symptom_remark") && !StringUtils.isBlank(complicationMap.get("chd_symptom_remark").toString())) {
                                chdSymptomRemark = complicationMap.get("chd_symptom_remark").toString();
                            }
                        }
                        if ("ZZ01".equals(chdSymptom)) {
                            resultMap.put("chdSymptom", "无症状");
                        } else {
                            String replace = chdSymptom.replace("ZZ02", " 典型胸痛").replace("ZZ03", " 心前区不适").replace("ZZ04", "出汗")
                                    .replace("ZZ05", "惊恐").replace("ZZ06", " 恶心").replace("ZZ07", " 心悸")
                                    .replace("ZZ08", " 乏力").replace("ZZ09", " 心力衰竭").replace("ZZ10", " 发热")
                                    .replace("ZZ11", " 猝死").replace("ZZ12", " 呕吐").replace("ZZ13", chdSymptomRemark);
                            resultMap.put("chdSymptom", replace);
                        }
                        if ("空".equals(chdSymptom)){
                            resultMap.put("chdSymptom", " -- ");
                        }
                    }
                }
            }
            //pad    是否有下肢血管病变
            if (null != complicationMap.get("pad") && !StringUtils.isBlank(complicationMap.get("pad").toString())) {
                String pad = complicationMap.get("pad").toString();
                if ("XZXG01".equals(pad)) {
                    resultMap.put("pad", "确诊有");
                } else if ("XZXG02".equals(pad)) {
                    resultMap.put("pad", "确诊无");
                } else if ("XZXG03".equals(pad)) {
                    resultMap.put("pad", "未评估");
                } else if ("XZXG04".equals(pad)) {
                    resultMap.put("pad", "疑似");
                }
                if ("XZXG01".equals(pad) || "XZXG03".equals(pad)) {
                    //pad_symptom_cms  表现症状
                    if (null != complicationMap.get("pad_symptom_cms") && !StringUtils.isBlank(complicationMap.get("pad_symptom_cms").toString())) {
                        String padSymptom = complicationMap.get("pad_symptom_cms").toString();
                        //pad_symptom_remark_cms
                        String padSymptomRemark = "";
                        if (padSymptom.contains("ZZ07")) {
                            if (null != complicationMap.get("pad_symptom_remark_cms") && !StringUtils.isBlank(complicationMap.get("pad_symptom_remark_cms").toString())) {
                                padSymptomRemark = complicationMap.get("pad_symptom_remark_cms").toString();
                            }
                        }
                        String replace = padSymptom.replace("ZZ01", " 下肢跛行").replace("ZZ02", " 脚部感染").replace("ZZ03", " 腿、足、脚趾的静息疼痛")
                                .replace("ZZ04", "夜间腿痛").replace("ZZ05", "下肢发凉").replace("ZZ06", " 足或脚趾的溃疡或坏疽")
                                .replace("ZZ07", padSymptomRemark);
                        resultMap.put("padSymptom", replace);
                        if ("空".equals(padSymptom)){
                            resultMap.put("padSymptom", " -- ");
                        }

                    }
                }
            }
            //是否有心脑血管疾病
            if (null != complicationMap.get("has_xnxg") && !StringUtils.isBlank(complicationMap.get("has_xnxg").toString())){
                String hasXnxg = complicationMap.get("has_xnxg").toString();
                if ("1".equals(hasXnxg)){
                    resultMap.put("hasXnxg", "确诊有");
                    //治疗情况
                    if (null != complicationMap.get("xnxg_status") && !StringUtils.isBlank(complicationMap.get("xnxg_status").toString())){
                        String xnxgStatus = complicationMap.get("xnxg_status").toString();
                        if ("QK01".equals(xnxgStatus)){
                            resultMap.put("xnxgStatus", "已治疗");
                        }else if ("QK02".equals(xnxgStatus)){
                            resultMap.put("xnxgStatus", "未治疗");
                        }else if ("QK03".equals(xnxgStatus)){
                            resultMap.put("xnxgStatus", "已用药");
                        }else if ("QK04".equals(xnxgStatus)){
                            resultMap.put("xnxgStatus", "未用药");
                        }else if ("QK05".equals(xnxgStatus)){
                            resultMap.put("xnxgStatus", "正常");
                        }

                    }
                }else if ("2".equals(hasXnxg)){
                    resultMap.put("hasXnxg", "确诊无");
                }else if ("3".equals(hasXnxg)){
                    resultMap.put("hasXnxg", "未评估");
                }
                //检查日期
                if (null != complicationMap.get("xnxg_dt") && !StringUtils.isBlank(complicationMap.get("xnxg_dt").toString())){
                    String xnxgDt = complicationMap.get("xnxg_dt").toString();
                    resultMap.put("xnxgDt", xnxgDt);
                }
                //心血管危险因素
                if (null != complicationMap.get("xnxg_show") && !StringUtils.isBlank(complicationMap.get("xnxg_show").toString())){
                    String xnxgShow = complicationMap.get("xnxg_show").toString();
                    xnxgShow = xnxgShow.replace("ZZO1","早发性心血管疾病家族史").replace("ZZO2","高血压")
                            .replace("ZZO3","血脂紊乱").replace("ZZO4","蛋白尿");
                    resultMap.put("xnxg_show", xnxgShow);
                }



            }
            //jyntnbjxbfz     近一年急性并发症情况
            if (null != complicationMap.get("complications_diabetes_status_02") || null != complicationMap.get("complications_diabetes_status_04") || null != complicationMap.get("complications_diabetes_status_05") || null != complicationMap.get("complications_diabetes_status_06")){
                if (null != complicationMap.get("jyntnbjxbfz") && !StringUtils.isBlank(complicationMap.get("jyntnbjxbfz").toString())) {
                    String jyntnbjxbfz = complicationMap.get("jyntnbjxbfz").toString();
                    if ("LX01".equals(jyntnbjxbfz)) {
                        resultMap.put("jyntnbjxbfz", "没有");
                    } else {
                        String two="";
                        String four="";
                        String five="";
                        String six="";
                       if (null !=complicationMap.get("complications_diabetes_status_02") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_02").toString())){
                            two = complicationMap.get("complications_diabetes_status_02").toString()+"次";
                        }
                        if (null !=complicationMap.get("complications_diabetes_status_04") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_04").toString())){
                            four = complicationMap.get("complications_diabetes_status_04").toString()+"次";
                        }
                        if (null !=complicationMap.get("complications_diabetes_status_05") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_05").toString())){
                            five = complicationMap.get("complications_diabetes_status_05").toString()+"次";
                        }
                        if (null !=complicationMap.get("complications_diabetes_status_06") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_06").toString())){
                            six = complicationMap.get("complications_diabetes_status_06").toString()+"次";
                        }
                        String replace = jyntnbjxbfz.replace("LX02", "酮症酸中毒"+two).replace("LX04", "高渗性昏迷"+four)
                                .replace("LX05", "乳酸酸中毒"+five).replace("LX06", "自诊断DM后发生过的次数"+six);
                        resultMap.put("jyntnbjxbfz", replace);
                    }
                    if ("空".equals(jyntnbjxbfz)){
                        resultMap.put("jyntnbjxbfz", " -- ");
                    }
                }
            }else {
                //jyntnbjxbfz     近一年急性并发症情况
                if (null != complicationMap.get("jyntnbjxbfz") && !StringUtils.isBlank(complicationMap.get("jyntnbjxbfz").toString())) {
                    String jyntnbjxbfz = complicationMap.get("jyntnbjxbfz").toString();
                    if ("LX01".equals(jyntnbjxbfz)) {
                        resultMap.put("jyntnbjxbfz", "没有");
                    } else {
                        String replace = jyntnbjxbfz.replace("LX02", "  酮症酸中毒").replace("LX03", " 低血糖")
                                .replace("LX04", "高渗性昏迷").replace("LX05", "乳酸酸中毒");
                        resultMap.put("jyntnbjxbfz", replace);
                    }
                    if ("空".equals(jyntnbjxbfz)) {
                        resultMap.put("jyntnbjxbfz", " -- ");
                    }
                }
            }

        }

        if (anamnesisMap != null && anamnesisMap.size() > 0) {
            //essential_hyp     是否有原发性高血压
            if (null != anamnesisMap.get("essential_hyp") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp").toString())) {
                String essentialHyp = anamnesisMap.get("essential_hyp").toString();
                if ("1".equals(essentialHyp)) {
                    resultMap.put("essentialHyp", "有");
                    //essential_hyp_treat    治疗情况
                    if (null != anamnesisMap.get("essential_hyp_treat") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_treat").toString())) {
                        String essentialHypTreat = anamnesisMap.get("essential_hyp_treat").toString();
                        if ("QK01".equals(essentialHypTreat)) {
                            resultMap.put("essentialHypTreat", "已治疗");
                        } else if ("QK02".equals(essentialHypTreat)) {
                            resultMap.put("essentialHypTreat", "未治疗");
                        } else if ("QK03".equals(essentialHypTreat)) {
                            resultMap.put("essentialHypTreat", "已用药");
                        } else if ("QK04".equals(essentialHypTreat)) {
                            resultMap.put("essentialHypTreat", "未用药");
                        } else if ("QK05".equals(essentialHypTreat)) {
                            resultMap.put("essentialHypTreat", "正常");
                        }
                    }
                    //essential_hyp_level   疾病类型
                    if (null != anamnesisMap.get("essential_hyp_level") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_level").toString())) {
                        String essentialHypLevel = anamnesisMap.get("essential_hyp_level").toString();
                        if ("FJ01".equals(essentialHypLevel)) {
                            resultMap.put("essentialHypLevel", "1级高血压（轻度）: 收缩压140-159mmHg和/或 舒张压90-99mmHg");
                        } else if ("FJ02".equals(essentialHypLevel)) {
                            resultMap.put("essentialHypLevel", "2级高血压（中度）: 收缩压160-179mmHg和/或 舒张压100-109mmHg");
                        } else if ("FJ03".equals(essentialHypLevel)) {
                            resultMap.put("essentialHypLevel", "3级高血压（重度）: 收缩压≥180 mmHg和/或 舒张压≥110mmHg");
                        } else if ("FJ04".equals(essentialHypLevel)) {
                            resultMap.put("essentialHypLevel", " 单纯收缩期高血压 : 收缩压≥140 mmHg和 舒张压<90mmHg");
                        } else if ("FJ05".equals(essentialHypLevel)) {
                            //essential_hyp_level_remark
                            if (null != anamnesisMap.get("essential_hyp_level_remark") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_level_remark").toString())) {
                                resultMap.put("essentialHypLevel",anamnesisMap.get("essential_hyp_level_remark").toString());
                            }else{
                                resultMap.put("essentialHypLevel","");
                            }
                        }
                    }
                    //essential_hyp_symptom   表现症状
                    if (null != anamnesisMap.get("essential_hyp_symptom") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_symptom").toString())) {
                        String essentialHypSymptom = anamnesisMap.get("essential_hyp_symptom").toString();
                        //essential_hyp_sym_remark
                        String essentialHypSymptomRemark = "";
                        if (essentialHypSymptom.contains("ZZ13")) {
                            if (null != anamnesisMap.get("essential_hyp_sym_remark") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_sym_remark").toString())) {
                                essentialHypSymptomRemark = anamnesisMap.get("essential_hyp_sym_remark").toString();
                            }
                        }
                        if ("ZZO1".equals(essentialHypSymptom)) {
                            resultMap.put("essentialHypSymptom", "无症状");
                        } else {
                            String replace = essentialHypSymptom.replace("ZZO2", " 注意力不集中").replace("ZZO3", " 头痛").replace("ZZO4", "肢体麻木")
                                    .replace("ZZO5", "头晕").replace("ZZO6", " 胸闷").replace("ZZO7", " 夜尿增多")
                                    .replace("ZZO8", " 记忆力减退").replace("ZZO9", " 心悸").replace("ZZ1O", " 乏力")
                                    .replace("ZZ11", " 呕吐").replace("ZZ12", " 眩晕").replace("ZZ13", essentialHypSymptomRemark);
                            resultMap.put("essentialHypSymptom", replace);
                        }
                        if ("空".equals(essentialHypSymptom)){
                            resultMap.put("essentialHypSymptom", " -- ");
                        }
                    }
                } else if ("2".equals(essentialHyp)) {
                    resultMap.put("essentialHyp", "无");
                }
                //essential_hyp_date   检查时间
                if (null != anamnesisMap.get("essential_hyp_date") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_date").toString())) {
                    resultMap.put("essentialHypDate", anamnesisMap.get("essential_hyp_date"));
                }
            }
        }


        return resultMap;
    }

    private static String reportTypeReplace(String sportType) {

        String replace = sportType.replace("YDFS05", "慢走").replace("YDFS18", "健美操").replace("YDFS06", "快走")
                .replace("YDFS24", "乒乓球").replace("YDFS25", "遛狗").replace("YDFS26", "下楼梯")
                .replace("YDFS19", "太极拳").replace("YDFS27", "钓鱼").replace("YDFS29", "气功")
                .replace("YDFS07", "慢跑").replace("YDFS15", "羽毛球").replace("YDFS09", "游泳(放松)")
                .replace("YDFS17", "篮球").replace("YDFS31", "足球").replace("YDFS10", "骑自行车")
                .replace("YDFS13", "瑜伽").replace("YDFS20", "体操").replace("YDFS33", "打高尔夫球")
                .replace("YDFS34", "游泳比赛").replace("YDFS35", "爬山").replace("YDFS36", "俯卧撑")
                .replace("YDFS16", "跳绳").replace("YDFS08", "跑步").replace("YDFS38", "爬楼梯")
                .replace("YDFS39", "仰卧起坐").replace("YDFS14", "舞蹈").replace("YDFS12", "有氧健身");
        return replace;
    }

    public static List<Object> dealBloodData(List<String> listDate, List<BloodSugarPO> sugarPo) {
        List<Object> result = new ArrayList<>();
        List<Map<String, Object>> listMap = new ArrayList<>();
        HashSet<Object> set = new HashSet<>();
        for (BloodSugarPO sugarPO : sugarPo) {
            set.add(sugarPO.getRecordDt());
        }
        for (String s : listDate) {
            Map<String, Object> map = new HashMap<>();
            for (BloodSugarPO po : sugarPo) {
                if (set.contains(s)) {
                    if (s.equals(po.getRecordDt())) {
                        if (!StringUtils.isBlank(po.getParamCode())) {
                            if (po.getParamCode().equals("beforeBreakfast")) {
                                map.put("beforeBreakfast", po.getParamValue());
                            }
                            if (po.getParamCode().equals("afterBreakfast")) {
                                map.put("afterBreakfast", po.getParamValue());
                            }
                            if (po.getParamCode().equals("beforeLunch")) {
                                map.put("beforeLunch", po.getParamValue());
                            }
                            if (po.getParamCode().equals("afterLunch")) {
                                map.put("afterLunch", po.getParamValue());
                            }
                            if (po.getParamCode().equals("beforeDinner")) {
                                map.put("beforeDinner", po.getParamValue());
                            }
                            if (po.getParamCode().equals("afterDinner")) {
                                map.put("afterDinner", po.getParamValue());
                            }
                        }
                    }
                } else {
                    map.put("beforeBreakfast", "--");
                    map.put("afterBreakfast", "--");
                    map.put("beforeLunch", "--");
                    map.put("afterLunch", "--");
                    map.put("beforeDinner", "--");
                    map.put("afterDinner", "--");
                }
            }
            if (map.get("beforeBreakfast") == null) {
                map.put("beforeBreakfast", "--");
            }
            if (map.get("afterBreakfast") == null) {
                map.put("afterBreakfast", "--");
            }
            if (map.get("beforeLunch") == null) {
                map.put("beforeLunch", "--");
            }
            if (map.get("afterLunch") == null) {
                map.put("afterLunch", "--");
            }
            if (map.get("beforeDinner") == null) {
                map.put("beforeDinner", "--");
            }
            if (map.get("afterDinner") == null) {
                map.put("afterDinner", "--");
            }

            listMap.add(map);
        }
        for (Map<String, Object> map : listMap) {
            result.add(map.get("beforeBreakfast"));
            result.add(map.get("afterBreakfast"));
            result.add(map.get("beforeLunch"));
            result.add(map.get("afterLunch"));
            result.add(map.get("beforeDinner"));
            result.add(map.get("afterDinner"));
        }

        return result;

    }

}
