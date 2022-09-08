package com.comvee.cdms.foot.helper;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.foot.vo.FootVO;
import com.comvee.cdms.member.bo.RangeBO;
import com.comvee.cdms.member.po.MemberPO;

import java.util.*;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Wxy on 2018/11/7.
 */
public class FootHelper {

    //足部管理处方评估逻辑（危险等级，治疗建议）
    public static Map<String,Object> outFootSuggest(MemberPO member, RangeBO healthRangeSet, FootVO followModel) {
        Map<String, Object> map = new HashMap<String, Object>();
        //华侨患者档案
        // MemberModel member = this.memberService.getMemberById(mid);

        // 胆固醇TC、甘油三脂 、低密度脂蛋白胆固醇等逻辑(心血管科会诊)  -----开始
        // 胆固醇TC
        followModel.setCardiovascular_consultation("");
        double cholesterol_tc = 0;
        if (!StringUtils.isBlank(followModel.getCholesterol_tc())) {
            cholesterol_tc = Double.valueOf(followModel.getCholesterol_tc()).doubleValue();
            // 心血管科会诊
            if (cholesterol_tc >= 4.5) {
                followModel.setCardiovascular_consultation("1");
            }
        }
        // 甘油三脂TG
        double triglyceride_tg = 0;
        if (!StringUtils.isBlank(followModel.getTriglyceride_tg())) {
            triglyceride_tg = Double.valueOf(followModel.getTriglyceride_tg()).doubleValue();
            // 心血管科会诊
            if (triglyceride_tg >= 1.7) {
                followModel.setCardiovascular_consultation("1");
            }
        }

        // LDL-C
        double ldl_c = 0;
        if (!StringUtils.isBlank(followModel.getLdl_c())) {
            ldl_c = Double.valueOf(followModel.getLdl_c()).doubleValue();
            //档案中冠心病 gxb_his
            String gxb_his = member.getChd();
            //勾选冠心病 ≥ 1.8
            if (gxb_his != null && gxb_his.equals("QZ01")) {
                if (ldl_c >= 1.8) {
                    followModel.setCardiovascular_consultation("1");
                }
            } else {
                //未勾选冠心病 ≥ 2.6
                if (ldl_c >= 2.6) {
                    followModel.setCardiovascular_consultation("1");
                }
            }
        }
        // HDL-C
        double hdl_c = 0;
        if (!StringUtils.isBlank(followModel.getHdl_c())) {
            hdl_c = Double.valueOf(followModel.getHdl_c()).doubleValue();
            //档案中性别 (1男2女)
            Integer sex = member.getSex();
            //男性 ≤ 1.0
            if (sex != null && sex==1) {
                if (hdl_c <= 1.0) {
                    followModel.setCardiovascular_consultation("1");
                }
            } else if (sex != null && sex==2) {
                //女性 ≤ 1.3
                if (hdl_c <= 1.3) {
                    followModel.setCardiovascular_consultation("1");
                }
            }
        }

        //血压控制范围
        //HealthRangeSetModel healthRangeSet = this.memberInvoke.loadMemberRangeSet(mid);
        //舒张压 lowDiastolicPressure 61 - highDiastolicPressure 62 mmHg
        double lowDiastolicPressure = 0;
        if (!StringUtils.isBlank(healthRangeSet.getLowDiastolicPress())) {
            lowDiastolicPressure = Double.valueOf(healthRangeSet.getLowDiastolicPress()).doubleValue();
        }
        double highDiastolicPressure = 0;
        if (!StringUtils.isBlank(healthRangeSet.getHighDiastolicPress())) {
            highDiastolicPressure = Double.valueOf(healthRangeSet.getHighDiastolicPress()).doubleValue();
        }
        // 收缩压 lowSystolicPressure 91 - highSystolicPressure 92 mmHg
        double lowSystolicPressure = 0;
        if (!StringUtils.isBlank(healthRangeSet.getLowSystolicPress())) {
            lowSystolicPressure = Double.valueOf(healthRangeSet.getLowSystolicPress()).doubleValue();
        }
        double highSystolicPressure = 0;
        if (!StringUtils.isBlank(healthRangeSet.getHighSystolicPress())) {
            highSystolicPressure = Double.valueOf(healthRangeSet.getHighSystolicPress()).doubleValue();
        }
        // 舒张压（mmHg）
        double diastolic_pressure = 0;
        if (!StringUtils.isBlank(followModel.getDiastolic_pressure())) {
            diastolic_pressure = Double.valueOf(followModel.getDiastolic_pressure()).doubleValue();
            if (diastolic_pressure < lowDiastolicPressure || diastolic_pressure > highDiastolicPressure) {
                followModel.setCardiovascular_consultation("1");
            }
        }
        // 收缩压（mmHg）
        double systolic_pressure = 0;
        if (!StringUtils.isBlank(followModel.getSystolic_pressure())) {
            systolic_pressure = Double.valueOf(followModel.getSystolic_pressure()).doubleValue();
            if (systolic_pressure < lowSystolicPressure || systolic_pressure > highSystolicPressure) {
                followModel.setCardiovascular_consultation("1");
            }
        }

        // 胆固醇TC、甘油三脂 、低密度脂蛋白胆固醇等逻辑(心血管科会诊)  -----结束

        //判定危险等级逻辑 -----开始
        //危险等级(0级 1级 2级 3级 )danger_level
        //危险等级：0级
        //若无 下肢神经病变、下肢血管病变、足溃疡史、截肢史
        // 点击评估，判定危险等级：0级
        followModel.setDanger_level("0");//0级
        //清空  下肢血管病变诊断  下肢神经  病变诊断

        //深圳足部
        if("2".equals(followModel.getFoot_type())){
            //神经
            if("1".equals(followModel.getLegs_neuropathy())){
                followModel.setDanger_level("1");
            }

            if("1".equals(followModel.getLegs_neuropathy_l())){
                followModel.setDanger_level("1");
            }
            //血管
            if("1".equals(followModel.getLegs_vasculopathy())){
                followModel.setDanger_level("2");
            }

            if("1".equals(followModel.getLegs_vasculopathy_l())){
                followModel.setDanger_level("2");
            }
        }else{
            followModel.setLegs_neuropathy("2");
            followModel.setLegs_neuropathy_l("2");
            followModel.setLegs_vasculopathy("2");
            followModel.setLegs_vasculopathy_l("2");

            //危险等级：1级
            //其中一项非“正常”，判定对应的脚  下肢神经病变诊断 为“有” legs_neuropathy_l
            //10克尼龙丝  ,针刺定位觉, 128Hz音叉震动觉 ,踝反射 ,VPT(震动感觉阀值)
            //若是患者无 足溃疡史和 截肢史 以及 下肢血管病变 ,点击评估，判定危险等级：1级
            /*---change 170621---*/
            // 尼龙丝
            if (!StringUtils.isBlank(followModel.getNylon_pressure_feel()) && !followModel.getNylon_pressure_feel().equals("1")) {
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if (!StringUtils.isBlank(followModel.getNylon_pressure_feel_l()) && !followModel.getNylon_pressure_feel_l().equals("1")) {
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }
            // 针刺定位
            if (!StringUtils.isBlank(followModel.getNeedle_position_sense()) && !followModel.getNeedle_position_sense().equals("1")) {
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if (!StringUtils.isBlank(followModel.getNeedle_position_sense_l()) && !followModel.getNeedle_position_sense_l().equals("1")) {
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }

            // 音叉震动觉
            if (!StringUtils.isBlank(followModel.getVibration_sense()) && !followModel.getVibration_sense().equals("1")) {
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if (!StringUtils.isBlank(followModel.getVibration_sense_l()) && !followModel.getVibration_sense_l().equals("1")) {
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }

            // vpt
            if(!StringUtils.isBlank(followModel.getVpt()) && !"1".equals(followModel.getVpt())){
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_l()) && !"1".equals(followModel.getVpt_l())){
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }

            if(!StringUtils.isBlank(followModel.getVpt_wh_r()) && !"1".equals(followModel.getVpt_wh_r())){
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_wh_l()) && !"1".equals(followModel.getVpt_wh_l())){
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_zb_r()) && !"1".equals(followModel.getVpt_zb_r())){
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_zb_l()) && !"1".equals(followModel.getVpt_zb_l())){
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_mz_r()) && !"1".equals(followModel.getVpt_mz_r())){
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_mz_l()) && !"1".equals(followModel.getVpt_mz_l())){
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }

            if(!StringUtils.isBlank(followModel.getVpt_xz_r()) && !"1".equals(followModel.getVpt_xz_r())){
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_xz_l()) && !"1".equals(followModel.getVpt_xz_l())){
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_zd_r()) && !"1".equals(followModel.getVpt_zd_r())){
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_zd_l()) && !"1".equals(followModel.getVpt_zd_l())){
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_nh_r()) && !"1".equals(followModel.getVpt_nh_r())){
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getVpt_nh_l()) && !"1".equals(followModel.getVpt_nh_l())){
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }

            // 畸形
            if(!StringUtils.isBlank(followModel.getFoot_deformity()) && !"1".equals(followModel.getFoot_deformity())){
                followModel.setDanger_level("1");
            }
            if(!StringUtils.isBlank(followModel.getFoot_deformity_l()) && !"1".equals(followModel.getFoot_deformity_l())){
                followModel.setDanger_level("1");
            }
            // 踝反射
            if (!StringUtils.isBlank(followModel.getAnkle_reflex()) && followModel.getAnkle_reflex().equals("2")) {
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if (!StringUtils.isBlank(followModel.getAnkle_reflex_l()) && followModel.getAnkle_reflex_l().equals("2")) {
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }
            // 棉花絮
            if (!StringUtils.isBlank(followModel.getLint_touch()) && followModel.getLint_touch().equals("2")) {
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if (!StringUtils.isBlank(followModel.getLint_touch_l()) && followModel.getLint_touch_l().equals("2")) {
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }
            // 温度觉
            if (!StringUtils.isBlank(followModel.getThermesthesia()) && followModel.getThermesthesia().equals("2")) {
                followModel.setLegs_neuropathy("1");
                followModel.setDanger_level("1");
            }
            if (!StringUtils.isBlank(followModel.getThermesthesia_l()) && followModel.getThermesthesia_l().equals("2")) {
                followModel.setLegs_neuropathy_l("1");
                followModel.setDanger_level("1");
            }
        /*---change end 170621---*/

            //危险等级：2级
            //其中一项非“正常”，判定对应的脚
            //1足背动脉搏动 dorsum_foot_arteriopalmus_l（1正常  2胫后动脉搏动 posterior_tibial_arteriopalmus_l(1正常 3ABI abi_l(4正常
            //下肢血管病变诊断 为“有”(1，有 2，无)legs_vasculopathy legs_vasculopathy_l
            //若是患者无 足溃疡史和 截肢史, 点击评估，判定危险等级：2级
            if (!StringUtils.isBlank(followModel.getDorsum_foot_arteriopalmus()) && !followModel.getDorsum_foot_arteriopalmus().equals("1")) {
                followModel.setLegs_vasculopathy("1");
                followModel.setDanger_level("2");
            }
            if (!StringUtils.isBlank(followModel.getDorsum_foot_arteriopalmus_l()) && !followModel.getDorsum_foot_arteriopalmus_l().equals("1")) {
                followModel.setLegs_vasculopathy_l("1");
                followModel.setDanger_level("2");
            }
            if (!StringUtils.isBlank(followModel.getPosterior_tibial_arteriopalmus()) && !followModel.getPosterior_tibial_arteriopalmus().equals("1")) {
                followModel.setLegs_vasculopathy("1");
                followModel.setDanger_level("2");
            }
            if (!StringUtils.isBlank(followModel.getPosterior_tibial_arteriopalmus_l()) && !followModel.getPosterior_tibial_arteriopalmus_l().equals("1")) {
                followModel.setLegs_vasculopathy_l("1");
                followModel.setDanger_level("2");
            }
            if (!StringUtils.isBlank(followModel.getAbi()) && !followModel.getAbi().equals("4")) {
                followModel.setLegs_vasculopathy("1");
                followModel.setDanger_level("2");
            }
            if (!StringUtils.isBlank(followModel.getAbi_l()) && !followModel.getAbi_l().equals("4")) {
                followModel.setLegs_vasculopathy_l("1");
                followModel.setDanger_level("2");
            }
        }
        //危险等级：3级
        //足溃疡史 foot_ulcer_his_l (1有 、截肢史 amputation_his_l 任一项“有”  (1有
        if (!StringUtils.isBlank(followModel.getFoot_ulcer_his()) && followModel.getFoot_ulcer_his().equals("1")) {
            followModel.setDanger_level("3");
        }
        if (!StringUtils.isBlank(followModel.getFoot_ulcer_his_l()) && followModel.getFoot_ulcer_his_l().equals("1")) {
            followModel.setDanger_level("3");
        }
        if (!StringUtils.isBlank(followModel.getAmputation_his()) && followModel.getAmputation_his().equals("1")) {
            followModel.setDanger_level("3");
        }
        if (!StringUtils.isBlank(followModel.getAmputation_his_l()) && followModel.getAmputation_his_l().equals("1")) {
            followModel.setDanger_level("3");
        }
        //判定危险等级逻辑 -----结束


        //判定下次随诊时间-----开始next_follow_time
        //Wagner等级wagner_level
        followModel.setWagner_level("0");

        String next_follow_time = "";
        //日期时间工具
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        //1.根据危险等级 设置下次随诊时间 ;2.随诊频率建议 follow_rate_advice
        if (followModel.getDanger_level().equals("0")) {
            c.add(Calendar.MONTH, 12);
            followModel.setFollow_rate_advice("每年普通医生或专科医生（护士）门诊检查一次");
        } else if (followModel.getDanger_level().equals("1")) {
            c.add(Calendar.MONTH, 6);
            followModel.setFollow_rate_advice("每隔3-6个月普通医生或专科医生（护士）门诊检查一次");
        } else if (followModel.getDanger_level().equals("2")) {
            c.add(Calendar.MONTH, 3);
            followModel.setFollow_rate_advice("每隔2-3个月专科医生（护士）门诊检查一次");
        } else if (followModel.getDanger_level().equals("3")) {
            c.add(Calendar.DATE, 45);
            followModel.setFollow_rate_advice("每隔1-2个月专科医生（护士）门诊检查一次");
        }
        //治疗护理建议
        List<Map<String, Object>> followMap = getFollowMap(followModel);
        //日期时间格式化
        next_follow_time = DateHelper.getDate(c.getTime(), "yyyy-MM-dd");
        followModel.setNext_follow_time(next_follow_time);
        //判定下次随诊时间-----结束

        //后续处理建议逻辑 -----开始
        //清空上一次的评估 后续处理建议
        followModel.setOphthalmology_consultation("");
        followModel.setEyeground_check("");
        followModel.setNail_care("");
        followModel.setMoisturizing("");
        followModel.setSkin_consultation("");
        followModel.setRepair_heloma_fat("");
        followModel.setSuction_blisters("");
        followModel.setShoe_pad_treatment("");
        followModel.setXZXGCSB_check("");
        followModel.setEMG_check("");
        followModel.setPlantar_pressure_check("");
        followModel.setBlood_vessel_consultation("");
        followModel.setDebridement_care("");
        followModel.setOrthopedics_consultation("");
        //1、眼科会诊、眼底检查
        //档案中眼睛勾选未检
        /*if (!StringUtils.isBlank(member.getEyes_never_check()) && member.getEyes_never_check().equals("1")) {
            followModel.setOphthalmology_consultation("1");
            followModel.setEyeground_check("1");
        }*/

        //2、修甲护理 Nail_care
        // 趾甲 选择 过长、过短、嵌甲 之一 （1正常，2过长，3增厚，4嵌甲，5灰甲）  nail;
        if (!StringUtils.isBlank(followModel.getNail())) {
            if (followModel.getNail().contains("2") || followModel.getNail().contains("6") || followModel.getNail().contains("4")) {
                followModel.setNail_care("1");
            }
        }
        if (!StringUtils.isBlank(followModel.getNail_l())) {
            if (followModel.getNail_l().contains("2") || followModel.getNail_l().contains("6") || followModel.getNail_l().contains("4")) {
                followModel.setNail_care("1");
            }
        }

        //3、涂抹润肤剂 Moisturizing
        // 皮肤湿润度 选择 皮肤干燥    (1正常 2：干燥 3：潮湿)  skin_humidity
        if (!StringUtils.isBlank(followModel.getSkin_humidity()) && followModel.getSkin_humidity().equals("3")) {
            followModel.setMoisturizing("1");
        }
        if (!StringUtils.isBlank(followModel.getSkin_humidity_l()) && followModel.getSkin_humidity_l().equals("3")) {
            followModel.setMoisturizing("1");
        }
        //皲裂有输入部位或大小
        if (!StringUtils.isBlank(followModel.getChap())) {
            followModel.setMoisturizing("1");
        }
        if (!StringUtils.isBlank(followModel.getChap_l())) {
            followModel.setMoisturizing("1");
        }

        //4、皮肤科会诊 skin_consultation
        //霉菌感染 选择非“正常”项 mould_infection_l
        if (!StringUtils.isBlank(followModel.getMould_infection()) && !followModel.getMould_infection().equals("1")) {
            followModel.setSkin_consultation("1");
        }
        if (!StringUtils.isBlank(followModel.getMould_infection_l()) && !followModel.getMould_infection_l().equals("1")) {
            followModel.setSkin_consultation("1");
        }

        //5、修剪胼胝 repair_heloma_fat
        //胼胝有输入部位或大小  callus_l
        if (!StringUtils.isBlank(followModel.getCallus())) {
            followModel.setRepair_heloma_fat("1");
        }
        if (!StringUtils.isBlank(followModel.getCallus_l())) {
            followModel.setRepair_heloma_fat("1");
        }

        //6、抽吸水疱 suction_blisters
        //水疱有输入部位或大小 blisters_l
        if (!StringUtils.isBlank(followModel.getBlisters())) {
            followModel.setSuction_blisters("1");
        }
        if (!StringUtils.isBlank(followModel.getBlisters_l())) {
            followModel.setSuction_blisters("1");
        }

        //7、矫型鞋垫 shoe_pad_treatment
        //畸形选择非“无”项 foot_deformity_l （1无，2拇外翻，3爪形趾，4棰状趾，5突出趾骨
        if (!StringUtils.isBlank(followModel.getFoot_deformity()) && !followModel.getFoot_deformity().equals("1")) {
            followModel.setShoe_pad_treatment("1");
        }
        if (!StringUtils.isBlank(followModel.getFoot_deformity_l()) && !followModel.getFoot_deformity_l().equals("1")) {
            followModel.setShoe_pad_treatment("1");
        }

        //8、下肢血管超声波检查 XZXGCSB_check
        //皮肤颜色选择非“正常”项 skin_color_l (1正常，2红斑，3暗紫，4苍白)
        if (!StringUtils.isBlank(followModel.getSkin_color()) && !followModel.getSkin_color().equals("1")) {
            followModel.setXZXGCSB_check("1");
        }
        if (!StringUtils.isBlank(followModel.getSkin_color_l()) && !followModel.getSkin_color_l().equals("1")) {
            followModel.setXZXGCSB_check("1");
        }

        //9、肌电图 EMG_check 、足底压力检查 plantar_pressure_check
        //足部异常感觉 foot_unusually_l (1正常  与 尼龙丝压力觉 选择非“正常”项  nylon_pressure_feel_l (1正常
        if (!StringUtils.isBlank(followModel.getFoot_unusually()) && !followModel.getFoot_unusually().contains("1")) {
            followModel.setEMG_check("1");
            followModel.setPlantar_pressure_check("1");
        }
        if (!StringUtils.isBlank(followModel.getFoot_unusually_l()) && !followModel.getFoot_unusually_l().contains("1")) {
            followModel.setEMG_check("1");
            followModel.setPlantar_pressure_check("1");
        }
        if (!StringUtils.isBlank(followModel.getNylon_pressure_feel()) && !followModel.getNylon_pressure_feel().equals("1")) {
            followModel.setEMG_check("1");
            followModel.setPlantar_pressure_check("1");
        }
        if (!StringUtils.isBlank(followModel.getNylon_pressure_feel_l()) && !followModel.getNylon_pressure_feel_l().equals("1")) {
            followModel.setEMG_check("1");
            followModel.setPlantar_pressure_check("1");
        }

        //10、血管介入科会诊 blood_vessel_consultation、清创护理 debridement_care、骨科会诊 orthopedics_consultation
        //下肢血管病变诊断：有 legs_vasculopathy_l (1有
        if (!StringUtils.isBlank(followModel.getLegs_vasculopathy()) && "1".equals(followModel.getLegs_vasculopathy())) {
            followModel.setBlood_vessel_consultation("1");
            followModel.setDebridement_care("1");
            followModel.setOrthopedics_consultation("1");
        }
        if (!StringUtils.isBlank(followModel.getLegs_vasculopathy_l()) && "1".equals(followModel.getLegs_vasculopathy_l())) {
            followModel.setBlood_vessel_consultation("1");
            followModel.setDebridement_care("1");
            followModel.setOrthopedics_consultation("1");
        }
        //后续处理建议逻辑 -----结束

        map.put("follow", followModel);
        map.put("advices", followMap);
        return map;
    }


    //分割线////////////////////////////////////////
    public static List<Map<String,Object>> getFollowMap(FootVO followModel){
        try {
            List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            //0级护理建议////////////////////// edit_status 1可编辑0不可编辑
            if("0".equals(followModel.getDanger_level())){
                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "温水洗足");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "检查足部");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "涂擦润肤膏");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "足部按摩");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "下肢运动");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "不要自行随便修剪及利用化学药物清除脚趾的鸡眼或胼胝");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "不要赤足走路和赤足穿鞋");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "慎用热水袋或电热毯等热源温暖足部，防灼伤");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "选用大小合适，软布面、透气性好、宽口宽头鞋");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "避免穿高跟鞋");
                map.put("is_check", "1");
                li.add(map);
                //////////////////
                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "选用袜口较松的浅色、棉质、无破损的袜");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "穿鞋前检查鞋内有无异物");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "戒烟/减少抽烟次数");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "继续糖尿病足教育");
                map.put("is_check", "0");
                li.add(map);

                if(!"".equals(followModel.getFoot_deformity()) && !"1".equals(followModel.getFoot_deformity())
                        || !"1".equals(followModel.getFoot_deformity_l())  &&  !"".equals(followModel.getFoot_deformity_l()) ){
                    map = new HashMap<String, Object>();
                    map.put("edit_status", "1");
                    map.put("follow_advice", "建议进行足部畸形预防性手术");//（本条护理建议出现条件：如果存在畸形）
                    map.put("is_check", "0");
                    li.add(map);
                }

            }


            //1级护理建议//////////////////////
            if("1".equals(followModel.getDanger_level())){
                li = new ArrayList<Map<String, Object>>();
                map = new HashMap<String, Object>();
                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "温水洗足");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "检查足部");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "涂擦润肤膏");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "足部按摩");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "下肢运动");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "检查足部皮肤感觉和足背动脉搏动");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "不要自行随便修剪及利用化学药物清除脚趾的鸡眼或胼胝");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "不要赤足走路和赤足穿鞋");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "禁止使用热水袋和电热毯");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "选用大小合适，软布面、透气性好、宽口宽头鞋");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "新鞋子第一天最好不超过15分钟，第二天起每天延长时间");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "穿鞋前用手摸摸鞋子里面是否有硬物等");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "脱鞋后检查双脚，有无局部发红，如果有说明鞋子紧，及时更换");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "避免穿高跟鞋");
                map.put("is_check", "1");
                li.add(map);
                //////////////////////////////
                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "选用袜口较松的浅色、棉质、无破损的袜");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "穿鞋前检查鞋内有无异物");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "戒烟/减少抽烟次数");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "继续糖尿病足教育");
                map.put("is_check", "0");
                li.add(map);

                if(!"".equals(followModel.getFoot_deformity()) && !"1".equals(followModel.getFoot_deformity())
                        || !"1".equals(followModel.getFoot_deformity_l())  &&  !"".equals(followModel.getFoot_deformity_l()) ){
                    map = new HashMap<String, Object>();
                    map.put("edit_status", "1");
                    map.put("follow_advice", "建议进行足部畸形预防性手术");//（本条护理建议出现条件：如果存在畸形）
                    map.put("is_check", "0");
                    li.add(map);
                }
            }

            //2级护理建议//////////////////////
            if("2".equals(followModel.getDanger_level())){
                li = new ArrayList<Map<String, Object>>();
                map = new HashMap<String, Object>();
                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "温水洗足");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "检查足部");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "涂擦润肤膏");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "足部按摩");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "下肢运动");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "检查足部皮肤感觉和足背动脉搏动");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "不要自行随便修剪及利用化学药物清除脚趾的鸡眼或胼胝");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "不要赤足走路和赤足穿鞋");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "禁止使用热水袋和电热毯");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "选用大小合适，软布面、透气性好、宽口宽头鞋");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "新鞋子第一天最好不超过15分钟，第二天起每天延长时间");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "穿鞋前用手摸摸鞋子里面是否有硬物等");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "脱鞋后检查双脚，有无局部发红，如果有说明鞋子紧，及时更换");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "避免穿高跟鞋");
                map.put("is_check", "1");
                li.add(map);
                /////////////////////

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "选用袜口较松的浅色、棉质、无破损的袜");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "穿鞋前检查鞋内有无异物");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "戒烟/减少抽烟次数");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "继续糖尿病足教育");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "建议结合血管（外科）咨询（会诊）");
                map.put("is_check", "0");
                li.add(map);

                if(!"".equals(followModel.getFoot_deformity()) && !"1".equals(followModel.getFoot_deformity())
                        || !"1".equals(followModel.getFoot_deformity_l())  &&  !"".equals(followModel.getFoot_deformity_l()) ){
                    map = new HashMap<String, Object>();
                    map.put("edit_status", "1");
                    map.put("follow_advice", "建议进行足部畸形预防性手术");//（本条护理建议出现条件：如果存在畸形）
                    map.put("is_check", "0");
                    li.add(map);
                }
            }

            //3级护理建议//////////////////////
            if("3".equals(followModel.getDanger_level())){
                li = new ArrayList<Map<String, Object>>();
                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "温水洗足");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "检查足部");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "涂擦润肤膏");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "足部按摩");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "下肢运动");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "检查足部皮肤感觉和足背动脉搏动");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "不要自行随便修剪及利用化学药物清除脚趾的鸡眼或胼胝");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "不要赤足走路和赤足穿鞋");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "禁止使用热水袋和电热毯");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "选用大小合适，软布面、透气性好、宽口宽头鞋");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "新鞋子第一天最好不超过15分钟，第二天起每天延长时间");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "穿鞋前用手摸摸鞋子里面是否有硬物等");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "脱鞋后检查双脚，有无局部发红，如果有说明鞋子紧，及时更换");
                map.put("is_check", "1");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "0");
                map.put("follow_advice", "避免穿高跟鞋");
                map.put("is_check", "1");
                li.add(map);
                ////////////////////////
                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "选用袜口较松的浅色、棉质、无破损的袜");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "穿鞋前检查鞋内有无异物");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "戒烟/减少抽烟次数");
                map.put("is_check", "0");
                li.add(map);

                map = new HashMap<String, Object>();
                map.put("edit_status", "1");
                map.put("follow_advice", "继续糖尿病足教育");
                map.put("is_check", "0");
                li.add(map);

                if(!"".equals(followModel.getLegs_vasculopathy()) && "1".equals(followModel.getLegs_vasculopathy()) ||
                        "1".equals(followModel.getLegs_vasculopathy_l()) && !"".equals(followModel.getLegs_vasculopathy_l())){
                    map = new HashMap<String, Object>();
                    map.put("edit_status", "1");
                    map.put("follow_advice", "建议结合血管（外科）咨询（会诊）");//（本条护理建议出现条件：如果下肢血管病变存在）
                    map.put("is_check", "0");
                    li.add(map);
                }


                if(!"".equals(followModel.getFoot_deformity()) && !"1".equals(followModel.getFoot_deformity())
                        || !"1".equals(followModel.getFoot_deformity_l())  &&  !"".equals(followModel.getFoot_deformity_l()) ){
                    map = new HashMap<String, Object>();
                    map.put("edit_status", "1");
                    map.put("follow_advice", "建议进行足部畸形预防性手术");//（本条护理建议出现条件：如果存在畸形）
                    map.put("is_check", "0");
                    li.add(map);
                }

                if(!"".equals(followModel.getAmputation_his()) && "1".equals(followModel.getAmputation_his()) ||
                        "1".equals(followModel.getAmputation_his_l()) && !"".equals(followModel.getAmputation_his_l())){
                    map = new HashMap<String, Object>();
                    map.put("edit_status", "1");
                    map.put("follow_advice", "洗澡时避免使用拐杖单独洗澡，一定要用假肢或寻求家属帮助");//（本条护理建议出现条件：存在截肢）
                    map.put("is_check", "0");
                    li.add(map);
                }

            }

            return li;
        } catch (Exception e) {
            throw new PatternSyntaxException("","",1);
        }
    }

}
