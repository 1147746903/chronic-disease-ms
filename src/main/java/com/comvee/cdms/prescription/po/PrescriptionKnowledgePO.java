package com.comvee.cdms.prescription.po;

/**
 *
 * @author 李左河
 * @date 2018/8/7 10:05
 *
 */
public class PrescriptionKnowledgePO {
    @Override
    public String toString() {
        return "PrescriptionKnowledgePO{}";
    }

    /**
     * 主键
     */
    private String id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 知识点列表
     */
    private String knowledge;

    /**
     * 第几周的学习计划
     */
    private Integer week;

    /**
     * 添加时间
     */
    private String insertDt;

    /**
     * 修改时间
     */
    private String modifyDt;

    /**
     * 是否有效 1有效 0无效
     */
    private Integer isValid;

    /**
     * 患者id
     */
    private String memberId;

    /**
     * 管理处方id
     */
    private String prescriptionId;

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 是否已经学习,默认未学习 1 已经学习 0 未学习
     */
    private Integer hasLearned;

    /**
     * 学习时间
     */
    private String learnDt;

    /**
     * 计划推送时间
     */
    private String planPushDt;

    /**
     * 计划推送序号
     */
    private Integer planPushOrder;

    /**
     * 是否要推送 1是 2否 默认1
     */
    private Integer needPush;

    /**
     * 首诊id
     */
    private String followId;

    /**
     * 计划类型 1 处方学习计划 2 首诊学习计划
     */
    private Integer knowledgeType;

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

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Integer getHasLearned() {
        return hasLearned;
    }

    public void setHasLearned(Integer hasLearned) {
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

    public Integer getPlanPushOrder() {
        return planPushOrder;
    }

    public void setPlanPushOrder(Integer planPushOrder) {
        this.planPushOrder = planPushOrder;
    }

    public Integer getNeedPush() {
        return needPush;
    }

    public void setNeedPush(Integer needPush) {
        this.needPush = needPush;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public Integer getKnowledgeType() {
        return knowledgeType;
    }

    public void setKnowledgeType(Integer knowledgeType) {
        this.knowledgeType = knowledgeType;
    }
}