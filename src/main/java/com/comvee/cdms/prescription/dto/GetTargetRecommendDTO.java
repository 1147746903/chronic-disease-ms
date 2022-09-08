package com.comvee.cdms.prescription.dto;

/**
 * 获取控制目标推荐 请求参数
 */
public class GetTargetRecommendDTO {

    /**
     * 处方id
     */
    private String prescriptionId;

    private String memberId;

    /**
     * 处方类型
     */
    private Integer eohType;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 高血压类型 HYP_TYPE_001:原发性高血压 HYP_TYPE_002:继发性高血压 HYP_TYPE_003:其他
     */
    private String hypType;
    /**
     * 体重
     */
    private String weight;
    /**
     * 身高
     */
    private String height;
    /**
     * 是否有冠心病
     */
    private String chd;
    /**
     *  //慢性肾脏病 MXSZB01:确诊有  MXSZB02:确诊无 MXSZB03:未评估 MXSZB04:疑似
     */
    private String slowNep;
    /**
     * 高血压分级分层
     */
    private Integer hypLayer;
    /**
     * CKD分期
     */
    private Integer ckd;
    /**
     * 是否有高血压并发症  1 确诊有 2 确诊无 3 疑似 4 未评估（有症状） 5 未评估(无症状)
     */
    private Integer hypBfz;

    /**
     * 是否有糖尿病并发症 1 确诊有 2 确诊无
     */
    private Integer diabetesBfz;

    /**
     * 糖尿病类型
     */
    private String diabetesType;

    /**
     * 是否有糖尿病 1 是 2 无
     */
    private String isDiabetes;

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getEohType() {
        return eohType;
    }

    public void setEohType(Integer eohType) {
        this.eohType = eohType;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getChd() {
        return chd;
    }

    public void setChd(String chd) {
        this.chd = chd;
    }

    public String getSlowNep() {
        return slowNep;
    }

    public void setSlowNep(String slowNep) {
        this.slowNep = slowNep;
    }

    public Integer getHypLayer() {
        return hypLayer;
    }

    public void setHypLayer(Integer hypLayer) {
        this.hypLayer = hypLayer;
    }

    public Integer getCkd() {
        return ckd;
    }

    public void setCkd(Integer ckd) {
        this.ckd = ckd;
    }

    public Integer getHypBfz() {
        return hypBfz;
    }

    public void setHypBfz(Integer hypBfz) {
        this.hypBfz = hypBfz;
    }

    public Integer getDiabetesBfz() {
        return diabetesBfz;
    }

    public void setDiabetesBfz(Integer diabetesBfz) {
        this.diabetesBfz = diabetesBfz;
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(String isDiabetes) {
        this.isDiabetes = isDiabetes;
    }
}
