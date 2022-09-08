package com.comvee.cdms.doctor.po;

import java.io.Serializable;

/**
 * 医生村所关联表(DoctorCommitteeRelation)实体类
 *
 * @author lr
 * @since 2022-07-29
 */
public class DoctorCommitteeRelationPO implements Serializable {
    private static final long serialVersionUID = 900767319598176851L;

    private String sid;
    /**
     * 医生id
     */
    private String doctorId;
    /**
     * 社区id
     */
    private String committeeId;

    private String insertDt;

    private String updateDt;

    private Integer isValid;


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

    public String getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(String committeeId) {
        this.committeeId = committeeId;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

}
