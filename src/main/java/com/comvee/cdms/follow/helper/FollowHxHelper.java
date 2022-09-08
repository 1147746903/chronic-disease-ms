package com.comvee.cdms.follow.helper;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.JsonSerializer;
import com.comvee.cdms.common.utils.StringUtils;

import java.util.*;

/**
 * Created by Wxy on 2019/03/15.
 */
public class FollowHxHelper {

    //华西医院患者 分层分级规则
    public static Map<String,Object> outHxFollow(Map<String, Object> followBodyMap,
        Map<String, Object> levelMap, Double afterNum,Double beforeNum){

        Map<String,Object> reMap=new HashMap<>();
        String reLevel="";

        String basic = followBodyMap.get("basic").toString();
        String lab = followBodyMap.get("lab").toString();
        String complication = followBodyMap.get("complication").toString();
        String hypoglycemia = followBodyMap.get("hypoglycemia").toString();
        String treatment = followBodyMap.get("treatment").toString();


        Map<String, Object> basicMap = JsonSerializer.jsonToMap(basic);
        Map<String, Object> labMap = JsonSerializer.jsonToMap(lab);
        Map<String, Object> complicationMap = JsonSerializer.jsonToMap(complication);
        Map<String, Object> hypoglycemiaMap = JsonSerializer.jsonToMap(hypoglycemia);
        Map<String, Object> treatmentMap = JsonSerializer.jsonToMap(treatment);

        //性别 1男 2女
        String sex = basicMap.get("sex").toString();
        String birthday = basicMap.get("birthday").toString();
        int age = 0;//年龄
        if(!StringUtils.isBlank(birthday)){
            age = DateHelper.getAge(birthday);
        }

        //1、近一周平均空腹beforeNum、 餐后afterNum
        String mqFbg = treatmentMap.get("mq_fbg").toString();
        String blg = treatmentMap.get("blg").toString();
        if (!StringUtils.isBlank(mqFbg)){
            beforeNum = Double.parseDouble(mqFbg);
        }
        if (!StringUtils.isBlank(blg)){
            afterNum = Double.parseDouble(blg);
        }

        //2、最近3个月内的糖化 lab  lab_hba
        Double th=0.0;
        if(null!=labMap.get("lab_hba") && !StringUtils.isBlank(labMap.get("lab_hba").toString())){
            th = Double.parseDouble(labMap.get("lab_hba").toString());
        }

        //3、近一周内发生低血糖 hypoglycemia  hyp_frequency
        Double dxt=0.0;
        //是否发生过低血糖 1是
        String hyp="";
        if(null!=hypoglycemiaMap.get("hyp") && !StringUtils.isBlank(hypoglycemiaMap.get("hyp").toString())){
            hyp = hypoglycemiaMap.get("hyp").toString();
        }
        if("1".equals(hyp)){
            if(null!=hypoglycemiaMap.get("hyp_frequency") && !StringUtils.isBlank(hypoglycemiaMap.get("hyp_frequency").toString())){
                dxt = Double.parseDouble(hypoglycemiaMap.get("hyp_frequency").toString());
            }
        }

        //4、近一次 自我管理行为得分
        Double q1_num=0.0;
        if(!StringUtils.isBlank(levelMap.get("q1_num").toString())){
            q1_num = Double.parseDouble(levelMap.get("q1_num").toString());
        }

            //ADA糖尿病足危险分级
            Double q3_level=0.0;
            if(!StringUtils.isBlank(levelMap.get("q3_level").toString())){
                q3_level = Double.parseDouble(levelMap.get("q3_level").toString());
            }

        //5、近1年糖尿病  酮症酸中毒 高渗性昏迷 乳酸酸中毒 总次数  complication  jyntnbjxbfz
        Double rsszdNum =0.0;
        Double rsszdNum2 =0.0;
        Double rsszdNum4 =0.0;
        Double rsszdNum5 =0.0;
        String jyntnbjxbfzStr = complicationMap.get("jyntnbjxbfz").toString();
        if(jyntnbjxbfzStr.contains("LX02")){
            if(null!=complicationMap.get("complications_diabetes_status_02") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_02").toString())){
                rsszdNum2= Double.parseDouble(complicationMap.get("complications_diabetes_status_02").toString());
            }
        }
        if(jyntnbjxbfzStr.contains("LX04")){
            if(null!=complicationMap.get("complications_diabetes_status_04") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_04").toString())){
                rsszdNum4= Double.parseDouble(complicationMap.get("complications_diabetes_status_04").toString());
            }
        }
        if(jyntnbjxbfzStr.contains("LX05")){
            if(null!=complicationMap.get("complications_diabetes_status_05") && !StringUtils.isBlank(complicationMap.get("complications_diabetes_status_05").toString())){
                rsszdNum5= Double.parseDouble(complicationMap.get("complications_diabetes_status_05").toString());
            }
        }
        rsszdNum= rsszdNum2 + rsszdNum4 + rsszdNum5;

        //糖尿病肾病 nephropathy SB01
        String  nephropathy= "";
        if(null!=complicationMap.get("nephropathy") && !StringUtils.isBlank(complicationMap.get("nephropathy").toString())){
            nephropathy= complicationMap.get("nephropathy").toString();
        }
        //1、糖尿病肾病类型选择
        String neph_type_cms= "";
        if("SB01".equals(nephropathy)){
            if(null!=complicationMap.get("neph_type_cms") && !StringUtils.isBlank(complicationMap.get("neph_type_cms").toString())){
                neph_type_cms= complicationMap.get("neph_type_cms").toString();
            }
        }


        //糖尿病眼底病变 SWM01有
        String  retinal= "";
        if(null!=complicationMap.get("retinal") && !StringUtils.isBlank(complicationMap.get("retinal").toString())){
            retinal= complicationMap.get("retinal").toString();
        }
        //2、糖尿病视网膜病变选择
        String  retinal_type_cms= "";
        if("SWM01".equals(retinal)){
            if(null!=complicationMap.get("retinal_type_cms") && !StringUtils.isBlank(complicationMap.get("retinal_type_cms").toString())){
                retinal_type_cms= complicationMap.get("retinal_type_cms").toString();
            }
        }



        //3、糖尿病周围神经病变选择
        String  neuropathy= "";
        if(null!=complicationMap.get("neuropathy") && !StringUtils.isBlank(complicationMap.get("neuropathy").toString())){
            neuropathy= complicationMap.get("neuropathy").toString();
        }
        String  neu_symptom_cms= "";
        if(null!=complicationMap.get("neu_symptom_cms") && !StringUtils.isBlank(complicationMap.get("neu_symptom_cms").toString())){
            neu_symptom_cms= complicationMap.get("neu_symptom_cms").toString();
        }

        //4、ADA糖尿病足危险分级




        //5、是否心脑血管疾病选择
        String  has_xnxg= "";
        if(null!=complicationMap.get("has_xnxg") && !StringUtils.isBlank(complicationMap.get("has_xnxg").toString())){
            has_xnxg= complicationMap.get("has_xnxg").toString();
        }
        String  xnxg_show= "";
        if(null!=complicationMap.get("xnxg_show") && !StringUtils.isBlank(complicationMap.get("xnxg_show").toString())){
            xnxg_show= complicationMap.get("xnxg_show").toString();
        }





        //高级
        //1、糖尿病肾病类型选择：临床糖尿病肾病病期LX04 或肾衰竭期LX05 complication  neph_type_cms
        //2、糖尿病视网膜病变选择：重度非增生型视网膜病变LX04 或 增殖期糖尿病视网膜病变LX02 complication retinal_type_cms
        //3、糖尿病周围神经病变选择：确诊有ZWSJ01 且症状表现选择“无症状”ZZ01 外的其他选项  周围神经neuropathy  症状neu_symptom_cms
        //4、ADA糖尿病足危险分级：3级
        //5、是否心脑血管疾病选择“确诊有” complication  has_xnxg 1

        //中级
        //1、糖尿病肾病类型选择：肾小球高过滤和肾脏肥大LX01 或无临床表现的肾损害期LX02 或早起糖尿病肾病期LX03
        //2、糖尿病视网膜病变眼部疾病类型选择：轻度非增殖期视网膜病变LX01 或轻度~中度非增值性视网膜病变LX03
        //3、糖尿病周围神经病变选择：确诊有确诊有ZWSJ01 且症状表现选择“无症状”ZZ01
        //4、ADA糖尿病足危险分级：1~2级
        //5、心脑血管疾病选择“确诊无” complication  has_xnxg 2
        // 且心脑血管疾病因素选择了“早发性心血管疾病家族史、吸烟、高血压、血脂紊乱或蛋白尿”任意一项或多项 complication xnxg_show
        // 且年龄≥40岁

        //低级
        //1、ADA糖尿病足危险分级：0级
        //2、心脑血管疾病选择“确诊无” complication  has_xnxg 2
        // 且心脑血管疾病因素选择了“早发性心血管疾病家族史、吸烟、高血压、血脂紊乱或蛋白尿”任意一项或多项 complication xnxg_show
        // 且年龄＜40岁

        //低危 三级支持
        if(age < 65) {
            //4.4≤空腹＜7 餐后≤10
            if( 4.4 <= beforeNum && beforeNum <7  && afterNum<=10 ){
                reLevel="1";
            }
            if(th <7 ){
                reLevel="1";
            }
        }else if(age >= 65){
            //8≤空腹＜10 且 8≤餐后＜12
            if( 8 <= beforeNum && beforeNum <10  && 8<=afterNum && afterNum<12 ){
                reLevel="1";
            }
            if(th <8 ){
                reLevel="1";
            }
        }
        if(dxt==0){
            reLevel="1";
        }
        if(q1_num>=5.6){
            reLevel="1";
        }
        if(rsszdNum==0){
            reLevel="1";
        }
        //1、ADA糖尿病足危险分级：0级
        if(q3_level == 0){
            reLevel="1";
        }
        //2、心脑血管疾病选择“确诊无” complication  has_xnxg 2
        // 且心脑血管疾病因素选择了“早发性心血管疾病家族史、吸烟、高血压、血脂紊乱或蛋白尿”任意一项或多项 complication xnxg_show
        // 且年龄＜40岁
        if(has_xnxg.contains("2") && !StringUtils.isBlank(xnxg_show) && age<40){
            reLevel="1";
        }



        //中危 二级支持
        if(age < 65){
            //7≤空腹＜10  或 空腹≥10、餐后≤13.9
            if((7 <= beforeNum && beforeNum <10) || (beforeNum>=10 &&  afterNum<=13.9) ){
                reLevel="2";
            }
            if(7<=th  && th<9){
                reLevel="2";
            }

        }else if(age >= 65){
            //空腹＜10、餐后≥12 或  空腹≥10、餐后≤13.9
            if((beforeNum<10 && afterNum>=12) || (beforeNum>=10 && afterNum<=13.9)){
                reLevel="2";
            }
            if(8<=th  && th<9){
                reLevel="2";
            }
        }
        if(dxt==1){
            reLevel="2";
        }
        if(4.2<=q1_num && q1_num<=5.5){
            reLevel="2";
        }
        if(rsszdNum==1){
            reLevel="2";
        }
        //1、糖尿病肾病类型选择：肾小球高过滤和肾脏肥大LX01 或无临床表现的肾损害期LX02 或早起糖尿病肾病期LX03
        if(neph_type_cms.contains("LX01") || neph_type_cms.contains("LX02") || neph_type_cms.contains("LX03")){
            reLevel="2";
        }
        //2、糖尿病视网膜病变眼部疾病类型选择：轻度非增殖期视网膜病变LX01 或轻度~中度非增值性视网膜病变LX03
        if(retinal_type_cms.contains("LX01") || retinal_type_cms.contains("LX03")){
            reLevel="2";
        }
        //3、糖尿病周围神经病变选择：确诊有确诊有ZWSJ01 且症状表现选择“无症状”ZZ01
        if(neuropathy.contains("ZWSJ01") && neu_symptom_cms.contains("ZZ01")){
            reLevel="2";
        }
        //4、ADA糖尿病足危险分级：1~2级
        if(q3_level == 1 || q3_level == 2){
            reLevel="2";
        }
        //5、心脑血管疾病选择“确诊无” complication  has_xnxg 2
        // 且心脑血管疾病因素选择了“早发性心血管疾病家族史、吸烟、高血压、血脂紊乱或蛋白尿”任意一项或多项 complication xnxg_show
        // 且年龄≥40岁
        if(has_xnxg.contains("2") && !StringUtils.isBlank(xnxg_show) && age>=40){
            reLevel="2";
        }


        //高危 一级支持
        if(beforeNum >=10 && afterNum >13.9){
            reLevel="3";
        }
        if(th >=9 ){
            reLevel="3";
        }
        if(dxt >=2 ){
            reLevel="3";
        }
        if(q1_num <=4.1){
            reLevel="3";
        }
        if(rsszdNum >=2){
            reLevel="3";
        }
        //1、糖尿病肾病类型选择：临床糖尿病肾病病期LX04 或肾衰竭期LX05 complication  neph_type_cms
        if(neph_type_cms.contains("LX04") || neph_type_cms.contains("LX05")){
            reLevel="3";
        }
        //2、糖尿病视网膜病变选择：重度非增生型视网膜病变LX04 或 增殖期糖尿病视网膜病变LX02 complication retinal_type_cms
        if(retinal_type_cms.contains("LX04") || retinal_type_cms.contains("LX02")){
            reLevel="3";
        }
        //3、糖尿病周围神经病变选择：确诊有ZWSJ01 且症状表现选择“无症状”ZZ01 外的其他选项  周围神经neuropathy  症状neu_symptom_cms
        if(neuropathy.contains("ZWSJ01") && !neu_symptom_cms.contains("ZZ01") && !StringUtils.isBlank(neu_symptom_cms)){
            reLevel="3";
        }
        //4、ADA糖尿病足危险分级：3级
        if(q3_level == 3){
            reLevel="3";
        }
        //5、是否心脑血管疾病选择“确诊有” complication  has_xnxg 1
        if(has_xnxg.contains("1")){
            reLevel="3";
        }

        //糖尿病足 TNBZ01有
        String  diabetic_foot= "";
        if(null!=complicationMap.get("diabetic_foot") && !StringUtils.isBlank(complicationMap.get("diabetic_foot").toString())){
            diabetic_foot= complicationMap.get("diabetic_foot").toString();
        }
        if("TNBZ01".equals(diabetic_foot)){
            reLevel="3";
        }

        //reLevel 1平稳层、2中危层、3高危层
        reMap.put("level",reLevel);
        return  reMap;
    }

}
