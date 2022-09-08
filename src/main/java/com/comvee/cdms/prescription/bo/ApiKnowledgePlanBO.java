package com.comvee.cdms.prescription.bo;

import java.io.Serializable;

public class ApiKnowledgePlanBO implements Serializable {
    /** 第几天,(字段类型:varchar) **/
    private String day;
    /** 文章标题,(字段类型:varchar) **/
    private String title;
    /** 知识点,(字段类型:varchar) **/
    private String knowledge;
    /** 文章id,(字段类型:varchar) **/
    private String id;
    /*文章标签*/
    private String tags;
    /*字典code*/
    private String knowledgeCode;


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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getKnowledgeCode() {
		return knowledgeCode;
	}

	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
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
