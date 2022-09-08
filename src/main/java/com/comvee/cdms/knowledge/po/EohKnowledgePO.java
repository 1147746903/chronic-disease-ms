/**
 * @File name:   EohKnowledgePO.java 知识文章
 * @Create on:   2017年1月12日
 * @Author   :  占铃树
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.knowledge.po;

import java.io.Serializable;

/**
 * @author 占铃树
 */
public class EohKnowledgePO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;
    private String articleId; // 文章id
    private String title; //文章标题
    private String knowledge; //知识点列表
    private String week; //第几周的学习计划
    private String memberId;
    private String prescriptId;//管理处方id
    private String insertDt;
    private String modifyDt;
    private String hasLearned; //是否已经学习,默认未学习 1 已经学习 0 未学习
    private String learnDt; //学习时间
    private String planPushDt; //计划推送时间
    private String planPushOrder; //计划推送序号
    private String studioId; // 工作室id
    private String leaderId; //主治id

    private String doctorId;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getKnowledge() {
        return knowledge;
    }
    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getPrescriptId() {
        return prescriptId;
    }
    public void setPrescriptId(String prescriptId) {
        this.prescriptId = prescriptId;
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
    public String getArticleId() {
        return articleId;
    }
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
    public String getHasLearned() {
        return hasLearned;
    }
    public void setHasLearned(String hasLearned) {
        this.hasLearned = hasLearned;
    }
    public String getLearnDt() {
        return learnDt;
    }
    public void setLearnDt(String learnDt) {
        this.learnDt = learnDt;
    }
    public String getPlanPushDt() {
        return planPushDt;
    }
    public void setPlanPushDt(String planPushDt) {
        this.planPushDt = planPushDt;
    }
    public String getPlanPushOrder() {
        return planPushOrder;
    }
    public void setPlanPushOrder(String planPushOrder) {
        this.planPushOrder = planPushOrder;
    }
    public String getStudioId() {
        return studioId;
    }
    public void setStudioId(String studioId) {
        this.studioId = studioId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
