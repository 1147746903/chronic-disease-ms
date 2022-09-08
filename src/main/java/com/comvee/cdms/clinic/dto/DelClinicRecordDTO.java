package com.comvee.cdms.clinic.dto;

/**
 * @author wyc
 * @date 2019/11/30 13:20
 */
public class DelClinicRecordDTO {

    private String sid;

    private String startDt;

    private String endDt;

    private String clinicId;

    /**
     * 筛查类型1:血糖情况,2:血脂情况,3:常规检查,4:营养情况,5:感染情况,6:肾功能检查7:心功能能检查8:下肢血运情况
     */
    private Integer clinicType;

    public Integer getClinicType() {
        return clinicType;
    }

    public void setClinicType(Integer clinicType) {
        this.clinicType = clinicType;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }
}
