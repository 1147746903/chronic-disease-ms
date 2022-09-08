package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.bo.RangeBO;

import java.util.*;

/** 日常随访智能建议
 * @author wyc
 * @date 2019/9/29 10:09
 */
public class FollowupHelper {
    /***
     *
     * @param followBodyMap  随访信息
     * @param range   控制目标
     * @param followType  随访类型 1默认 2华西随访
     * @return
     */
    public static Map<String,Object> outFollowup(Map<String, Object> followBodyMap,RangeBO range,Integer sex){
        Map<String, Object> reMap = new HashMap<>();

        List<String> wtList = new ArrayList<>();//问题集合
        List<String> gjList = new ArrayList<>();//改进集合
        List<String> mbList = new ArrayList<>();//目标集合

        Set<Integer> gjSet = new LinkedHashSet<>();//改进去重
        Set<Integer> mbSet = new LinkedHashSet<>();//目标去重

        String sexBasic = "";
        if (null != followBodyMap.get("basic") && !StringUtils.isBlank(followBodyMap.get("basic").toString())){
            Map<String, Object> basicMap = JsonSerializer.jsonToMap(followBodyMap.get("basic").toString());
            if (null != basicMap.get("sex") && !StringUtils.isBlank(basicMap.get("sex").toString())){
                sexBasic = basicMap.get("sex").toString();
            }
        }
        //性别 1: 男 2 :女
        String sexStr = sex == null ? sexBasic : String.valueOf(sex);

        if (null != followBodyMap.get("sign") && !StringUtils.isBlank(followBodyMap.get("sign").toString())){
            Map<String, Object> signMap = JsonSerializer.jsonToMap(followBodyMap.get("sign").toString());
            //血压
            if (null != signMap.get("sbp") && null != signMap.get("dbp") && !StringUtils.isBlank(signMap.get("sbp").toString()) && !StringUtils.isBlank(signMap.get("dbp").toString())){
                double sbp = Double.parseDouble(signMap.get("sbp").toString()); //收缩压
                double dbp = Double.parseDouble(signMap.get("dbp").toString()); //舒张压

                double lowSbp = Double.parseDouble(range.getLowSystolicPress());  //收缩压下限
                double highSbp = Double.parseDouble(range.getHighSystolicPress()); //收缩压上限
                double lowDbp = Double.parseDouble(range.getLowDiastolicPress());  //舒张压下限
                double highDbp = Double.parseDouble(range.getHighDiastolicPress()); //舒张压上限

                if (dbp > highDbp || sbp > highSbp){
                    String wt = "您目前血压（收缩压/舒张压）："+sbp+"/"+ dbp +"mmHg,血压偏高；\r\n";
                    wtList.add(wt);
                    gjSet.add(1);
                    mbSet.add(1);
                }else if (dbp < lowDbp || sbp < lowSbp){
                    String wt = "您目前血压（收缩压/舒张压）："+sbp+"/"+ dbp +"mmHg,血压偏低；\r\n";
                    wtList.add(wt);
                    gjSet.add(2);
                    mbSet.add(1);
                }
            }
            //心率
            if (null != signMap.get("tz_heart_rate") && !StringUtils.isBlank(signMap.get("tz_heart_rate").toString())){
                double rate = Double.parseDouble(signMap.get("tz_heart_rate").toString());
                if (rate < 60){
                    String wt = "静息心率："+Math.round(rate)+"次/分钟，心动过缓；\r\n";
                    wtList.add(wt);
                    gjSet.add(3);
                    mbSet.add(2);
                }else if (rate > 100){
                    String wt = "静息心率："+Math.round(rate)+"次/分钟，心动过速；\r\n";
                    wtList.add(wt);
                    gjSet.add(4);
                    mbSet.add(2);
                }
            }
            //腰臀比
            if (null != signMap.get("whr") && !StringUtils.isBlank(signMap.get("whr").toString()) && !"--".equals(signMap.get("whr").toString())){
                double whr = Double.parseDouble(signMap.get("whr").toString());
                if ("1".equals(sexStr)){  //男
                    if (whr >= 0.9){
                        String wt = "腰臀比："+whr+",中心型肥胖；\r\n";
                        wtList.add(wt);
                        gjSet.add(5);
                        mbSet.add(3);
                    }
                }else if ("2".equals(sexStr)){  //女
                    if (whr >=0.85){
                        String wt = "腰臀比："+whr+",中心型肥胖；\r\n";
                        wtList.add(wt);
                        gjSet.add(5);
                        mbSet.add(4);
                    }
                }
            }
            //BMI
            if (null != signMap.get("bmi") && !StringUtils.isBlank(signMap.get("bmi").toString()) && !"--".equals(signMap.get("bmi").toString())){
                double bmi = Double.parseDouble(signMap.get("bmi").toString());
                double lowBmi = Double.parseDouble(range.getLowBmi()); //BMI下限
                double highBmi = Double.parseDouble(range.getHighBmi()); //BMI上限
                if(bmi < lowBmi){
                    String wt = "BMI值为："+bmi+"，消瘦；\r\n";
                    wtList.add(wt);
                    gjSet.add(15);
                    mbSet.add(10);
                }else if(highBmi < bmi && bmi < 28){
                    String wt = "BMI值为："+bmi+"，超重；\r\n";
                    wtList.add(wt);
                    gjSet.add(16);
                    mbSet.add(11);
                }else if(bmi >= 28){
                    String wt = "BMI值为："+bmi+"，肥胖；\r\n";
                    wtList.add(wt);
                    gjSet.add(17);
                    mbSet.add(11);
                }
            }
        }

        if (null != followBodyMap.get("treatment") && !StringUtils.isBlank(followBodyMap.get("treatment").toString())){
            Map<String, Object> treatmentMap = JsonSerializer.jsonToMap(followBodyMap.get("treatment").toString());
            //空腹血糖
            if (null != treatmentMap.get("mq_fbg") && !StringUtils.isBlank(treatmentMap.get("mq_fbg").toString())){
                double mqFbg = Double.parseDouble(treatmentMap.get("mq_fbg").toString());  //空腹血糖
                double lowKf = Double.parseDouble(range.getLowBeforeBreakfast());  //空腹血糖下限
                double highKf = Double.parseDouble(range.getHighBeforeBreakfast());  //空腹血糖上限
                if (mqFbg < lowKf){
                    String wt = "您目前的空腹血糖为："+mqFbg+"mmol/l，空腹血糖偏低；\r\n";
                    wtList.add(wt);
                    gjSet.add(6);
                    mbSet.add(5);
                }else if (mqFbg > highKf){
                    String wt = "您目前的空腹血糖为："+mqFbg+"mmol/l，空腹血糖偏高；\r\n";
                    wtList.add(wt);
                    gjSet.add(7);
                    mbSet.add(5);
                }
            }
            //餐后2小时血糖
            if (null != treatmentMap.get("blg") && !StringUtils.isBlank(treatmentMap.get("blg").toString())){
                double blg = Double.parseDouble(treatmentMap.get("blg").toString());
                double lowCh = Double.parseDouble(range.getLowAfterMeal());   //餐后血糖下限
                double highCh = Double.parseDouble(range.getHighAfterMeal());   //餐后血糖上限
                if (blg < lowCh){
                    String wt = "您目前的餐后2小时血糖值为："+blg+"mmol/L，血糖偏低；\r\n";
                    wtList.add(wt);
                    gjSet.add(10);
                    mbSet.add(7);
                }else if (blg > highCh){
                    String wt = "您目前的餐后2小时血糖值为："+blg+"mmol/L，血糖偏高；\r\n";
                    wtList.add(wt);
                    gjSet.add(9);
                    mbSet.add(7);
                }
            }
            //睡前血糖
            if (null != treatmentMap.get("bsg") && !StringUtils.isBlank(treatmentMap.get("bsg").toString())){
                double bsg = Double.parseDouble(treatmentMap.get("bsg").toString());
                double lowSq = Double.parseDouble(range.getLowBeforeSleep()); //睡前血糖下限
                double highSq = Double.parseDouble(range.getHighBeforeSleep()); //睡前血糖上限
                if (bsg < lowSq){
                    String wt = "您目前的睡前血糖值为："+bsg+"mmol/L，睡前血糖偏低；\r\n";
                    wtList.add(wt);
                    gjSet.add(11);
                    mbSet.add(8);
                }else if (bsg > highSq){
                    String wt = "您目前的睡前血糖值为："+bsg+"mmol/L，睡前血糖偏高；\r\n";
                    wtList.add(wt);
                    gjSet.add(12);
                    mbSet.add(8);
                }
            }
            //夜间血糖
            if (null != treatmentMap.get("glucose") && !StringUtils.isBlank(treatmentMap.get("glucose").toString())){
                double glucose = Double.parseDouble(treatmentMap.get("glucose").toString());
                double lowYj = Double.parseDouble(range.getLowBeforedawn());  //夜间血糖下限
                double highYj = Double.parseDouble(range.getHighBeforedawn());  //夜间血糖上限
                if (glucose < lowYj){
                    String wt = "您目前的夜间血糖值为："+glucose+"mmol/L，夜间血糖偏低；\r\n";
                    wtList.add(wt);
                    gjSet.add(14);
                    mbSet.add(9);
                }else if (glucose > highYj){
                    String wt = "您目前的夜间血糖值为："+glucose+"mmol/L，夜间血糖偏高；\r\n";
                    wtList.add(wt);
                    gjSet.add(13);
                    mbSet.add(9);
                }
            }
        }

        if (null != followBodyMap.get("lab") && !StringUtils.isBlank(followBodyMap.get("lab").toString())){
            Map<String, Object> labMap = JsonSerializer.jsonToMap(followBodyMap.get("lab").toString());
            //糖化血红蛋白
            if (null != labMap.get("lab_hba") && !StringUtils.isBlank(labMap.get("lab_hba").toString())){
                double labHba = Double.parseDouble(labMap.get("lab_hba").toString());
                double highTh = Double.parseDouble(range.getHighGlycosylatedVal());  //糖化血红蛋白上限
                if(labHba > highTh){
                    String wt = "您目前的糖化血红蛋白值为："+labHba+"%（偏高），近期血糖控制不佳；\r\n";
                    wtList.add(wt);
                    gjSet.add(8);
                    mbSet.add(6);
                }
            }
            //白细胞计数(WBC)
            if (null != labMap.get("lab_xcg_wbc") && !StringUtils.isBlank(labMap.get("lab_xcg_wbc").toString())){
                double wbc = Double.parseDouble(labMap.get("lab_xcg_wbc").toString());
                if (wbc > 10){
                    String wt = "白细胞计数增高；\r\n";
                    wtList.add(wt);
                    gjSet.add(20);
                    mbSet.add(14);
                }else if (wbc < 4){
                    String wt = "白细胞计数减低；\r\n";
                    wtList.add(wt);
                    gjSet.add(21);
                    mbSet.add(15);
                }
            }
            //红细胞计数(RBC)
            if (null != labMap.get("lab_xcg_rbc") && !StringUtils.isBlank(labMap.get("lab_xcg_rbc").toString())){
                double rbc = Double.parseDouble(labMap.get("lab_xcg_rbc").toString());
                if ("1".equals(sexStr)){ //男
                    if (rbc > 5.5){
                        String wt = "红细胞计数增高；\r\n";
                        wtList.add(wt);
                        gjSet.add(22);
                        mbSet.add(16);
                    }else if (rbc < 4.0){
                        String wt = "红细胞计数减低；\r\n";
                        wtList.add(wt);
                        gjSet.add(23);
                        mbSet.add(18);
                    }
                }else if ("2".equals(sexStr)){  //女
                    if (rbc > 5.0){
                        String wt = "红细胞计数增高；\r\n";
                        wtList.add(wt);
                        gjSet.add(22);
                        mbSet.add(17);
                    }else if (rbc < 3.5){
                        String wt = "红细胞计数减低；\r\n";
                        wtList.add(wt);
                        gjSet.add(23);
                        mbSet.add(19);
                    }
                }
            }
            //血小板计数、(PLT)
            if (null != labMap.get("lab_xcg_plt") && !StringUtils.isBlank(labMap.get("lab_xcg_plt").toString())){
                double plt = Double.parseDouble(labMap.get("lab_xcg_plt").toString());
                if (plt > 300){
                    String wt = "血小板计数增高；\r\n";
                    wtList.add(wt);
                    gjSet.add(24);
                    mbSet.add(20);
                }else if (plt < 100){
                    String wt = "血小板计数减低；\r\n";
                    wtList.add(wt);
                    gjSet.add(25);
                    mbSet.add(21);
                }
            }
            //血红蛋白浓度、(HGB)
            if (null != labMap.get("lab_xcg_hgb") && !StringUtils.isBlank(labMap.get("lab_xcg_hgb").toString())){
                double hgb = Double.parseDouble(labMap.get("lab_xcg_hgb").toString());
                if ("1".equals(sexStr)){
                    if (hgb > 160){
                        String wt = "血红蛋白浓度增高；\r\n";
                        wtList.add(wt);
                        gjSet.add(26);
                        mbSet.add(22);
                    }else if (hgb <120){
                        String wt = "血红蛋白浓度减低；\r\n";
                        wtList.add(wt);
                        gjSet.add(27);
                        mbSet.add(24);
                    }
                }else if ("2".equals(sexStr)){
                    if (hgb > 150){
                        String wt = "血红蛋白浓度增高；\r\n";
                        wtList.add(wt);
                        gjSet.add(26);
                        mbSet.add(23);
                    }else if (hgb <110){
                        String wt = "血红蛋白浓度减低；\r\n";
                        wtList.add(wt);
                        gjSet.add(27);
                        mbSet.add(25);
                    }
                }
            }
            //总胆固醇(CHO)
            if (null != labMap.get("tc") && !StringUtils.isBlank(labMap.get("tc").toString())){
                double tc = Double.parseDouble(labMap.get("tc").toString());
                double highTc = Double.parseDouble(range.getHighTCholesterol());  //总胆固醇上限
                if(tc > highTc){
                    String wt = "总胆固醇升高；\r\n";
                    wtList.add(wt);
                    gjSet.add(28);
                    mbSet.add(26);
                }
            }
            //甘油三酯（TG）
            if (null != labMap.get("tg") && !StringUtils.isBlank(labMap.get("tg").toString())){
                double tg = Double.parseDouble(labMap.get("tg").toString());
                double highTg = Double.parseDouble(range.getHighTriglyceride());  //甘油三酯上限
                if(tg > highTg){
                    String wt = "甘油三酯升高；\r\n";
                    wtList.add(wt);
                    gjSet.add(29);
                    mbSet.add(27);
                }
            }
            //低密度脂蛋白(LDL)
            if (null != labMap.get("ldl") && !StringUtils.isBlank(labMap.get("ldl").toString())){
                double ldl = Double.parseDouble(labMap.get("ldl").toString());
                double highLdl = Double.parseDouble(range.getHighLDLCholesterol()); //低密度脂蛋上限
                if(ldl >highLdl){
                    String wt = "低密度脂蛋白升高；\r\n";
                    wtList.add(wt);
                    gjSet.add(31);
                    mbSet.add(28);
                }
            }
            //高密度脂蛋白(HDL)
            if (null != labMap.get("hdl") && !StringUtils.isBlank(labMap.get("hdl").toString())){
                double hdl = Double.parseDouble(labMap.get("hdl").toString());
                double lowHdl = Double.parseDouble(range.getLowHDLCholesterol());//高密度脂蛋白下限
                if(hdl < lowHdl){
                    String wt = "高密度脂蛋白降低；\r\n";
                    wtList.add(wt);
                    gjSet.add(32);
                    mbSet.add(29);
                }
            }
            //白蛋白(ALB）
            if (null != labMap.get("uae") && !StringUtils.isBlank(labMap.get("uae").toString())){
                double uae = Double.parseDouble(labMap.get("uae").toString());
                if (uae < 34){
                    String wt = "白蛋白降低；\r\n";
                    wtList.add(wt);
                    gjSet.add(33);
                    mbSet.add(30);
                }else if (uae > 54){
                    String wt = "白蛋白增高；\r\n";
                    wtList.add(wt);
                    gjSet.add(34);
                    mbSet.add(30);
                }
            }
            //24小时尿蛋白定量
            if (null != labMap.get("day_uae") && !StringUtils.isBlank(labMap.get("day_uae").toString())){
                double dayUae = Double.parseDouble(labMap.get("day_uae").toString());
                if (dayUae > 150){
                    String wt = "24小时尿蛋白定量增高；\r\n";
                    wtList.add(wt);
                    gjSet.add(36);
                    mbSet.add(33);
                }
            }
            //尿素氮(BUN)
            if (null != labMap.get("bun") && !StringUtils.isBlank(labMap.get("bun").toString())){
                double bun = Double.parseDouble(labMap.get("bun").toString());
                if (bun > 7.1){
                    String wt = "尿素氮增高；\r\n";
                    wtList.add(wt);
                    gjSet.add(37);
                    mbSet.add(34);
                }
            }
            //肌酐
            if (null != labMap.get("cr") && !StringUtils.isBlank(labMap.get("cr").toString())){
                double cr = Double.parseDouble(labMap.get("cr").toString());
                if ("1".equals(sexStr)){  //男
                    if (cr > 106){
                        String wt = "肌酐增高；\r\n";
                        wtList.add(wt);
                        gjSet.add(38);
                        mbSet.add(35);
                    }
                }else if ("2".equals(sexStr)){  //女
                    if (cr > 97){
                        String wt = "肌酐增高；\r\n";
                        wtList.add(wt);
                        gjSet.add(38);
                        mbSet.add(36);
                    }
                }
            }
            //尿糖
            if (null != labMap.get("nt") && !StringUtils.isBlank(labMap.get("nt").toString())){
                String labNt = labMap.get("nt").toString();
                if ("2".equals(labNt)){
                    String wt = "尿糖异常；\r\n";
                    wtList.add(wt);
                    gjSet.add(39);
                    mbSet.add(37);
                }
            }
            //尿白细胞
            if (null != labMap.get("lab_nbdb") && !StringUtils.isBlank(labMap.get("lab_nbdb").toString())){
                String labNbdb = labMap.get("lab_nbdb").toString();
                if ("2".equals(labNbdb)){
                    String wt = "尿白细胞异常；\r\n";
                    wtList.add(wt);
                    gjSet.add(40);
                    mbSet.add(38);
                }
            }

            //尿酮  1: 阴极 2 :阳极
            if (null != labMap.get("tt") && !StringUtils.isBlank(labMap.get("tt").toString())){
                String tt = labMap.get("tt").toString();
                if ("2".equals(tt)){
                    String wt = "尿酮异常；\r\n";
                    wtList.add(wt);
                    gjSet.add(42);
                    mbSet.add(40);
                }
            }
            //胰岛素分泌功能
            //sex 为null  普通日常随访   sex不为null华西随访
            if (null == sex){
                if (null != labMap.get("lab_yds") && !StringUtils.isBlank(labMap.get("lab_yds").toString())){
                    gjSet.add(46);
                    mbSet.add(42);
                }
            }else{
                if ((null != labMap.get("yds_0") && !StringUtils.isBlank(labMap.get("yds_0").toString())) || (null != labMap.get("yds_2") && !StringUtils.isBlank(labMap.get("yds_2").toString()))){
                    gjSet.add(46);
                    mbSet.add(42);
                }
            }
            //C肽释放试验
            if (null == sex){
                if (null != labMap.get("lab_ct") && !StringUtils.isBlank(labMap.get("lab_ct").toString())){
                    gjSet.add(46);
                    mbSet.add(43);
                }
            }else{
                if ((null != labMap.get("lab_yds") && !StringUtils.isBlank( labMap.get("lab_yds").toString())) || (null != labMap.get("ctyds_2") && !StringUtils.isBlank(labMap.get("ctyds_2").toString()))){
                    gjSet.add(46);
                    mbSet.add(43);
                }
            }



        }

        if (null != followBodyMap.get("history") && !StringUtils.isBlank(followBodyMap.get("history").toString())){
            Map<String, Object> historyMap = JsonSerializer.jsonToMap(followBodyMap.get("history").toString());
            //吸烟
            if (null != historyMap.get("bs_smoke") && !StringUtils.isBlank(historyMap.get("bs_smoke").toString())){
                String bsSmoke = historyMap.get("bs_smoke").toString();
                //sex 为null  普通日常随访   sex不为null华西随访
                if (("1".equals(bsSmoke)) || (null != sex && !"0".equals(bsSmoke)) && !"4".equals(bsSmoke)){
                    String wt = "有吸烟不良习惯；\r\n";
                    wtList.add(wt);
                    gjSet.add(18);
                    mbSet.add(12);
                }else if (null != sex && "4".equals(bsSmoke)){
                    String wt = "您现在不抽烟了，这很好，但请确保您不要再次吸烟；\r\n";
                    wtList.add(wt);
                    gjSet.add(51);
                    mbSet.add(45);
                }
            }
            //饮酒
            if (null != historyMap.get("drink_rate") && !StringUtils.isBlank(historyMap.get("drink_rate").toString())){
                String drinkRate = historyMap.get("drink_rate").toString();
                if ("YJQK02".equals(drinkRate)){
                    String wt = "有喝酒不良习惯；\r\n";
                    wtList.add(wt);
                    gjSet.add(19);
                    mbSet.add(13);
                }
            }
        }

        if (null != followBodyMap.get("complication") && !StringUtils.isBlank(followBodyMap.get("complication").toString())){
            Map<String, Object> complicationMap = JsonSerializer.jsonToMap(followBodyMap.get("complication").toString());
            //尿微量白蛋白/肌酐比值（uACR）
            if (null != complicationMap.get("neph_acr") && !StringUtils.isBlank(complicationMap.get("neph_acr").toString())){
                double nephAcr = Double.parseDouble(complicationMap.get("neph_acr").toString());
                if ("1".equals(sexStr)){   //男
                    if (nephAcr >= 22){
                        String wt = "尿微量白蛋白/肌酐比值增高；\r\n";
                        wtList.add(wt);
                        gjSet.add(35);
                        mbSet.add(31);
                    }
                }else if ("2".equals(sexStr)){  //女
                    if (nephAcr >= 31){
                        String wt = "尿微量白蛋白/肌酐比值增高；\r\n";
                        wtList.add(wt);
                        gjSet.add(35);
                        mbSet.add(32);
                    }
                }
            }
            //尿蛋白
            if (null != complicationMap.get("neph_pro_value") && !StringUtils.isBlank(complicationMap.get("neph_pro_value").toString())){
                String nephProValue = complicationMap.get("neph_pro_value").toString();
                if ("2".equals(nephProValue)){
                    String wt = "尿蛋白异常；\r\n";
                    wtList.add(wt);
                    gjSet.add(41);
                    mbSet.add(39);
                }
            }
            //急性并发症
            String ywjxbfz = ""; //有无发生急性并发症 LX00: 有 LX01 : 无
            if (null != complicationMap.get("jyntnbjxbfz") && !StringUtils.isBlank(complicationMap.get("jyntnbjxbfz").toString())){
                ywjxbfz = complicationMap.get("jyntnbjxbfz").toString();
            }
            if (null != complicationMap.get("jyntnbjxbfz2") && !StringUtils.isBlank(complicationMap.get("jyntnbjxbfz2").toString())){
                String jxbfzlx = complicationMap.get("jyntnbjxbfz2").toString();
                if ("LX00".equals(ywjxbfz)){
                    //急性并发症-酮症酸中毒
                    if (jxbfzlx.contains("LX02")){
                        String wt = "近1年发生过酮症酸中毒；\r\n";
                        wtList.add(wt);
                        gjSet.add(47);
                        mbSet.add(44);
                    }
                    //急性并发症-低血糖
                    if (jxbfzlx.contains("LX03")){
                        String wt = "近1年发生过低血糖；\r\n";
                        wtList.add(wt);
                        gjSet.add(48);
                        mbSet.add(44);
                    }
                    //急性并发症-高渗性昏迷
                    if (jxbfzlx.contains("LX04")){
                        String wt = "近1年发生过高渗性昏迷；\r\n";
                        wtList.add(wt);
                        gjSet.add(49);
                        mbSet.add(44);
                    }
                    //急性并发症-乳酸性酸中毒
                    if (jxbfzlx.contains("LX05")){
                        String wt = "近1年发生过乳酸性酸中毒；\r\n";
                        wtList.add(wt);
                        gjSet.add(50);
                        mbSet.add(44);
                    }
                }
            }

        }

        //去重//////////////////////////
        Map<Integer, String> gjMapping = getGjMapping();
        for (Integer code : gjSet) {
            gjList.add(gjMapping.get(code));
        }
        Map<Integer, String> mbMapping = getMbMapping(range);
        for (Integer code : mbSet) {
            mbList.add(mbMapping.get(code));
        }

        reMap.put("wtList", wtList);
        reMap.put("gjList", gjList);
        reMap.put("mbList", mbList);
        return  reMap;
    }

    /**
     * 主要改进措施文案
     * @param range  控制目标
     * @return
     */
    private static Map<Integer,String> getGjMapping(){
        Map<Integer, String> outMap = new HashMap<>();
        //血压
        outMap.put(1,"您的血压偏高，建议内科就诊明确诊断。平时请用低盐饮食，适当运动，戒烟戒酒，避免情绪紧张，激动等，使血压保持在正常水平；\r\n");
        outMap.put(2,"您的血压偏低，建议专科诊治，日常适当增加体育运动，增强体质、监测血压变化，平时下蹲位时不宜猛起身，防止发生体位性低血压而造成外伤；\r\n");
        //心率,腰臀比
        outMap.put(3,"心率偏慢，建议专科诊治，进一步检查明确心动过缓病因；\r\n");
        outMap.put(4,"心率偏快，建议专科诊治，进一步检查明确心动过速病因；\r\n");
        outMap.put(5,"腰臀围超范围，建议积极配合专业人员进行个性化的调控，适当减重，控制腰围、腰臀比在合理范围内；\r\n");
        //血糖
        outMap.put(6,"您的空腹血糖偏低，注意预防低血糖，建议及时进餐或尝试适当进食点心。日常多监测血糖来减少低血糖的发生；\r\n");
        outMap.put(7,"您目前的空腹血糖高了哦，空腹血糖高的原因有很多，如：晚餐吃得太晚太多、运动量不够、“苏木杰现象”或是“黎明现象”、睡眠问题等等引起，需要逐一排查，根据具体情况进行适宜的调整；\r\n");
        outMap.put(8,"糖化血红蛋白测试通常可以反映患者近8～12周的血糖平均控制水平。您需加强血糖控制，多注意饮食结构及运动，必要时在医生指导下调整治疗方案；\r\n");
        outMap.put(9,"您餐后血糖偏高了，是否正餐吃得太丰盛了？建议饮食宜清淡，控制总摄入量，每餐主食不宜超过2两，辛辣油腻、煎炸的食品少吃，多吃新鲜蔬菜，另要遵医嘱用药哦；\r\n");
        outMap.put(10,"血糖偏低，注意预防低血糖，建议可以尝试适当加餐，多监测血糖来减少低血糖的发生；\r\n");
        outMap.put(11,"您的睡前血糖偏低，注意预防低血糖，建议及时进餐或尝试适当进食点心。日常多监测血糖来减少低血糖的发生；\r\n");
        outMap.put(12,"您睡前血糖偏高，建议适当加强晚餐的饮食控制、餐后的科学运动，看能否将血糖控制得更好一点，也有利用次日空腹血糖的控制；\r\n");
        outMap.put(13,"你的夜间血糖偏高，建议结合睡前血糖一起分析，明确病因，然后进行针对性处理；\r\n");
        outMap.put(14,"你的夜间血糖偏低，建议可以适当吃些点心、并加强监测血糖来预防低血糖的发生；\r\n");
        //BMI,烟,酒
        outMap.put(15,"您的体重偏轻，建议适当加强营养，平衡膳食，增强体质；\r\n");
        outMap.put(16,"您的体重已属于超重范围，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例。坚持合理健身运动，控制体重；\r\n");
        outMap.put(17,"您的体重已属于肥胖范围，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例。坚持合理健身运动，控制体重；\r\n");
        outMap.put(18,"您有日常吸烟习惯，吸烟对人体有百害而无一利，可引起呼吸系统、心血管系统等多种疾病，而对糖尿病人的危害就更大，可以加重糖代谢紊乱，增加糖尿病慢性并发症的发生率。所以对糖尿病来说更是需要绝对禁烟；\r\n");
        outMap.put(19,"您日常有饮酒习惯，关于糖尿病人饮酒，一般是不推荐。若饮酒应计算酒精中所含的热量在每日的饮食总热量中进行扣除。女性每天饮酒的酒精量不超过15g，男性不超过25g(15g酒精相当于450毫升啤酒、150毫升葡萄酒或50毫升低度白酒)，每周不超过2次。另外要注意避免空腹饮酒，预防酒精诱发的低血糖；\r\n");
        outMap.put(51,"很多人之所以无法戒烟是因为他们在戒烟后又忍不住再次吸烟。所以如果您很想吸烟，请克制住，不要因为一次吸烟而放弃了您之前为戒烟所做的努力；\r\n");
        //血常规
        outMap.put(20,"白细胞计数增高一般主要见于细菌性感染、组织损伤等，另外也可以见于剧烈运动、恐惧、孕期等情况，建议就医诊治，明确病因，一般炎症引起的，经治疗好转后白细胞数可下降；\r\n");
        outMap.put(21,"白细胞计数减低一般主要见于病毒感染，也可见于其它情况，比如脾功能亢进、自身免疫疾病等，建议就医诊治，明确病因后，进行针对性的治疗；\r\n");
        outMap.put(22,"红细胞计数增高一般见于多种原因引起的脱水，另外也可见于饮酒后，及某些缺氧性疾病，建议就医诊治，明确病因后，在进行针对性的治疗；\r\n");
        outMap.put(23,"红细胞计数减低一般多见于造血物质缺乏引起的贫血，也可见于急慢性失血等等，建议日常可以多样化的食物，注意保持营养均衡的摄入，遵医嘱定期复查观察变化；\r\n");
        outMap.put(24,"血小板计数增高，建议请临床医生结合临床综合评估，并定期复查观察变化；\r\n");
        outMap.put(25,"血小板计数减低，建议请临床医生结合临床综合评估，明确病因后进行针对性的处理，并定期复查观察变化；\r\n");
        outMap.put(26,"血红蛋白浓度增高，可见于脱水、饮酒、长期抽烟、高血压等情况，建议请临床医生评估分析，定期复查观察变化；\r\n");
        outMap.put(27,"血红蛋白量减低的程度一般反映贫血的程度，建议请临床医生评估分析具体病因 ，并定期复查观察变化；\r\n");
        //血脂
        outMap.put(28,"建议控制膳食总热量，减少饱和脂肪酸和胆固醇的摄入，提高饮食中膳食纤维比例，坚持规律运动，定期复查，如仍异常，请在专科医生指导下服用调脂药物；\r\n");
        outMap.put(29,"甘油三酯的含量可随膳食的改变而改变。平时请控制膳食总热量，用低脂、少糖、高纤维素膳食，坚持运动；\r\n");
//        outMap.put(30,"调整饮食控制膳食总热量，减少饱和脂肪酸和胆固醇的摄入。坚持有规律的体力活动，建议定期复查，必要时在医生指导下服用调脂药；\r\n");
        outMap.put(31,"建议认真调整饮食控制膳食总热量，减少饱和脂肪酸和胆固醇的摄入。坚持规律运动，定期复查，必要时在医生指导下配合服用调脂药，将血脂控制在理想范围内；\r\n");
        outMap.put(32,"高密度脂蛋白胆固醇减低是动脉硬化的危险指标，建议关注变化，日常注意控制膳食总热量，劳逸结合，半年复查；\r\n");
        //肝功能
        outMap.put(33,"白蛋白降低，建议到专科就诊，明确病因后进行针对性的治疗，并注意复查；\r\n");
        outMap.put(34,"白蛋白增高，建议适当的补充水分，饮食方面适当减少蛋白的摄入，定期复查观察变化；\r\n");
        //尿常规,肾功能
        outMap.put(35,"尿微量白蛋白/肌酐比值（uACR）是诊断早期糖尿病肾病的一项敏感而可靠的指标，有异常升高，建议及时到专科进行诊治；\r\n");
        outMap.put(36,"24小时尿蛋白定量用于肾脏疾病的诊断和疗效观察。有出现异常升高，需要请专科结合临床进行综合分析，定期复查跟踪观察变化；\r\n");
        outMap.put(37,"尿素氮偏高，可见于剧烈运动和高蛋白饮食、饥饿、急慢性肾衰等。需注意关注肾功能改变，建议到专科进一步诊治；\r\n");
        outMap.put(38,"肌酐偏高，可见于急慢性肾衰。糖尿病患者需注意关注肾功能改变，建议到专科进一步诊治；\r\n");
        outMap.put(39,"血糖浓度的升高、肾脏对血糖的重吸收障碍及肾脏对血糖的过滤功能降低等，都会造成尿糖阳性，建议请医生结合临床进行综合评估处理；\r\n");
        outMap.put(40,"常见于泌尿系统的炎症感染，建议请医生结合临床进行分析处理；\r\n");
        outMap.put(41,"建议复查，因体位、寒冷、剧烈运动、炎症、药物和妊娠等因素可出现一过性生理性或无症状性蛋白尿，如尿蛋白持续存在应专科诊治；\r\n");
        outMap.put(42,"由于多种原因都会造成尿酮体阳性的出现，比如饥饿、腹泻、糖尿病酮症、酮症酸中毒等等，建议请医生结合血糖等情况进行综合评估分析，明确诊断；\r\n");
        //胰岛功能
        outMap.put(43,"空腹血糖受损也是从正常过渡到糖尿病的一个过渡阶段，在这阶段，患者如果注意饮食疗法和运动疗法(也许还可加一些口服降糖药)的话，血糖有可能逐渐变为正常。否则的话，也有可能发展成为糖尿病；\r\n");
        outMap.put(44,"糖耐量减低也是从正常过渡到糖尿病的一个过渡阶段，在这阶段，患者如果注意饮食疗法和运动疗法(也许还可加一些口服降糖药)的话，血糖有可能逐渐变为正常。否则的话，也有可能发展成为糖尿病；\r\n");
        outMap.put(45,"需要及时请临床医生结合临床进行评估诊断，明确是否糖尿病的情况；\r\n");
        outMap.put(46,"胰岛素分泌和C肽测定，可以直接或间接反映自身胰岛素的分泌情况，建议请医生根据空腹、餐后等相关指标的波动变化综合分析明确胰岛β细胞的功能状态，判断糖尿病类型和必要时确定个体化的治疗方案；\r\n");
        //酮症酸中毒
        outMap.put(47,"酮症酸中毒的预防措施：\n" +
                "①、您和家人需要掌握糖尿病的基本知识，提高对糖尿病酮症酸中毒的认识。一旦怀疑本病，应尽早就诊检查。②、日常应遵医嘱用药，不可自行随意减药、换药、停药。③、加强血糖监测，在合并应激情况如各种感染、严重呕吐、腹泻、精神创伤、手术等等，需要密切关注血糖变化。④、控制诱发糖尿病的因素，如防止饥饿，预防脱水。⑤、保持良好的情绪；\r\n");
        //低血糖
        outMap.put(48,"低血糖的预防措施：\n" +
                "①、定时定量进餐，如果进餐量减少则相对减少降糖药物剂量，有可能误餐时应提前做好准备。②、药物的使用，应该从小剂量开始，逐渐增加剂量，谨慎调整剂量；运动前应该增加额外的碳水化合物摄入。③、不要空腹饮酒，酒精能直接导致低血糖，应避免醺酒和空腹饮酒。④、严重、反复的低血糖应该调整糖尿病的治疗方案，并适当调整血糖控制目标。⑤、常规随身备用碳水化合物类食品，一旦发生低血糖，立即食用；\r\n");
        //高渗性昏迷
        outMap.put(49,"高渗性昏迷的预防措施：\n" +
                "①、您和家人需要掌握糖尿病的基本知识，提高对非酮症高渗性昏迷的认识。一旦怀疑本病，应尽早就诊检查。②、自我监测血糖，保持良好的血糖控制状态。③、保证充足的水分摄入，鼓励主动饮水，特别是口渴阈值升高的老人。④、预防各种因素引起脱水的情况发生，比如呕吐、腹泻、烧伤、感染等疾病。⑤、保持良好的情绪；\r\n");
        //乳酸性酸中毒
        outMap.put(50,"乳酸性酸中毒的预防措施：\n" +
                "①、肝肾功能不全、慢性缺氧性心肺疾病患者，食欲不佳，一般情况差的患者需忌用双胍类降糖药。②、尽量选择乳酸性酸中毒发生率低的二甲双胍。③、使用双胍类药物在遇到急性危重疾病时，应暂停本药，改用胰岛素治疗。④、使用双胍类药物，需要定期检查肝肾及心肺功能；\r\n");
        return outMap;
    }

    /**
     * 预期达到目标文案
     * @param range  控制目标
     * @return
     */
    private static Map<Integer, String> getMbMapping(RangeBO range) {
        Map<Integer, String> outMap = new HashMap<>();
        //血压
        outMap.put(1,"控制血压，直至舒张压："+range.getLowDiastolicPress()+"～"+range.getHighDiastolicPress()+"mmHg，收缩压： "+range.getLowSystolicPress()+"～"+range.getHighSystolicPress()+"mmHg；\r\n");
        //心率,腰臀比
        outMap.put(2,"安静状态下心率保持在60-100 次/分钟；\r\n");
        outMap.put(3,"腰臀比＜0.90；\r\n");  //男
        outMap.put(4,"腰臀比＜0.85；\r\n");  //女
        //血糖
        outMap.put(5,"控制空腹血糖，达到"+range.getLowBeforeBreakfast()+"-"+range.getHighBeforeBreakfast()+"mmol/L；\r\n");
        outMap.put(6,"控制糖化血红蛋白，≤"+range.getHighGlycosylatedVal()+"%；\r\n");
        outMap.put(7,"控制餐后2小时血糖，达到"+range.getLowAfterMeal()+"-"+range.getHighAfterMeal()+"mmol/L；\r\n");
        outMap.put(8,"控制睡前血糖，达到"+range.getLowBeforeSleep()+"-"+range.getHighBeforeSleep()+"mmol/L；\r\n");
        outMap.put(9,"控制夜间血糖，达到"+range.getLowBeforedawn()+"-"+range.getHighBeforedawn()+"mmol/L；\r\n");
        //BMI,烟,酒
        outMap.put(10,"增加体重，直至BMI控制在"+range.getLowBmi()+"～"+range.getHighBmi()+"之间；\r\n");
        outMap.put(11,"减轻体重，直至BMI控制在"+range.getLowBmi()+"～"+range.getHighBmi()+"之间；\r\n");
        outMap.put(12,"戒烟；\r\n");
        outMap.put(13,"限酒；\r\n");
        outMap.put(45,"请继续保持不抽烟的生活习惯；\r\n");
        //血常规
        outMap.put(14,"白细胞计数降到10×10^9/L以下；\r\n");
        outMap.put(15,"白细胞计数升到4×10^9/L以上；\r\n");
        outMap.put(16,"红细胞计数降到5.5×10^12/L以下；\r\n");  //男
        outMap.put(17,"红细胞计数降到 5.0×10^12/L以下；\r\n");  //女
        outMap.put(18,"红细胞计数升到4.0×10^12/L以上；\r\n");  //男
        outMap.put(19,"红细胞计数升到 3.5×10^12/L以上；\r\n");  //女
        outMap.put(20,"血小板计数降到300×10^9/L以下；\r\n");
        outMap.put(21,"血小板计数升到100×10^9/L以上；\r\n");
        outMap.put(22,"血红蛋白浓度降到160g/L以下；\r\n");
        outMap.put(23,"血红蛋白浓度降到150g/L以下；\r\n");
        outMap.put(24,"血红蛋白浓度升到120g/L以上；\r\n");
        outMap.put(25,"血红蛋白浓度升到110g/L以上；\r\n");
        //血脂
        outMap.put(26,"控制胆固醇，≤"+range.getHighTCholesterol()+"mmol/L；\r\n");
        outMap.put(27,"控制甘油三酯，≤"+range.getHighTriglyceride()+"mmol/L；\r\n");
        outMap.put(28,"控制低密度脂蛋白≤"+range.getHighLDLCholesterol()+"mmol/L；\r\n");
        outMap.put(29,"控制高密度脂蛋白≥"+range.getLowHDLCholesterol()+"mmol/L；\r\n");
        //肝功能
        outMap.put(30,"控制白蛋白，达到 34-54 g/L；\r\n");
        //尿常规,肾功能
        outMap.put(31,"控制uACR＜22mg/g；\r\n");
        outMap.put(32,"控制uACR＜31mg/g；\r\n");
        outMap.put(33,"24小时尿蛋白定量：控制＜150mg/d；\r\n");
        outMap.put(34,"控制尿素氮≤7.1mmol/L；\r\n");
        outMap.put(35,"控制肌酐，达到54-106μmoI/L；\r\n");
        outMap.put(36,"控制肌酐，达到44-97μmol/L；\r\n");
        outMap.put(37,"控制尿糖至阴性（-）；\r\n");
        outMap.put(38,"控制尿白细胞至阴性（-）；\r\n");
        outMap.put(39,"控制尿蛋白至阴性（-）；\r\n");
        outMap.put(40,"控制尿酮至阴性（-）；\r\n");
        //胰岛功能
        outMap.put(41,"控制空腹血糖  ＜6.1 mmol/L ，餐后2 h 葡萄糖<7.8mmol/L；\r\n");
        outMap.put(42,"胰岛素分泌功能空腹值:4~12 μU/ml；\r\n");
        outMap.put(43,"C肽空腹值：0.9~3.9 ng/ml；\r\n");
        //急性并发症---酮症酸中毒  ,低血糖,高渗性昏迷
        outMap.put(44,"加强自我管理，结合饮食，运动，药物控制，配合监测血糖，及时了解病情，避免发生急性并发症；\r\n");
        return outMap;
    }

    public static void main(String[] args) {
        String a = "102";
        long round = Math.round(Double.parseDouble(a));
        System.out.println(round);
    }
}
