package com.comvee.cdms.prescription.bo;

import java.io.Serializable;

public class ApiArticleBO implements Serializable {
    /** 文章id,(字段类型:bigint) **/
    private Long id;
    /** 摘要,(字段类型:varchar) **/
    private String summary;
    /** 标题,(字段类型:varchar) **/
    private String title;
    /** 平台,(字段类型:varchar) **/
    private String platform;
    /** 内容出处,(字段类型:varchar) **/
    private String reference;
    /** 文章图片,(字段类型:varchar) **/
    private String img;
    /** 添加时间,(字段类型:datetime) **/
    private String insertDt;
    /** 修改时间,(字段类型:timestamp) **/
    private String modifyDt;
    /** 是否有效(1有效 0无效),(字段类型:tinyint) **/
    private Integer isValid;
    /** 操作者id,(字段类型:varchar) **/
    private String operateId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
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
