package com.comvee.cdms.prescription.vo.eduplan;

/**
 * @author 李左河
 * @date 2018/8/13 15:44.
 */
public class KnowledgeTreeVO {
    @Override
    public String toString() {
        return "KnowledgeTreeVO{}";
    }

    private String id;
    private String name;
    private String knowledge;
    private String type;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
