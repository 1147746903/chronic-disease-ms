package com.comvee.cdms.doctor.po;

import java.io.Serializable;
/**
 * 
 * @author 李左河
 *
 */
public class DoctorRelationPO implements Serializable {

    /**
     * sid
     */
    private String sid;

    /**
     * 医生id
     * doctor_id
     */
    private String doctorId;

    /**
     * 外键
     * foreign_id
     */
    private String foreignId;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    @Override
    public String toString() {
        return "DoctorRelationPO{" +
                "doctorId='" + doctorId + '\'' +
                '}';
    }
}
