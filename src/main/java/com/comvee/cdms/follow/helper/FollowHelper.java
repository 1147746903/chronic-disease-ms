package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.member.bo.RangeBO;

import java.util.*;

/**
 * Created by Wxy on 2018/11/7.
 */
public class FollowHelper {

    //首诊总结（存在问题分析逻辑、改进措施逻辑、预期达到目标逻辑）
    public static Map<String,Object> outFirstFollow(Map<String, Object> followBodyMap,RangeBO range){
        Map<String,Object> reMap=new HashMap<>();

        List<String> wtList=new ArrayList<>();//问题集合
        List<String> gjList=new ArrayList<>();//改进集合
        List<String> mbList=new ArrayList<>();//目标集合

        Set<Integer> gjSet = new LinkedHashSet<>();//改进去重
        Set<Integer> mbSet = new LinkedHashSet<>();//目标去重


        String basic = followBodyMap.get("basic").toString();
        String sign = followBodyMap.get("sign").toString();
        String treatment = followBodyMap.get("treatment").toString();
        String sugar = followBodyMap.get("sugar").toString();
        String hypoglycemia = followBodyMap.get("hypoglycemia").toString();
        String history = followBodyMap.get("history").toString();
        String lab = followBodyMap.get("lab").toString();
        String complication = followBodyMap.get("complication").toString();
        String anamnesis = followBodyMap.get("anamnesis").toString();

        Map<String, Object> basicMap = JsonSerializer.jsonToMap(basic);
        Map<String, Object> signMap = JsonSerializer.jsonToMap(sign);
        Map<String, Object> treatmentMap = JsonSerializer.jsonToMap(treatment);
        Map<String, Object> sugarMap = JsonSerializer.jsonToMap(sugar);
        Map<String, Object> hypoglycemiaMap = JsonSerializer.jsonToMap(hypoglycemia);

        Map<String, Object> historyMap = JsonSerializer.jsonToMap(history);
        Map<String, Object> labMap = JsonSerializer.jsonToMap(lab);
        Map<String, Object> complicationMap = JsonSerializer.jsonToMap(complication);
        Map<String, Object> anamnesisMap = JsonSerializer.jsonToMap(anamnesis);

        //性别 1男 2女
        String sex = basicMap.get("sex").toString();
        String birthday = basicMap.get("birthday").toString();
        int age = 0;//年龄
        if(!StringUtils.isBlank(birthday)){
            age = DateHelper.getAge(birthday);
        }

        //空腹血糖
        if(null!=treatmentMap.get("mq_fbg") && !StringUtils.isBlank(treatmentMap.get("mq_fbg").toString())){
            Double mq_fbg = Double.parseDouble(treatmentMap.get("mq_fbg").toString());
            Double lowKf = Double.parseDouble(range.getLowBeforeBreakfast());  //空腹血糖下限
            Double highKf = Double.parseDouble(range.getHighBeforeBreakfast());  //空腹血糖上限
            if (mq_fbg < lowKf){
                String wt = "您目前的空腹血糖为："+mq_fbg+"mmol/l(血糖偏低)；\r\n";
                wtList.add(wt);
                gjSet.add(1);
                mbSet.add(1);
            }else if (mq_fbg > highKf){
                String wt = "您目前的空腹血糖为："+mq_fbg+"mmol/l(血糖偏高)；\r\n";
                wtList.add(wt);
                gjSet.add(2);
                mbSet.add(2);
            }
        }

        if(null!=treatmentMap.get("blg") && !StringUtils.isBlank(treatmentMap.get("blg").toString())){
            //餐后2小时血糖
            Double blg = Double.parseDouble(treatmentMap.get("blg").toString());
            Double lowCh = Double.parseDouble(range.getLowAfterMeal());   //餐后血糖下限
            Double highCh = Double.parseDouble(range.getHighAfterMeal());   //餐后血糖上限
            if (blg < lowCh){
                String wt = "您目前的餐后2小时血糖值为："+blg+"mmol/L，血糖偏低；\r\n";
                wtList.add(wt);
                gjSet.add(1);
                mbSet.add(1);
            }else if (blg > highCh){
                String wt = "您目前的餐后2小时血糖值为："+blg+"mmol/L，血糖偏高；\r\n";
                wtList.add(wt);
                gjSet.add(4);
                mbSet.add(3);
            }
        }

        if(null!=treatmentMap.get("bsg") && !StringUtils.isBlank(treatmentMap.get("bsg").toString())){
            //睡前血糖
            Double bsg = Double.parseDouble(treatmentMap.get("bsg").toString());
            Double lowSq = Double.parseDouble(range.getLowBeforeSleep()); //睡前血糖下限
            Double highSq = Double.parseDouble(range.getHighBeforeSleep()); //睡前血糖上限
            if (bsg < lowSq){
                String wt = "您目前的睡前血糖值为："+bsg+"mmol/L(血糖偏低)；\r\n";
                wtList.add(wt);
                gjSet.add(1);
                mbSet.add(1);
            }else if (bsg > highSq){
                String wt = "您目前的睡前血糖值为："+bsg+"mmol/L(血糖偏高)；\r\n";
                wtList.add(wt);
                gjSet.add(6);
                mbSet.add(4);
            }
        }

        //糖化血红蛋白
        if(null!=labMap.get("lab_hba") && !StringUtils.isBlank(labMap.get("lab_hba").toString())){
            Double lab_hba = Double.parseDouble(labMap.get("lab_hba").toString());
            Double highTh = Double.parseDouble(range.getHighGlycosylatedVal());  //糖化血红蛋白上限
            if(lab_hba > highTh){
                String wt = "糖化血红蛋白值为："+lab_hba+"%（偏高），近期血糖控制不佳；\r\n";
                wtList.add(wt);
                gjSet.add(8);
                mbSet.add(5);
            }
        }


        //BMI
        if(null!=signMap.get("bmi") && !StringUtils.isBlank(signMap.get("bmi").toString())){
            Double bmi = Double.parseDouble(signMap.get("bmi").toString());
            if(bmi < 18.5){
                String wt = "BMI值为："+bmi+"，处于消瘦状态；\r\n";
                wtList.add(wt);
                gjSet.add(9);
                mbSet.add(6);
            }else if(24 <= bmi && bmi < 28){
                String wt = "BMI值为："+bmi+"，处于超重状态；\r\n";
                wtList.add(wt);
                gjSet.add(10);
                mbSet.add(28);
            }else if(bmi >= 28){
                String wt = "BMI值为："+bmi+"，处于肥胖状态；\r\n";
                wtList.add(wt);
                gjSet.add(11);
                mbSet.add(28);
            }
        }

        //原发性高血压病
        String essential_hyp = "";
        if(null!=anamnesisMap.get("essential_hyp") && !StringUtils.isBlank(anamnesisMap.get("essential_hyp").toString())){
            essential_hyp = anamnesisMap.get("essential_hyp").toString();
        }
        if(!StringUtils.isBlank(signMap.get("sbp").toString()) && !StringUtils.isBlank(signMap.get("dbp").toString())){
            Double sbp = Double.parseDouble(signMap.get("sbp").toString()); //收缩压
            Double dbp = Double.parseDouble(signMap.get("dbp").toString()); //舒张压

            Double lowSbp = Double.parseDouble(range.getLowSystolicPress());  //收缩压下限
            Double highSbp = Double.parseDouble(range.getHighSystolicPress()); //收缩压上限
            Double lowDbp = Double.parseDouble(range.getLowDiastolicPress());  //舒张压下限
            Double highDbp = Double.parseDouble(range.getHighDiastolicPress()); //舒张压上限

            if (dbp > highDbp || sbp > highSbp){
                String wt = "您目前血压（收缩压/舒张压）："+sbp+"/"+ dbp +"mmHg,血压偏高；\r\n";
                wtList.add(wt);
                gjSet.add(12);
                mbSet.add(7);
            }else if (dbp < lowDbp || sbp < lowSbp){
                String wt = "您目前血压（收缩压/舒张压）："+sbp+"/"+ dbp +"mmHg,血压偏低；\r\n";
                wtList.add(wt);
                gjSet.add(13);
                mbSet.add(7);
            }

        }

        if(!StringUtils.isBlank(signMap.get("waistline").toString()) && !StringUtils.isBlank(signMap.get("hipline").toString())
                && !StringUtils.isBlank(signMap.get("whr").toString())){
            //腰围
            Double waistline = Double.parseDouble(signMap.get("waistline").toString());
            //臀围
            Double hipline = Double.parseDouble(signMap.get("hipline").toString());
            //腰臀比
            Double whr = Double.parseDouble(signMap.get("whr").toString());
            if("1".equals(sex)){
                //  男，腰围＞90cm或腰围/臀围（≥0.9） 腰围：xxx,臀围：xxx,腰臀比：XXX,中心型肥胖
                if(waistline > 90 || whr >= 0.9){
                    String wt = "您的腰围为："+waistline+",臀围为："+ hipline +",腰臀比为："+ whr +",处于中心型肥胖状态；\r\n";
                    wtList.add(wt);
                    gjSet.add(16);
                    mbSet.add(10);
                }
            }else if("2".equals(sex)){
                // 女，腰围＞85cm或腰围/臀围（≥0.8）
                if(waistline > 85 || whr >= 0.85){
                    String wt = "您的腰围为："+waistline+",臀围为："+ hipline +",腰臀比为："+ whr +",处于中心型肥胖状态；\r\n";
                    wtList.add(wt);
                    gjSet.add(16);
                    mbSet.add(11);
                }
            }
        }


       //静态心率
        if(null!=signMap.get("tz_heart_rate") && !StringUtils.isBlank(signMap.get("tz_heart_rate").toString())){
            Integer tz_heart_rate = Integer.parseInt(signMap.get("tz_heart_rate").toString());
            if(tz_heart_rate < 60){
                //<60 次/分钟  静息心率：XX次/分钟，心动过缓
                String wt = "您静态心率为："+tz_heart_rate+"次/分钟（心动过缓）；\r\n";
                wtList.add(wt);
                gjSet.add(15);
                mbSet.add(9);
            }else if(tz_heart_rate > 100){
                //＞100 次/分钟
                String wt = "您静态心率为："+tz_heart_rate+"次/分钟（心动过速）；\r\n";
                wtList.add(wt);
                gjSet.add(115);
                mbSet.add(9);
            }
        }


        //冠心病 complication chd  确诊有QZ01 确诊无QZ02
        String chd = "";
        if(null!=complicationMap.get("chd") && !StringUtils.isBlank(complicationMap.get("chd").toString())){
            chd = complicationMap.get("chd").toString();
        }

        //低密度脂蛋白 lab  ldl
        if(null!=labMap.get("ldl") && !StringUtils.isBlank(labMap.get("ldl").toString())){
            Double ldl = Double.parseDouble(labMap.get("ldl").toString());
            Double highLdl = Double.parseDouble(range.getHighLDLCholesterol()); //低密度脂蛋上限
            if(ldl >highLdl){
                String wt = "您低密度脂蛋白值为："+ ldl +"mmol/L（偏高）\r\n";
                wtList.add(wt);
                gjSet.add(20);
                mbSet.add(14);
            }
        }



        //高密度脂蛋白  hdl
        if(null!=labMap.get("hdl") && !StringUtils.isBlank(labMap.get("hdl").toString())){
            Double hdl = Double.parseDouble(labMap.get("hdl").toString());
            Double lowHdl = Double.parseDouble(range.getLowHDLCholesterol());//高密度脂蛋白下限
            if(hdl < lowHdl){
                String wt = "您高密度脂蛋白值为："+ hdl +"mmol/L（偏低）；\r\n";
                wtList.add(wt);
                gjSet.add(21);
                mbSet.add(16);
            }
        }


        //甘油三酯 tg
        if(null!=labMap.get("tg") && !StringUtils.isBlank(labMap.get("tg").toString())){
            Double tg = Double.parseDouble(labMap.get("tg").toString());
            Double highTg = Double.parseDouble(range.getHighTriglyceride());  //甘油三酯上限
            if(tg > highTg){
                //＞1.7mmol/L 甘油三酯：XXmmol/L/,升高
                String wt = "您甘油三酯值为："+tg+"mmol/L（偏高）；\r\n";
                wtList.add(wt);
                gjSet.add(19);
                mbSet.add(13);
            }
        }


        //总胆固醇 tc
        if(null!=labMap.get("tc") && !StringUtils.isBlank(labMap.get("tc").toString())){
            Double tc = Double.parseDouble(labMap.get("tc").toString());
            Double highTc = Double.parseDouble(range.getHighTCholesterol());  //总胆固醇上限
            if(tc > highTc){
                //≥4.5mmol/L 总胆固醇：XXmmol/L/,升高
                String wt = "您总胆固醇值为："+tc+"mmol/L（偏高）；\r\n";
                wtList.add(wt);
                gjSet.add(17);
                mbSet.add(12);
            }
        }


        // 酮症酸中毒 LX02 complication jyntnbjxbfz
        String jyntnbjxbfz ="";
        if(null!=complicationMap.get("jyntnbjxbfz") && !StringUtils.isBlank(complicationMap.get("jyntnbjxbfz").toString())){
            jyntnbjxbfz = complicationMap.get("jyntnbjxbfz").toString();
            if(jyntnbjxbfz.contains("LX02")){
                String wt = "近1年发生过酮症酸中毒；\r\n";
                wtList.add(wt);
                gjSet.add(22);
                mbSet.add(18);
            }
            // 高渗性昏迷
            if(jyntnbjxbfz.contains("LX04")){
                String wt = "近1年发生过高渗性昏迷；\r\n";
                wtList.add(wt);
                gjSet.add(27);
                mbSet.add(18);
            }
            // 乳酸酸中毒
            if(jyntnbjxbfz.contains("LX05")){
                String wt = "近1年发生过乳酸酸中毒；\r\n";
                wtList.add(wt);
                gjSet.add(32);
                mbSet.add(18);
            }
            // 低血糖
            if(jyntnbjxbfz.contains("LX03")){
                String wt = "近1年发生过低血糖；\r\n";
                wtList.add(wt);
                gjSet.add(36);
                mbSet.add(18);
            }
        }


        //血肌酐 lab cr
        if(null!=labMap.get("cr") && !StringUtils.isBlank(labMap.get("cr").toString())){
            Double cr = Double.parseDouble(labMap.get("cr").toString());
            if("1".equals(sex)){
                //男:＞106μmoI/L 肌酐值：XXμmoI/L,增高
                if(cr >106){
                    String wt = "您血肌酐值为："+ cr +"μmoI/L（增高）；\r\n";
                    wtList.add(wt);
                    gjSet.add(43);
                    mbSet.add(20);
                }
            }else if("2".equals(sex)){
                // 女:＞97μmol/L
                if(cr >97){
                    String wt = "您血肌酐值为："+ cr +"μmoI/L（增高）；\r\n";
                    wtList.add(wt);
                    gjSet.add(43);
                    mbSet.add(21);
                }
            }
        }


        //血尿酸 lab_xns
        if(null!=labMap.get("lab_xns") && !StringUtils.isBlank(labMap.get("lab_xns").toString())){
            Double lab_xns = Double.parseDouble(labMap.get("lab_xns").toString());
            if("1".equals(sex)){
                //男:＞416umol/L 尿酸值：umol/L，增高
                if(lab_xns >416){
                    String wt = "您血尿酸值为："+ lab_xns +"umol/L（增高）；\r\n";
                    wtList.add(wt);
                    gjSet.add(44);
                    mbSet.add(22);
                }else if(lab_xns <149){
                    String wt = "您血尿酸值为："+ lab_xns +"umol/L（偏低）；\r\n";
                    wtList.add(wt);
                    gjSet.add(45);
                    mbSet.add(22);
                }
            }else if("2".equals(sex)){
                //女:＞357umol/L
                if(lab_xns >357){
                    String wt = "您血尿酸值为："+ lab_xns +"umol/L（增高）；\r\n";
                    wtList.add(wt);
                    gjSet.add(44);
                    mbSet.add(23);
                }else if(lab_xns <89){
                    String wt = "您血尿酸值为："+ lab_xns +"umol/L（偏低）；\r\n";
                    wtList.add(wt);
                    gjSet.add(45);
                    mbSet.add(23);
                }
            }
        }


        //尿微量白蛋白 lab_nwlbdb
        if(null!=labMap.get("lab_nwlbdb") && !StringUtils.isBlank(labMap.get("lab_nwlbdb").toString())){
            Double lab_nwlbdb = Double.parseDouble(labMap.get("lab_nwlbdb").toString());
            if(lab_nwlbdb >=30){
                //≥20μg/min 尿微量白蛋白：μg/min，增高
                String wt = "您尿微量白蛋白值为："+ lab_nwlbdb +"mg/L（增高）；\r\n";
                wtList.add(wt);
                gjSet.add(46);
                mbSet.add(24);
            }
        }


        //尿素氮 bun
        if(null!=labMap.get("bun") && !StringUtils.isBlank(labMap.get("bun").toString())){
            Double bun = Double.parseDouble(labMap.get("bun").toString());
            if(bun >7.14){
                //＞7.1mmol/L
                String wt = "您尿素氮值为："+ bun +"mmol/L（增高）；\r\n";
                wtList.add(wt);
                gjSet.add(42);
                mbSet.add(19);
            }
        }



        //尿蛋白 complication neph_pro_value
        String neph_pro_value="";
        if(null!=complicationMap.get("neph_pro_value") && !StringUtils.isBlank(complicationMap.get("neph_pro_value").toString())){
            neph_pro_value = complicationMap.get("neph_pro_value").toString();
            if(neph_pro_value.contains("+")){
                //+或++或3+或4+   尿蛋白：XXX,异常
                String wt = "尿蛋白："+ neph_pro_value +",异常；\r\n";
                wtList.add(wt);
                gjSet.add(47);
                mbSet.add(25);
            }
        }



        //谷丙转氨酶 alt
        if(null!=labMap.get("alt") && !StringUtils.isBlank(labMap.get("alt").toString())){
            Double alt = Double.parseDouble(labMap.get("alt").toString());
            if (alt > 40){
                String wt = "您谷丙转氨酶值为："+ alt +"U/L（增高）；\r\n";
                wtList.add(wt);
                gjSet.add(48);
                mbSet.add(26);
            }

        }


        //谷草转氨酶
        if(null!=labMap.get("ast") && !StringUtils.isBlank(labMap.get("ast").toString())){
            Double ast = Double.parseDouble(labMap.get("ast").toString());
            if (ast > 40){
                String wt = "您谷草转氨酶值为："+ ast +"U/L（增高）；\r\n";
                wtList.add(wt);
                gjSet.add(51);
                mbSet.add(27);
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

        //糖尿病并发症/////////////////////////
        String zzStr="";
        // 糖尿病视网膜病变
        String retinal ="";
        if(null!=complicationMap.get("retinal") && !StringUtils.isBlank(complicationMap.get("retinal").toString())){
            retinal = complicationMap.get("retinal").toString();
            if("SWM01".equals(retinal)){
                zzStr+="糖尿病视网膜病变、";
            }
        }


        //糖尿病肾病
        String nephropathy = "";
        if(null!=complicationMap.get("nephropathy") && !StringUtils.isBlank(complicationMap.get("nephropathy").toString())){
            nephropathy = complicationMap.get("nephropathy").toString();
            if("SB01".equals(nephropathy)){
                zzStr+="糖尿病肾病、";
            }
        }


        // 周围神经病变
        String neuropathy ="";
        if(null!=complicationMap.get("neuropathy") && !StringUtils.isBlank(complicationMap.get("neuropathy").toString())){
            neuropathy = complicationMap.get("neuropathy").toString();
            if("ZWSJ01".equals(neuropathy)){
                zzStr+="周围神经病变、";
            }
        }



        // 糖尿病足  complication
        String diabetic_foot ="";
        if(null!=complicationMap.get("diabetic_foot") && !StringUtils.isBlank(complicationMap.get("diabetic_foot").toString())){
            diabetic_foot = complicationMap.get("diabetic_foot").toString();
            if("TNBZ01".equals(diabetic_foot)){
                zzStr+="糖尿病足、";
            }
        }


        // 糖尿病下肢血管病变
        String pad ="";
        if(null!=complicationMap.get("pad") && !StringUtils.isBlank(complicationMap.get("pad").toString())){
            pad = complicationMap.get("pad").toString();
            if("XZXG01".equals(pad)){
                zzStr+="糖尿病下肢血管病变、";
            }
        }


        //自主神经病变  dan
        String dan ="";
        if(null!=complicationMap.get("dan") && !StringUtils.isBlank(complicationMap.get("dan").toString())){
            dan = complicationMap.get("dan").toString();
            if("ZZ01".equals(dan)){
                zzStr+="自主神经病变、";
            }
        }


        // 冠心病 chd
        if("QZ01".equals(chd)){
            zzStr+="冠心病、";
        }

        // 高血压 essential_hyp
        if("1".equals(essential_hyp)){
            zzStr+="高血压、";
        }

        if(!StringUtils.isBlank(zzStr)){
            zzStr = zzStr.substring(0,zzStr.length()-1);
            wtList.add("您患有"+zzStr+"；");
            if(null==labMap.get("lab_hba") || StringUtils.isBlank(labMap.get("lab_hba").toString())){
                String str="您有"+ zzStr +"，观察您未做糖化检查，建议近期复查，" +
                        "评估您近三个月整体的血糖控制水平。目前继续积极配合临床医生进行系统针对性的治疗，严格控制好" +
                        "血糖、血脂、血压，并定期复查，观察病情变化。日常需加强相关糖尿病知识的学习，合理控制饮食、" +
                        "科学运动，根据监测血糖变化来灵活调整，避免血糖剧烈波动等，有助于您整体病情的改善；\r\n";
                gjList.add(str);
            }else{
                String hStr ="";
                if(null!=labMap.get("lab_hba") && !StringUtils.isBlank(labMap.get("lab_hba").toString())){
                    Double lab_hba1 = Double.parseDouble(labMap.get("lab_hba").toString());
                    Double highTh = Double.parseDouble(range.getHighGlycosylatedVal());  //糖化血红蛋白上限
                    if(lab_hba1 > highTh){
                        hStr = "观察您的糖化指标为"+lab_hba1+"%（偏高），提示您近期血糖控制不佳，";
                    }else if(lab_hba1 <= highTh){
                        hStr = "观察您的糖化指标为"+lab_hba1+"% ，提示您近期血糖还好，";
                    }
                }
                String str="您有"+ zzStr +"，"+ hStr +
                        "建议继续积极配合临床医生进行系统针对性的治疗，严格控制好血糖、血脂、血压，并定期复查相关指标，" +
                        "观察病情变化。日常需加强相关糖尿病知识的学习，合理控制饮食、科学运动，根据监测血糖变化来灵活" +
                        "调整，避免血糖剧烈波动等，有助于您整体病情的改善；\r\n";
                gjList.add(str);
            }
        }



        reMap.put("wtList", wtList);
        reMap.put("gjList", gjList);
        reMap.put("mbList", mbList);
        return  reMap;
    }

//    Set<Integer> wtSet = new HashSet<>();
//        wtSet.add(1);
//        wtSet.add(7);
//        wtSet.add(2);
//        wtSet.add(4);
//        wtSet.add(7);
//        for (Integer str : wtSet) {
//        System.out.println(str);
//    }


    //改进输出文案
    private static Map<Integer, String> getGjMapping() {
        Map<Integer, String> outMap=new HashMap<>();
        //血糖
        outMap.put(1,"发生低血糖需及时补充糖果或糖水进行纠正，建议遵从“吃15等15”的原则纠正低血糖。如果出现意识障碍，则需要及时就医处理；\r\n");
        outMap.put(2,"您目前的空腹血糖偏高，需要逐一排查，然后根据具体原因进行适宜的调整；\r\n");
        outMap.put(4,"您的餐后2小时血糖偏高，建议通过加强合理的饮食控制和科学运动，将血糖控制得更好一点；\r\n");
        outMap.put(6,"您的睡前血糖偏高，建议通过加强合理控制晚餐的饮食和餐后的科学运动，看能否将血糖控制得更好一点，以便能更好控制第二天的空腹血糖；\r\n");
        outMap.put(8,"您的糖化偏高，建议加强血糖控制，注意饮食结构及运动，必要时在医生指导下调整治疗方案；\r\n");

        //体重
        outMap.put(9,"您的体重偏轻，建议适当加强营养，平衡膳食，增强体质；\r\n");
        outMap.put(10,"您的体重已属于超重范围，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例，坚持合理健身运动，控制体重；\r\n");
        outMap.put(11,"您的体重已属于肥胖范围，请注意膳食平衡，在控制总热量的前提下，减少脂肪摄入量，增加蔬菜、水果比例，坚持合理健身运动，控制体重；\r\n");
        //血压
        outMap.put(12,"您本次血压超过糖尿病患者控制的理想范围，建议专科复诊，在日常生活中注意限盐、平衡膳食、戒烟限酒、控制体重、坚持科学运动并保持心态平和，有助于您血压的控制；\r\n");
        outMap.put(13,"您目前的血压偏低，建议专科诊治，日常适当增加体育运动，增强体质、监测血压变化，平时下蹲位时不宜猛起身，防止发生体位性低血压而造成外伤；\r\n");
        outMap.put(14,"建议内科复诊，调整用药，同时关注血糖、血脂及动脉硬化防治，控制膳食总热量，用低糖、低脂、低盐、高纤维素饮食，坚持有氧运动，控制体重，戒烟限酒，定期专科复查诊治；\r\n");
        //心率 、腰臀比
        outMap.put(15,"您的心动过缓，建议专科诊治，进一步检查明确心动过缓病因；\r\n");
        outMap.put(115,"您的心动过速，建议专科诊治，进一步检查明确心动过速病因；\r\n");
        outMap.put(16,"您处于中心型肥胖，建议积极配合专业人员进行个性化的调控，适当减重，控制腰围、腰臀比在合理范围内；\r\n");
        //血脂
        outMap.put(17,"您的总胆固醇偏高，建议控制膳食总热量，减少饱和脂肪酸和胆固醇的摄入，提高饮食中膳食纤维比例，坚持规律运动，定期复查，如仍异常，请在专科医生指导下服用调脂药物；\r\n");
        outMap.put(18,"可见于营养不良，甲亢、女性月经期等，建议必要时就医诊治，注意适宜调整饮食结构，酌情复查观察变化；\r\n");
        outMap.put(19,"您的甘油三脂偏高，请控制膳食总热量，注意低脂、少糖、提高饮食中膳食纤维比例，坚持规律运动，定期复查，必要时在医生指导下配合调脂药治疗；\r\n");
        outMap.put(20,"您的低密度脂蛋白偏高，建议调整饮食，控制膳食总热量，减少饱和脂肪酸和胆固醇的摄入。坚持规律运动，定期复查，必要时在医生指导下配合调脂药治疗；\r\n");
        outMap.put(21,"您的高密度脂蛋白偏低，建议关注变化，日常注意控制膳食总热量，劳逸结合，半年复查；\r\n");
        //糖尿病酮症、酮症酸中毒
        outMap.put(22,"您近一年有酮症酸中毒发生，建议您和家人多掌握尿病的基本知识，提高对糖尿病酮症酸中毒的认识。一旦怀疑本病，应尽早就诊检查，同时日常遵循医嘱用药，不可自行随意减药、换药、停药，加强血糖监测，控制诱发糖尿病因素，保持良好情绪。将有助于预防本病的发生；\r\n");
        //高渗性昏迷
        outMap.put(27,"您近一年有高渗性昏迷发生，建议您和家人需要掌握尿病的基本知识，提高对高渗性昏迷的认识。一旦怀疑本病，应尽早就诊检查，同时日常加强血糖监测，控制好血糖，保证充足水份摄入，预防各种因素引起的脱水情况发生，如呕吐、腹泻等，并保持良好情绪。将有助于预防本病的发生；\r\n");
        //乳酸性酸中毒
        outMap.put(32,"您近一年有乳酸中毒发生，建议用药时尽量选择乳酸性酸中毒发生率低的二甲双胍，肝肾功能不全、慢性缺氧性心肺疾病患者，食欲不佳，一般情况差的患者需忌用双胍类降糖药，在遇到急性危重疾病时，应暂停双胍类药物，改用胰岛素治疗，使用双胍类药物的同时，需要定期检查肝肾及心肺功能。这将有助于预防本病的发生；\r\n");
        //低血糖
        outMap.put(36,"你近一年有低血糖发生，建议遵循医护人员的指导，定时定量进餐，谨慎药物使用，不空腹饮酒，并随身备碳水化合物类食品，一旦发生低血糖立即使用；\r\n");
        //肾功能、尿常规
        outMap.put(42,"您的尿素氮增高，可见于剧烈运动和高蛋白饮食、饥饿、急慢性肾衰等。需注意关注肾功能改变，建议到专科进一步诊治；\r\n");
        outMap.put(43,"您的血肌酐增高，可见于急慢性肾衰。糖尿病患者需注意关注肾功能改变，建议到专科进一步诊治；\r\n");
        outMap.put(44,"您的血尿酸增高，建议日常注意低嘌呤膳食，如减少动物内脏、沙丁鱼、虾皮、浓肉汤、啤酒及肉类等含有高嘌呤食物；\r\n");
        outMap.put(45,"您的血尿酸偏低，如降低明显可以请专科结合临床进行综合分析，定期复查观察变化；\r\n");
        outMap.put(46,"您的尿微量白蛋白异常增高，可见于糖尿病肾病、高血压、妊娠子痫前期，也可见于高龄、受凉、发热、运动后及标本污染等情况，建议专科进行综合分析；\r\n");
        outMap.put(47,"建议复查，因体位、寒冷、剧烈运动、炎症、药物和妊娠等因素可出现一过性生理性或无症状性蛋白尿，如尿蛋白持续存在应专科诊治；\r\n");
        //肝功能
        outMap.put(48,"您的谷丙转氨酶增高，建议调整作息，避免伤肝药物，建议三个月内复查；\r\n");
        outMap.put(51,"您的谷草转氨酶偏高，说明肝功能异常，建议定期复查观察变化，若出现如恶心、乏力、肝区疼痛等等，请专科诊治；\r\n");


        return outMap;
    }

    //目标输出文案
    private static Map<Integer, String> getMbMapping(RangeBO range) {
        Map<Integer, String> outMap=new HashMap<>();
        //血糖
        outMap.put(1,"学会低血糖的防治，平稳控糖，减少低血糖的出现；\r\n");
        outMap.put(2,"控制空腹血糖，达到"+range.getLowBeforeBreakfast()+"～"+range.getHighBeforeBreakfast()+"mmol/L；\r\n");
        outMap.put(3,"控制餐后血糖，达到"+range.getLowAfterMeal()+"～"+range.getHighAfterMeal()+"mmol/L；\r\n");
        outMap.put(4,"控制睡前血糖，达到"+range.getLowBeforeSleep()+"～"+range.getHighBeforeSleep()+"mmol/L；\r\n");
        outMap.put(5,"控制糖化血红蛋白 ≤"+range.getHighGlycosylatedVal()+"%；\r\n");
        //体重
        outMap.put(6,"增加体重，直至BMI控制在"+range.getLowBmi()+"～"+range.getHighBmi()+"之间；\r\n");
        //血压
        outMap.put(7,"控制血压，直至 舒张压："+range.getLowDiastolicPress()+"～"+range.getHighDiastolicPress()+"mmHg， 收缩压："+range.getLowSystolicPress()+"～"+range.getHighSystolicPress()+"mmHg；\r\n");
        //心率 、腰臀比
        outMap.put(9,"安静状态下心率保持在：60～100 次/分钟；\r\n");
        outMap.put(10,"控制腰围<90cm，腰臀比＜0.9；\r\n");//男
        outMap.put(11,"控制腰围<85cm，腰臀比＜0.85；\r\n");//女
        //血脂
        outMap.put(12,"控制胆固醇≤ "+range.getHighTCholesterol()+"mmol/L；\r\n");
        outMap.put(13,"控制甘油三酯 ≤"+range.getHighTriglyceride()+"mmol/L；\r\n");
        outMap.put(14,"控制低密度脂蛋白 ≤"+range.getHighLDLCholesterol()+"mmol/L；\r\n");
        outMap.put(16,"控制高密度脂蛋白 ≥ "+range.getLowHDLCholesterol()+"mmol/L；\r\n");//男
        //糖尿病酮症、酮症酸中毒  高渗性昏迷  乳酸性酸中毒 低血糖
        outMap.put(18,"加强自我管理，结合饮食，运动，药物控制，配合监测血糖，及时了解病情，避免发生急性并发症；\r\n");
        //肾功能、尿常规
        outMap.put(19,"控制尿素氮≤7.14mmol/L；\r\n");
        outMap.put(20,"控制肌酐，达到 54～106 μmoI/L；\r\n");//男
        outMap.put(21,"控制肌酐，达到 44～97 μmol/L；\r\n");//女
        outMap.put(22,"控制尿酸，达到 149～416umol/L；\r\n");//男
        outMap.put(23,"控制尿酸，达到 89～357umol/L；\r\n");//女
        outMap.put(24,"控制尿微量白蛋白 ＜30mg/L；\r\n");
        outMap.put(25,"控制尿蛋白至阴性（-）；\r\n");
        //肝功能
        outMap.put(26,"控制谷丙转氨酶，达到 0～40u/L；\r\n");
        outMap.put(27,"控制谷草转氨酶，达到 0～40u/L；\r\n");
        //体重
        outMap.put(28,"减轻体重，直至BMI控制在18.5～23.9之间；\r\n");

        return outMap;
    }


}
