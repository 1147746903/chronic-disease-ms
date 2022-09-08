package com.comvee.cdms.sign.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_bmi
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class BmiPO {
    /**
     * sid
     */
    private String sid;

    /**
     * 体重
     * weight
     */
    private Float weight;

    /**
     * 身高
     * height
     */
    private Float height;

    /**
     * bmi
     */
    private String bmi;

    /**
     * 记录时间
     * record_dt
     */
    private String recordDt;

    /**
     * 入库时间
     * insert_dt
     */
    private String insertDt;

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * is_valid
     */
    private Integer isValid;

    /**
     * 来源
     * origin
     */
    private Integer origin;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * 操作者类型 1 用户 2 医护
     * operator_type
     */
    private Integer operatorType;

    /**
     * operator_id
     */
    private String operatorId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.sid
     *
     * @return the value of t_bmi.sid
     *
     * @mbggenerated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.sid
     *
     * @param sid the value for t_bmi.sid
     *
     * @mbggenerated
     */
    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.weight
     *
     * @return the value of t_bmi.weight
     *
     * @mbggenerated
     */
    public Float getWeight() {
        return weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.weight
     *
     * @param weight the value for t_bmi.weight
     *
     * @mbggenerated
     */
    public void setWeight(Float weight) {
        this.weight = weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.height
     *
     * @return the value of t_bmi.height
     *
     * @mbggenerated
     */
    public Float getHeight() {
        return height;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.height
     *
     * @param height the value for t_bmi.height
     *
     * @mbggenerated
     */
    public void setHeight(Float height) {
        this.height = height;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.bmi
     *
     * @return the value of t_bmi.bmi
     *
     * @mbggenerated
     */
    public String getBmi() {
        return bmi;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.bmi
     *
     * @param bmi the value for t_bmi.bmi
     *
     * @mbggenerated
     */
    public void setBmi(String bmi) {
        this.bmi = bmi == null ? null : bmi.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.record_dt
     *
     * @return the value of t_bmi.record_dt
     *
     * @mbggenerated
     */
    public String getRecordDt() {
        return recordDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.record_dt
     *
     * @param recordDt the value for t_bmi.record_dt
     *
     * @mbggenerated
     */
    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt == null ? null : recordDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.insert_dt
     *
     * @return the value of t_bmi.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.insert_dt
     *
     * @param insertDt the value for t_bmi.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.member_id
     *
     * @return the value of t_bmi.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.member_id
     *
     * @param memberId the value for t_bmi.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.is_valid
     *
     * @return the value of t_bmi.is_valid
     *
     * @mbggenerated
     */
    public Integer getIsValid() {
        return isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.is_valid
     *
     * @param isValid the value for t_bmi.is_valid
     *
     * @mbggenerated
     */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.origin
     *
     * @return the value of t_bmi.origin
     *
     * @mbggenerated
     */
    public Integer getOrigin() {
        return origin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.origin
     *
     * @param origin the value for t_bmi.origin
     *
     * @mbggenerated
     */
    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.update_dt
     *
     * @return the value of t_bmi.update_dt
     *
     * @mbggenerated
     */
    public String getUpdateDt() {
        return updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.update_dt
     *
     * @param updateDt the value for t_bmi.update_dt
     *
     * @mbggenerated
     */
    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt == null ? null : updateDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.operator_type
     *
     * @return the value of t_bmi.operator_type
     *
     * @mbggenerated
     */
    public Integer getOperatorType() {
        return operatorType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.operator_type
     *
     * @param operatorType the value for t_bmi.operator_type
     *
     * @mbggenerated
     */
    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bmi.operator_id
     *
     * @return the value of t_bmi.operator_id
     *
     * @mbggenerated
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bmi.operator_id
     *
     * @param operatorId the value for t_bmi.operator_id
     *
     * @mbggenerated
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }
}