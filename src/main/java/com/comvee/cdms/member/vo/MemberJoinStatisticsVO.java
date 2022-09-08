package com.comvee.cdms.member.vo;

public class MemberJoinStatisticsVO {
    private Integer allMember;
    private Integer inHosMember;
    private String inHosMemberRate;
    private Integer atHomeMember;
    //private String atHomeMemberRate;
    private Integer attentionMember;
    private Integer diabetesMember;//糖尿病人数
    private String diabetesMemberRate;//糖尿病人数占比
    private Integer hypMember;//高血压人数
    private String hypMemberRate;//高血压人数占比

    public Integer getAllMember() {
        return allMember;
    }

    public void setAllMember(Integer allMember) {
        this.allMember = allMember;
    }

    public Integer getInHosMember() {
        return inHosMember;
    }

    public void setInHosMember(Integer inHosMember) {
        this.inHosMember = inHosMember;
    }

    public Integer getAtHomeMember() {
        return atHomeMember;
    }

    public void setAtHomeMember(Integer atHomeMember) {
        this.atHomeMember = atHomeMember;
    }

    public Integer getAttentionMember() {
        return attentionMember;
    }

    public void setAttentionMember(Integer attentionMember) {
        this.attentionMember = attentionMember;
    }

    public String getInHosMemberRate() {
        return inHosMemberRate;
    }

    public void setInHosMemberRate(String inHosMemberRate) {
        this.inHosMemberRate = inHosMemberRate;
    }

    public Integer getDiabetesMember() {
        return diabetesMember;
    }

    public void setDiabetesMember(Integer diabetesMember) {
        this.diabetesMember = diabetesMember;
    }

    public Integer getHypMember() {
        return hypMember;
    }

    public void setHypMember(Integer hypMember) {
        this.hypMember = hypMember;
    }

    public String getDiabetesMemberRate() {
        return diabetesMemberRate;
    }

    public void setDiabetesMemberRate(String diabetesMemberRate) {
        this.diabetesMemberRate = diabetesMemberRate;
    }

    public String getHypMemberRate() {
        return hypMemberRate;
    }

    public void setHypMemberRate(String hypMemberRate) {
        this.hypMemberRate = hypMemberRate;
    }
}
