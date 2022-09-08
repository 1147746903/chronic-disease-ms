/*
*
* @author wyc
* @date 2019-12-23
*/
package com.comvee.cdms.hospital.model.po;


public class HospitalRelatedPO {
    /**
     * 主键id
     */
    private String sid;

    /**
     * 医院id
     */
    private String hospitalId;

    /**
     * 上级医院id
     */
    private String upHospitalId;

    /**
     * 入库时间
     */
    private String insertDt;

    /**
     * 更新时间
     */
    private String modifyDt;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getUpHospitalId() {
        return upHospitalId;
    }

    public void setUpHospitalId(String upHospitalId) {
        this.upHospitalId = upHospitalId;
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
}