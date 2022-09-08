package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.follow.po.FollowPO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.po.MemberArchivesPO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.tool.EighteenRangeHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高血压首诊报告
 * v5.0.0
 *
 * @author wyc
 * @date 2019/11/19 10:14
 */
public class FollowGxyReportHelper {
    private static final String LOW_BMI = "18.5";  //bmi下限
    private static final String HIGHT_BMI = "23.9";  //bmi上限

    private static final String LOW_SBP = "90";  //收缩压下限
    private static final String HIGHT_SBP = "139";  //收缩压上限

    private static final String LOW_DBP = "60";  //舒张压下限
    private static final String HIGHT_DBP = "89";  //舒张压上限

    private static final String LOW_TC = "4.49";  //胆固醇下限
    private static final String HIGHT_TC = "4.49";  //胆固醇上限

    private static final String LOW_TG = "1.69";  //甘油三酯下限
    private static final String HIGHT_TG = "1.69";  //甘油三酯上限

    private static final String LOW_LDL = "3.39";  //高密度脂蛋白下限(男)
    private static final String HIGHT_LDL = "3.39";  //高密度脂蛋白上限(男)

    private static final String LOW_HDL = "1.01";  //低密度脂蛋白下限(中危或低危)
    private static final String HIGHT_HDL = "1.76";  //低密度脂蛋白上限(中危或低危)

//    public static String getFirstGxyReportJson(FollowPO followPO,boolean tnbBfz) {
    public static String getFirstGxyReportJson(FollowPO followPO,RangeBO range) {
        Map<String, Object> resultMap = new HashMap<>();
        Integer sex = 1;  //性别 1男2女
        Integer age = 0;  //年龄
        String cardiopathy = "";  //心脏疾病
        String slowNepStage = "";   //慢性肾脏病
        boolean gxyFlag = false;   //是否有高血压并发症
        String layer = "";  //高血压分层 1:高危 2:中危 3:低危
        String height = ""; //身高

        List<String> mxbfList = new ArrayList<>(); //高血压相关慢性并发症
        List<String> jxbfList = new ArrayList<>(); //高血压相关急性并发症
        List<String> hbList = new ArrayList<>(); //高血压相关合并症


        if (null != followPO.getArchivesJson() && !StringUtils.isBlank(followPO.getArchivesJson())) {
            Map<String, Object> followMap = JsonSerializer.jsonToMap(followPO.getArchivesJson().toString());

            if (null != followMap.get("basic") && !StringUtils.isBlank(followMap.get("basic").toString())) {
                String basic = followMap.get("basic").toString();
                Map<String, Object> basicMap = JsonSerializer.jsonToMap(basic);
                resultMap.put("member_name", basicMap.get("member_real_name"));  //姓名
                resultMap.put("sex", basicMap.get("sex"));  //性别
                if (null != basicMap.get("birthday") && !StringUtils.isBlank(basicMap.get("birthday").toString())) {
                    resultMap.put("birthday", basicMap.get("birthday"));  //出生日期
                    age = DateHelper.getAge(basicMap.get("birthday").toString());
                }
                if (null != basicMap.get("sex") && !StringUtils.isBlank(basicMap.get("sex").toString())) {
                    sex = Integer.parseInt(basicMap.get("sex").toString());
                }
            }

            if (null != followMap.get("sign") && !StringUtils.isBlank(followMap.get("sign").toString())) {
                String sign = followMap.get("sign").toString();
                Map<String, Object> signMap = JsonSerializer.jsonToMap(sign);
                if (signMap.get("height") != null){
                    height = signMap.get("height").toString();
                }
                resultMap.put("height", signMap.get("height"));  //身高
                resultMap.put("weight", signMap.get("weight"));  //体重
                resultMap.put("bmi", signMap.get("bmi"));  //BMI
                resultMap.put("waistline", signMap.get("waistline"));  //腰围
                resultMap.put("hipline", signMap.get("hipline"));  //臀围
                resultMap.put("whr", signMap.get("whr"));  //腰臀比
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
            }

            if (null != followMap.get("hypertension") && !StringUtils.isBlank(followMap.get("hypertension").toString())) {
                String hypertension = followMap.get("hypertension").toString();
                Map<String, Object> hypMap = JsonSerializer.jsonToMap(hypertension);
                //心脏疾病
                if (null != hypMap.get("cardiopathy") && !StringUtils.isBlank(hypMap.get("cardiopathy").toString())) {
                    if ("XZJB01".equals(hypMap.get("cardiopathy").toString())) {
                        cardiopathy = hypMap.get("cardiopathy").toString();
                        gxyFlag = true;
                        if (null != hypMap.get("cardiopathyType") && !StringUtils.isBlank(hypMap.get("cardiopathyType").toString())) {
                            String cardiopathyType = hypMap.get("cardiopathyType").toString();
                            if ("XZJBLX01".equals(cardiopathyType)) {
                                mxbfList.add("心脏疾病(左心室肥厚)");
                            } else if ("XZJBLX02".equals(cardiopathyType)) {
                                mxbfList.add("心脏疾病(冠心病)");
                            } else if ("XZJBLX03".equals(cardiopathyType)) {
                                mxbfList.add("心脏疾病(心力衰竭)");
                            } else if ("XZJBLX04".equals(cardiopathyType)) {
                                mxbfList.add("心脏疾病(心率失常)");
                            } else if ("XZJBLX05".equals(cardiopathyType) && null != hypMap.get("cardiopathyTypeOther") && !StringUtils.isBlank(hypMap.get("cardiopathyTypeOther").toString())) {
                                String cardiopathyTypeOther = hypMap.get("cardiopathyTypeOther").toString();
                                mxbfList.add("心脏疾病(" + cardiopathyTypeOther + ")");
                            }
                        } else {
                            mxbfList.add("心脏疾病");
                        }
                    }else if ("XZJB03".equals(hypMap.get("cardiopathy").toString())){
                        if (null != hypMap.get("cardiopathyCms") && !StringUtils.isBlank(hypMap.get("cardiopathyCms").toString())){
                            gxyFlag = true;
                        }
                    }else if ("XZJB04".equals(hypMap.get("cardiopathy").toString())){
                        gxyFlag = true;
                    }
                }

                //脑血管病
                if (null != hypMap.get("cerebral") && !StringUtils.isBlank(hypMap.get("cerebral").toString())) {
                    if ("NXGB01".equals(hypMap.get("cerebral").toString())) {
                        gxyFlag = true;
                        if (null != hypMap.get("cerebralType") && !StringUtils.isBlank(hypMap.get("cerebralType").toString())) {
                            String cerebralType = hypMap.get("cerebralType").toString();
                            if ("NXGLX01".equals(cerebralType)){
                                mxbfList.add("脑血管病(脑出血)");
                            }else if ("NXGLX02".equals(cerebralType)){
                                mxbfList.add("脑血管病(脑梗死)");
                            }else if ("NXGLX03".equals(cerebralType)){
                                mxbfList.add("脑血管病(腔隙性脑梗死)");
                            }else if ("NXGLX04".equals(cerebralType)){
                                mxbfList.add("脑血管病(短暂性脑缺血发作)");
                            } else if ("NXGLX05".equals(cerebralType) && null != hypMap.get("cerebralTypeOther") && !StringUtils.isBlank(hypMap.get("cerebralTypeOther").toString())){
                                String cerebralTypeOther = hypMap.get("cerebralTypeOther").toString();
                                mxbfList.add("脑血管病("+cerebralTypeOther+")");
                            }
                        }else {
                            mxbfList.add("脑血管病");
                        }

                    }else if ("NXGB03".equals(hypMap.get("cerebral").toString())){
                        if (null != hypMap.get("cerebralCms") && !StringUtils.isBlank(hypMap.get("cerebralCms").toString())) {
                            gxyFlag = true;
                        }
                    }else if ("NXGB04".equals(hypMap.get("cerebral").toString())){
                        gxyFlag = true;
                    }
                }

                //高血压眼底视网膜病变
                if (null != hypMap.get("hypRet") && !StringUtils.isBlank(hypMap.get("hypRet").toString())) {
                    if ("GXYYD01".equals(hypMap.get("hypRet").toString())) {
                        gxyFlag = true;
                        if (null != hypMap.get("hypRetLevel") && !StringUtils.isBlank(hypMap.get("hypRetLevel").toString())) {
                            String hypRetLevel = hypMap.get("hypRetLevel").toString();
                            if ("GXYYDFJ01".equals(hypRetLevel)) {
                                mxbfList.add("高血压眼底视网膜病变(I级)");
                            } else if ("GXYYDFJ02".equals(hypRetLevel)) {
                                mxbfList.add("高血压眼底视网膜病变(II级)");
                            } else if ("GXYYDFJ03".equals(hypRetLevel)) {
                                mxbfList.add("高血压眼底视网膜病变(III级)");
                            } else if ("GXYYDFJ04".equals(hypRetLevel)) {
                                mxbfList.add("高血压眼底视网膜病变(IV级)");
                            }
                        } else {
                            mxbfList.add("高血压眼底视网膜病变");
                        }
                    }else if ("GXYYD03".equals(hypMap.get("hypRet").toString())){
                        if (null != hypMap.get("hypRetCms") && !StringUtils.isBlank(hypMap.get("hypRetCms").toString())) {
                            if (!"GXYYDZZ01".equals(hypMap.get("hypRetCms").toString())){
                                gxyFlag = true;
                            }
                        }
                    }else if ("GXYYD04".equals(hypMap.get("hypRet").toString())){
                        gxyFlag = true;
                    }
                }

                //慢性肾脏病
                if (null != hypMap.get("slowNep") && !StringUtils.isBlank(hypMap.get("slowNep").toString())) {
                    if ("MXSZB01".equals(hypMap.get("slowNep").toString())) {
                        gxyFlag = true;
                        if (null != hypMap.get("slowNepStage") && !StringUtils.isBlank(hypMap.get("slowNepStage").toString())) {
                             slowNepStage = hypMap.get("slowNepStage").toString();
                            if ("MXSZBFQ01".equals(slowNepStage)) {
                                mxbfList.add("慢性肾脏病(CKD 1期)");
                            } else if ("MXSZBFQ02".equals(slowNepStage)) {
                                mxbfList.add("慢性肾脏病(CKD 2期)");
                            } else if ("MXSZBFQ03".equals(slowNepStage)) {
                                mxbfList.add("慢性肾脏病(CKD 3期)");
                            } else if ("MXSZBFQ04".equals(slowNepStage)) {
                                mxbfList.add("慢性肾脏病(CKD 4期)");
                            }else if ("MXSZBFQ05".equals(slowNepStage)) {
                                mxbfList.add("慢性肾脏病(CKD 5期)");
                            }
                        } else {
                            mxbfList.add("慢性肾脏病");
                        }
                    }else if ("MXSZB03".equals(hypMap.get("slowNep").toString())){
                        if (null != hypMap.get("slowNepCms") && !StringUtils.isBlank(hypMap.get("slowNepCms").toString())) {
                            if (!"MXSZBZZ01".equals(hypMap.get("slowNepCms").toString())){
                                gxyFlag = true;
                            }
                        }
                    }else if ("MXSZB04".equals(hypMap.get("slowNep").toString())){
                        gxyFlag = true;
                    }
                }

                //外周血管疾病
                if (null != hypMap.get("peripheral") && !StringUtils.isBlank(hypMap.get("peripheral").toString())) {
                    if ("WZXG01".equals(hypMap.get("peripheral").toString())) {
                        gxyFlag = true;
                        mxbfList.add("外周血管疾病");
                    }else if ("WZXG03".equals(hypMap.get("peripheral").toString())){
                        if (null != hypMap.get("peripheralCms") && !StringUtils.isBlank(hypMap.get("peripheralCms").toString())) {
                            gxyFlag = true;
                        }
                    }else if ("WZXG04".equals(hypMap.get("peripheral").toString())){
                        gxyFlag = true;
                    }
                }

                //糖尿病
                if (null != hypMap.get("diabetes") && !StringUtils.isBlank(hypMap.get("diabetes").toString())) {
                    if ("1".equals(hypMap.get("diabetes").toString())) {
                        gxyFlag = true;
                        hbList.add("糖尿病");
                    }
                }

                //急性并发症
                if (null != hypMap.get("gxyjxbfz") && !StringUtils.isBlank(hypMap.get("gxyjxbfz").toString())) {
                    String gxyjxbfz = hypMap.get("gxyjxbfz").toString().toString();
                    String[] split = gxyjxbfz.split(",");
                    for (String s : split) {
                        if ("GXYJXBFZ02".equals(s)) {
                            jxbfList.add("主动脉夹层");
                        }else if ("GXYJXBFZ03".equals(s)) {
                            jxbfList.add("高血压危象（高血压急诊、高血压亚急诊）");
                        }else if ("GXYJXBFZ04".equals(s)) {
                            jxbfList.add("高血压脑病");
                        }else if ("GXYJXBFZ05".equals(s) && null != hypMap.get("gxyjxbfzOther") && !StringUtils.isBlank(hypMap.get("gxyjxbfzOther").toString())) {
                            jxbfList.add(hypMap.get("gxyjxbfzOther").toString());
                        }
                    }
                }
            }
            //冠心病
            if (null != followMap.get("complication") && !StringUtils.isBlank(followMap.get("complication").toString())) {
                String complication = followMap.get("complication").toString();
                Map<String, Object> complicationMap = JsonSerializer.jsonToMap(complication);
                if (null != complicationMap.get("chd") && !StringUtils.isBlank(complicationMap.get("chd").toString())) {
                    String chd = complicationMap.get("chd").toString();
                    if ("QZ01".equals(chd)) {
                        gxyFlag = true;
                        if (null != complicationMap.get("chd_type") && !StringUtils.isBlank(complicationMap.get("chd_type").toString())){
                            String chdType = complicationMap.get("chd_type").toString();
                            if ("LX01".equals(chdType)){
                                mxbfList.add("冠心病(无症状心肌缺血（隐匿性冠心病）)");
                            }else if("LX02".equals(chdType)){
                                mxbfList.add("冠心病(心绞痛)");
                            }else if("LX03".equals(chdType)){
                                mxbfList.add("冠心病(心肌梗死)");
                            }else if("LX04".equals(chdType)){
                                mxbfList.add("冠心病( 缺血性心力衰竭（缺血性心脏病）)");
                            }else if("LX05".equals(chdType)){
                                if (null != complicationMap.get("chd_type_remark") && !StringUtils.isBlank(complicationMap.get("chd_type_remark").toString())){
                                    String chdTypeRemark = complicationMap.get("chd_type_remark").toString();
                                    mxbfList.add("冠心病("+chdTypeRemark+")");
                                }else{
                                    mxbfList.add("冠心病");
                                }
                            }
                        }else{
                            mxbfList.add("冠心病");
                        }
                    } else if ("QZ03".equals(chd)) {
                        if (null != complicationMap.get("chd_symptom") && !StringUtils.isBlank(complicationMap.get("chd_symptom").toString())) {
                            if (!"ZZ01".equals(complicationMap.get("chd_symptom").toString())) {
                                gxyFlag = true;
                            }
                        }
                    }else if ("QZ04".equals(chd)){
                        gxyFlag = true;
                    }

                }
            }

            resultMap.put("mxbfList",mxbfList); //高血压相关慢性并发症
            resultMap.put("jxbfList",jxbfList); //高血压相关急性并发症
            resultMap.put("hbList",hbList); //高血压相关合并症
            if (null != followPO.getLevelJson() && !StringUtils.isBlank(followPO.getLevelJson().toString())) {
                Map<String, Object> levelJson = JsonSerializer.jsonToMap(followPO.getLevelJson().toString());
                if (null != levelJson.get("layer") && !StringUtils.isBlank(levelJson.get("layer").toString())){
                    layer = levelJson.get("layer").toString();
                }
                resultMap.put("level", levelJson.get("level"));  //高血压分级 1:一级 2:二级 3:三级 0:其他
                resultMap.put("layer", levelJson.get("layer"));  //高血压分层 1高危 2中危 3 低危
                resultMap.put("advise", levelJson.get("advise"));  //分层分级建议
            }

        }
//        RangeBO range = getRange(sex, age, cardiopathy, slowNepStage, layer, gxyFlag, tnbBfz,height);
        resultMap.put("range", range);

        String jsonString = JsonSerializer.objectToJson(resultMap);
        return jsonString;
    }

    /**
     * 获取控制目标
     * @param sex 性别 1男2女
     * @param age 年龄
     * @param cardiopathy 心脏疾病
     * @param slowNepStage 慢性肾脏病
     * @param layer 高血压分层 1:高危 2:中危 3:低危
     * @param gxyFlag 是否有高血压并发症
     * @param tnbBfz 是否有糖尿病并发症
     * @return
     */
    private static RangeBO getRange(Integer sex,Integer age,String cardiopathy,String slowNepStage,
                             String layer,boolean gxyFlag,boolean tnbBfz,String height){
        RangeBO range = new RangeBO();
        range.setLowBmi(LOW_BMI);  //bmi下限
        range.setHighBmi(HIGHT_BMI);   //bmi上限
        range.setLowSystolicPress(LOW_SBP); //收缩压下限
        range.setHighSystolicPress(HIGHT_SBP); //收缩压上限
        range.setLowDiastolicPress(LOW_DBP); //舒张压下限
        range.setHighDiastolicPress(HIGHT_DBP); //舒张压上限
        range.setLowTCholesterol(LOW_TC);  //胆固醇下限
        range.setHighTCholesterol(HIGHT_TC);  //胆固醇上限
        range.setLowTriglyceride(LOW_TG);   //甘油三酯下限
        range.setHighTriglyceride(HIGHT_TG);      //甘油三酯上限
        range.setLowLDLCholesterol(LOW_LDL);      //低密度脂蛋白下限(中危或低危)
        range.setHighLDLCholesterol(HIGHT_LDL);    //低密度脂蛋白上限(中危或低危)
        range.setLowHDLCholesterol(LOW_HDL);       //高密度脂蛋白下限(男)
        range.setHighHDLCholesterol(HIGHT_HDL);   //高密度脂蛋白上限(男)

        if (age >= 18 && age < 65 && gxyFlag){
            range.setLowSystolicPress("90"); //收缩压下限
            range.setHighSystolicPress("129"); //收缩压上限
        }else if (age >= 65){
            range.setLowSystolicPress("90"); //收缩压下限
            range.setHighSystolicPress("149"); //收缩压上限
        }
        if ("3".equals(layer)){
            range.setLowLDLCholesterol("2.59");      //低密度脂蛋白下限
            range.setHighLDLCholesterol("2.59");    //低密度脂蛋白上限
        }
        if (tnbBfz || "XZJB01".equals(cardiopathy) || "MXSZBFQ04".equals(slowNepStage) || "MXSZBFQ05".equals(slowNepStage)){
            range.setLowLDLCholesterol("1.79");      //低密度脂蛋白下限
            range.setHighLDLCholesterol("1.79");    //低密度脂蛋白上限
        }
        if (2 == sex){
            range.setLowHDLCholesterol("1.31");       //高密度脂蛋白下限(女)
            range.setHighHDLCholesterol("1.76");   //高密度脂蛋白上限(女)
        }
        //小于18岁控制目标
        if (age < 18 && !StringUtils.isBlank(height)){
            int hypBfz = gxyFlag ? 1 : 2;
            Map<String, Float> eighteenRange = EighteenRangeHelper.getEighteenRange(age, Double.parseDouble(height), hypBfz, sex);
            range.setLowSystolicPress(eighteenRange.get("sbpLow").toString()); //收缩压下限
            range.setHighSystolicPress(eighteenRange.get("sbpHigh").toString()); //收缩压上限
            range.setLowDiastolicPress(eighteenRange.get("dbpLow").toString()); //舒张压下限
            range.setHighDiastolicPress(eighteenRange.get("dbpHigh").toString()); //舒张压上限
        }

        return range;
    }


    /**
     * 判断患者是否有糖尿病并发症(急性,慢性)
     * @return
     */
    public static boolean checkTnbBfz(MemberArchivesPO archives, MemberPO member){
        boolean flag = false;
        if (null != member){
            if (!StringUtils.isBlank(member.getIsDiabetes()) && "1".equals(member.getIsDiabetes())){
                if (null != archives && !StringUtils.isBlank(archives.getArchivesJson())){
                    String archivesJson = archives.getArchivesJson();
                    Map<String, Object> jsonMap = JsonSerializer.jsonToMap(archivesJson);
                    if (null != jsonMap.get("complication") && !StringUtils.isBlank(jsonMap.get("complication").toString())){
                        String complication = jsonMap.get("complication").toString();
                        Map<String, Object> complicationMap = JsonSerializer.jsonToMap(complication);
                        //糖尿病眼底病变
                        if (null != complicationMap.get("retinal") && !StringUtils.isBlank(complicationMap.get("retinal").toString())) {
                            if ("SWM01".equals(complicationMap.get("retinal").toString())){
                                flag = true;
                            }
                        }
                        //糖尿病肾病
                        if (null != complicationMap.get("nephropathy") && !StringUtils.isBlank(complicationMap.get("nephropathy").toString())) {
                            if ("SB01".equals(complicationMap.get("nephropathy").toString())){
                                flag = true;
                            }
                        }
                        //周围神经病变
                        if (null != complicationMap.get("neuropathy") && !StringUtils.isBlank(complicationMap.get("neuropathy").toString())) {
                            if ("ZWSJ01".equals(complicationMap.get("neuropathy").toString())){
                                flag = true;
                            }
                        }
                        // 糖尿病足
                        if (null != complicationMap.get("diabetic_foot") && !StringUtils.isBlank(complicationMap.get("diabetic_foot").toString())) {
                            if ("TNBZ01".equals(complicationMap.get("diabetic_foot").toString())){
                                flag = true;
                            }
                        }
                        // 下肢血管病变
                        if (null != complicationMap.get("pad") && !StringUtils.isBlank(complicationMap.get("pad").toString())) {
                            if ("XZXG01".equals(complicationMap.get("pad").toString())){
                                flag = true;
                            }
                        }
                        //  自主神经病变
                        if (null != complicationMap.get("dan") && !StringUtils.isBlank(complicationMap.get("dan").toString())) {
                            if ("ZZ01".equals(complicationMap.get("dan").toString())){
                                flag = true;
                            }
                        }
                        //  冠心病
                        if (null != complicationMap.get("chd") && !StringUtils.isBlank(complicationMap.get("chd").toString())) {
                            if ("QZ01".equals(complicationMap.get("chd").toString())){
                                flag = true;
                            }
                        }
                        //急性并发症
                        if (null != complicationMap.get("jyntnbjxbfz") && !StringUtils.isBlank(complicationMap.get("jyntnbjxbfz").toString())) {
                            if (!"LX01".equals(complicationMap.get("jyntnbjxbfz").toString())){
                                flag = true;
                            }
                        }

                    }
                }
            }
        }

        return flag;
    }

}



