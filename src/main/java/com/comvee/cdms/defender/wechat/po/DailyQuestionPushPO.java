package com.comvee.cdms.defender.wechat.po;

import java.io.Serializable;

/**
 * 每日一答用户推送表(DailyQuestionPushPO)实体类
 *
 * @author makejava
 * @since 2021-11-29 10:50:30
 */
public class DailyQuestionPushPO implements Serializable {
    private static final long serialVersionUID = -38811734394955584L;
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
    /**
     * 推送时间
     */
    private String pushDt;
    /**
     * 是否已提交1已提交0未提交
     */
    private Integer isSubmit;
    private Integer stage;
    /**
     * 添加时间
     */
    private String insertDt;
    /**
     * 修改时间
     */
    private String modifyDt;


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

    public String getPushDt() {
        return pushDt;
    }

    public void setPushDt(String pushDt) {
        this.pushDt = pushDt;
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

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }
}
