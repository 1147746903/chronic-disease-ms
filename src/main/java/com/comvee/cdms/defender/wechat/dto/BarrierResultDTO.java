package com.comvee.cdms.defender.wechat.dto;

/**
 * @Author linr
 * @Date 2021/12/31
 */
public class BarrierResultDTO {

    private String sid;
    private String score;
    //private Integer overTime;
    private String batchId;
//    private Integer correctNum;
//    private Integer wrongNum;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
