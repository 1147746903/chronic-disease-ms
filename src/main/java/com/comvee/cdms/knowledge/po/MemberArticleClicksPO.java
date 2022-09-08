package com.comvee.cdms.knowledge.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_member_article_clicks
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class MemberArticleClicksPO {
    /**
     * sid
     */
    private String sid;

    /**
     * member_id
     */
    private String memberId;

    /**
     * 患教知识点击数
     * wiki_clicks
     */
    private Integer wikiClicks;

    /**
     * 知识学习点击数
     * knowledge_clicks
     */
    private Integer knowledgeClicks;

    /**
     * insert_dt
     */
    private String insertDt;

    /**
     * update_dt
     */
    private String updateDt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_article_clicks.sid
     *
     * @return the value of t_member_article_clicks.sid
     *
     * @mbggenerated
     */
    public String getSid() {
        return sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_article_clicks.sid
     *
     * @param sid the value for t_member_article_clicks.sid
     *
     * @mbggenerated
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_article_clicks.member_id
     *
     * @return the value of t_member_article_clicks.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_article_clicks.member_id
     *
     * @param memberId the value for t_member_article_clicks.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_article_clicks.wiki_clicks
     *
     * @return the value of t_member_article_clicks.wiki_clicks
     *
     * @mbggenerated
     */
    public Integer getWikiClicks() {
        return wikiClicks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_article_clicks.wiki_clicks
     *
     * @param wikiClicks the value for t_member_article_clicks.wiki_clicks
     *
     * @mbggenerated
     */
    public void setWikiClicks(Integer wikiClicks) {
        this.wikiClicks = wikiClicks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_article_clicks.knowledge_clicks
     *
     * @return the value of t_member_article_clicks.knowledge_clicks
     *
     * @mbggenerated
     */
    public Integer getKnowledgeClicks() {
        return knowledgeClicks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_article_clicks.knowledge_clicks
     *
     * @param knowledgeClicks the value for t_member_article_clicks.knowledge_clicks
     *
     * @mbggenerated
     */
    public void setKnowledgeClicks(Integer knowledgeClicks) {
        this.knowledgeClicks = knowledgeClicks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_article_clicks.insert_dt
     *
     * @return the value of t_member_article_clicks.insert_dt
     *
     * @mbggenerated
     */
    public String getInsertDt() {
        return insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_article_clicks.insert_dt
     *
     * @param insertDt the value for t_member_article_clicks.insert_dt
     *
     * @mbggenerated
     */
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_member_article_clicks.update_dt
     *
     * @return the value of t_member_article_clicks.update_dt
     *
     * @mbggenerated
     */
    public String getUpdateDt() {
        return updateDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_member_article_clicks.update_dt
     *
     * @param updateDt the value for t_member_article_clicks.update_dt
     *
     * @mbggenerated
     */
    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }
}