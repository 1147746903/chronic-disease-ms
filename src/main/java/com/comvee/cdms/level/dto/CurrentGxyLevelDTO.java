package com.comvee.cdms.level.dto;

/**
 * @author wyc
 * @date 2019/12/13 9:52
 */
public class CurrentGxyLevelDTO {
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 医院id
     */
    private String hospitalId;
    /**
     * 疾病类型 1 高血压
     */
    private Integer levelType;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }
}
