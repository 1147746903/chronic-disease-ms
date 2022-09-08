package com.comvee.cdms.follow.model;

import java.io.Serializable;

/**
 *
 * @author 林雨堆
 * @date 2018/4/2 9:42
 *
 */
public class FollowListModel implements Serializable {
    /**
     * 唯一标识
     */
    private String sid;
    /**
     * 下次随访时间
     */
    private String nextDt;
    /**
     * 随访时间
     */
    private String followDt;
    /**
     * 随访类型：1首诊  2日常随访 3自我行为问卷 4糖尿病足随访表 5糖尿病随访表
     * 问卷类型：1:普通问卷 2:自定义随访问卷
     */
    private Integer followType;
    /**
     * 随访对象-患者编号
     */
    private String memberId;
    /**
     * 工作室编号
     */
    private String leaderId;
    /**
     * 医生编号
     */
    private String doctorId;

    /**
     * 创建时间
     */
    private String createDt;

    /**
     * 更新时间
     */
    private String modifyDt;

    /**
     * 是否有效 1有 0否
     */
    private Integer isValid;

    /**
     * 创建者名称
     */
    private String doctorName;

    /**
     * 处理状态
     * 随访 1已处理 0未处理
     * 问卷 3已处理 1，2未处理
     */
    private Integer dealStatus;

    /**
     * 患者名称
     */
    private String memberName;

    /**
     * 地址
     */
    private String url;

    /**
     * 随访名称|问卷名称
     */
    private String followName;

    /**
     * 随访模版编号|自定义随访内容表id
     */
    private String templateId;
    /**
     * 判断提交过的随访有没有报告 0 : 没有  1: 有
     * 用来判断打印按钮是否显示
     */
    private String statusFlag;

    /**
     * 标志 'follow' 随访，'question' 问卷
     */
    private String flag;

    /**
     *  1 默认 2华西
     */
    private Integer fType;

    /**
     * 填写人 1 医生 2 患者
     */
    private Integer fillFormBy;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getNextDt() {
        return nextDt;
    }

    public void setNextDt(String nextDt) {
        this.nextDt = nextDt;
    }

    public String getFollowDt() {
        return followDt;
    }

    public void setFollowDt(String followDt) {
        this.followDt = followDt;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }

    public Integer getFillFormBy() {
        return fillFormBy;
    }

    public void setFillFormBy(Integer fillFormBy) {
        this.fillFormBy = fillFormBy;
    }
}


