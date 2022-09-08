/*
*
* @author wyc
* @date 2019-11-29
*/
package com.comvee.cdms.clinic.po;


public class ClinicRecordPO {
    /**
     * 主键id
     */
    private String sid;

    /**
     * 筛查类型1:血糖情况,2:血脂情况,3:常规检查,4:营养情况,5:感染情况,6:肾功能检查7:心功能能检查8:下肢血运情况
     */
    private Integer clinicType;

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 临床检查id
     */
    private String clinicId;

    /**
     * 记录时间
     */
    private String recordDt;

    /**
     * 入库时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String modifyDt;

    /**
     * 是否有效 0:否 1:是
     */
    private Integer isValid;

    /**
     * 筛查数据
     */
    private String clinicData;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getClinicData() {
        return clinicData;
    }

    public void setClinicData(String clinicData) {
        this.clinicData = clinicData;
    }
}