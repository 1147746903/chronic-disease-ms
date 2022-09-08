package com.comvee.cdms.follow.bo;

public class HypJwFollowBodyBO {

    private String doctorId;
    private String memberId;
    //基本信息

    /**
     * 姓名
     */
    private String memberName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private String birthday;
    /*
    高血压类型
     */
    private String hypType;
    /**
     * 随访方式
     */
    private String followType;
    /**
     * 随访日期
     */
    private String followDate;

    //症状
    /**
     * 症状
     * GXYZZ01 - GXYZZ10
     */
    private String hypSymptom;
    /**
     * 症状-其他
     */
    private String hypSymptomOtherText;

    //体征

    /**
     *  身高
     */
    private String height;
    /**
     * 体重
     */
    private String weight;
    /**
     * bmi
     */
    private Float bmi;
    /**
     * 心率
     */
    private Integer heartRate;
    /**
     * 下次目标体重
     */
    private String targetWeight;
    /**
     * 目标bmi
     */
    private String targetBmi;
    /**
     * 收缩压
     */
    private Integer sbp;
    /**
     * 舒张压
     */
    private Integer dbp;
    /**
     * 体征 - 其他
     */
    private String signOtherText;

    //生活方式

    /**
     * 吸烟量
     */
    private Integer smokeNum;
    /**
     * 饮酒量
     */
    private Integer drinkNum;
    /**
     * 运动频率
     */
    private String sportRate;
    /**-
     * 目标吸烟量
     */
    private Integer targetSmokeNum;
    /**
     * 目标饮酒量
     */
    private Integer targetDrinkNum;
    /**
     * 目标运动频率
     */
    private String targetSportRate;
    /***
     * 运动时长
     */
    private String sportTime;
    /**
     * 遵医行为
     */
    private String complianceAction;
    /**
     * 心里调整
     */
    private String heartAdjust;
    /**
     * 目标运动时长
     */
    private String targetSportTime;
    /**
     * 盐摄入量
     */
    private String saltDay;

    //辅助检查

    /**
     * 服药依从性
     */
    private String drugCompliance;
    /**
     * 此次随访分类
     */
    private String followClassify;
    /**
     * 药物不良反应
     */
    private String sideEffects;
    /**
     * 药物不良反应 具体内容
     */
    private String sideEffectsDetail;
    /**
     * 辅助检查 -其他
     */
    private String assistOtherText;

    //用药情况
    /**
     * 用药详情 json
     */
    private String drugDetail;

    //转诊
    /**
     * 转诊原因
     */
    private String referralReason;
    /**
     * 转诊机构
     */
    private String referralOrgan;
    /***
     * 转诊科别
     */
    private String referralDepart;

    //随访日期
    /**
     * 下次随访时间
     */
    private String nextFollowDate;
    /**
     * 随访医生
     */
    private String followDoctorName;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHypType() {
        return hypType;
    }

    public void setHypType(String hypType) {
        this.hypType = hypType;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public String getFollowDate() {
        return followDate;
    }

    public void setFollowDate(String followDate) {
        this.followDate = followDate;
    }

    public String getHypSymptom() {
        return hypSymptom;
    }

    public void setHypSymptom(String hypSymptom) {
        this.hypSymptom = hypSymptom;
    }

    public String getHypSymptomOtherText() {
        return hypSymptomOtherText;
    }

    public void setHypSymptomOtherText(String hypSymptomOtherText) {
        this.hypSymptomOtherText = hypSymptomOtherText;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Float getBmi() {
        return bmi;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public String getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(String targetWeight) {
        this.targetWeight = targetWeight;
    }

    public String getTargetBmi() {
        return targetBmi;
    }

    public void setTargetBmi(String targetBmi) {
        this.targetBmi = targetBmi;
    }

    public Integer getSbp() {
        return sbp;
    }

    public void setSbp(Integer sbp) {
        this.sbp = sbp;
    }

    public Integer getDbp() {
        return dbp;
    }

    public void setDbp(Integer dbp) {
        this.dbp = dbp;
    }

    public String getSignOtherText() {
        return signOtherText;
    }

    public void setSignOtherText(String signOtherText) {
        this.signOtherText = signOtherText;
    }

    public Integer getSmokeNum() {
        return smokeNum;
    }

    public void setSmokeNum(Integer smokeNum) {
        this.smokeNum = smokeNum;
    }

    public Integer getDrinkNum() {
        return drinkNum;
    }

    public void setDrinkNum(Integer drinkNum) {
        this.drinkNum = drinkNum;
    }

    public String getSportRate() {
        return sportRate;
    }

    public void setSportRate(String sportRate) {
        this.sportRate = sportRate;
    }

    public Integer getTargetSmokeNum() {
        return targetSmokeNum;
    }

    public void setTargetSmokeNum(Integer targetSmokeNum) {
        this.targetSmokeNum = targetSmokeNum;
    }

    public Integer getTargetDrinkNum() {
        return targetDrinkNum;
    }

    public void setTargetDrinkNum(Integer targetDrinkNum) {
        this.targetDrinkNum = targetDrinkNum;
    }

    public String getTargetSportRate() {
        return targetSportRate;
    }

    public void setTargetSportRate(String targetSportRate) {
        this.targetSportRate = targetSportRate;
    }

    public String getSportTime() {
        return sportTime;
    }

    public void setSportTime(String sportTime) {
        this.sportTime = sportTime;
    }

    public String getComplianceAction() {
        return complianceAction;
    }

    public void setComplianceAction(String complianceAction) {
        this.complianceAction = complianceAction;
    }

    public String getHeartAdjust() {
        return heartAdjust;
    }

    public void setHeartAdjust(String heartAdjust) {
        this.heartAdjust = heartAdjust;
    }

    public String getTargetSportTime() {
        return targetSportTime;
    }

    public void setTargetSportTime(String targetSportTime) {
        this.targetSportTime = targetSportTime;
    }

    public String getSaltDay() {
        return saltDay;
    }

    public void setSaltDay(String saltDay) {
        this.saltDay = saltDay;
    }

    public String getDrugCompliance() {
        return drugCompliance;
    }

    public void setDrugCompliance(String drugCompliance) {
        this.drugCompliance = drugCompliance;
    }

    public String getFollowClassify() {
        return followClassify;
    }

    public void setFollowClassify(String followClassify) {
        this.followClassify = followClassify;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getSideEffectsDetail() {
        return sideEffectsDetail;
    }

    public void setSideEffectsDetail(String sideEffectsDetail) {
        this.sideEffectsDetail = sideEffectsDetail;
    }

    public String getAssistOtherText() {
        return assistOtherText;
    }

    public void setAssistOtherText(String assistOtherText) {
        this.assistOtherText = assistOtherText;
    }

    public String getDrugDetail() {
        return drugDetail;
    }

    public void setDrugDetail(String drugDetail) {
        this.drugDetail = drugDetail;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public void setReferralReason(String referralReason) {
        this.referralReason = referralReason;
    }

    public String getReferralOrgan() {
        return referralOrgan;
    }

    public void setReferralOrgan(String referralOrgan) {
        this.referralOrgan = referralOrgan;
    }

    public String getReferralDepart() {
        return referralDepart;
    }

    public void setReferralDepart(String referralDepart) {
        this.referralDepart = referralDepart;
    }

    public String getNextFollowDate() {
        return nextFollowDate;
    }

    public void setNextFollowDate(String nextFollowDate) {
        this.nextFollowDate = nextFollowDate;
    }

    public String getFollowDoctorName() {
        return followDoctorName;
    }

    public void setFollowDoctorName(String followDoctorName) {
        this.followDoctorName = followDoctorName;
    }
}
