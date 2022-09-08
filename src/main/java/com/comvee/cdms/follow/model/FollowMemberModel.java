package com.comvee.cdms.follow.model;

import java.io.Serializable;
/**
 * 
 * @author 李左河
 *
 */
public class FollowMemberModel implements Serializable {
    /**
     * 患者编号
     */
    private String memberId;
    /**
     * 患者名称
     */
    private String memberName;
    /**
     * 患者头像地址
     */
    private String picUrl;
    /**
     * 下次随访时间
     */
    private String nextFollowDate;
    /**
     * 最近随访记录编号
     */
    private String lastFollowSid;
    private Integer sex;
    private Integer diabetesType;
    private String birthday;
    private String diabetesDate;
    private String bedNo;
    private String age;
    private String diabetesDt;
    /**
     * 随访设置唯一编号
     */
    private String followSetId;
    private String leaderId;  //医生id

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getFollowSetId() {
        return followSetId;
    }

    public void setFollowSetId(String followSetId) {
        this.followSetId = followSetId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getNextFollowDate() {
        return nextFollowDate;
    }

    public void setNextFollowDate(String nextFollowDate) {
        this.nextFollowDate = nextFollowDate;
    }

    public String getLastFollowSid() {
        return lastFollowSid;
    }

    public void setLastFollowSid(String lastFollowSid) {
        this.lastFollowSid = lastFollowSid;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(Integer diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getDiabetesDate() {
        return diabetesDate;
    }

    public void setDiabetesDate(String diabetesDate) {
        this.diabetesDate = diabetesDate;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDiabetesDt(String diabetesDt) {
        this.diabetesDt = diabetesDt;
    }

    public String getDiabetesDt() {
        return diabetesDt;
    }
}
