package com.comvee.cdms.drugs.po;


/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public class DrugsDepotPO {

    /**
     * 唯一标识
     */
    private String id;
    /**
     * 药品名
     */
    private String drugName;
    /**
     * 剂量/规格
     */
    private String dose;
    /**
     * 单位
     */
    private String unit;
    /**
     * 类型1-降糖药，2-降脂药，3-降压药，4-胰岛素，5-常用药（用户添加）
     */
    private String type;
    /**
     * 单价
     */
    private String price;
    /**
     * 首字母缩写
     */
    private String initials;
    /**
     * 拼音
     */
    private String pinyin;
    /**
     * 药品说明
     */
    private String description;
    /**
     * 药品用法
     */
    private String drugUsage;
    /**
     * 药品副作用
     */
    private String sideEffect;
    /**
     * 不良反应
     */
    private String adverseReactions;
    /**
     * 用药禁忌
     */
    private String contraindications;
    /**
     * 注意事项
     */
    private String precautions;
    /**
     * 用户自己添加就保存成员id
     */
    private String memberId;
    /**
     * 是否有效
     */
    private String isValid;
    /**
     * 所属类型（默认0系统1医生2患者3科室4医院）
     */
    private int belongType = 0;
    /**
     * 所属编号（默认-1系统/医生编号/患者编号/科室编号/医院编号）
     */
    private String belongId = "-1";
    /**
     * 插入时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDrugUsage() {
        return drugUsage;
    }

    public void setDrugUsage(String drugUsage) {
        this.drugUsage = drugUsage;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }

    public String getAdverseReactions() {
        return adverseReactions;
    }

    public void setAdverseReactions(String adverseReactions) {
        this.adverseReactions = adverseReactions;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public void setBelongType(int belongType) {
        this.belongType = belongType;
    }

    public int getBelongType() {
        return belongType;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getBelongId() {
        return belongId;
    }
}