package com.comvee.cdms.clinic.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author wyc
 * @date 2019/11/29 11:15
 */
public class AddClinicRecordDTO {
    /**
     * 筛查类型1:血糖情况,2:血脂情况,3:常规检查,4:营养情况,5:感染情况,6:肾功能检查7:心功能能检查8:下肢血运情况
     */
    @NotNull
    private Integer clinicType;

    /**
     * 患者id
     */
    @NotEmpty
    private String memberId;

    /**
     * 临床检查id
     */
    @NotEmpty
    private String clinicId;

    /**
     * 记录时间
     */
    private String recordDt;

    /**
     * 筛查数据
     */
    private String clinicData;

    public Integer getClinicType() {
        return clinicType;
    }

    public void setClinicType(Integer clinicType) {
        this.clinicType = clinicType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public String getClinicData() {
        return clinicData;
    }

    public void setClinicData(String clinicData) {
        this.clinicData = clinicData;
    }
}
