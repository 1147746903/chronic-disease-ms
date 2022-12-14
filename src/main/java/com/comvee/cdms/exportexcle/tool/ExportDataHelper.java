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
            //??????
            if (null != memberMap.get("memberName") && !StringUtils.isBlank(memberMap.get("memberName").toString())) {
                resultMap.put("memberName", memberMap.get("memberName"));
            }
            //??????
            if (null != memberMap.get("sex") && !StringUtils.isBlank(memberMap.get("sex").toString())) {
                String sex = memberMap.get("sex").toString();
                if ("1".equals(sex)) {
                    resultMap.put("sex", "???");
                } else if ("2".equals(sex)) {
                    resultMap.put("sex", "???");
                }
            }
            //??????
            if (null != memberMap.get("birthday") && !StringUtils.isBlank(memberMap.get("birthday").toString())) {
                String birthday = memberMap.get("birthday").toString();
                int age = DateHelper.getAge(birthday);
                resultMap.put("age", age);
            }
            //??????
            if (null != memberMap.get("height") && !StringUtils.isBlank(memberMap.get("height").toString())) {
                resultMap.put("height", memberMap.get("height") + "cm");
            }
            //??????
            if (null != memberMap.get("weight") && !StringUtils.isBlank(memberMap.get("weight").toString())) {
                resultMap.put("weight", memberMap.get("weight") + "kg");
            }
            //BMI
            if (null != memberMap.get("bmi") && !StringUtils.isBlank(memberMap.get("bmi").toString())) {
                resultMap.put("bmi", memberMap.get("bmi"));
            }
            //????????? mobilePhone
            if (null != memberMap.get("mobilePhone") && !StringUtils.isBlank(memberMap.get("mobilePhone").toString())) {
                resultMap.put("mobilePhone", memberMap.get("mobilePhone"));
            }
            //???????????? idCard
            if (null != memberMap.get("idCard") && !StringUtils.isBlank(memberMap.get("idCard").toString())) {
                resultMap.put("idCard", memberMap.get("idCard"));
            }
        }

        if (signMap != null && signMap.size() > 0) {
            //??????
            if (null != signMap.get("waistline") && !StringUtils.isBlank(signMap.get("waistline").toString())) {
                resultMap.put("waistline", signMap.get("waistline") + "cm");
            }
            //??????  hipline
            if (null != signMap.get("hipline") && !StringUtils.isBlank(signMap.get("hipline").toString())) {
                resultMap.put("hipline", signMap.get("hipline") + "cm");
            }
            //????????? whr
            if (null != signMap.get("whr") && !StringUtils.isBlank(signMap.get("whr").toString())) {
                resultMap.put("whr", signMap.get("whr"));
            }
            //?????????   sbp
            if (null != signMap.get("sbp") && !StringUtils.isBlank(signMap.get("sbp").toString())) {
                resultMap.put("sbp", signMap.get("sbp") + "mmHg");
            }
            //????????? dbp
            if (null != signMap.get("dbp") && !StringUtils.isBlank(signMap.get("dbp").toString())) {
                resultMap.put("dbp", signMap.get("dbp") + "mmHg");
            }
        }


        if (basicMap != null && basicMap.size() > 0) {
            //???????????? diabetes_date
            if (null != basicMap.get("diabetes_date") && !StringUtils.isBlank(basicMap.get("diabetes_date").toString())) {
                resultMap.put("diabetesDate", basicMap.get("diabetes_date"));
            }

            //??????????????? diabetes_type
            if (null != basicMap.get("diabetes_type") && !StringUtils.isBlank(basicMap.get("diabetes_type").toString())) {
                String diabetesType = basicMap.get("diabetes_type").toString();
                if ("SUGAR_TYPE_001".equals(diabetesType)) {
                    resultMap.put("diabetesType", "1???");
                } else if ("SUGAR_TYPE_002".equals(diabetesType)) {
                    resultMap.put("diabetesType", "2???");
                } else if ("SUGAR_TYPE_003".equals(diabetesType)) {
                    resultMap.put("diabetesType", "??????");
                } else if ("SUGAR_TYPE_004".equals(diabetesType)) {
                    resultMap.put("diabetesType", "??????");
                }
            }
            //????????????  profession
            if (null != basicMap.get("profession") && !StringUtils.isBlank(basicMap.get("profession").toString())) {
                String profession = basicMap.get("profession").toString();
                if ("ZY01".equals(profession)) {
                    resultMap.put("profession", "??????????????????????????????????????????...");
                } else if ("ZY02".equals(profession)) {
                    resultMap.put("profession", "??????????????????????????????????????????...");
                } else if ("ZY03".equals(profession)) {
                    resultMap.put("profession", "???????????????????????????????????????...");
                }
            }
            //???????????????????????? jbxx_mujcxtpl_week
            if (null != basicMap.get("jbxx_mujcxtpl_week") && !StringUtils.isBlank(basicMap.get("jbxx_mujcxtpl_week").toString())) {
                String mujcxtplWeek = basicMap.get("jbxx_mujcxtpl_week").toString();
                if ("JCXTPV01".equals(mujcxtplWeek)) {
                    resultMap.put("jbxxMujcxtplWeek", "<1???/???");
                } else if ("JCXTPV02".equals(mujcxtplWeek)) {
                    resultMap.put("jbxxMujcxtplWeek", "1???/???");
                } else if ("JCXTPV03".equals(mujcxtplWeek)) {
                    resultMap.put("jbxxMujcxtplWeek", ">1-3???/???");
                } else if ("JCXTPV04".equals(mujcxtplWeek)) {
                    resultMap.put("jbxxMujcxtplWeek", "3-5???/???");
                } else if ("JCXTPV05".equals(mujcxtplWeek)) {
                    resultMap.put("jbxxMujcxtplWeek", ">5???/???");
                } else if ("JCXTPV06".equals(mujcxtplWeek)) {
                    if (null != basicMap.get("jbxx_mujcxtpl") && !StringUtils.isBlank(basicMap.get("jbxx_mujcxtpl").toString())) {
                        resultMap.put("jbxxMujcxtplWeek", basicMap.get("jbxx_mujcxtpl").toString());
                    }
                }
            }
        }


        if (treatmentMap != null && treatmentMap.size() > 0) {
            //???????????? mq_fbg
            if (null != treatmentMap.get("mq_fbg") && !StringUtils.isBlank(treatmentMap.get("mq_fbg").toString())) {
                resultMap.put("mqFbg", treatmentMap.get("mq_fbg") + "mmol/l");
            }
            //??????2????????????  blg
            if (null != treatmentMap.get("blg") && !StringUtils.isBlank(treatmentMap.get("blg").toString())) {
                resultMap.put("blg", treatmentMap.get("blg") + "mmol/l");
            }
            //???????????? bsg
            if (null != treatmentMap.get("bsg") && !StringUtils.isBlank(treatmentMap.get("bsg").toString())) {
                resultMap.put("bsg", treatmentMap.get("bsg") + "mmol/l");
            }
        }


        if (labMap != null && labMap.size() > 0) {
            //??????????????????  lab_hba
            if (null != labMap.get("lab_hba") && !StringUtils.isBlank(labMap.get("lab_hba").toString())) {
                resultMap.put("labHba", labMap.get("lab_hba") + "%");
            }
            //??????????????????   ldl
            if (null != labMap.get("ldl") && !StringUtils.isBlank(labMap.get("ldl").toString())) {
                resultMap.put("ldl", labMap.get("ldl") + "mmol/l");
            }
            //hdl        ??????????????????
            if (null != labMap.get("hdl") && !StringUtils.isBlank(labMap.get("hdl").toString())) {
                resultMap.put("hdl", labMap.get("hdl") + "mmol/l");
            }
            //tg         ????????????
            if (null != labMap.get("tg") && !StringUtils.isBlank(labMap.get("tg").toString())) {
                resultMap.put("tg", labMap.get("tg") + "mmol/l");
            }
            //tc         ????????????
            if (null != labMap.get("tc") && !StringUtils.isBlank(labMap.get("tc").toString())) {
                resultMap.put("tc", labMap.get("tc") + "mmol/l");
            }
            //cr         ?????????
            if (null != labMap.get("cr") && !StringUtils.isBlank(labMap.get("cr").toString())) {
                resultMap.put("cr", labMap.get("cr") + "umol/l");
            }
            //lab_xns     ?????????
            if (null != labMap.get("lab_xns") && !StringUtils.isBlank(labMap.get("lab_xns").toString())) {
                resultMap.put("xns", labMap.get("lab_xns") + "umol/l");
            }
            //alt      ???????????????
            if (null != labMap.get("alt") && !StringUtils.isBlank(labMap.get("alt").toString())) {
                resultMap.put("alt", labMap.get("alt") + "U/L");
            }
        }


        if (historyMap != null && historyMap.size() > 0) {
            //??????????????????????????????  diabetes_edu
            if (null != historyMap.get("diabetes_edu") && !StringUtils.isBlank(historyMap.get("diabetes_edu").toString())) {
                String diabetesEdu = historyMap.get("diabetes_edu").toString();
                if ("TNBJY02".equals(diabetesEdu)) {
                    resultMap.put("diabetesEdu", "??????");
                } else{
                    String replace = diabetesEdu.replace("TNBJY04", "?????????????????????")
                            .replace("TNBJY05", "??????")
                            .replace("TNBJY06", "??????")
                            .replace("TNBJY07", "??????")
                            .replace("TNBJY08", "????????????")
                            .replace("TNBJY09", "??????????????????");
                    resultMap.put("diabetesEdu", replace);
                }
                if ("???".equals(diabetesEdu)){
                    resultMap.put("diabetesEdu"," -- ");
                }
            }
            //??????????????????  bs_sport_type
            if (null != historyMap.get("bs_sport_type") && !StringUtils.isBlank(historyMap.get("bs_sport_type").toString())) {
                String sportType = historyMap.get("bs_sport_type").toString();
                String replace = reportTypeReplace(sportType);
                resultMap.put("sportType", replace);
                if ("???".equals(sportType)){
                    resultMap.put("sportType", " -- ");
                }
            }
            double energy = 0;
            //bs_dinner_jc   ????????????
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
            //bs_jcnr  ???????????????
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
            //bs_lunch_jc ?????????
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
                resultMap.put("energy", new DecimalFormat("0.00").format(energy) + "??????");
            }
        }

        if (hypoglycemiaMap != null && hypoglycemiaMap.size() > 0) {
            //???????????????????????? hyp
            if (null != hypoglycemiaMap.get("hyp") && !StringUtils.isBlank(hypoglycemiaMap.get("hyp").toString())) {
                String hypStr = hypoglycemiaMap.get("hyp").toString();
                if ("1".equals(hypStr)) {
                    resultMap.put("hyp", "???");
                    //???????????????????????????????????? hyp_frequency
                    if (null != hypoglycemiaMap.get("hyp_frequency") && !StringUtils.isBlank(hypoglycemiaMap.get("hyp_frequency").toString())) {
                        if ("0".equals(hypoglycemiaMap.get("hyp_frequency").toString())){
                            resultMap.put("hypFrequency", " -- ");
                        }else {
                            resultMap.put("hypFrequency", hypoglycemiaMap.get("hyp_frequency") + "???");
                        }
                    }
                } else if ("2".equals(hypStr)) {
                    resultMap.put("hyp", "???");
                }
            }

        }


        if (complicationMap != null && complicationMap.size() > 0) {
            //neph_pro_value     ?????????
            if (null != complicationMap.get("neph_pro_value") && !StringUtils.isBlank(complicationMap.get("neph_pro_value").toString())) {
                resultMap.put("nephPro", complicationMap.get("neph_pro_value"));
            }
            //retinal    ??????????????????????????????  SWM01
            if (null != complicationMap.get("retinal") && !StringUtils.isBlank(complicationMap.get("retinal").toString())) {
                String retinal = complicationMap.get("retinal").toString();
                if ("SWM01".equals(retinal)) {
                    resultMap.put("retinal", "?????????");
                    //retinal_type_treat    ????????????  QK01
                    if (null != complicationMap.get("retinal_type_treat") && !StringUtils.isBlank(complicationMap.get("retinal_type_treat").toString())) {
                        String typeTreat = complicationMap.get("retinal_type_treat").toString();
                        if ("QK01".equals(typeTreat)) {
                            resultMap.put("retinalTreat", "?????????");
                        } else if ("QK02".equals(typeTreat)) {
                            resultMap.put("retinalTreat", "?????????");
                        } else if ("QK03".equals(typeTreat)) {
                            resultMap.put("retinalTreat", "?????????");
                        } else if ("QK04".equals(typeTreat)) {
                            resultMap.put("retinalTreat", "?????????");
                        } else if ("QK05".equals(typeTreat)) {
                            resultMap.put("retinalTreat", "??????");
                        }
                        //retinal_type_cms    ????????????
                        if (null != complicationMap.get("retinal_type_cms") && !StringUtils.isBlank(complicationMap.get("retinal_type_cms").toString())) {
                            String retinalCms = complicationMap.get("retinal_type_cms").toString();
                            if ("LX01".equals(retinalCms)) {
                                resultMap.put("retinalCms", "????????????????????????");
                            } else if ("LX02".equals(retinalCms)) {
                                resultMap.put("retinalCms", "????????????????????????");
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
                    resultMap.put("retinal", "?????????");
                } else if ("SWM03".equals(retinal)) {
                    resultMap.put("retinal", "?????????");
                } else if ("SWM04".equals(retinal)) {
                    resultMap.put("retinal", "??????");
                }
                if ("SWM01".equals(retinal) || "SWM02".equals(retinal) || "SWM04".equals(retinal)) {
                    //retinal_date    ????????????
                    if (null != complicationMap.get("retinal_date") && !StringUtils.isBlank(complicationMap.get("retinal_date").toString())) {
                        resultMap.put("retinalDate", complicationMap.get("retinal_date"));
                    }
                }
                if ("SWM01".equals(retinal) || "SWM03".equals(retinal)) {
                    //                        ret_symptom_cms   ????????????  ZZ01
                    if (null != complicationMap.get("ret_symptom_cms") && !StringUtils.isBlank(complicationMap.get("ret_symptom_cms").toString())) {
                        String symptomCms = complicationMap.get("ret_symptom_cms").toString();
                        String symptomCmsRemark = "";
                        if (symptomCms.contains("ZZ09")) {
                            if (null != complicationMap.get("ret_symptom_cms_remark") && !StringUtils.isBlank(complicationMap.get("ret_symptom_cms_remark").toString())) {
                                symptomCmsRemark = complicationMap.get("ret_symptom_cms_remark").toString();
                            }
                        }
                        if ("ZZ01".equals(symptomCms)) {
                            resultMap.put("symptomCms", "?????????");
                        } else {
                            String replace = symptomCms.replace("ZZ02", "????????????").replace("ZZ03", "???????????????????????????").replace("ZZ04", "??????????????????")
                                    .replace("ZZ05", "??????").replace("ZZ06", "????????????").replace("ZZ07", "????????????????????????")
                                    .replace("ZZ08", "??????????????????").replace("ZZ09", symptomCmsRemark);
                            resultMap.put("symptomCms", replace);
                        }
                        if ("???".equals(symptomCms)){
                            resultMap.put("symptomCms", " -- ");
                        }
                    }
                }
            }

            //nephropathy   ????????????????????????  SB01
            if (null != complicationMap.get("nephropathy") && !StringUtils.isBlank(complicationMap.get("nephropathy").toString())) {
                String nephropathy = complicationMap.get("nephropathy").toString();
                if ("SB01".equals(nephropathy)) {
                    resultMap.put("nephropathy", "?????????");
                    //neph_treat   ????????????
                    if (null != complicationMap.get("neph_treat") && !StringUtils.isBlank(complicationMap.get("neph_treat").toString())) {
                        String nephTreat = complicationMap.get("neph_treat").toString();
                        if ("QK01".equals(nephTreat)) {
                            resultMap.put("nephTreat", "?????????");
                        } else if ("QK02".equals(nephTreat)) {
                            resultMap.put("nephTreat", "?????????");
                        } else if ("QK03".equals(nephTreat)) {
                            resultMap.put("nephTreat", "?????????");
                        } else if ("QK04".equals(nephTreat)) {
                            resultMap.put("nephTreat", "?????????");
                        } else if ("QK05".equals(nephTreat)) {
                            resultMap.put("nephTreat", "??????");
                        }
                    }
                    //neph_type_cms   ????????????  LX01
                    if (null != complicationMap.get("neph_type_cms") && !StringUtils.isBlank(complicationMap.get("neph_type_cms").toString())) {
                        String nephCms = complicationMap.get("neph_type_cms").toString();
                        if (null != complicationMap.get("retinal_type_cms") && !StringUtils.isBlank(complicationMap.get("retinal_type_cms").toString())) {
                            if ("LX01".equals(nephCms)) {
                                resultMap.put("nephCms", "?????????????????????????????????");
                            } else if ("LX02".equals(nephCms)) {
                                resultMap.put("nephCms", "??????????????????????????????");
                            } else if ("LX03".equals(nephCms)) {
                                resultMap.put("nephCms", "????????????????????????");
                            } else if ("LX04".equals(nephCms)) {
                                resultMap.put("nephCms", "????????????????????????");
                            } else if ("LX05".equals(nephCms)) {
                                resultMap.put("nephCms", "????????????");
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
                    resultMap.put("nephropathy", "?????????");
                } else if ("SB03".equals(nephropathy)) {
                    resultMap.put("nephropathy", "?????????");
                } else if ("SB04".equals(nephropathy)) {
                    resultMap.put("nephropathy", "??????");
                }
                if ("SB01".equals(nephropathy) || "SB02".equals(nephropathy) || "SB04".equals(nephropathy)) {
                    //neph_date   ???????????? ????????????
                    if (null != complicationMap.get("neph_date") && !StringUtils.isBlank(complicationMap.get("neph_date").toString())) {
                        resultMap.put("nephDate", complicationMap.get("neph_date"));
                    }
                }
                if ("SB01".equals(nephropathy) || "SB03".equals(nephropathy)) {
                    //neph_symptom_cms   ????????????
                    if (null != complicationMap.get("neph_symptom_cms") && !StringUtils.isBlank(complicationMap.get("neph_symptom_cms").toString())) {
                        String nephSymptom = complicationMap.get("neph_symptom_cms").toString();
                        String nephSymptomRemark = "";
                        if (nephSymptom.contains("ZZ07")) {
                            if (null != complicationMap.get("neph_symptom_cms_remark") && !StringUtils.isBlank(complicationMap.get("neph_symptom_cms_remark").toString())) {
                                nephSymptomRemark = complicationMap.get("neph_symptom_cms_remark").toString();
                            }
                        }
                        if ("ZZ01".equals(nephSymptom)) {
                            resultMap.put("nephSymptom", "?????????");
                        } else {
                            String replace = nephSymptom.replace("ZZ02", "????????????").replace("ZZ03", "????????????????????????????????????????????????????????????????????????????????????").replace("ZZ04", "??????")
                                    .replace("ZZ05", "???????????????").replace("ZZ06", "?????????????????????????????????").replace("ZZ07", nephSymptomRemark);
                            resultMap.put("nephSymptom", replace);
                        }
                        if ("???".equals(nephSymptom)){
                            resultMap.put("nephSymptom"," -- ");
                        }
                    }
                }

            }
            //neuropathy   ????????????????????????????????????
            if (null != complicationMap.get("neuropathy") && !StringUtils.isBlank(complicationMap.get("neuropathy").toString())) {
                String neuropathy = complicationMap.get("neuropathy").toString();
                if ("ZWSJ01".equals(neuropathy)) {
                    resultMap.put("neuropathy", "?????????");
                } else if ("ZWSJ02".equals(neuropathy)) {
                    resultMap.put("neuropathy", "?????????");
                } else if ("ZWSJ03".equals(neuropathy)) {
                    resultMap.put("neuropathy", "?????????");

                } else if ("ZWSJ04".equals(neuropathy)) {
                    resultMap.put("neuropathy", "??????");
                }
                if ("ZWSJ01".equals(neuropathy) || "ZWSJ03".equals(neuropathy)) {
                    //neu_symptom_cms   ????????????
                    if (null != complicationMap.get("neu_symptom_cms") && !StringUtils.isBlank(complicationMap.get("neu_symptom_cms").toString())) {
                        String neuSymptom = complicationMap.get("neu_symptom_cms").toString();
                        String neuSymptomRemark = "";
                        if (neuSymptom.contains("ZZ12")) {
                            if (null != complicationMap.get("neu_symptom_cms_remark") && !StringUtils.isBlank(complicationMap.get("neu_symptom_cms_remark").toString())) {
                                neuSymptomRemark = complicationMap.get("neu_symptom_cms_remark").toString();
                            }
                        }

                        if ("ZZ01".equals(neuSymptom)) {
                            resultMap.put("neuSymptom", "?????????");
                        } else {
                            String replace = neuSymptom.replace("ZZ02", " ???????????????").replace("ZZ03", "?????????").replace("ZZ04", "????????????????????????")
                                    .replace("ZZ05", " ??????").replace("ZZ06", "????????????").replace("ZZ07", "??????")
                                    .replace("ZZ08", "??????(??????????????????????????????????????????????????????)").replace("ZZ11", "???????????????").replace("ZZ12", neuSymptomRemark);
                            resultMap.put("neuSymptom", replace);
                        }
                        if ("???".equals(neuSymptom)){
                            resultMap.put("neuSymptom", " -- ");
                        }
                    }
                }
            }

            //diabetic_foot     ?????????????????????
            if (null != complicationMap.get("diabetic_foot") && !StringUtils.isBlank(complicationMap.get("diabetic_foot").toString())) {
                String diabeticFoot = complicationMap.get("diabetic_foot").toString();
                if ("TNBZ01".equals(diabeticFoot)) {
                    resultMap.put("diabeticFoot", "?????????");
                    //df_level   ????????????  CD01
                    if (null != complicationMap.get("df_level") && !StringUtils.isBlank(complicationMap.get("df_level").toString())) {
                        String dfLevel = complicationMap.get("df_level").toString();
                        if ("CD01".equals(dfLevel)) {
                            resultMap.put("dfLevel", "0???????????????????????????????????????");
                        } else if ("CD02".equals(dfLevel)) {
                            resultMap.put("dfLevel", "1???????????????????????????????????????");
                        } else if ("CD03".equals(dfLevel)) {
                            resultMap.put("dfLevel", "2???????????????????????????????????????????????????");
                        } else if ("CD04".equals(dfLevel)) {
                            resultMap.put("dfLevel", "3???????????????????????????????????????????????????)");
                        } else if ("CD05".equals(dfLevel)) {
                            resultMap.put("dfLevel", " 4????????????????????????????????????????????????");
                        } else if ("CD06".equals(dfLevel)) {
                            resultMap.put("dfLevel", "5??????????????????)");
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
                    resultMap.put("diabeticFoot", "?????????");
                }
                if ("TNBZ03".equals(diabeticFoot)) {
                    resultMap.put("diabeticFoot", "?????????");
                }
                if ("TNBZ04".equals(diabeticFoot)) {
                    resultMap.put("diabeticFoot", "??????");
                }
            }

            //dan      ????????????????????????????????????
            if (null != complicationMap.get("dan") && !StringUtils.isBlank(complicationMap.get("dan").toString())) {
                String dan = complicationMap.get("dan").toString();
                if ("ZZ01".equals(dan)) {
                    resultMap.put("dan", "?????????");
                    //dan_type   ????????????
                    if (null != complicationMap.get("dan_type") && !StringUtils.isBlank(complicationMap.get("dan_type").toString())) {
                        String danType = complicationMap.get("dan_type").toString();
                        //dan_type_remark
                        String danTypeRemark = "";
                        if (danType.contains("LX07")) {
                            if (null != complicationMap.get("dan_type_remark") && !StringUtils.isBlank(complicationMap.get("dan_type_remark").toString())) {
                                danTypeRemark = complicationMap.get("dan_type_remark").toString();
                            }
                        }
                        String replace = danType.replace("LX01", "???????????????").replace("LX02", "?????????(??????????????????????????????)").replace("LX03", "??????????????????(????????????????????????????????????)")
                                .replace("LX04", "????????????").replace("LX05", "???????????????????????????").replace("LX06", "????????????????????????(???????????????)")
                                .replace("LX07", danTypeRemark);
                        resultMap.put("danType", replace);
                    }
                } else if ("ZZ02".equals(dan)) {
                    resultMap.put("dan", "?????????");
                } else if ("ZZ03".equals(dan)) {
                    resultMap.put("dan", "?????????");
                } else if ("ZZ04".equals(dan)) {
                    resultMap.put("dan", "??????");
                }
                if ("ZZ01".equals(dan) || "ZZ01".equals(dan)) {
                    //dan_symptom   ????????????
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
                            resultMap.put("danSymptom", "?????????");
                        } else {
                            String replace = danSymptom.replace("ZZ02", " ?????????").replace("ZZ03", " ????????????????????????").replace("ZZ04", "????????????")
                                    .replace("ZZ05", "?????????????????????").replace("ZZ06", danSymptomRemark);
                            resultMap.put("danSymptom", replace);
                        }
                        if ("???".equals(danSymptom)){
                            resultMap.put("danSymptom", " -- ");
                        }
                    }
                }
            }

            //chd     ??????????????????
            if (null != complicationMap.get("chd") && !StringUtils.isBlank(complicationMap.get("chd").toString())) {
                String chd = complicationMap.get("chd").toString();
                if ("QZ01".equals(chd)) {
                    resultMap.put("chd", "?????????");
                    //cbd_treat   ????????????
                    if (null != complicationMap.get("cbd_treat") && !StringUtils.isBlank(complicationMap.get("cbd_treat").toString())) {
                        String cbdTreat = complicationMap.get("cbd_treat").toString();
                        if ("ZL01".equals(cbdTreat)) {
                            resultMap.put("cbdTreat", "?????????");
                        } else if ("ZL02".equals(cbdTreat)) {
                            resultMap.put("cbdTreat", "?????????");
                        } else if ("ZL03".equals(cbdTreat)) {
                            resultMap.put("cbdTreat", "?????????");
                        } else if ("ZL04".equals(cbdTreat)) {
                            resultMap.put("cbdTreat", "?????????");
                        } else if ("ZL05".equals(cbdTreat)) {
                            resultMap.put("cbdTreat", "??????");
                        }
                    }
                    //chd_type     ????????????
                    if (null != complicationMap.get("chd_type") && !StringUtils.isBlank(complicationMap.get("chd_type").toString())) {
                        String chdType = complicationMap.get("chd_type").toString();
                        if ("LX01".equals(chdType)) {
                            resultMap.put("chdType", "?????????????????????????????????????????????");
                        } else if ("LX02".equals(chdType)) {
                            resultMap.put("chdType", "?????????");
                        } else if ("LX03".equals(chdType)) {
                            resultMap.put("chdType", "????????????");
                        } else if ("LX04".equals(chdType)) {
                            resultMap.put("chdType", " ?????????????????????????????????????????????");
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
                    resultMap.put("chd", "?????????");
                } else if ("QZ03".equals(chd)) {
                    resultMap.put("chd", "?????????");
                } else if ("QZ04".equals(chd)) {
                    resultMap.put("chd", "??????");
                }
                if ("QZ01".equals(chd) || "QZ02".equals(chd) || "QZ04".equals(chd)) {
                    //chd_date    ????????????
                    if (null != complicationMap.get("chd_date") && !StringUtils.isBlank(complicationMap.get("chd_date").toString())) {
                        resultMap.put("chdDate", complicationMap.get("chd_date"));
                    }
                }
                if ("QZ01".equals(chd) || "QZ03".equals(chd)) {
                    //chd_symptom   ????????????
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
                            resultMap.put("chdSymptom", "?????????");
                        } else {
                            String replace = chdSymptom.replace("ZZ02", " ????????????").replace("ZZ03", " ???????????????").replace("ZZ04", "??????")
                                    .replace("ZZ05", "??????").replace("ZZ06", " ??????").replace("ZZ07", " ??????")
                                    .replace("ZZ08", " ??????").replace("ZZ09", " ????????????").replace("ZZ10", " ??????")
                                    .replace("ZZ11", " ??????").replace("ZZ12", " ??????").replace("ZZ13", chdSymptomRemark);
                            resultMap.put("chdSymptom", replace);
                        }
                        if ("???".equals(chdSymptom)){
                            resultMap.put("chdSymptom", " -- ");
                        }
                    }
                }
            }
            //pad    ???????????????????????????
            if (null != complicationMap.get("pad") && !StringUtils.isBlank(complicationMap.get("pad").toString())) {
                String pad = complicationMap.get("pad").toString();
                if ("XZXG01".equals(pad)) {
                    resultMap.put("pad", "?????????");
                } else if ("XZXG02".equals(pad)) {
                    resultMap.put("pad", "?????????");
                } else if ("XZXG03".equals(pad)) {
                    resultMap.put("pad", "?????????");
                } else if ("XZXG04".equals(pad)) {
                    resultMap.put("pad", "??????");
                }
                if ("XZXG01".equals(pad) || "XZXG03".equals(pad)) {
                    //pad_symptom_cms  ????????????
                    if (null != complicationMap.get("pad_symptom_cms") && !StringUtils.isBlank(complicationMap.get("pad_symptom_cms").toString())) {
                        String padSymptom = complicationMap.get("pad_symptom_cms").toString();
                        //pad_symptom_remark_cms
                        String padSymptomRemark = "";
                        if (padSymptom.contains("ZZ07")) {
                            if (null != complicationMap.get("pad_symptom_remark_cms") && !StringUtils.isBlank(complicationMap.get("pad_symptom_remark_cms").toString())) {
                                padSymptomRemark = complicationMap.get("pad_symptom_remark_cms").toString();
                            }
                        }
                        String replace = padSymptom.replace("ZZ01", " ????????????").replace("ZZ02", " ????????????").replace("ZZ03", " ?????????????????????????????????")
                                .replace("ZZ04", "????????????").replace("ZZ05", "????????????").replace("ZZ06", " ??????????????????????????????")
                                .replace("ZZ07", padSymptomRemark);
                        resultMap.put("padSymptom", replace);
                        if ("???".equals(padSymptom)){
                            resultMap.put("padSymptom", " -- ");
                        }

                    }
                }
            }
            //???????????????????????????
            if (null != complicationMap.get("has_xnxg") && !StringUtils.isBlank(complicationMap.get("has_xnxg").toString())){
                String hasXnxg = complicationMap.get("has_xnxg").toString();
                if ("1".equals(hasXnxg)){
                    resultMap.put("hasXnxg", "?????????");
                    //????????????
                    if (null != complicationMap.get("xnxg_status") && !StringUtils.isBlank(complicationMap.get("xnxg_status").toString())){
                        String xnxgStatus = complicationMap.get("xnxg_status").toString();
                        if ("QK01".equals(xnxgStatus)){
                            resultMap.put("xnxgStatus", "?????????");
                        }else if ("QK02".equals(xnxgStatus)){
                            resultMap.put("xnxgStatus", "?????????");
                        }else if ("QK03".equals(xnxgStatus)){
                            resultMap.put("xnxgStatus", "?????????");
                        }else if ("QK04".equals(xnxgStatus)){
                            resultMap.put("xnxgStatus", "?????????");
                        }else if ("QK05".equals(xnxgStatus)){
                            resultMap.put("xnxgStatus", "??????");
                        }

                    }
                }else if ("2".equals(hasXnxg)){
                    resultMap.put("hasXnxg", "?????????");
                }else if ("3".equals(hasXnxg)){
                    resultMap.put("hasXnxg", "?????????");
                }
                //????????????
                if (null != complicationMap.get("xnxg_dt") && !StringUtils.isBlank(complicationMap.get("xnxg_dt").toString())){
                    String xnxgDt = complicationMap.get("xnxg_dt").toString();
                    resultMap.put("xnxgDt", xnxgDt);
                }
                //?????????????????????
                if (null != complicationMap.get("xnxg_show") && !StringUtils.isBlank(complicationMap.get("xnxg_show").toString())){
                    String xnxgShow = complicationMap.get("xnxg_show").toString();
                    xnxgShow = xnxgShow.replace("ZZO1","?????????????????????????????????").replace("ZZO2","?????????")
                            .replace("ZZO3","????????????").replace("ZZO4","?????????");
                    resultMap.put("xnxg_show", xnxgShow);
                }



            }
            //jyntnbjxbfz     ??????????????????????????????
            if (null != complicationMap.get("complications_diabetes_status_02") || null != complicationMap.get("complications_diabetes_status_04") || null != complicationMap.get("complications_diabetes_status_05") || null != complicationMap.get("complications_diabetes_status_06")){
                if (null != complicationMap.get("jyntnbjxbfz") && !StringUtils.isBlank(complicationMap.get("jyntnbjxbfz").toString())) {
                    String jyntnbjxbfz = complicationMap.get("jyntnbjxbfz").toString();
                    if ("LX01".equals(jyntnbjxbfz)) {
                        resultMap.put("jyntnbjxbfz", "??????");
                    } else {
                        String two="";
                        String four="";
                        String five="";
                        String six="";
                       if (null !=complicationMap.get("complications_diabetes_status_02") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_02").toString())){
                            two = complicationMap.get("complications_diabetes_status_02").toString()+"???";
                        }
                        if (null !=complicationMap.get("complications_diabetes_status_04") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_04").toString())){
                            four = complicationMap.get("complications_diabetes_status_04").toString()+"???";
                        }
                        if (null !=complicationMap.get("complications_diabetes_status_05") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_05").toString())){
                            five = complicationMap.get("complications_diabetes_status_05").toString()+"???";
                        }
                        if (null !=complicationMap.get("complications_diabetes_status_06") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_06").toString())){
                            six = complicationMap.get("complications_diabetes_status_06").toString()+"???";
                        }
                        String replace = jyntnbjxbfz.replace("LX02", "???????????????"+two).replace("LX04", "???????????????"+four)
                                .replace("LX05", "???????????????"+five).replace("LX06", "?????????DM?????????????????????"+six);
                        resultMap.put("jyntnbjxbfz", replace);
                    }
                    if ("???".equals(jyntnbjxbfz)){
                        resultMap.put("jyntnbjxbfz", " -- ");
                    }
                }
            }else {
                //jyntnbjxbfz     ??????????????????????????????
                if (null != complicationMap.get("jyntnbjxbfz") && !StringUtils.isBlank(complicationMap.get("jyntnbjxbfz").toString())) {
                    String jyntnbjxbfz = complicationMap.get("jyntnbjxbfz").toString();
                    if ("LX01".equals(jyntnbjxbfz)) {
                        resultMap.put("jyntnbjxbfz", "??????");
                    } else {
                        String replace = jyntnbjxbfz.replace("LX02", "  ???????????????").replace("LX03", " ?????????")
                                .replace("LX04", "???????????????").replace("LX05", "???????????????");
                        resultMap.put("jyntnbjxbfz", replace);
                    }
                    if ("???".equals(jyntnbjxbfz)) {
                        resultMap.put("jyntnbjxbfz", " -- ");
                    }
                }
            }

        }

        if (anamnesisMap != null && anamnesisMap.size() > 0) {
            //essential_hyp     ???????????????????????????
            if (null != anamnesisMap.get("essential_hyp") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp").toString())) {
                String essentialHyp = anamnesisMap.get("essential_hyp").toString();
                if ("1".equals(essentialHyp)) {
                    resultMap.put("essentialHyp", "???");
                    //essential_hyp_treat    ????????????
                    if (null != anamnesisMap.get("essential_hyp_treat") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_treat").toString())) {
                        String essentialHypTreat = anamnesisMap.get("essential_hyp_treat").toString();
                        if ("QK01".equals(essentialHypTreat)) {
                            resultMap.put("essentialHypTreat", "?????????");
                        } else if ("QK02".equals(essentialHypTreat)) {
                            resultMap.put("essentialHypTreat", "?????????");
                        } else if ("QK03".equals(essentialHypTreat)) {
                            resultMap.put("essentialHypTreat", "?????????");
                        } else if ("QK04".equals(essentialHypTreat)) {
                            resultMap.put("essentialHypTreat", "?????????");
                        } else if ("QK05".equals(essentialHypTreat)) {
                            resultMap.put("essentialHypTreat", "??????");
                        }
                    }
                    //essential_hyp_level   ????????????
                    if (null != anamnesisMap.get("essential_hyp_level") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_level").toString())) {
                        String essentialHypLevel = anamnesisMap.get("essential_hyp_level").toString();
                        if ("FJ01".equals(essentialHypLevel)) {
                            resultMap.put("essentialHypLevel", "1????????????????????????: ?????????140-159mmHg???/??? ?????????90-99mmHg");
                        } else if ("FJ02".equals(essentialHypLevel)) {
                            resultMap.put("essentialHypLevel", "2????????????????????????: ?????????160-179mmHg???/??? ?????????100-109mmHg");
                        } else if ("FJ03".equals(essentialHypLevel)) {
                            resultMap.put("essentialHypLevel", "3????????????????????????: ????????????180 mmHg???/??? ????????????110mmHg");
                        } else if ("FJ04".equals(essentialHypLevel)) {
                            resultMap.put("essentialHypLevel", " ???????????????????????? : ????????????140 mmHg??? ?????????<90mmHg");
                        } else if ("FJ05".equals(essentialHypLevel)) {
                            //essential_hyp_level_remark
                            if (null != anamnesisMap.get("essential_hyp_level_remark") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_level_remark").toString())) {
                                resultMap.put("essentialHypLevel",anamnesisMap.get("essential_hyp_level_remark").toString());
                            }else{
                                resultMap.put("essentialHypLevel","");
                            }
                        }
                    }
                    //essential_hyp_symptom   ????????????
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
                            resultMap.put("essentialHypSymptom", "?????????");
                        } else {
                            String replace = essentialHypSymptom.replace("ZZO2", " ??????????????????").replace("ZZO3", " ??????").replace("ZZO4", "????????????")
                                    .replace("ZZO5", "??????").replace("ZZO6", " ??????").replace("ZZO7", " ????????????")
                                    .replace("ZZO8", " ???????????????").replace("ZZO9", " ??????").replace("ZZ1O", " ??????")
                                    .replace("ZZ11", " ??????").replace("ZZ12", " ??????").replace("ZZ13", essentialHypSymptomRemark);
                            resultMap.put("essentialHypSymptom", replace);
                        }
                        if ("???".equals(essentialHypSymptom)){
                            resultMap.put("essentialHypSymptom", " -- ");
                        }
                    }
                } else if ("2".equals(essentialHyp)) {
                    resultMap.put("essentialHyp", "???");
                }
                //essential_hyp_date   ????????????
                if (null != anamnesisMap.get("essential_hyp_date") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_date").toString())) {
                    resultMap.put("essentialHypDate", anamnesisMap.get("essential_hyp_date"));
                }
            }
        }


        return resultMap;
    }

    private static String reportTypeReplace(String sportType) {

        String replace = sportType.replace("YDFS05", "??????").replace("YDFS18", "?????????").replace("YDFS06", "??????")
                .replace("YDFS24", "?????????").replace("YDFS25", "??????").replace("YDFS26", "?????????")
                .replace("YDFS19", "?????????").replace("YDFS27", "??????").replace("YDFS29", "??????")
                .replace("YDFS07", "??????").replace("YDFS15", "?????????").replace("YDFS09", "??????(??????)")
                .replace("YDFS17", "??????").replace("YDFS31", "??????").replace("YDFS10", "????????????")
                .replace("YDFS13", "??????").replace("YDFS20", "??????").replace("YDFS33", "???????????????")
                .replace("YDFS34", "????????????").replace("YDFS35", "??????").replace("YDFS36", "?????????")
                .replace("YDFS16", "??????").replace("YDFS08", "??????").replace("YDFS38", "?????????")
                .replace("YDFS39", "????????????").replace("YDFS14", "??????").replace("YDFS12", "????????????");
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
