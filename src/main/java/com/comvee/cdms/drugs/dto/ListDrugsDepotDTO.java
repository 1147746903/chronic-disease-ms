package com.comvee.cdms.drugs.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author: wangyx
 * @date: 2018/12/27
 */
public class ListDrugsDepotDTO {

    private String memberId;

    private String drugName;

    private String type;

    /**
     * 所属类型（默认0系统1医生2患者3科室4医院）
     */
    private int belongType;

    /**
     * 所属编号（默认-1系统/医生编号/患者编号/科室编号/医院编号）
     */
    private String belongId;

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public int getBelongType() {
        return belongType;
    }

    public void setBelongType(int belongType) {
        this.belongType = belongType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }
}
