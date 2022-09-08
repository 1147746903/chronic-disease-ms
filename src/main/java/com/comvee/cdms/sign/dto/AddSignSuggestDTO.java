package com.comvee.cdms.sign.dto;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_sign_suggest
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class AddSignSuggestDTO {

    /**
     * 患者id
     * member_id
     */
    private String memberId;

    /**
     * 体征记录id
     * sign_id
     */
    private String signId;

    /**
     * 建议内容
     * suggest_text
     */
    private String suggestText;


    /**
     * 体征类型 1 血糖 2 血压 3 bmi
     * sign_type
     */
    private Integer signType;

    /**
     * doctor_id
     */
    private String doctorId;

    private String senderId;


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sign_suggest.member_id
     *
     * @return the value of t_sign_suggest.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sign_suggest.member_id
     *
     * @param memberId the value for t_sign_suggest.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sign_suggest.sign_id
     *
     * @return the value of t_sign_suggest.sign_id
     *
     * @mbggenerated
     */
    public String getSignId() {
        return signId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sign_suggest.sign_id
     *
     * @param signId the value for t_sign_suggest.sign_id
     *
     * @mbggenerated
     */
    public void setSignId(String signId) {
        this.signId = signId == null ? null : signId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sign_suggest.suggest_text
     *
     * @return the value of t_sign_suggest.suggest_text
     *
     * @mbggenerated
     */
    public String getSuggestText() {
        return suggestText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sign_suggest.suggest_text
     *
     * @param suggestText the value for t_sign_suggest.suggest_text
     *
     * @mbggenerated
     */
    public void setSuggestText(String suggestText) {
        this.suggestText = suggestText == null ? null : suggestText.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sign_suggest.sign_type
     *
     * @return the value of t_sign_suggest.sign_type
     *
     * @mbggenerated
     */
    public Integer getSignType() {
        return signType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sign_suggest.sign_type
     *
     * @param signType the value for t_sign_suggest.sign_type
     *
     * @mbggenerated
     */
    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sign_suggest.doctor_id
     *
     * @return the value of t_sign_suggest.doctor_id
     *
     * @mbggenerated
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sign_suggest.doctor_id
     *
     * @param doctorId the value for t_sign_suggest.doctor_id
     *
     * @mbggenerated
     */
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId == null ? null : doctorId.trim();
    }
}