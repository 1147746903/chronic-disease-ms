package com.comvee.cdms.member.dto;


/**
 * @author wyc
 * @date 2019/10/11 15:33
 */
public class MemberRangeDTO {
    /**
     * 患者id
     */
    private String memberId;

    /**
     * 性别 1男2女
     */
    private Integer sex;
    /**
     * 是否有冠心病:确诊有(QZ01)、确诊无(QZ02)、未评估(QZ03)、疑似（界面中不显示）(QZ04)
     */
    private String chd;
    /**
     * 糖尿病类型：1型SUGAR_TYPE_001)、2型(SUGAR_TYPE_002)、妊娠(SUGAR_TYPE_003)、其他(SUGAR_TYPE_004)
     */
    private String diabetesType;
    /**
     * 高血压:1有、2无
     */
    private String essentialHyp;
    /**
     * 生日
     */
    private String birthday;

    /**
     * 是否有糖尿病 1 有 2 无
     */
    private String isDiabetes;

    /**
     * 高血压分层
     */
    private Integer hypLayer;

    /**
     * CKD分期
     */
    private Integer ckd;

    /**
     * 是否有高血压并发症  1 确诊有 2 确诊无 3 疑似 4 未评估（有症状） 5 未评估(无症状)
     * 是否有高血压并发症  1 确诊有、疑似、有症状   2 确诊无、未评估、无症状
     */
    private Integer hypBfz;

    /**
     * 是否有糖尿病并发症 1 确诊有 2 确诊无
     */
    private Integer diabetesBfz;

    private String height;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getChd() {
        return chd;
    }

    public void setChd(String chd) {
        this.chd = chd;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getEssentialHyp() {
        return essentialHyp;
    }

    public void setEssentialHyp(String essentialHyp) {
        this.essentialHyp = essentialHyp;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(String isDiabetes) {
        this.isDiabetes = isDiabetes;
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
