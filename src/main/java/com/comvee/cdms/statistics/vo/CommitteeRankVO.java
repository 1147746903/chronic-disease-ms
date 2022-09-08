package com.comvee.cdms.statistics.vo;

/**
 * @Author linr
 * @Date 2022/6/28
 */
public class CommitteeRankVO {

    private String committeeName;//居委会名
    private String joinMemberNum;//建档患者人数
    private String quarterSugarFollowNum;//本季度糖尿病随访人数
    private String quarterHypFollowNum;//本季度高血压随访人数
    private String checkMemberNum;//体检人数
    private String sugarMemberNum;//血糖测量人数
    private String pressureMemberNum;//血压测量人数

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getJoinMemberNum() {
        return joinMemberNum;
    }

    public void setJoinMemberNum(String joinMemberNum) {
        this.joinMemberNum = joinMemberNum;
    }

    public String getQuarterSugarFollowNum() {
        return quarterSugarFollowNum;
    }

    public void setQuarterSugarFollowNum(String quarterSugarFollowNum) {
        this.quarterSugarFollowNum = quarterSugarFollowNum;
    }

    public String getQuarterHypFollowNum() {
        return quarterHypFollowNum;
    }

    public void setQuarterHypFollowNum(String quarterHypFollowNum) {
        this.quarterHypFollowNum = quarterHypFollowNum;
    }

    public String getCheckMemberNum() {
        return checkMemberNum;
    }

    public void setCheckMemberNum(String checkMemberNum) {
        this.checkMemberNum = checkMemberNum;
    }

    public String getSugarMemberNum() {
        return sugarMemberNum;
    }

    public void setSugarMemberNum(String sugarMemberNum) {
        this.sugarMemberNum = sugarMemberNum;
    }

    public String getPressureMemberNum() {
        return pressureMemberNum;
    }

    public void setPressureMemberNum(String pressureMemberNum) {
        this.pressureMemberNum = pressureMemberNum;
    }
}
