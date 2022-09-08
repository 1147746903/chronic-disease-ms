package com.comvee.cdms.defender.wechat.vo;

/**
 * @Author linr
 * @Date 2021/12/7
 */
public class BarrierResultVO {

    private String barrierId;
    private String memberId;
    private String score;
    private Integer isSuccess;//分数话术
    private String correctNum;//正确题数
    private String wrongNum;//错误题数
    private String tipTitle;//小助点评标题
    private String tip;//小助点评
    private String rankRate;//排名

    public String getBarrierId() {
        return barrierId;
    }

    public void setBarrierId(String barrierId) {
        this.barrierId = barrierId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getCorrectNum() {
        return correctNum;
    }

    public void setCorrectNum(String correctNum) {
        this.correctNum = correctNum;
    }

    public String getWrongNum() {
        return wrongNum;
    }

    public void setWrongNum(String wrongNum) {
        this.wrongNum = wrongNum;
    }


    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getRankRate() {
        return rankRate;
    }

    public void setRankRate(String rankRate) {
        this.rankRate = rankRate;
    }

    public String getTipTitle() {
        return tipTitle;
    }

    public void setTipTitle(String tipTitle) {
        this.tipTitle = tipTitle;
    }
}
