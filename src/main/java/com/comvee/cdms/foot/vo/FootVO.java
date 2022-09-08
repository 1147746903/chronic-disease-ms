package com.comvee.cdms.foot.vo;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public class FootVO {

    /** 脉率（次/分钟） ,(字段类型:varchar) **/
    private String mem_pr;
    /** 腰围（cm） ,(字段类型:varchar) **/
    private String waist;
    /** 臀围 ,(字段类型:varchar) **/
    private String hip;
    /** 腰臀比 ,(字段类型:varchar) **/
    private String waist_hip_ratio;
    /** 舒张压（mmHg） ,(字段类型:varchar) **/
    private String diastolic_pressure;
    /** 收缩压（mmHg） ,(字段类型:varchar) **/
    private String systolic_pressure;
    /** 身高（cm） ,(字段类型:varchar) **/
    private String height;
    /** 体重（kg） ,(字段类型:varchar) **/
    private String weight;
    /** BMI ,(字段类型:varchar) **/
    private String bmi;
    /** 尿ALB ,(字段类型:varchar) **/
    private String urine_alb;
    /** 尿白蛋白 ,(字段类型:varchar) **/
    private String urinary_albumin;
    /** 尿肌酐 ,(字段类型:varchar) **/
    private String urine_creatinine;
    /** 尿白蛋白/肌酐 比 ,(字段类型:varchar) **/
    private String urinary_albumin_creatinine_ratio;
    /** FBG ,(字段类型:varchar) **/
    private String fbg;
    /** PBG ,(字段类型:varchar) **/
    private String pbg;
    /** HbA1C ,(字段类型:varchar) **/
    private String hba1c;
    /** 血酮 ,(字段类型:varchar) **/
    private String blood_ketone;
    /** 胆固醇TC ,(字段类型:varchar) **/
    private String cholesterol_tc;
    /** 甘油三脂TG ,(字段类型:varchar) **/
    private String triglyceride_tg;
    /** LDL-C ,(字段类型:varchar) **/
    private String ldl_c;
    /** HDL-C ,(字段类型:varchar) **/
    private String hdl_c;
    /** 袜子（1： 合适，2： 不合适） ,(字段类型:varchar) **/
    private String socks;
    /** 袜子不合适（1：破洞、2：线头、3：尼龙袜、4：太紧） ,(字段类型:varchar) **/
    private String socks_not_right;
    /** 鞋（1：合适 2：不合适） ,(字段类型:varchar) **/
    private String shoes;
    /** 鞋子不合适（1：拖鞋: 2：鞋底太薄、3鞋底太厚、4鞋底硬） ,(字段类型:varchar) **/
    private String shoes_not_right;
    /** 趾甲（1正常，2过长，3增厚，4嵌甲，5灰甲） ,(字段类型:tinyint) **/
    private String nail;
    /** 跛行(1有，2无) ,(字段类型:varchar) **/
    private String limp;
    /** 坏疽 ,(字段类型:varchar) **/
    private String gangrene;
    /** 水疱 ,(字段类型:varchar) **/
    private String blisters;
    /** 胼胝 ,(字段类型:varchar) **/
    private String callus;
    /** 皲裂 ,(字段类型:varchar) **/
    private String chap;
    /** 皮肤颜色(1正常，2红斑，3暗紫，4苍白) ,(字段类型:tinyint) **/
    private String skin_color;
    /** 霉菌感染(1正常，2灰甲，3脚气，4灰甲和脚气) ,(字段类型:tinyint) **/
    private String mould_infection;
    /** 皮肤湿度(1：干燥 2：潮湿) ,(字段类型:tinyint) **/
    private String skin_humidity;
    /** 足部异常感觉(1正常，2麻木，3疼痛，4瘙痒，5感觉消失) ,(字段类型:tinyint) **/
    private String foot_unusually;
    /** 静息通 ,(字段类型:tinyint) **/
    private String resting;
    /** 下肢皮温(1正常，2高，3低) ,(字段类型:tinyint) **/
    private String legs_skin_temperature;
    /** 足溃疡史(1有，2无) ,(字段类型:tinyint) **/
    private String foot_ulcer_his;
    /** 截肢史(1有，2无) ,(字段类型:tinyint) **/
    private String amputation_his;
    /** 足部畸形（1无，2拇外翻，3爪形趾，4棰状趾，5突出趾骨头，6Charcot关节） ,(字段类型:tinyint) **/
    private String foot_deformity;
    /** 足背动脉搏动（1正常，2减弱，3未能触及） ,(字段类型:tinyint) **/
    private String dorsum_foot_arteriopalmus;
    /** 胫后动脉搏动(1正常，2减弱，3未能触及) ,(字段类型:tinyint) **/
    private String posterior_tibial_arteriopalmus;
    /**
     * ABI(踝肱指数检查)(0.00-0.40(严重程度)，0.41-0.70(中度程度)，0.71-0.90(轻度程度)，0.91-1.30(正常)
     * ，&gt;1.30(下肢中层钙化)) ,(字段类型:tinyint)
     **/
    private String abi;
    /** 下肢血管病变诊断(1，有 2，无) ,(字段类型:tinyint) **/
    private String legs_vasculopathy;
    /** 10g尼龙丝压力觉(1正常，2缺失，3消失) ,(字段类型:tinyint) **/
    private String nylon_pressure_feel;
    /** 针刺定位觉 ,(字段类型:tinyint) **/
    private String needle_position_sense;
    /** 棉花絮触觉 ,(字段类型:tinyint) **/
    private String lint_touch;
    /** 128Hz音叉震动觉(&gt;=5(正常)，&lt;5（异常）) ,(字段类型:tinyint) **/
    private String vibration_sense;
    /** 温度觉 ,(字段类型:tinyint) **/
    private String thermesthesia;
    /** 踝反射 ,(字段类型:tinyint) **/
    private String ankle_reflex;
    /** VPT(震动感觉阀值){0-15V(低风险)，16-24V(中度风险)，&gt;25V(高风险)} ,(字段类型:tinyint) **/
    private String vpt;
    /** 下肢神经病变诊断 ,(字段类型:tinyint) **/
    private String legs_neuropathy;
    /** ,(字段类型:varchar) **/
    private String insert_dt;
    /** 医生id ,(字段类型:varchar) **/
    private String create_id;
    /** 随访医生 ,(字段类型:varchar) **/
    private String create_name;
    /** 随访主键 ,(字段类型:varchar) **/
    private String follow_id;
    /** 患者id ,(字段类型:varchar) **/
    private String mid;
    /** 下次随诊时间 ,(字段类型:datetime) **/
    private String next_follow_time;
    /** Wagner等级 ,(字段类型:varchar) **/
    private String wagner_level;
    /** 危险等级(0级 1级 2级 3级) ,(字段类型:varchar) **/
    private String danger_level;
    /** 随诊意见 ,(字段类型:varchar) **/
    private String follow_opinion;
    /** 责任医生id ,(字段类型:varchar) **/
    private String responsibility_doctor_id;
    /** 责任医生名字 ,(字段类型:varchar) **/
    private String responsibility_doctor_name;
    /** 袜子（1： 合适，2： 不合适） ,(字段类型:varchar) **/
    private String socks_l;
    /** 袜子不合适（1：破洞、2：线头、3：尼龙袜、4：太紧） ,(字段类型:varchar) **/
    private String socks_not_right_l;
    /** 鞋（1：合适 2：不合适） ,(字段类型:varchar) **/
    private String shoes_l;
    /** 鞋子不合适（1：拖鞋: 2：鞋底太薄、3鞋底太厚、4鞋底硬） ,(字段类型:varchar) **/
    private String shoes_not_right_l;
    /** 趾甲（1正常，2过长，3增厚，4嵌甲，5灰甲） ,(字段类型:tinyint) **/
    private String nail_l;
    /** 跛行(1有，2无) ,(字段类型:tinyint) **/
    private String limp_l;
    /** 坏疽 ,(字段类型:tinyint) **/
    private String gangrene_l;
    /** 水疱 ,(字段类型:tinyint) **/
    private String blisters_l;
    /** 胼胝 ,(字段类型:tinyint) **/
    private String callus_l;
    /** 皲裂 ,(字段类型:tinyint) **/
    private String chap_l;
    /** 皮肤颜色(1正常，2红斑，3暗紫，4苍白) ,(字段类型:tinyint) **/
    private String skin_color_l;
    /** 霉菌感染(1正常，2灰甲，3脚气，4灰甲和脚气) ,(字段类型:tinyint) **/
    private String mould_infection_l;
    /** 皮肤湿度(1：干燥 2：潮湿) ,(字段类型:tinyint) **/
    private String skin_humidity_l;
    /** 足部异常感觉(1正常，2麻木，3疼痛，4瘙痒，5感觉消失) ,(字段类型:tinyint) **/
    private String foot_unusually_l;
    /** 静息通 ,(字段类型:tinyint) **/
    private String resting_l;
    /** 下肢皮温(1正常，2高，3低) ,(字段类型:tinyint) **/
    private String legs_skin_temperature_l;
    /** 足溃疡史(1有，2无) ,(字段类型:tinyint) **/
    private String foot_ulcer_his_l;
    /** 截肢史(1有，2无) ,(字段类型:tinyint) **/
    private String amputation_his_l;
    /** 足部畸形（1无，2拇外翻，3爪形趾，4棰状趾，5突出趾骨头，6Charcot关节） ,(字段类型:tinyint) **/
    private String foot_deformity_l;
    /** 足背动脉搏动（1正常，2减弱，3未能触及） ,(字段类型:tinyint) **/
    private String dorsum_foot_arteriopalmus_l;
    /** 胫后动脉搏动(1正常，2减弱，3未能触及) ,(字段类型:tinyint) **/
    private String posterior_tibial_arteriopalmus_l;
    /**
     * ABI(踝肱指数检查)(0.00-0.40(严重程度)，0.41-0.70(中度程度)，0.71-0.90(轻度程度)，0.91-1.30(正常)
     * ，&gt;1.30(下肢中层钙化)) ,(字段类型:tinyint)
     **/
    private String abi_l;
    /** 下肢血管病变诊断(1，有 2，无) ,(字段类型:tinyint) **/
    private String legs_vasculopathy_l;
    /** 10g尼龙丝压力觉(1正常，2缺失，3消失) ,(字段类型:tinyint) **/
    private String nylon_pressure_feel_l;
    /** 针刺定位觉 ,(字段类型:tinyint) **/
    private String needle_position_sense_l;
    /** 棉花絮触觉 ,(字段类型:tinyint) **/
    private String lint_touch_l;
    /** 128Hz音叉震动觉(&gt;=5(正常)，&lt;5（异常）) ,(字段类型:tinyint) **/
    private String vibration_sense_l;
    /** 温度觉 ,(字段类型:tinyint) **/
    private String thermesthesia_l;
    /** 踝反射 ,(字段类型:tinyint) **/
    private String ankle_reflex_l;
    /** VPT(震动感觉阀值){0-15V(低风险)，16-24V(中度风险)，&gt;25V(高风险)} ,(字段类型:tinyint) **/
    private String vpt_l;
    /** 下肢神经病变诊断 ,(字段类型:tinyint) **/
    private String legs_neuropathy_l;
    /** 随诊频率建议 ,(字段类型:varchar) **/
    private String follow_rate_advice;
    /** 随诊时间 ,(字段类型:datetime) **/
    private String record_dt;
    /** 是否有效 ,(字段类型:tinyint) **/
    private String is_valid;
    /** 保存状态(1完成,０草稿) ,(字段类型:tinyint) **/
    private String save_type;

    /** 后续处理(处理鞋垫 ) ,(字段类型:tinyint) **/
    private String shoe_pad_treatment;
    /** 修胼脂 ,(字段类型:tinyint) **/
    private String repair_heloma_fat;
    /** 润肤 ,(字段类型:tinyint) **/
    private String moisturizing;
    /** 血管会诊 ,(字段类型:tinyint) **/
    private String blood_vessel_consultation;
    /** 骨科会诊 ,(字段类型:tinyint) **/
    private String orthopedics_consultation;
    /** 皮肤科会诊 ,(字段类型:tinyint) **/
    private String skin_consultation;
    /** 图片描述 ,(字段类型:varchar) **/
    private String img_description;
    /** 修甲护理 ,(字段类型:tinyint) **/
    private String nail_care;
    /** 抽吸水疱 ,(字段类型:tinyint) **/
    private String suction_blisters;
    /** 清创护理 ,(字段类型:tinyint) **/
    private String debridement_care;
    /** 眼科会诊 ,(字段类型:tinyint) **/
    private String ophthalmology_consultation;
    /** 心血管科会诊 ,(字段类型:tinyint) **/
    private String cardiovascular_consultation;
    /** 眼底检查 ,(字段类型:tinyint) **/
    private String eyeground_check;
    /** 肌电检查 ,(字段类型:tinyint) **/
    private String EMG_check;
    /** 足底压力检查 ,(字段类型:tinyint) **/
    private String plantar_pressure_check;
    /** 下肢血管超声波检查 ,(字段类型:tinyint) **/
    private String XZXGCSB_check;
    /** 左脚伤口 ,(字段类型:tinyint) **/
    private String wound_l;
    /** 右脚伤口 ,(字段类型:tinyint) **/
    private String wound_r;
    /** 左脚伤口评估 ,(字段类型:varchar) **/
    private String wound_l_access;
    /** 右脚伤口评估 ,(字段类型:varchar) **/
    private String wound_r_access;
    /** 左脚临床症状积分 ,(字段类型:tinyint) **/
    private String clinical_integral_l;
    /** 左脚临床症状评估结果 ,(字段类型:varchar) **/
    private String clinical_result_l;
    /** 左脚疗效指数 ,(字段类型:varchar) **/
    private String clinical_diagnoindex_l;
    /** 左脚临床诊断结果描述 ,(字段类型:varchar) **/
    private String clinical_access_des_l;
    /** 右脚临床症状积分 ,(字段类型:tinyint) **/
    private String clinical_integral_r;
    /** 右脚临床症状评估结果 ,(字段类型:varchar) **/
    private String clinical_result_r;
    /** 右脚疗效指数 ,(字段类型:varchar) **/
    private String clinical_diagnoindex_r;
    /** 右脚临床诊断结果描述 ,(字段类型:varchar) **/
    private String clinical_access_des_r;
    /** 左脚上次临床症状积分 ,(字段类型:tinyint) **/
    private String clinical_lastintegral_l;
    /** 右脚上次临床症状积分 ,(字段类型:tinyint) **/
    private String clinical_lastintegral_r;
    /** 左脚临床症状选项 ,(字段类型:varchar) **/
    private String clinical_l;
    /** 右脚临床症状选项 ,(字段类型:varchar) **/
    private String clinical_r;
    /** 关联的血管报告id ,(字段类型:bigint) **/
    private String blood_vessel_report_id;
    /** 关联的血管报告pdf地址 ,(字段类型:varchar) **/
    private String blood_vessel_report_pdf_url;
    /** 关联的神经报告id ,(字段类型:bigint) **/
    private String nerve_report_id;
    /** 关联的神经报告pdf地址 ,(字段类型:varchar) **/
    private String nerve_report_pdf_url;
    /** 关联的神经报告名称 ,(字段类型:varchar) **/
    private String nerve_report_name;
    /** 关联的血管报告名称 ,(字段类型:varchar) **/
    private String blood_vessel_report_name;
    /** 关联的血管报告图片地址 ,(字段类型:varchar) **/
    private String blood_vessel_report_pdf_img_url;
    /** 关联的神经报告图片地址 ,(字段类型:varchar) **/
    private String nerve_report_pdf_img_url;
    /**  查看足部报告的h5url **/
    private String url;
    /** 管理处方名字 **/
    private String followName;
    /** 下发次数**/
    private Integer sendNum;
    /**下发时间**/
    private String sms_date;
    /**是否下发**/
    private String is_sms;
    /** 工作室ID **/
    private String studio_id;
    /** 左右外踝  **/
    private String vpt_wh_l;
    private String vpt_wh_r;
    /** 左右足背  **/
    private String vpt_zb_l;
    private String vpt_zb_r;
    /** 左右拇趾  **/
    private String vpt_mz_l;
    private String vpt_mz_r;
    /**  左右小趾  **/
    private String vpt_xz_l;
    private String vpt_xz_r;
    /** 左右足底  **/
    private String vpt_zd_l;
    private String vpt_zd_r;
    /**  左右内踝  **/
    private String vpt_nh_l;
    private String vpt_nh_r;

    /**
     * 订单id,thc_order主键
     */
    private String orderId;

    //炎症指标
    /** 超敏C反应蛋白  **/
    private String yz_hs_crp;
    /** 血沉  **/
    private String yz_esr;
    /** 白介素-6 **/
    private String yz_il_6;
    /** 降钙素  **/
    private String yz_ct;
    /** 白细胞计数 **/
    private String yz_leucocyte_count;
    /** 中性粒细胞百分比 **/
    private String yz_gra;

    //其他检查项目
    /** 血红蛋白 **/
    private String qt_hgb;
    /** ACT **/
    private String qt_act;
    /** AST  **/
    private String qt_ast;
    /** ACB  **/
    private String qt_acb;
    /** BUN  **/
    private String qt_bun;
    /** CAC（乳酸） **/
    private String qt_cac;
    /** 血酮体 **/
    private String qt_ketone_body;
    /** 血钾  **/
    private String qt_potassium;
    /** 血钠  **/
    private String qt_sodium;
    /** 血氯  **/
    private String qt_chloride;
    /** 血钙  **/
    private String qt_calcium;
    /** 足部类型 1默认 2深圳足部  **/
    private String foot_type;

    /**
     * 肾小球滤过率
     */
    private String egfr;

    private Integer leftEyesLevel;
    private Integer rightEyesLevel;

    public String getEgfr() {
        return egfr;
    }

    public void setEgfr(String egfr) {
        this.egfr = egfr;
    }

    public Integer getLeftEyesLevel() {
        return leftEyesLevel;
    }

    public void setLeftEyesLevel(Integer leftEyesLevel) {
        this.leftEyesLevel = leftEyesLevel;
    }

    public Integer getRightEyesLevel() {
        return rightEyesLevel;
    }

    public void setRightEyesLevel(Integer rightEyesLevel) {
        this.rightEyesLevel = rightEyesLevel;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNail_care() {
        return nail_care;
    }

    public void setNail_care(String nailCare) {
        nail_care = nailCare;
    }

    public String getSuction_blisters() {
        return suction_blisters;
    }

    public void setSuction_blisters(String suctionBlisters) {
        suction_blisters = suctionBlisters;
    }

    public String getDebridement_care() {
        return debridement_care;
    }

    public void setDebridement_care(String debridementCare) {
        debridement_care = debridementCare;
    }

    public String getOphthalmology_consultation() {
        return ophthalmology_consultation;
    }

    public void setOphthalmology_consultation(String ophthalmologyConsultation) {
        ophthalmology_consultation = ophthalmologyConsultation;
    }

    public String getCardiovascular_consultation() {
        return cardiovascular_consultation;
    }

    public void setCardiovascular_consultation(String cardiovascularConsultation) {
        cardiovascular_consultation = cardiovascularConsultation;
    }

    public String getEyeground_check() {
        return eyeground_check;
    }

    public void setEyeground_check(String eyegroundCheck) {
        eyeground_check = eyegroundCheck;
    }


    public String getEMG_check() {
        return EMG_check;
    }

    public void setEMG_check(String eMG_check) {
        EMG_check = eMG_check;
    }

    public String getPlantar_pressure_check() {
        return plantar_pressure_check;
    }

    public void setPlantar_pressure_check(String plantarPressureCheck) {
        plantar_pressure_check = plantarPressureCheck;
    }

    public String getXZXGCSB_check() {
        return XZXGCSB_check;
    }

    public void setXZXGCSB_check(String xZXGCSBCheck) {
        XZXGCSB_check = xZXGCSBCheck;
    }

    public String getImg_description() {
        return img_description;
    }

    public void setImg_description(String imgDescription) {
        img_description = imgDescription;
    }

    public String getShoe_pad_treatment() {
        return shoe_pad_treatment;
    }

    public void setShoe_pad_treatment(String shoe_pad_treatment) {
        this.shoe_pad_treatment = shoe_pad_treatment;
    }

    public String getRepair_heloma_fat() {
        return repair_heloma_fat;
    }

    public void setRepair_heloma_fat(String repair_heloma_fat) {
        this.repair_heloma_fat = repair_heloma_fat;
    }

    public String getMoisturizing() {
        return moisturizing;
    }

    public void setMoisturizing(String moisturizing) {
        this.moisturizing = moisturizing;
    }

    public String getBlood_vessel_consultation() {
        return blood_vessel_consultation;
    }

    public void setBlood_vessel_consultation(String blood_vessel_consultation) {
        this.blood_vessel_consultation = blood_vessel_consultation;
    }

    public String getOrthopedics_consultation() {
        return orthopedics_consultation;
    }

    public void setOrthopedics_consultation(String orthopedics_consultation) {
        this.orthopedics_consultation = orthopedics_consultation;
    }

    public String getSkin_consultation() {
        return skin_consultation;
    }

    public void setSkin_consultation(String skin_consultation) {
        this.skin_consultation = skin_consultation;
    }

    public String getMem_pr() {
        return this.mem_pr;
    }

    public void setMem_pr(String mem_pr) {
        this.mem_pr = mem_pr;
    }

    public String getWaist() {
        return this.waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getHip() {
        return this.hip;
    }

    public void setHip(String hip) {
        this.hip = hip;
    }

    public String getWaist_hip_ratio() {
        return this.waist_hip_ratio;
    }

    public void setWaist_hip_ratio(String waist_hip_ratio) {
        this.waist_hip_ratio = waist_hip_ratio;
    }

    public String getDiastolic_pressure() {
        return this.diastolic_pressure;
    }

    public void setDiastolic_pressure(String diastolic_pressure) {
        this.diastolic_pressure = diastolic_pressure;
    }

    public String getSystolic_pressure() {
        return this.systolic_pressure;
    }

    public void setSystolic_pressure(String systolic_pressure) {
        this.systolic_pressure = systolic_pressure;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return this.bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getUrine_alb() {
        return this.urine_alb;
    }

    public void setUrine_alb(String urine_alb) {
        this.urine_alb = urine_alb;
    }

    public String getUrinary_albumin() {
        return this.urinary_albumin;
    }

    public void setUrinary_albumin(String urinary_albumin) {
        this.urinary_albumin = urinary_albumin;
    }

    public String getUrine_creatinine() {
        return this.urine_creatinine;
    }

    public void setUrine_creatinine(String urine_creatinine) {
        this.urine_creatinine = urine_creatinine;
    }

    public String getUrinary_albumin_creatinine_ratio() {
        return this.urinary_albumin_creatinine_ratio;
    }

    public void setUrinary_albumin_creatinine_ratio(String urinary_albumin_creatinine_ratio) {
        this.urinary_albumin_creatinine_ratio = urinary_albumin_creatinine_ratio;
    }

    public String getFbg() {
        return this.fbg;
    }

    public void setFbg(String fbg) {
        this.fbg = fbg;
    }

    public String getPbg() {
        return this.pbg;
    }

    public void setPbg(String pbg) {
        this.pbg = pbg;
    }

    public String getHba1c() {
        return this.hba1c;
    }

    public void setHba1c(String hba1c) {
        this.hba1c = hba1c;
    }

    public String getBlood_ketone() {
        return this.blood_ketone;
    }

    public void setBlood_ketone(String blood_ketone) {
        this.blood_ketone = blood_ketone;
    }

    public String getCholesterol_tc() {
        return this.cholesterol_tc;
    }

    public void setCholesterol_tc(String cholesterol_tc) {
        this.cholesterol_tc = cholesterol_tc;
    }

    public String getTriglyceride_tg() {
        return this.triglyceride_tg;
    }

    public void setTriglyceride_tg(String triglyceride_tg) {
        this.triglyceride_tg = triglyceride_tg;
    }

    public String getLdl_c() {
        return this.ldl_c;
    }

    public void setLdl_c(String ldl_c) {
        this.ldl_c = ldl_c;
    }

    public String getHdl_c() {
        return this.hdl_c;
    }

    public void setHdl_c(String hdl_c) {
        this.hdl_c = hdl_c;
    }

    public String getSocks() {
        return this.socks;
    }

    public void setSocks(String socks) {
        this.socks = socks;
    }

    public String getSocks_not_right() {
        return this.socks_not_right;
    }

    public void setSocks_not_right(String socks_not_right) {
        this.socks_not_right = socks_not_right;
    }

    public String getShoes() {
        return this.shoes;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }

    public String getShoes_not_right() {
        return this.shoes_not_right;
    }

    public void setShoes_not_right(String shoes_not_right) {
        this.shoes_not_right = shoes_not_right;
    }

    public String getNail() {
        return this.nail;
    }

    public void setNail(String nail) {
        this.nail = nail;
    }

    public String getLimp() {
        return this.limp;
    }

    public void setLimp(String limp) {
        this.limp = limp;
    }

    public String getGangrene() {
        return this.gangrene;
    }

    public void setGangrene(String gangrene) {
        this.gangrene = gangrene;
    }

    public String getBlisters() {
        return this.blisters;
    }

    public void setBlisters(String blisters) {
        this.blisters = blisters;
    }

    public String getCallus() {
        return this.callus;
    }

    public void setCallus(String callus) {
        this.callus = callus;
    }

    public String getChap() {
        return this.chap;
    }

    public void setChap(String chap) {
        this.chap = chap;
    }

    public String getSkin_color() {
        return this.skin_color;
    }

    public void setSkin_color(String skin_color) {
        this.skin_color = skin_color;
    }

    public String getMould_infection() {
        return this.mould_infection;
    }

    public void setMould_infection(String mould_infection) {
        this.mould_infection = mould_infection;
    }

    public String getSkin_humidity() {
        return this.skin_humidity;
    }

    public void setSkin_humidity(String skin_humidity) {
        this.skin_humidity = skin_humidity;
    }

    public String getFoot_unusually() {
        return this.foot_unusually;
    }

    public void setFoot_unusually(String foot_unusually) {
        this.foot_unusually = foot_unusually;
    }

    public String getResting() {
        return this.resting;
    }

    public void setResting(String resting) {
        this.resting = resting;
    }

    public String getLegs_skin_temperature() {
        return this.legs_skin_temperature;
    }

    public void setLegs_skin_temperature(String legs_skin_temperature) {
        this.legs_skin_temperature = legs_skin_temperature;
    }

    public String getFoot_ulcer_his() {
        return this.foot_ulcer_his;
    }

    public void setFoot_ulcer_his(String foot_ulcer_his) {
        this.foot_ulcer_his = foot_ulcer_his;
    }

    public String getAmputation_his() {
        return this.amputation_his;
    }

    public void setAmputation_his(String amputation_his) {
        this.amputation_his = amputation_his;
    }

    public String getFoot_deformity() {
        return this.foot_deformity;
    }

    public void setFoot_deformity(String foot_deformity) {
        this.foot_deformity = foot_deformity;
    }

    public String getDorsum_foot_arteriopalmus() {
        return this.dorsum_foot_arteriopalmus;
    }

    public void setDorsum_foot_arteriopalmus(String dorsum_foot_arteriopalmus) {
        this.dorsum_foot_arteriopalmus = dorsum_foot_arteriopalmus;
    }

    public String getPosterior_tibial_arteriopalmus() {
        return this.posterior_tibial_arteriopalmus;
    }

    public void setPosterior_tibial_arteriopalmus(String posterior_tibial_arteriopalmus) {
        this.posterior_tibial_arteriopalmus = posterior_tibial_arteriopalmus;
    }

    public String getAbi() {
        return this.abi;
    }

    public void setAbi(String abi) {
        this.abi = abi;
    }

    public String getLegs_vasculopathy() {
        return this.legs_vasculopathy;
    }

    public void setLegs_vasculopathy(String legs_vasculopathy) {
        this.legs_vasculopathy = legs_vasculopathy;
    }

    public String getNylon_pressure_feel() {
        return this.nylon_pressure_feel;
    }

    public void setNylon_pressure_feel(String nylon_pressure_feel) {
        this.nylon_pressure_feel = nylon_pressure_feel;
    }

    public String getNeedle_position_sense() {
        return this.needle_position_sense;
    }

    public void setNeedle_position_sense(String needle_position_sense) {
        this.needle_position_sense = needle_position_sense;
    }

    public String getLint_touch() {
        return this.lint_touch;
    }

    public void setLint_touch(String lint_touch) {
        this.lint_touch = lint_touch;
    }

    public String getVibration_sense() {
        return this.vibration_sense;
    }

    public void setVibration_sense(String vibration_sense) {
        this.vibration_sense = vibration_sense;
    }

    public String getThermesthesia() {
        return this.thermesthesia;
    }

    public void setThermesthesia(String thermesthesia) {
        this.thermesthesia = thermesthesia;
    }

    public String getAnkle_reflex() {
        return this.ankle_reflex;
    }

    public void setAnkle_reflex(String ankle_reflex) {
        this.ankle_reflex = ankle_reflex;
    }

    public String getVpt() {
        return this.vpt;
    }

    public void setVpt(String vpt) {
        this.vpt = vpt;
    }

    public String getLegs_neuropathy() {
        return this.legs_neuropathy;
    }

    public void setLegs_neuropathy(String legs_neuropathy) {
        this.legs_neuropathy = legs_neuropathy;
    }

    public String getInsert_dt() {
        return this.insert_dt;
    }

    public void setInsert_dt(String insert_dt) {
        this.insert_dt = insert_dt;
    }

    public String getCreate_id() {
        return this.create_id;
    }

    public void setCreate_id(String create_id) {
        this.create_id = create_id;
    }

    public String getCreate_name() {
        return this.create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
    }

    public String getFollow_id() {
        return this.follow_id;
    }

    public void setFollow_id(String follow_id) {
        this.follow_id = follow_id;
    }

    public String getMid() {
        return this.mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getNext_follow_time() {
        return this.next_follow_time;
    }

    public void setNext_follow_time(String next_follow_time) {
        this.next_follow_time = next_follow_time;
    }

    public String getWagner_level() {
        return this.wagner_level;
    }

    public void setWagner_level(String wagner_level) {
        this.wagner_level = wagner_level;
    }

    public String getDanger_level() {
        return this.danger_level;
    }

    public void setDanger_level(String danger_level) {
        this.danger_level = danger_level;
    }

    public String getFollow_opinion() {
        return this.follow_opinion;
    }

    public void setFollow_opinion(String follow_opinion) {
        this.follow_opinion = follow_opinion;
    }

    public String getResponsibility_doctor_id() {
        return this.responsibility_doctor_id;
    }

    public void setResponsibility_doctor_id(String responsibility_doctor_id) {
        this.responsibility_doctor_id = responsibility_doctor_id;
    }

    public String getResponsibility_doctor_name() {
        return this.responsibility_doctor_name;
    }

    public void setResponsibility_doctor_name(String responsibility_doctor_name) {
        this.responsibility_doctor_name = responsibility_doctor_name;
    }

    public String getSocks_l() {
        return this.socks_l;
    }

    public void setSocks_l(String socks_l) {
        this.socks_l = socks_l;
    }

    public String getSocks_not_right_l() {
        return this.socks_not_right_l;
    }

    public void setSocks_not_right_l(String socks_not_right_l) {
        this.socks_not_right_l = socks_not_right_l;
    }

    public String getShoes_l() {
        return this.shoes_l;
    }

    public void setShoes_l(String shoes_l) {
        this.shoes_l = shoes_l;
    }

    public String getShoes_not_right_l() {
        return this.shoes_not_right_l;
    }

    public void setShoes_not_right_l(String shoes_not_right_l) {
        this.shoes_not_right_l = shoes_not_right_l;
    }

    public String getNail_l() {
        return this.nail_l;
    }

    public void setNail_l(String nail_l) {
        this.nail_l = nail_l;
    }

    public String getLimp_l() {
        return this.limp_l;
    }

    public void setLimp_l(String limp_l) {
        this.limp_l = limp_l;
    }

    public String getGangrene_l() {
        return this.gangrene_l;
    }

    public void setGangrene_l(String gangrene_l) {
        this.gangrene_l = gangrene_l;
    }

    public String getBlisters_l() {
        return this.blisters_l;
    }

    public void setBlisters_l(String blisters_l) {
        this.blisters_l = blisters_l;
    }

    public String getCallus_l() {
        return this.callus_l;
    }

    public void setCallus_l(String callus_l) {
        this.callus_l = callus_l;
    }

    public String getChap_l() {
        return this.chap_l;
    }

    public void setChap_l(String chap_l) {
        this.chap_l = chap_l;
    }

    public String getSkin_color_l() {
        return this.skin_color_l;
    }

    public void setSkin_color_l(String skin_color_l) {
        this.skin_color_l = skin_color_l;
    }

    public String getMould_infection_l() {
        return this.mould_infection_l;
    }

    public void setMould_infection_l(String mould_infection_l) {
        this.mould_infection_l = mould_infection_l;
    }

    public String getSkin_humidity_l() {
        return this.skin_humidity_l;
    }

    public void setSkin_humidity_l(String skin_humidity_l) {
        this.skin_humidity_l = skin_humidity_l;
    }

    public String getFoot_unusually_l() {
        return this.foot_unusually_l;
    }

    public void setFoot_unusually_l(String foot_unusually_l) {
        this.foot_unusually_l = foot_unusually_l;
    }

    public String getResting_l() {
        return this.resting_l;
    }

    public void setResting_l(String resting_l) {
        this.resting_l = resting_l;
    }

    public String getLegs_skin_temperature_l() {
        return this.legs_skin_temperature_l;
    }

    public void setLegs_skin_temperature_l(String legs_skin_temperature_l) {
        this.legs_skin_temperature_l = legs_skin_temperature_l;
    }

    public String getFoot_ulcer_his_l() {
        return this.foot_ulcer_his_l;
    }

    public void setFoot_ulcer_his_l(String foot_ulcer_his_l) {
        this.foot_ulcer_his_l = foot_ulcer_his_l;
    }

    public String getAmputation_his_l() {
        return this.amputation_his_l;
    }

    public void setAmputation_his_l(String amputation_his_l) {
        this.amputation_his_l = amputation_his_l;
    }

    public String getFoot_deformity_l() {
        return this.foot_deformity_l;
    }

    public void setFoot_deformity_l(String foot_deformity_l) {
        this.foot_deformity_l = foot_deformity_l;
    }

    public String getDorsum_foot_arteriopalmus_l() {
        return this.dorsum_foot_arteriopalmus_l;
    }

    public void setDorsum_foot_arteriopalmus_l(String dorsum_foot_arteriopalmus_l) {
        this.dorsum_foot_arteriopalmus_l = dorsum_foot_arteriopalmus_l;
    }

    public String getPosterior_tibial_arteriopalmus_l() {
        return this.posterior_tibial_arteriopalmus_l;
    }

    public void setPosterior_tibial_arteriopalmus_l(String posterior_tibial_arteriopalmus_l) {
        this.posterior_tibial_arteriopalmus_l = posterior_tibial_arteriopalmus_l;
    }

    public String getAbi_l() {
        return this.abi_l;
    }

    public void setAbi_l(String abi_l) {
        this.abi_l = abi_l;
    }

    public String getLegs_vasculopathy_l() {
        return this.legs_vasculopathy_l;
    }

    public void setLegs_vasculopathy_l(String legs_vasculopathy_l) {
        this.legs_vasculopathy_l = legs_vasculopathy_l;
    }

    public String getNylon_pressure_feel_l() {
        return this.nylon_pressure_feel_l;
    }

    public void setNylon_pressure_feel_l(String nylon_pressure_feel_l) {
        this.nylon_pressure_feel_l = nylon_pressure_feel_l;
    }

    public String getNeedle_position_sense_l() {
        return this.needle_position_sense_l;
    }

    public void setNeedle_position_sense_l(String needle_position_sense_l) {
        this.needle_position_sense_l = needle_position_sense_l;
    }

    public String getLint_touch_l() {
        return this.lint_touch_l;
    }

    public void setLint_touch_l(String lint_touch_l) {
        this.lint_touch_l = lint_touch_l;
    }

    public String getVibration_sense_l() {
        return this.vibration_sense_l;
    }

    public void setVibration_sense_l(String vibration_sense_l) {
        this.vibration_sense_l = vibration_sense_l;
    }

    public String getThermesthesia_l() {
        return this.thermesthesia_l;
    }

    public void setThermesthesia_l(String thermesthesia_l) {
        this.thermesthesia_l = thermesthesia_l;
    }

    public String getAnkle_reflex_l() {
        return this.ankle_reflex_l;
    }

    public void setAnkle_reflex_l(String ankle_reflex_l) {
        this.ankle_reflex_l = ankle_reflex_l;
    }

    public String getVpt_l() {
        return this.vpt_l;
    }

    public void setVpt_l(String vpt_l) {
        this.vpt_l = vpt_l;
    }

    public String getLegs_neuropathy_l() {
        return this.legs_neuropathy_l;
    }

    public void setLegs_neuropathy_l(String legs_neuropathy_l) {
        this.legs_neuropathy_l = legs_neuropathy_l;
    }

    public String getFollow_rate_advice() {
        return this.follow_rate_advice;
    }

    public void setFollow_rate_advice(String follow_rate_advice) {
        this.follow_rate_advice = follow_rate_advice;
    }

    public String getRecord_dt() {
        return this.record_dt;
    }

    public void setRecord_dt(String record_dt) {
        this.record_dt = record_dt;
    }

    public String getIs_valid() {
        return this.is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

    public String getSave_type() {
        return this.save_type;
    }

    public void setSave_type(String save_type) {
        this.save_type = save_type;
    }

    public String getWound_l() {
        return wound_l;
    }

    public void setWound_l(String wound_l) {
        this.wound_l = wound_l;
    }

    public String getWound_r() {
        return wound_r;
    }

    public void setWound_r(String wound_r) {
        this.wound_r = wound_r;
    }

    public String getWound_l_access() {
        return wound_l_access;
    }

    public void setWound_l_access(String wound_l_access) {
        this.wound_l_access = wound_l_access;
    }

    public String getWound_r_access() {
        return wound_r_access;
    }

    public void setWound_r_access(String wound_r_access) {
        this.wound_r_access = wound_r_access;
    }

    public String getClinical_integral_l() {
        return this.clinical_integral_l;
    }

    public void setClinical_integral_l(String clinical_integral_l) {
        this.clinical_integral_l = clinical_integral_l;
    }

    public String getClinical_result_l() {
        return this.clinical_result_l;
    }

    public void setClinical_result_l(String clinical_result_l) {
        this.clinical_result_l = clinical_result_l;
    }

    public String getClinical_diagnoindex_l() {
        return this.clinical_diagnoindex_l;
    }

    public void setClinical_diagnoindex_l(String clinical_diagnoindex_l) {
        this.clinical_diagnoindex_l = clinical_diagnoindex_l;
    }

    public String getClinical_access_des_l() {
        return this.clinical_access_des_l;
    }

    public void setClinical_access_des_l(String clinical_access_des_l) {
        this.clinical_access_des_l = clinical_access_des_l;
    }

    public String getClinical_integral_r() {
        return this.clinical_integral_r;
    }

    public void setClinical_integral_r(String clinical_integral_r) {
        this.clinical_integral_r = clinical_integral_r;
    }

    public String getClinical_result_r() {
        return this.clinical_result_r;
    }

    public void setClinical_result_r(String clinical_result_r) {
        this.clinical_result_r = clinical_result_r;
    }

    public String getClinical_diagnoindex_r() {
        return this.clinical_diagnoindex_r;
    }

    public void setClinical_diagnoindex_r(String clinical_diagnoindex_r) {
        this.clinical_diagnoindex_r = clinical_diagnoindex_r;
    }

    public String getClinical_access_des_r() {
        return this.clinical_access_des_r;
    }

    public void setClinical_access_des_r(String clinical_access_des_r) {
        this.clinical_access_des_r = clinical_access_des_r;
    }

    public String getClinical_lastintegral_l() {
        return this.clinical_lastintegral_l;
    }

    public void setClinical_lastintegral_l(String clinical_lastintegral_l) {
        this.clinical_lastintegral_l = clinical_lastintegral_l;
    }

    public String getClinical_lastintegral_r() {
        return this.clinical_lastintegral_r;
    }

    public void setClinical_lastintegral_r(String clinical_lastintegral_r) {
        this.clinical_lastintegral_r = clinical_lastintegral_r;
    }

    public String getClinical_l() {
        return this.clinical_l;
    }

    public void setClinical_l(String clinical_l) {
        this.clinical_l = clinical_l;
    }

    public String getClinical_r() {
        return this.clinical_r;
    }

    public void setClinical_r(String clinical_r) {
        this.clinical_r = clinical_r;
    }

    public String getBlood_vessel_report_id() {
        return blood_vessel_report_id;
    }

    public void setBlood_vessel_report_id(String blood_vessel_report_id) {
        this.blood_vessel_report_id = blood_vessel_report_id;
    }

    public String getBlood_vessel_report_pdf_url() {
        return blood_vessel_report_pdf_url;
    }

    public void setBlood_vessel_report_pdf_url(String blood_vessel_report_pdf_url) {
        this.blood_vessel_report_pdf_url = blood_vessel_report_pdf_url;
    }

    public String getNerve_report_id() {
        return nerve_report_id;
    }

    public void setNerve_report_id(String nerve_report_id) {
        this.nerve_report_id = nerve_report_id;
    }

    public String getNerve_report_pdf_url() {
        return nerve_report_pdf_url;
    }

    public void setNerve_report_pdf_url(String nerve_report_pdf_url) {
        this.nerve_report_pdf_url = nerve_report_pdf_url;
    }

    public String getNerve_report_name() {
        return nerve_report_name;
    }

    public void setNerve_report_name(String nerve_report_name) {
        this.nerve_report_name = nerve_report_name;
    }

    public String getBlood_vessel_report_name() {
        return blood_vessel_report_name;
    }

    public void setBlood_vessel_report_name(String blood_vessel_report_name) {
        this.blood_vessel_report_name = blood_vessel_report_name;
    }

    public String getBlood_vessel_report_pdf_img_url() {
        return blood_vessel_report_pdf_img_url;
    }

    public void setBlood_vessel_report_pdf_img_url(String blood_vessel_report_pdf_img_url) {
        this.blood_vessel_report_pdf_img_url = blood_vessel_report_pdf_img_url;
    }

    public String getNerve_report_pdf_img_url() {
        return nerve_report_pdf_img_url;
    }

    public void setNerve_report_pdf_img_url(String nerve_report_pdf_img_url) {
        this.nerve_report_pdf_img_url = nerve_report_pdf_img_url;
    }

    public String getStudio_id() {
        return studio_id;
    }

    public void setStudio_id(String studio_id) {
        this.studio_id = studio_id;
    }

    public String getIs_sms() {
        return is_sms;
    }

    public void setIs_sms(String is_sms) {
        this.is_sms = is_sms;
    }

    public String getSms_date() {
        return sms_date;
    }

    public void setSms_date(String sms_date) {
        this.sms_date = sms_date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVpt_wh_l() {
        return vpt_wh_l;
    }

    public void setVpt_wh_l(String vpt_wh_l) {
        this.vpt_wh_l = vpt_wh_l;
    }

    public String getVpt_wh_r() {
        return vpt_wh_r;
    }

    public void setVpt_wh_r(String vpt_wh_r) {
        this.vpt_wh_r = vpt_wh_r;
    }

    public String getVpt_zb_l() {
        return vpt_zb_l;
    }

    public void setVpt_zb_l(String vpt_zb_l) {
        this.vpt_zb_l = vpt_zb_l;
    }

    public String getVpt_zb_r() {
        return vpt_zb_r;
    }

    public void setVpt_zb_r(String vpt_zb_r) {
        this.vpt_zb_r = vpt_zb_r;
    }

    public String getVpt_mz_l() {
        return vpt_mz_l;
    }

    public void setVpt_mz_l(String vpt_mz_l) {
        this.vpt_mz_l = vpt_mz_l;
    }

    public String getVpt_mz_r() {
        return vpt_mz_r;
    }

    public void setVpt_mz_r(String vpt_mz_r) {
        this.vpt_mz_r = vpt_mz_r;
    }

    public String getVpt_xz_l() {
        return vpt_xz_l;
    }

    public void setVpt_xz_l(String vpt_xz_l) {
        this.vpt_xz_l = vpt_xz_l;
    }

    public String getVpt_xz_r() {
        return vpt_xz_r;
    }

    public void setVpt_xz_r(String vpt_xz_r) {
        this.vpt_xz_r = vpt_xz_r;
    }

    public String getVpt_zd_l() {
        return vpt_zd_l;
    }

    public void setVpt_zd_l(String vpt_zd_l) {
        this.vpt_zd_l = vpt_zd_l;
    }

    public String getVpt_zd_r() {
        return vpt_zd_r;
    }

    public void setVpt_zd_r(String vpt_zd_r) {
        this.vpt_zd_r = vpt_zd_r;
    }

    public String getVpt_nh_l() {
        return vpt_nh_l;
    }

    public void setVpt_nh_l(String vpt_nh_l) {
        this.vpt_nh_l = vpt_nh_l;
    }

    public String getVpt_nh_r() {
        return vpt_nh_r;
    }

    public void setVpt_nh_r(String vpt_nh_r) {
        this.vpt_nh_r = vpt_nh_r;
    }

    public String getYz_hs_crp() {
        return yz_hs_crp;
    }

    public void setYz_hs_crp(String yz_hs_crp) {
        this.yz_hs_crp = yz_hs_crp;
    }

    public String getYz_esr() {
        return yz_esr;
    }

    public void setYz_esr(String yz_esr) {
        this.yz_esr = yz_esr;
    }

    public String getYz_il_6() {
        return yz_il_6;
    }

    public void setYz_il_6(String yz_il_6) {
        this.yz_il_6 = yz_il_6;
    }

    public String getYz_ct() {
        return yz_ct;
    }

    public void setYz_ct(String yz_ct) {
        this.yz_ct = yz_ct;
    }

    public String getYz_leucocyte_count() {
        return yz_leucocyte_count;
    }

    public void setYz_leucocyte_count(String yz_leucocyte_count) {
        this.yz_leucocyte_count = yz_leucocyte_count;
    }

    public String getYz_gra() {
        return yz_gra;
    }

    public void setYz_gra(String yz_gra) {
        this.yz_gra = yz_gra;
    }

    public String getQt_hgb() {
        return qt_hgb;
    }

    public void setQt_hgb(String qt_hgb) {
        this.qt_hgb = qt_hgb;
    }

    public String getQt_act() {
        return qt_act;
    }

    public void setQt_act(String qt_act) {
        this.qt_act = qt_act;
    }

    public String getQt_ast() {
        return qt_ast;
    }

    public void setQt_ast(String qt_ast) {
        this.qt_ast = qt_ast;
    }

    public String getQt_acb() {
        return qt_acb;
    }

    public void setQt_acb(String qt_acb) {
        this.qt_acb = qt_acb;
    }

    public String getQt_bun() {
        return qt_bun;
    }

    public void setQt_bun(String qt_bun) {
        this.qt_bun = qt_bun;
    }

    public String getQt_cac() {
        return qt_cac;
    }

    public void setQt_cac(String qt_cac) {
        this.qt_cac = qt_cac;
    }

    public String getQt_ketone_body() {
        return qt_ketone_body;
    }

    public void setQt_ketone_body(String qt_ketone_body) {
        this.qt_ketone_body = qt_ketone_body;
    }

    public String getQt_potassium() {
        return qt_potassium;
    }

    public void setQt_potassium(String qt_potassium) {
        this.qt_potassium = qt_potassium;
    }

    public String getQt_sodium() {
        return qt_sodium;
    }

    public void setQt_sodium(String qt_sodium) {
        this.qt_sodium = qt_sodium;
    }

    public String getQt_chloride() {
        return qt_chloride;
    }

    public void setQt_chloride(String qt_chloride) {
        this.qt_chloride = qt_chloride;
    }

    public String getQt_calcium() {
        return qt_calcium;
    }

    public void setQt_calcium(String qt_calcium) {
        this.qt_calcium = qt_calcium;
    }

    public String getFoot_type() {
        return foot_type;
    }

    public void setFoot_type(String foot_type) {
        this.foot_type = foot_type;
    }
}