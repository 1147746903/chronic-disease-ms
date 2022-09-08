package com.comvee.cdms.healthrecord.model.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_health_record
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class HealthRecordPO {
    /**
     * sid
     */
    private String sid;

    /**
     * member_id
     */
    private String memberId;

    /**
     * hospital_id
     */
    private String hospitalId;

    /**
     * checkup_date
     */
    private String checkupDate;

    /**
     * visit_type
     */
    private Integer visitType;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * valid
     */
    private Integer valid;

    private Integer jwSyncStatus;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_health_record.sid
     *
     * @return the value of t_health_record.sid
     *
     * @mbggenerated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_health_record.sid
     *
     * @param sid the value for t_health_record.sid
     *
     * @mbggenerated
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_health_record.member_id
     *
     * @return the value of t_health_record.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_health_record.member_id
     *
     * @param memberId the value for t_health_record.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_health_record.hospital_id
     *
     * @return the value of t_health_record.hospital_id
     *
     * @mbggenerated
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_health_record.hospital_id
     *
     * @param hospitalId the value for t_health_record.hospital_id
     *
     * @mbggenerated
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_health_record.checkup_date
     *
     * @return the value of t_health_record.checkup_date
     *
     * @mbggenerated
     */
    public String getCheckupDate() {
        return checkupDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_health_record.checkup_date
     *
     * @param checkupDate the value for t_health_record.checkup_date
     *
     * @mbggenerated
     */
    public void setCheckupDate(String checkupDate) {
        this.checkupDate = checkupDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_health_record.visit_type
     *
     * @return the value of t_health_record.visit_type
     *
     * @mbggenerated
     */
    public Integer getVisitType() {
        return visitType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_health_record.visit_type
     *
     * @param visitType the value for t_health_record.visit_type
     *
     * @mbggenerated
     */
    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_health_record.insert_dt
     *
     * @return the value of t_health_record.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_health_record.insert_dt
     *
     * @param insertDt the value for t_health_record.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_health_record.update_dt
     *
     * @return the value of t_health_record.update_dt
     *
     * @mbggenerated
     */
    public String getUpdateDt() {
        return updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_health_record.update_dt
     *
     * @param updateDt the value for t_health_record.update_dt
     *
     * @mbggenerated
     */
    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_health_record.valid
     *
     * @return the value of t_health_record.valid
     *
     * @mbggenerated
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_health_record.valid
     *
     * @param valid the value for t_health_record.valid
     *
     * @mbggenerated
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getJwSyncStatus() {
        return jwSyncStatus;
    }

    public void setJwSyncStatus(Integer jwSyncStatus) {
        this.jwSyncStatus = jwSyncStatus;
    }
}