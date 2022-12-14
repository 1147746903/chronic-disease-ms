package com.comvee.cdms.sign.dto;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_blood_sugar
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class AddBloodKetoneServiceDTO {
    /**
     * sid
     */
    private String sid;

    /**
     * 血酮值
     * param_value
     */
    private String paramValue;


    /**
     * 记录时间
     * record_dt
     */
    private String recordDt;

    /**
     * 备注
     * remark
     */
    private String remark;

    /**
     * member_id
     */
    private String memberId;

    /**
     * operator_id
     */
    private String operatorId;


    /**
     * 参考范围（mmol/L）
     */
    private String referenceRange;


    /**
     * 操作者类型 1 用户 2 医护
     */
    private Integer operatorType;

    /**
     * 来源 1：手动记录 2：设备 3：his--同步的his数据
     */
    private Integer origin;
    //1偏低 2偏高
    private Integer ketoneStatus;

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getKetoneStatus() {
        return ketoneStatus;
    }

    public void setKetoneStatus(Integer ketoneStatus) {
        this.ketoneStatus = ketoneStatus;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar.sid
     *
     * @return the value of t_blood_sugar.sid
     *
     * @mbggenerated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar.sid
     *
     * @param sid the value for t_blood_sugar.sid
     *
     * @mbggenerated
     */
    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar.param_value
     *
     * @return the value of t_blood_sugar.param_value
     *
     * @mbggenerated
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar.param_value
     *
     * @param paramValue the value for t_blood_sugar.param_value
     *
     * @mbggenerated
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }




    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar.record_dt
     *
     * @return the value of t_blood_sugar.record_dt
     *
     * @mbggenerated
     */
    public String getRecordDt() {
        return recordDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar.record_dt
     *
     * @param recordDt the value for t_blood_sugar.record_dt
     *
     * @mbggenerated
     */
    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt == null ? null : recordDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar.remark
     *
     * @return the value of t_blood_sugar.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar.remark
     *
     * @param remark the value for t_blood_sugar.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar.member_id
     *
     * @return the value of t_blood_sugar.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar.member_id
     *
     * @param memberId the value for t_blood_sugar.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }



    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar.operator_id
     *
     * @return the value of t_blood_sugar.operator_id
     *
     * @mbggenerated
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar.operator_id
     *
     * @param operatorId the value for t_blood_sugar.operator_id
     *
     * @mbggenerated
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getReferenceRange() {
        return referenceRange;
    }

    public void setReferenceRange(String referenceRange) {
        this.referenceRange = referenceRange;
    }

}