/**
 * @File name: DoctorPlanOps.java 
 * @Create on:  2017-1-20
 * @Author   :  zhengsw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.knowledge.model;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;

import java.util.*;

/**
 * @File name: DoctorPlanOps.java 
 * @Create on:  2017-1-20
 * @Author   :  zhengsw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
 **/
public class DoctorPlanOps {
//    private static final  Logger LOGGER = LoggerFactory.getLogger(DoctorPlanOps.class);
    
    public final static String  TAG_LIKE_FOOD="糖尿病科学饮食与饮食交换份法";
    public final static String  TAG_LIKE_SPORT="正确的运动方法与相关注意事项";
    public final static String  TAG_LIKE_MEDICINE="合理用药知识";
    public final static String  TAG_TNB_KNOWLEDGE="糖尿病并发症知识";
    public final static String  TAG_LIKE_WEIGHT="科学减重";
    public final static String  TAG_LIKE_SMBG="合理正确的自我血糖监测";
    public final static String  TAG_LIKE_LOWBLOOD="低血糖的发现、自救与预防";
    public final static String  TAG_MEDICINE_KF="合理应用口服药知识";
    public final static String  TAG_MEDICINE_YDS="正确注射胰岛素知识";
    public final static String  TAG_COMPLICATION="并发症的防治知识";

    public final static String SB01 = "SB01";
    public final static String SB02 = "SB02";
    public final static String SB03 = "SB03";
    
//    {
//        大档案字段：
//        archives:
//        {
//            "basic": { 
//                BIRTHDAY: "1985-09-05", 出生日期--年龄
//                "diabetes_date": "2017-01-05", 糖尿病确诊时间--病程
//                "jbxx_mujcxtpl_week": "JCXTPV05" 目前监测血糖频率--SMBG行为
//            },
//            "complication": {--并发症 确诊并发症
//    --并发症确诊并发症
//    "diabetic_foot": "TNBZ01",    糖尿病足病（确诊有：TNBZ01确诊无：TNBZ02未评估：TNBZ03）
//    "nephropathy": SB01,    --糖尿病肾病（确诊有：SB01确诊无：SB02未评估：SB03）
//    "pad": XZXG01,    --下肢血管病变（确诊有：XZXG01确诊无：XZXG02未评估：XZXG03）
//    "retinal": SWM01,   --糖尿病视网膜病变（确诊有：SWM01确诊无：SWM02未评估：SWM03）
//    "chd": "QZ02",--冠心病（确诊有：QZ01确诊无：QZ02未评估：QZ03）
//    "neuropathy": "ZWSJ03",    --神经病变（确诊有：ZWSJ01确诊无：ZWSJ02未评估：ZWSJ03）
//            },
//            "history": {
//                "bs_sport": Constant.CONST_NUM_01,  是否有运动习惯
//                "bs_sport_requency": "YDPL04", 运动频率--规律运动( >=5次/周： YDPL04 3-4次／周: YDPL05 <3次/周:YDPL01)
//                "know_knowledge": "LJZS02,LJZS04" 患者希望了解哪方面知识--患者偏好(无:LJZS01 饮食:LJZS02 运动: LJZS03 用药:LJZS04 并发症相关知识:LJZS05 减重:LJZS06 血糖监测:LJZS07 低血糖相关知识: LJZS08)
//            },
//            "hypoglycemia": {
//                "hyp": Constant.CONST_NUM_01,
//                "hyp_frequency": "4444" 1个月内是否出现低血糖（次数）
//            },
//            "lab": {
//                "lab_hba": "5.4" 糖化血红蛋白--糖化血红蛋白（HbA1c）
//            },
//            "sign": {
//                "bmi": "21.4", BMI--超重
//                "height": "153", 身高
//                "weight": "50" 体重
//            },
//            drug: [{--用药情况 --SMBG行为
//                drug_name: 二甲双胍, 药物名称--DPP-4抑制剂、GLP-1受体激动剂、二甲双胍、噻唑烷二酮类、糖苷酶抑制剂、磺脲类、格列奈类
//                drug_dose: 1, 剂量
//                drug_frequency: 2, 用药频次
//                drug_time_type: 3, 服药时间 (1早 2中 3晚 4睡前)
//                drug_type: (1 用药类型--单纯生活方式:1 口服降糖药/GLP-1: 7 胰岛素:2 胰岛素联合口服药:6 其他:5)
//            }]
//        },
//
//        控制目标
//        healthRangeSet: 
//        {
//            highHemoglobin: 7,糖化血红蛋白上限
//            lowBmi: 18.5, Bmi指数下限
//            highBmi: 23.9 Bmi指数上限
//        }
//    }
    
    @SuppressWarnings("unchecked")
    public static Map<String,String> transformArchives(String json,String drug){
        Map<String,String> resMap = new HashMap<String, String>(7);
        Map<String,Object> map = null;
        try {
        	map = JSON.parseObject(json, Map.class );
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        
        Map<String,Object> basic = (Map<String, Object>) map.get("basic");
        Map<String,Object> complication = (Map<String, Object>) map.get("complication");
        Map<String,Object> history = (Map<String, Object>) map.get("history");
        Map<String,Object> hypoglycemia = (Map<String, Object>) map.get("hypoglycemia");
        Map<String,Object> lab = (Map<String, Object>) map.get("lab");
        Map<String,Object> sign = (Map<String, Object>) map.get("sign");
        
        Map<String,Object> anamnesis = (Map<String, Object>) map.get("anamnesis");
        
        
        
        String jbxxMujcxtplWeek = null;
        if(basic != null){
            jbxxMujcxtplWeek = basic.get("jbxx_mujcxtpl_week") == null ? "": basic.get("jbxx_mujcxtpl_week").toString();
        }
        
        transformDrug(drug, resMap, jbxxMujcxtplWeek);
        sign(resMap, sign);
        lab(resMap, lab);
        hypoglycemia(resMap, hypoglycemia);
        history(resMap, history);
        complication(resMap, complication, anamnesis);
        basic(resMap, basic);
        
        return resMap;
    }
    
    /**
     * @TODO 处理用药信息
     * @param json 用药报文列表
     * @param resMap 存入数据的对像
     * @param jbxxMujcxtplWeek 目前监测血糖频率
     * @
     * @author zhengsw
     * @date 2017-1-23
     */
    @SuppressWarnings("unchecked")
    private static void transformDrug(String json,Map<String,String> resMap,String jbxxMujcxtplWeek){
        if(json == null){
            resMap.put(MEDICINE, getMedicineType(null));
            return;
        }
        
        Set<String> setTypes = new HashSet<String>();
        List<Map>  list = null;
        try {
           list = JSON.parseArray(json, Map.class);
        } catch (Exception e) {
            resMap.put(MEDICINE, getMedicineType(null));
            return;
        }
       
        if(list == null || list.size() < 1){
            resMap.put(MEDICINE, getMedicineType(null));
            return;
        }
        //DPP-4   DPP-4抑制剂
        int flagDpp =0;
        //GLP-1受体激动剂    GLP-1
        int flagGlp =0;
        //二甲双胍  METFORMIN
        int flagMetformin =0;
        //噻唑烷二酮类    TZD
        int flagTzd =0;
        //糖苷酶抑制剂    AGIS
        int flagAgis =0;
        //磺脲类   SU
        int flagSu =0;
        //格列奈类  NATEGLINIDE
        int flagNateglinide =0;
        
        
        for (Map map : list) {
            String drugType = map.get("drugType") == null ? "": map.get("drugType").toString();
            if(drugType.length() > 0){
                setTypes.add(drugType);
            }
            
            String name = map.get("name") == null ? "": map.get("name").toString();
            if("DPP-4抑制剂".equals(name.trim())){
                flagDpp =1;
            }else  if("GLP-1受体激动剂".equals(name.trim())){
                flagGlp =1;
            }else  if("二甲双胍".equals(name.trim())){
                flagMetformin =1;
            }else  if("噻唑烷二酮类".equals(name.trim())){
                flagTzd =1;
            }else  if("糖苷酶抑制剂".equals(name.trim())){
                flagAgis =1;
            }else  if("磺脲类".equals(name.trim())){
                flagSu =1;
            }else  if("格列奈类".equals(name.trim())){
                flagNateglinide =1;
            }else{
            }
        }
        
        //DPP-4   DPP-4抑制剂
        if( flagDpp ==1){
            // DPP-4抑制剂
            resMap.put("DPP-4", Constant.CONST_NUM_01);
        }else {
            // DPP-4抑制剂
            resMap.put("DPP-4", Constant.CONST_NUM_00);
        }  
        
        if( flagGlp ==1){
            //GLP-1受体激动剂
            resMap.put("GLP-1", Constant.CONST_NUM_01);
        } else {
            //GLP-1受体激动剂
            resMap.put("GLP-1", Constant.CONST_NUM_00);
        } 
        
        if( flagMetformin ==1){
            //二甲双胍
            resMap.put("METFORMIN", Constant.CONST_NUM_01);
        } else {
            //二甲双胍
            resMap.put("METFORMIN", Constant.CONST_NUM_00);
        } 
        
        if( flagTzd ==1){
            //噻唑烷二酮类
            resMap.put("TZD", Constant.CONST_NUM_01);
        } else {
            //噻唑烷二酮类
            resMap.put("TZD", Constant.CONST_NUM_00);
        } 
        
        if( flagAgis ==1){
            //糖苷酶抑制剂
            resMap.put("AGIS", Constant.CONST_NUM_01);
        } else {
            //糖苷酶抑制剂
            resMap.put("AGIS", Constant.CONST_NUM_00);
        } 
        
        if( flagSu ==1){
            //磺脲类
            resMap.put("SU", Constant.CONST_NUM_01);
        } else {
            //磺脲类
            resMap.put("SU", Constant.CONST_NUM_00);
        } 
        
        if( flagNateglinide ==1){
            //格列奈类
            resMap.put("NATEGLINIDE", Constant.CONST_NUM_01);
        } else {
            //格列奈类
            resMap.put("NATEGLINIDE", Constant.CONST_NUM_00);
        }
        
        
        String isSmbg = getisSmbg(jbxxMujcxtplWeek, setTypes);
        //SMBG
        resMap.put(SMBG, isSmbg);
        
        resMap.put(MEDICINE, getMedicineType(setTypes));
    }
    
    /**
     * 获取用药情况
     * @param setTypes
     * @return 1:	口服降糖药/GLP-1 ,2:胰岛素 ，3：口服联合胰岛素，0:无数据
     * @author zhengsw
     * @date 2017-1-20
     */
    private static String getMedicineType(Set<String> setTypes){
        if(setTypes == null || setTypes.size() < 1){
            return Constant.CONST_NUM_00;
        }
        if(setTypes.contains(Constant.CONST_NUM_02) && setTypes.contains(Constant.CONST_NUM_07)){
            return Constant.CONST_NUM_03;
        }else if(setTypes.contains(Constant.CONST_NUM_02)){
            return Constant.CONST_NUM_02;
        }else if ( setTypes.contains(Constant.CONST_NUM_07)){
            return Constant.CONST_NUM_01;
        }else if(setTypes.contains(Constant.CONST_NUM_06)){
            return Constant.CONST_NUM_03;
        }else{
            return Constant.CONST_NUM_00;
        }
    }
    
    /**
     * 体征
     * @TODO
     * @param resMap
     * @param sign
     * @author zhengsw
     * @date 2017-1-23
     */
    private static void sign(Map<String,String> resMap,Map<String,Object> sign){
        if(sign == null){
            return;
        }
        String bmi = (String) sign.get("bmi");
        if(bmi != null){
            float nBmi = Float.parseFloat(bmi);
            float num = 24.0f;
            if(nBmi > num){
                //超重
                resMap.put(OVERWEIGHT, Constant.CONST_NUM_01);
            } else {
                //没超重
                resMap.put(OVERWEIGHT, Constant.CONST_NUM_00);
            }
        } else {
            //没超重
            resMap.put(OVERWEIGHT, Constant.CONST_NUM_00);
        }
    }
    
    /**
     * 糖化
     * @TODO
     * @param resMap
     * @param lab
     * @author zhengsw
     * @date 2017-1-23
     */
    private static void lab(Map<String,String> resMap,Map<String,Object> lab){
        if(lab == null){
            return;
        }
        String labHba = (String) lab.get("lab_hba");
        if(labHba != null){
            resMap.put("lab_hba", labHba);
        }
    }
    
    /**
     * @TODO 低血糖
     * @param resMap
     * @param hypoglycemia
     * @author zhengsw
     * @date 2017-1-23
     */
    private static void hypoglycemia(Map<String,String> resMap,Map<String,Object> hypoglycemia){
        if(hypoglycemia == null){
            return;
        }
        String hypFrequency = (String) hypoglycemia.get("hyp_frequency");
        if(!StringUtils.isBlank(hypFrequency)){
            int num = Integer.parseInt(hypFrequency);
            //出现低血糖次数大于0次
            if(num > 0){
                resMap.put(LOWBLOOD_IN_MONTH, Constant.CONST_NUM_01);
            } else {
                resMap.put(LOWBLOOD_IN_MONTH, Constant.CONST_NUM_00);
            }
        }
    }
    /**
     * 常量code
     */
    private final static String YDPL01 = "YDPL01";
    private final static String YDPL04 = "YDPL04";
    private final static String YDPL05 = "YDPL05";
    private final static String TNBZ01 = "TNBZ01";
    private final static String TNBZ02 = "TNBZ02";
    private final static String TNBZ03 = "TNBZ03";
    private final static String D_FOOT = "D_FOOT";
    private final static String LJZS02 = "LJZS02";
    private final static String LJZS03 = "LJZS03";
    private final static String LJZS04 = "LJZS04";
    private final static String LJZS05 = "LJZS05";
    private final static String LJZS06 = "LJZS06";
    private final static String LJZS07 = "LJZS07";
    private final static String LJZS08 = "LJZS08";
    private final static String LIKE_FOOD = "LIKE_FOOD";
    private final static String LIKE_SPORT = "LIKE_SPORT";
    private final static String LIKE_MEDICINE = "LIKE_MEDICINE";
    private final static String LIKE_COMPLICATION = "LIKE_COMPLICATION";
    private final static String LIKE_WEIGHT = "LIKE_WEIGHT";
    private final static String LIKE_SMBG = "LIKE_SMBG";
    private final static String LIKE_LOWBLOOD = "LIKE_LOWBLOOD";
    private final static String LIKE_FOOT = "LIKE_FOOT";
    private final static String SPORT_REQUENCY = "SPORT_REQUENCY";
    private final static String MEDICINE = "MEDICINE";
    private final static String D_NEPHROPATHY = "D_NEPHROPATHY";
    private final static String XZXG01 = "XZXG01";
    private final static String XZXG02 = "XZXG02";
    private final static String XZXG03 = "XZXG03";
    private final static String D_PAD = "D_PAD";
    private final static String SWM01 = "SWM01";
    private final static String SWM02 = "SWM02";
    private final static String SWM03 = "SWM03";
    private final static String QZ01 = "QZ01";
    private final static String D_RETINAL = "D_RETINAL";
    private final static String CORONARY_HEART_DISEASE = "CORONARY_HEART_DISEASE";
    private final static String ZWSJ01 = "ZWSJ01";
    private final static String D_NEUROPATHY = "D_NEUROPATHY";
    private final static String PRIMARY_HYPERTENSION = "PRIMARY_HYPERTENSION";
    private final static String COMPLICATION_IF = "COMPLICATION_IF";
    private final static String LOWBLOOD_IN_MONTH = "LOWBLOOD_IN_MONTH";
    private final static String OVERWEIGHT = "OVERWEIGHT";
    private final static String SMBG = "SMBG";
    private final static String BIRTHDAY = "BIRTHDAY";
    private final static String DISEASE_AGE = "DISEASE_AGE";
    /**
     * 病史
     * @param resMap
     * @param history
     * @author zhengsw
     * @date 2017-1-23
     */
    private static void history(Map<String,String> resMap,Map<String,Object> history){
        if(history == null){
            return;
        }
        
        String bsSportRequency = (String) history.get("bs_sport_requency");
        if(bsSportRequency != null){
            if(YDPL01.equals(bsSportRequency)){
                //运动不规律动
                resMap.put(SPORT_REQUENCY, Constant.CONST_NUM_00);
            }else if(YDPL04.equals(bsSportRequency) || YDPL05.equals(bsSportRequency)){
                //运动规律
                resMap.put(SPORT_REQUENCY, Constant.CONST_NUM_01);
            }else{
                //运动不规律动(没数据默认不规律)
                resMap.put(SPORT_REQUENCY, Constant.CONST_NUM_00);
            }
        }
        
        //偏好初始情况
        resMap.put(LIKE_FOOD, Constant.CONST_NUM_00);
        resMap.put(LIKE_SPORT, Constant.CONST_NUM_00);
        resMap.put(LIKE_MEDICINE, Constant.CONST_NUM_00);
        resMap.put(LIKE_COMPLICATION, Constant.CONST_NUM_00);
        resMap.put(LIKE_WEIGHT, Constant.CONST_NUM_00);
        resMap.put(LIKE_SMBG, Constant.CONST_NUM_00);
        resMap.put(LIKE_LOWBLOOD, Constant.CONST_NUM_00);
        resMap.put(LIKE_FOOT, Constant.CONST_NUM_00);
        
        //偏好(无:LJZS01 饮食:LJZS02 运动: LJZS03 用药:LJZS04 并发症相关知识:LJZS05 减重:LJZS06 血糖监测:LJZS07 低血糖相关知识: LJZS08)
        String knowKnowledge = (String) history.get("know_knowledge");
        String[] knowledge = knowKnowledge.split(",");
        for (int i = 0; i < knowledge.length; i++) {
            String kl = knowledge[i].trim();
            if(LJZS02.equals(kl)){
                resMap.put(LIKE_FOOD, Constant.CONST_NUM_01);
            }
            if(LJZS03.equals(kl)){
                resMap.put(LIKE_SPORT, Constant.CONST_NUM_01);
            }
            if(LJZS04.equals(kl)){
                resMap.put(LIKE_MEDICINE, Constant.CONST_NUM_01);
            }
            if(LJZS05.equals(kl)){
                resMap.put(LIKE_COMPLICATION, Constant.CONST_NUM_01);
            }
            if(LJZS06.equals(kl)){
                resMap.put(LIKE_WEIGHT, Constant.CONST_NUM_01);
            }
            if(LJZS07.equals(kl)){
                resMap.put(LIKE_SMBG, Constant.CONST_NUM_01);
            }
            if(LJZS08.equals(kl)){
                resMap.put(LIKE_LOWBLOOD, Constant.CONST_NUM_01);
            }
        }
    }
    
    /**
     * @TODO 并发症处理
     * @param resMap
     * @param complication
     * @param anamnesis
     * @author zhengsw
     * @date 2017-1-23
     */
    private static void complication(Map<String,String> resMap,Map<String,Object> complication,Map<String,Object> anamnesis){
        int flag = 0;
        if (complication != null) {
            String diabeticFoot = (String) complication.get("diabetic_foot");
            if(diabeticFoot != null){
                if(TNBZ01.equals(diabeticFoot)){
                    //足部确诊1
                    resMap.put(D_FOOT, Constant.CONST_NUM_01);
                    flag++;
                }else if(TNBZ02.equals(diabeticFoot)){
                    //足部未确诊0
                    resMap.put(D_FOOT, Constant.CONST_NUM_00);
                }else if(TNBZ03.equals(diabeticFoot)){
                    //足部未确诊
                    resMap.put(D_FOOT, Constant.CONST_NUM_00);
                }else{
                    //足部未确诊
                    resMap.put(D_FOOT, Constant.CONST_NUM_00);
                }
            }
            
            String nephropathy = (String) complication.get("nephropathy");
            if(nephropathy != null){
                if(SB01.equals(nephropathy)){
                    //糖尿病肾病 确诊
                    resMap.put(D_NEPHROPATHY, Constant.CONST_NUM_01);
                    flag++;
                }else if(SB02.equals(nephropathy)){
                    //糖尿病肾病 未确诊
                    resMap.put(D_NEPHROPATHY, Constant.CONST_NUM_00);
                }else if(SB03.equals(diabeticFoot)){
                    //糖尿病肾病 未确诊
                    resMap.put(D_NEPHROPATHY, Constant.CONST_NUM_00);
                }else{
                    //糖尿病肾病 未确诊
                    resMap.put(D_NEPHROPATHY, Constant.CONST_NUM_00);
                }
            }
            
            String pad = (String) complication.get("pad");
            if(pad != null){
                if(XZXG01.equals(pad)){
                    //下肢血管病变 确诊
                    resMap.put(D_PAD, Constant.CONST_NUM_01);
                    flag++;
                }else if(XZXG02.equals(pad)){
                    //下肢血管病变未确诊
                    resMap.put(D_PAD, Constant.CONST_NUM_00);
                }else if(XZXG03.equals(pad)){
                    //下肢血管病变未 确诊
                    resMap.put(D_PAD, Constant.CONST_NUM_00);
                }else{
                    //下肢血管病变未 确诊
                    resMap.put(D_PAD, Constant.CONST_NUM_00);
                }
            }
            
            String retinal = (String) complication.get("retinal");
            if(retinal != null){
                if(SWM01.equals(retinal)){
                    //网膜病变 确诊
                    resMap.put(D_RETINAL, Constant.CONST_NUM_01);
                    flag++;
                }else if(SWM02.equals(retinal)){
                    //网膜病变未确诊
                    resMap.put(D_RETINAL, Constant.CONST_NUM_00);
                }else if(SWM03.equals(retinal)){
                    //网膜病变未确诊
                    resMap.put(D_RETINAL, Constant.CONST_NUM_00);
                }else{
                    //网膜病变未确诊
                    resMap.put(D_RETINAL, Constant.CONST_NUM_00);
                }
            }
            
            String chd = (String) complication.get("chd");
            if(QZ01.equals(chd)){
                //冠心病 确诊
                    resMap.put(CORONARY_HEART_DISEASE, Constant.CONST_NUM_01);
                    flag++;
            } else {
                //冠心病未确诊
                resMap.put(CORONARY_HEART_DISEASE, Constant.CONST_NUM_00);
            }
            
            String neuropathy = (String) complication.get("neuropathy");

            if(ZWSJ01.equals(neuropathy)){
                //糖尿病神经病变确诊
                    resMap.put(D_NEUROPATHY, Constant.CONST_NUM_01);
                    flag++;
            } else {
                //糖尿病神经病变未确诊
                resMap.put(D_NEUROPATHY, Constant.CONST_NUM_00);
            }
        }
        
        
        if(anamnesis != null){
            String essentialHyp = (String) anamnesis.get("essential_hyp");
            if(Constant.CONST_NUM_01.equals(essentialHyp)){
                //原发性高血压确诊
                resMap.put(PRIMARY_HYPERTENSION, Constant.CONST_NUM_01);
                flag++;
            } else {
                //原发性高血压未确诊
                resMap.put(PRIMARY_HYPERTENSION, Constant.CONST_NUM_00);
            } 
        }
        
        if(flag > 0){
            //有无并发症:有
            resMap.put(COMPLICATION_IF, Constant.CONST_NUM_01);
        } else {
            //有无并发症:无
            resMap.put(COMPLICATION_IF, Constant.CONST_NUM_00);
        }
        
    }
    
    /**
     * @TODO 基础信息处理
     * @param resMap
     * @param basic
     * @author zhengsw
     * @date 2017-1-23
     */
    private static void basic(Map<String,String> resMap,Map<String,Object> basic){
        if(basic == null){
            return;
        }
        String birthday = (String) basic.get(BIRTHDAY);
        if(birthday != null){
            //年龄
            resMap.put(BIRTHDAY, String.valueOf(DateHelper.getAge(birthday)));
        }
        
        String diabetesDate = (String) basic.get("diabetes_date");
        if(!StringUtils.isBlank(diabetesDate)){
            //病程
            resMap.put(DISEASE_AGE, String.valueOf(DateHelper.getAge(diabetesDate)));
        }
    }
    
    
    /**
     * 根据逻辑生成是否有SMBG行为
     * 
     * @param jbxxMujcxtplWeek 目前监测血糖频率
     * @param drugTypeList 用药列表
     * @return
     * @author 占铃树
     * @date 2017年1月16日
     */
    public static String getisSmbg(String jbxxMujcxtplWeek,
            Set<String> drugTypeList) {
        String isSmbg = Constant.CONST_NUM_00;
        String condition = "JCXTPV02,JCXTPV03,JCXTPV04,JCXTPV05";
//        1、用药情况为“胰岛素”或“胰岛素联合口服药”且目前监测血糖频率为>5次/周时，则算有SMBG行为，其他情况均为否
        String constNum01 = Constant.CONST_NUM_01;
        String constNum02 = Constant.CONST_NUM_02;
        String constNum06 = Constant.CONST_NUM_06;
        String constNum07 = Constant.CONST_NUM_07;
        String hcxrpvCode05 = "JCXTPV05";
        boolean b = (drugTypeList.contains(constNum02) || drugTypeList.contains(constNum06)) && hcxrpvCode05.equalsIgnoreCase(jbxxMujcxtplWeek);
		if(b) {
            isSmbg = Constant.CONST_NUM_01;
        } else {
			boolean c = (drugTypeList.contains(constNum01) || drugTypeList.contains(constNum07))
			        && (condition.contains(jbxxMujcxtplWeek));
			if(c) {
			    isSmbg = Constant.CONST_NUM_01;
			}
		}

        return isSmbg;
    }
    
    public static  List<String> getKnowledgeTag(Map<String,String> mapMemberFile){
    	List<String> list = new ArrayList<String>();
//   	患者偏好-饮食：糖尿病科学饮食与饮食交换份法
//   	患者偏好-运动：正确的运动方法与相关注意事项
//   	患者偏好-用药：合理用药知识
//   	患者偏好-并发症：糖尿病并发症知识
//   	患者偏好-减重：科学减重
//   	患者偏好-监测：合理正确的自我血糖监测
//   	患者偏好-低血糖：低血糖的发现、自救与预防
//   	用药情况-口服药：合理应用口服药知识
//   	用药情况-胰岛素：正确注射胰岛素知识
//   	1周是否出现低血糖-出现：低血糖的发现、自救与预防
//   	是否超重-是：科学减重
//   	SMBG行为-无：合理正确的自我血糖监测
//   	是否规律运动-否：正确的运动方法与相关注意事项
//   	是否有并发症-有：并发症的防治知识
   	 
   	//偏好初始情况
   	if(mapMemberFile == null){
   		return list;
   	}
   	
   	//患者偏好-饮食：糖尿病科学饮食与饮食交换份法
   	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_FOOD))){
   		list.add(TAG_LIKE_FOOD);
   	}
   	
   	//患者偏好-运动：正确的运动方法与相关注意事项
   	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_SPORT))){
   		list.add(TAG_LIKE_SPORT);
   	}
//	患者偏好-用药：合理用药知识
	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_MEDICINE))){
   		list.add(TAG_LIKE_MEDICINE);
   	}
//	患者偏好-并发症：糖尿病并发症知识
	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_COMPLICATION))){
   		list.add(TAG_TNB_KNOWLEDGE);
   	}
//	患者偏好-减重：科学减重
	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_WEIGHT))){
   		list.add(TAG_LIKE_WEIGHT);
   	}
//	患者偏好-监测：合理正确的自我血糖监测
	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_SMBG))){
   		list.add(TAG_LIKE_SMBG);
   	}
//	患者偏好-低血糖：低血糖的发现、自救与预防
	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_LOWBLOOD))){
   		list.add(TAG_LIKE_LOWBLOOD);
   	}
//	用药情况-口服药：合理应用口服药知识
//	用药情况-胰岛素：正确注射胰岛素知识
        //口服
	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(MEDICINE))){
   		list.add(TAG_MEDICINE_KF);
   	}
    //胰岛素
   	else if(Constant.CONST_NUM_02.equals(mapMemberFile.get(MEDICINE))){
   		list.add(TAG_MEDICINE_YDS);
   	}
    // 联合
   	else if(Constant.CONST_NUM_03.equals(mapMemberFile.get(MEDICINE))){
   		list.add(TAG_MEDICINE_KF);
   		list.add(TAG_MEDICINE_YDS);
   	}
	
//	1周是否出现低血糖-出现：低血糖的发现、自救与预防
   	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LOWBLOOD_IN_MONTH))){
   		if(!list.contains(TAG_LIKE_LOWBLOOD)){
   			list.add(TAG_LIKE_LOWBLOOD);
   		}
	}
	
//	是否超重-是：科学减重
	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(OVERWEIGHT))){
		if(!list.contains(TAG_LIKE_WEIGHT)){
			list.add(TAG_LIKE_WEIGHT);
		}
   	}
	

//	SMBG行为-有：合理正确的自我血糖监测
	if(Constant.CONST_NUM_00.equals(mapMemberFile.get(SMBG))){
		if(!list.contains(TAG_LIKE_SMBG)){
			list.add(TAG_LIKE_SMBG);
		}
   	}
//	是否规律运动-否：正确的运动方法与相关注意事项
	
	if(Constant.CONST_NUM_00.equals(mapMemberFile.get(SPORT_REQUENCY))){
		if(!list.contains(TAG_LIKE_SPORT)){
			list.add(TAG_LIKE_SPORT);
		}
   		
   	}
//	是否有并发症-有：并发症的防治知识
	
	if(Constant.CONST_NUM_01.equals(mapMemberFile.get(COMPLICATION_IF))){
   		list.add(TAG_COMPLICATION);
   	}
   	return list;
   	
   }
}
