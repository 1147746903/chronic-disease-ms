package com.comvee.cdms.member.dto;

/**
 * @author: suyz
 * @date: 2018/10/8
 */
public class AddMemberMapperDTO {

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 患者姓名
     * member_name
     */
    private String memberName;

    /**
     * 患者姓名拼音
     * member_name_py
     */
    private String memberNamePy;

    /**
     * 头像地址
     * pic_url
     */
    private String picUrl;

    /**
     * 手机号
     * mobile_phone
     */
    private String mobilePhone;

    /**
     * 性别 1男2女
     * sex
     */
    private String sex;

    /**
     * 出生日期
     * birthday
     */
    private String birthday;

    /**
     * 身份证号码
     * id_card
     */
    private String idCard;

    /**
     * 职业
     * profession
     */
    private String profession;

    /**
     * 糖尿病类型：1型SUGAR_TYPE_001)、2型(SUGAR_TYPE_002)、妊娠(SUGAR_TYPE_003)、其他(SUGAR_TYPE_004)	当糖尿病诊断选项选“确诊有”时出现糖尿病类型选项	是
     * diabetes_type
     */
    private String diabetesType;

    /**
     * 确诊日期
     * diabetes_date
     */
    private String diabetesDate;

    /**
     * 身高
     * height
     */
    private String height;

    /**
     * 体重
     * weight
     */
    private String weight;

    /**
     * bmi
     * bmi
     */
    private String bmi;

    /**
     * 收缩压
     * blood_pressure
     */
    private String sbpPressure;
    /**
     * 舒张压
     * blood_pressure
     */
    private String dbpPressure;

    /**
     * 国家
     * country
     */
    private String country;

    /**
     * 详细地址
     * address
     */
    private String address;

    /**
     * 高血压:1有、2无
     * essential_hyp
     */
    private String essentialHyp;

    /**
     * 是否有冠心病:确诊有(QZ01)、确诊无(QZ02)、未评估(QZ03)、疑似（界面中不显示）(QZ04)
     * chd
     */
    private String chd;

    /**
     * 是否有糖尿病肾病:确诊有SB01、确诊无SB02、未确诊  未评估SB03疑似SB04
     * nephropathy
     */
    private String nephropathy;

    /**
     * 劳动强度 RCHDQD01：轻体力劳动 RCHDQD02：中体力劳动 RCHDQD03:重体力劳动 RCHDQD04:非劳动/卧床
     * labour_intensity
     */
    private String labourIntensity;

    /**
     * 心率
     * hreat_rate
     */
    private String hreatRate;

    /**
     * 患者详细信息
     * member_info
     */
    private String memberInfo;
    /**
     * 是否有糖尿病 1:是 2:否
     */
    private String isDiabetes;

    /**
     * 患者来源 1 web端医生添加  2 APP医生添加 3  小程序添加 4 筛查系统同步患者
     */
    private Integer memberSource;

    /**
     *高血压类型 :原发性高血压:HYP_TYPE_001  继发性高血压:HYP_TYPE_002 其他:HYP_TYPE_003
     */
    private String hypType;

    /**
     * 高血压确诊日期
     * diabetes_date
     */
    private String hypDate;

    private String fat;

    private String memberTag;

    private String treatment;

    /**
     * 拼音缩写
     */
    private String memberNamePys;
    private String socialCard;

    private String committeeId;//社区id
    private String committeeName;

    public String getMemberNamePys() {
        return memberNamePys;
    }

    public void setMemberNamePys(String memberNamePys) {
        this.memberNamePys = memberNamePys;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(String isDiabetes) {
        this.isDiabetes = isDiabetes;
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

    public String getMemberNamePy() {
        return memberNamePy;
    }

    public void setMemberNamePy(String memberNamePy) {
        this.memberNamePy = memberNamePy;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getDiabetesDate() {
        return diabetesDate;
    }

    public void setDiabetesDate(String diabetesDate) {
        this.diabetesDate = diabetesDate;
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

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getSbpPressure() {
        return sbpPressure;
    }

    public void setSbpPressure(String sbpPressure) {
        this.sbpPressure = sbpPressure;
    }

    public String getDbpPressure() {
        return dbpPressure;
    }

    public void setDbpPressure(String dbpPressure) {
        this.dbpPressure = dbpPressure;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEssentialHyp() {
        return essentialHyp;
    }

    public void setEssentialHyp(String essentialHyp) {
        this.essentialHyp = essentialHyp;
    }

    public String getChd() {
        return chd;
    }

    public void setChd(String chd) {
        this.chd = chd;
    }

    public String getNephropathy() {
        return nephropathy;
    }

    public void setNephropathy(String nephropathy) {
        this.nephropathy = nephropathy;
    }

    public String getLabourIntensity() {
        return labourIntensity;
    }

    public void setLabourIntensity(String labourIntensity) {
        this.labourIntensity = labourIntensity;
    }

    public String getHreatRate() {
        return hreatRate;
    }

    public void setHreatRate(String hreatRate) {
        this.hreatRate = hreatRate;
    }

    public String getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(String memberInfo) {
        this.memberInfo = memberInfo;
    }

    public Integer getMemberSource() {
        return memberSource;
    }

    public void setMemberSource(Integer memberSource) {
        this.memberSource = memberSource;
    }

    public String getHypType() {
        return hypType;
    }

    public void setHypType(String hypType) {
        this.hypType = hypType;
    }

    public String getHypDate() {
        return hypDate;
    }

    public void setHypDate(String hypDate) {
        this.hypDate = hypDate;
    }

    public String getMemberTag() {
        return memberTag;
    }

    public void setMemberTag(String memberTag) {
        this.memberTag = memberTag;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getSocialCard() {
        return socialCard;
    }

    public void setSocialCard(String socialCard) {
        this.socialCard = socialCard;
    }

    public String getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(String committeeId) {
        this.committeeId = committeeId;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }
}
