package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.follow.po.FollowDiabetesPO;
import com.comvee.cdms.follow.po.FollowPO;
import com.comvee.cdms.member.bo.MemberControlTargetBO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.constant.ControlTargetConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**首诊报告
 * @author wyc
 * @date 2019/8/19 15:12
 */
public class FollowReportHelper {

    //首诊报告
    public static String getFirstReportJson(FollowPO followPO, Integer followType,RangeBO range) {
        Map<String, Object> resultMap = new HashMap<>();
        String essentialHyp = ""; //是否有高血压病史  1:有   2:无
        Integer sex = 1;  //性别 1男2女
        String chd = ""; //是否有冠心病:确诊有(QZ01)、确诊无(QZ02)、未评估(QZ03)、疑似（界面中不显示）(QZ04)
        String birthday = "";
        List<String> list = new ArrayList<>(); //糖尿病相关并发症
        List<String> hbList = new ArrayList<>();  //糖尿病相关合并症
        List<String> knowledgeList = new ArrayList<>();  //知识学习计划标签
        //获取报告数据信息
        if (null != followPO.getArchivesJson() && !StringUtils.isBlank(followPO.getArchivesJson())) {
            Map<String, Object> followMap = JsonSerializer.jsonToMap(followPO.getArchivesJson().toString());
            if (null != followMap.get("complication") && !StringUtils.isBlank(followMap.get("complication").toString())) {
                String complication = followMap.get("complication").toString();
                Map<String, Object> complicationMap = JsonSerializer.jsonToMap(complication);
                if (null != complicationMap.get("chd") && !StringUtils.isBlank(complicationMap.get("chd").toString())) {
                    chd = complicationMap.get("chd").toString();
                }
            }
            if (null != followMap.get("basic") && !StringUtils.isBlank(followMap.get("basic").toString())) {
                String basic = followMap.get("basic").toString();
                Map<String, Object> basicMap = JsonSerializer.jsonToMap(basic);
                resultMap.put("member_name", basicMap.get("member_real_name"));  //姓名
                resultMap.put("sex", basicMap.get("sex"));  //性别
                if (null != basicMap.get("sex") && !StringUtils.isBlank(basicMap.get("sex").toString())) {
                    sex = Integer.parseInt(basicMap.get("sex").toString());
                }
                if (null != basicMap.get("birthday") && !StringUtils.isBlank(basicMap.get("birthday").toString())) {
                    birthday = basicMap.get("birthday").toString();
                }
            }
            if (null != followMap.get("sign") && !StringUtils.isBlank(followMap.get("sign").toString())) {
                String sign = followMap.get("sign").toString();
                Map<String, Object> signMap = JsonSerializer.jsonToMap(sign);
                resultMap.put("height", signMap.get("height"));  //身高
                resultMap.put("weight", signMap.get("weight"));  //体重
                resultMap.put("bmi", signMap.get("bmi"));  //BMI
                resultMap.put("sbp", signMap.get("sbp"));  //收缩压
                resultMap.put("dbp", signMap.get("dbp"));  //舒张压
            }
            if (null != followMap.get("lab") && !StringUtils.isBlank(followMap.get("lab").toString())) {
                String lab = followMap.get("lab").toString();
                Map<String, Object> labMap = JsonSerializer.jsonToMap(lab);
                resultMap.put("tc", labMap.get("tc"));  //总胆固醇
                resultMap.put("tg", labMap.get("tg"));  //甘油三酯
                resultMap.put("hdl", labMap.get("hdl"));  //高密度脂蛋白
                resultMap.put("ldl", labMap.get("ldl"));  //低密度脂蛋白
                resultMap.put("lab_hba", labMap.get("lab_hba"));  //糖化血红蛋白
            }
            if (null != followMap.get("treatment") && !StringUtils.isBlank(followMap.get("treatment").toString())) {
                String treatment = followMap.get("treatment").toString();
                Map<String, Object> treatmentMap = JsonSerializer.jsonToMap(treatment);
                resultMap.put("mq_fbg", treatmentMap.get("mq_fbg"));  //空腹血糖
                resultMap.put("blg", treatmentMap.get("blg"));  //餐后2小时血糖
                //华西
                if (null != followType && followType == 2){
                    resultMap.put("nw_mq_fbg", treatmentMap.get("nw_mq_fbg"));  //近一周平均空腹血糖
                    resultMap.put("nw_blg", treatmentMap.get("nw_blg"));  //近一周平均餐后血糖
                }
            }
            if (null != followMap.get("hypoglycemia") && !StringUtils.isBlank(followMap.get("hypoglycemia").toString())) {
                String hypoglycemia = followMap.get("hypoglycemia").toString();
                Map<String, Object> hypoglycemiaMap = JsonSerializer.jsonToMap(hypoglycemia);
                resultMap.put("hyp", hypoglycemiaMap.get("hyp"));  //是否发生过低血糖1:是  2:否
                if (null != hypoglycemiaMap.get("hyp") && !StringUtils.isBlank(hypoglycemiaMap.get("hyp").toString())) {
                    if ("1".equals(hypoglycemiaMap.get("hyp").toString())) {
                        resultMap.put("hyp_frequency", hypoglycemiaMap.get("hyp_frequency"));  //发生低血糖次数
                    }
                }
            }
            if (null != followMap.get("complication") && !StringUtils.isBlank(followMap.get("complication").toString())) {
                String complication = followMap.get("complication").toString();
                Map<String, Object> complicationMap = JsonSerializer.jsonToMap(complication);
                //糖尿病眼底病变
                if (null != complicationMap.get("retinal") && !StringUtils.isBlank(complicationMap.get("retinal").toString())) {
                    if ("SWM01".equals(complicationMap.get("retinal").toString())) {
                        if (null != complicationMap.get("retinal_type_cms") && !StringUtils.isBlank(complicationMap.get("retinal_type_cms").toString())) {
                            String retinalTypeCms = complicationMap.get("retinal_type_cms").toString();
                            //华西
                            if (null != followType && followType == 2) {
                                if ("LX01".equals(retinalTypeCms)) {
                                    list.add("糖尿病视网膜病变（轻度非增生型视网膜病变）");
                                } else if ("LX03".equals(retinalTypeCms)) {
                                    list.add("糖尿病视网膜病变（中度非增生型视网膜病变）");
                                } else if ("LX04".equals(retinalTypeCms)) {
                                    list.add("糖尿病视网膜病变（重度非增生型视网膜病变）");
                                } else if ("LX02".equals(retinalTypeCms)) {
                                    list.add("糖尿病视网膜病变（增生型视网膜病变）");
                                }
                            } else {  //默认
                                if ("LX01".equals(retinalTypeCms)) {
                                    list.add("糖尿病视网膜病变（视网膜病变单纯型）");
                                } else if ("LX02".equals(retinalTypeCms)) {
                                    list.add("糖尿病视网膜病变（视网膜病变增殖型）");
                                } else if ("LX03".equals(retinalTypeCms)) {
                                    if (null != complicationMap.get("retinal_type_cms_remark") && !StringUtils.isBlank(complicationMap.get("retinal_type_cms_remark").toString())) {
                                        list.add("糖尿病视网膜病变（" + complicationMap.get("retinal_type_cms_remark") + "）");
                                    } else {
                                        list.add("糖尿病视网膜病变");
                                    }
                                }
                            }
                        } else {
                            list.add("糖尿病视网膜病变");
                        }
                    }
                }
                //糖尿病肾病
                if (null != complicationMap.get("nephropathy") && !StringUtils.isBlank(complicationMap.get("nephropathy").toString())) {
                    if ("SB01".equals(complicationMap.get("nephropathy").toString())) {
                        if (null != complicationMap.get("neph_type_cms") && !StringUtils.isBlank(complicationMap.get("neph_type_cms").toString())) {
                            String nephTypeCms = complicationMap.get("neph_type_cms").toString();
                            if ("LX01".equals(nephTypeCms)) {
                                list.add("糖尿病肾病 1 期（肾小球高滤过和肾脏肥大）");
                            } else if ("LX02".equals(nephTypeCms)) {
                                list.add("糖尿病肾病 2 期（无临床表现的肾损害期）");
                            } else if ("LX03".equals(nephTypeCms)) {
                                list.add("糖尿病肾病 3 期（早期糖尿病肾病期）");
                            } else if ("LX04".equals(nephTypeCms)) {
                                list.add("糖尿病肾病 4 期（临床糖尿病肾病期）");
                            } else if ("LX05".equals(nephTypeCms)) {
                                list.add("糖尿病肾病 5 期（肾衰竭期）");
                            } else if ("LX06".equals(nephTypeCms)) {
                                if (null != complicationMap.get("neph_type_remark") && !StringUtils.isBlank(complicationMap.get("neph_type_remark").toString())) {
                                    list.add("糖尿病肾病（" + complicationMap.get("neph_type_remark") + "）");
                                } else {
                                    list.add("糖尿病肾病");
                                }
                            }
                        } else {
                            list.add("糖尿病肾病");
                        }
                    }
                }
                //糖尿病周围神经病变
                if (null != complicationMap.get("neuropathy") && !StringUtils.isBlank(complicationMap.get("neuropathy").toString())) {
                    if ("ZWSJ01".equals(complicationMap.get("neuropathy").toString())) {
                        list.add("糖尿病周围神经病变");
                    }
                }
                //糖尿病足
                if (null != complicationMap.get("diabetic_foot") && !StringUtils.isBlank(complicationMap.get("diabetic_foot").toString())) {
                    if ("TNBZ01".equals(complicationMap.get("diabetic_foot").toString())) {
                        list.add("糖尿病足");
                    }
                }
                //糖尿病下肢血管病变
                if (null != complicationMap.get("pad") && !StringUtils.isBlank(complicationMap.get("pad").toString())) {
                    if ("XZXG01".equals(complicationMap.get("pad").toString())) {
                        list.add("糖尿病下肢血管病变");
                    }
                }
                //糖尿病自主神经病变
                if (null != complicationMap.get("dan") && !StringUtils.isBlank(complicationMap.get("dan").toString())) {
                    if ("ZZ01".equals(complicationMap.get("dan").toString())) {
                        if (null != complicationMap.get("dan_type") && !StringUtils.isBlank( complicationMap.get("dan_type").toString())) {
                            String danType = complicationMap.get("dan_type").toString();
                            String other = ""; //其他类型
                            if (danType.contains("LX07")) {
                                if (null != complicationMap.get("dan_type_remark")) {
                                    other = complicationMap.get("dan_type_remark").toString();
                                }
                            }
                            String replace = danType.replace("LX01", "体位低血压").replace("LX02", "胃轻瘫(进食后食物不能往下走)").replace("LX03", "汗腺神经病变(下肢无汗、上肢或局部多汗)")
                                    .replace("LX04", "男性阳痿").replace("LX05", "尿潴留（排尿困难）").replace("LX06", "心脏自主神经改变(心率变化大)")
                                    .replace("LX07", other);
                            if (!"空".equals(replace)){
                                list.add("糖尿病自主神经病变（" + replace + "）");
                            }else{
                                list.add("糖尿病自主神经病变");
                            }

                        } else {
                            list.add("糖尿病自主神经病变");
                        }
                    }
                }
                //冠心病
                if (null != complicationMap.get("chd") && !StringUtils.isBlank(complicationMap.get("chd").toString())) {
                    if ("QZ01".equals(complicationMap.get("chd").toString())) {
                        if (null != complicationMap.get("chd_type") && !StringUtils.isBlank(complicationMap.get("chd_type").toString())){
                            String chdType = complicationMap.get("chd_type").toString();
                            if ("LX01".equals(chdType)){
                                list.add("冠心病(无症状心肌缺血（隐匿性冠心病）)");
                            }else if ("LX02".equals(chdType)){
                                list.add("冠心病(心绞痛)");
                            }else if ("LX03".equals(chdType)){
                                list.add("冠心病(心肌梗死)");
                            }else if ("LX04".equals(chdType)){
                                list.add("冠心病(缺血性心力衰竭（缺血性心脏病）)");
                            }else if ("LX05".equals(chdType)){
                                if (null != complicationMap.get("chd_type_remark") && !StringUtils.isBlank(complicationMap.get("chd_type_remark").toString())){
                                    list.add("冠心病("+complicationMap.get("chd_type_remark")+")");
                                }
                            }
                        }else{
                            list.add("冠心病");
                        }
                    }
                }
                //华西首诊含有心脑血管疾病
                if (null != followType && followType == 2){
                    if (null != complicationMap.get("has_xnxg") && !StringUtils.isBlank(complicationMap.get("has_xnxg").toString())) {
                        if ("1".equals(complicationMap.get("has_xnxg").toString())) {
                            list.add("心脑血管疾病");
                        }
                    }
                }

            }

            if (null != followMap.get("anamnesis") && !StringUtils.isBlank(followMap.get("anamnesis").toString())) {
                Map<String, Object> anamnesisMap = JsonSerializer.jsonToMap(followMap.get("anamnesis").toString());
                if (null != anamnesisMap.get("essential_hyp")){
                    essentialHyp = anamnesisMap.get("essential_hyp").toString();
                    if ("1".equals(essentialHyp)){
                        if (null != anamnesisMap.get("essential_hyp_level")){
                            String hypLevel = anamnesisMap.get("essential_hyp_level").toString();
                            if ("FJ01".equals(hypLevel)){
                                hbList.add("高血压（1级高血压（轻度）: 收缩压140-159mmHg和/或 舒张压90-99mmH）");
                            }else if ("FJ02".equals(hypLevel)){
                                hbList.add("高血压（ 2级高血压（中度）: 收缩压160-179mmHg和/或 舒张压100-109mmHg）");
                            }else if ("FJ03".equals(hypLevel)){
                                hbList.add("高血压（ 3级高血压（重度）: 收缩压≥180 mmHg和/或 舒张压≥110mmHg）");
                            }else if ("FJ04".equals(hypLevel)){
                                hbList.add("高血压（ 单纯收缩期高血压 : 收缩压≥140 mmHg和 舒张压<90mmHg）");
                            }else if ("FJ05".equals(hypLevel)){
                                if (null != anamnesisMap.get("essential_hyp_level_remark") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp_level_remark").toString())){
                                    hbList.add("高血压（ "+anamnesisMap.get("essential_hyp_level_remark").toString()+"）");
                                }
                            }
                        }else{
                            hbList.add("高血压");
                        }
                    }
                }
            }

            if (null != followMap.get("knowledge") && !StringUtils.isBlank(followMap.get("knowledge").toString())){
                Map<String, Object> knowledgeMap = JsonSerializer.jsonToMap(followMap.get("knowledge").toString());
                if (null != knowledgeMap){
                    if (null != knowledgeMap.get("knowledgeTagList") && !StringUtils.isBlank(knowledgeMap.get("knowledgeTagList").toString())){
                        knowledgeList = JsonSerializer.jsonToList(knowledgeMap.get("knowledgeTagList").toString(), String.class);
                    }
                }
            }




        }
        //华西首诊报告  包括自我评估结果和管理等级评估结果
        if (null != followType && followType == 2) {
            if (null != followPO.getLevelJson() && !StringUtils.isBlank(followPO.getLevelJson().toString())) {
                Map<String, Object> levelJson = JsonSerializer.jsonToMap(followPO.getLevelJson().toString());
                resultMap.put("q1_level", levelJson.get("q1_level"));  //自我评估结果 差 中等 良好
                resultMap.put("level_hx", levelJson.get("level_hx"));  //分层评级 1平稳层 2中危层 3高危层
                resultMap.put("dsme_hx", levelJson.get("dsme_hx"));    //DSME/S等级  1 一级 2 二级 3 三级
            }
        }
        resultMap.put("mqzywt", followPO.getMqzywt()); //目前主要问题
        resultMap.put("zygjcs", followPO.getZygjcs()); //主要改进措施
        resultMap.put("yqddmb", followPO.getYqddmb()); //预期达到目标
        resultMap.put("essentialHyp", essentialHyp);
        resultMap.put("bfList", list);
        resultMap.put("hbList", hbList);
        resultMap.put("knowledgeList", knowledgeList);
        //控制目标
        resultMap.put("range", range);
        String jsonString = JsonSerializer.objectToJson(resultMap);
        return jsonString;
    }


}
