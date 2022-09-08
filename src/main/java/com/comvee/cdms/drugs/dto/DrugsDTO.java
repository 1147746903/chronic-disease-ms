package com.comvee.cdms.drugs.dto;

/**
 * @author wyc
 * @date 2019/10/31 16:27
 */
public class DrugsDTO {
    /**
     * 药物名称
     */
    private String drugName;
    /**
     * 药物类型类型1-降糖药，2-降脂药，3-降压药，4-胰岛素，
     * 5-常用药（用户添加） 6 促泌剂 7 双胍类 8 AGI 9 增敏剂
     * 10 DPP-IV 11 胰岛素 12 GLP-1 13 SGLT2 14 ACEI 15 CCB 16 抗PLT
     * 17 ARB 18 调脂药 19 补充 (6-19为南京)
     */
    private Integer type;

    /**
     * 所属编号（默认-1系统/医生编号/患者编号/科室编号/医院编号）
     */
    private String belongId;

    private Integer belongType;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public Integer getBelongType() {
        return belongType;
    }

    public void setBelongType(Integer belongType) {
        this.belongType = belongType;
    }
}
