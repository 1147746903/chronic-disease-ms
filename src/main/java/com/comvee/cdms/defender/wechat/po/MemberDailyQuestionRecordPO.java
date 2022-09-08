package com.comvee.cdms.defender.wechat.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 每日一答用户记录表(MemberDailyQuestionRecordPO)实体类
 *
 * @author makejava
 * @since 2021-11-29 10:19:29
 */
public class MemberDailyQuestionRecordPO  implements Serializable{
    private static final long serialVersionUID = -76464392457385999L;
    /**
     * 主键id
     */
    private String sid;
    /**
     * 患者id
     */
    private String memberId;
    /**
     * 题目id
     */
    private String qid;
    private String pushId;
    /**
     * 答案
     */
    private String answer;
    /**
     * 是否正确1正确0不正确
     */
    private Integer isCorrect;
    /**
     * 推送日期
     */
    private String pushDt;
    /**
     * 答题日期
     */
    private String submitDt;
    /**
     * 是否有效1有效0无效
     */
    private Integer isValid;
    /**
     * 添加时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private Date modifyDt;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Integer isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getPushDt() {
        return pushDt;
    }

    public void setPushDt(String pushDt) {
        this.pushDt = pushDt;
    }

    public String getSubmitDt() {
        return submitDt;
    }

    public void setSubmitDt(String submitDt) {
        this.submitDt = submitDt;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
