package com.comvee.cdms.dybloodsugar.po;

public class DyBloodSugarRemarkPO {
    private String sid;

    private String bid;

    private Byte isValid;

    private String modifyDt;

    private String insertDt;

    private String content;

    private String operatorId;

    private Integer origin;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar_remark.sid
     *
     * @return the value of t_blood_sugar_remark.sid
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar_remark.sid
     *
     * @param sid the value for t_blood_sugar_remark.sid
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar_remark.bid
     *
     * @return the value of t_blood_sugar_remark.bid
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public String getBid() {
        return bid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar_remark.bid
     *
     * @param bid the value for t_blood_sugar_remark.bid
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public void setBid(String bid) {
        this.bid = bid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar_remark.is_valid
     *
     * @return the value of t_blood_sugar_remark.is_valid
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public Byte getIsValid() {
        return isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar_remark.is_valid
     *
     * @param isValid the value for t_blood_sugar_remark.is_valid
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar_remark.modify_dt
     *
     * @return the value of t_blood_sugar_remark.modify_dt
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public String getModifyDt() {
        return modifyDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar_remark.modify_dt
     *
     * @param modifyDt the value for t_blood_sugar_remark.modify_dt
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt == null ? null : modifyDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar_remark.insert_dt
     *
     * @return the value of t_blood_sugar_remark.insert_dt
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar_remark.insert_dt
     *
     * @param insertDt the value for t_blood_sugar_remark.insert_dt
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt == null ? null : insertDt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_blood_sugar_remark.content
     *
     * @return the value of t_blood_sugar_remark.content
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_blood_sugar_remark.content
     *
     * @param content the value for t_blood_sugar_remark.content
     *
     * @mbggenerated Thu Jul 04 14:49:31 CST 2019
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}