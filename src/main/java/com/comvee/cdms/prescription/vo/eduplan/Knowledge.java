package com.comvee.cdms.prescription.vo.eduplan;

/**
 * @author 李左河
 * @date 2018/8/6 9:22.
 */
public class Knowledge {
    @Override
    public String toString() {
        return "Knowledge{}";
    }

    /**
     * 文章id
     */
    private String articleId;
    /**
     * 第几天
     */
    private String day;
    /**
     * 知识点
     */
    private String knowledge;
    /**
     * 文章标题
     */
    private String title;

    /**
     * 学习状态 1 已学习  0 未学习
     */
    private Integer learnStatus;
    /**
     * 关注状态 1 已关注 0 未关注
     */
    private Integer followStatus;
    /**
     * 下发状态 1 已下发 0 为下发
     */
    private Integer hairDownStatus;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLearnStatus() {
        return learnStatus;
    }

    public void setLearnStatus(Integer learnStatus) {
        this.learnStatus = learnStatus;
    }

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }

    public Integer getHairDownStatus() {
        return hairDownStatus;
    }

    public void setHairDownStatus(Integer hairDownStatus) {
        this.hairDownStatus = hairDownStatus;
    }
}
