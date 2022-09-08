package com.comvee.cdms.tcm.model.po;

public class TcmCollectQueAnsPO {
    private String sid;
    private String queId;
    private Integer ansIndex;
    private String ansDesc;
    private String insertDt;
    private String updateDt;
    private Integer valid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public Integer getAnsIndex() {
        return ansIndex;
    }

    public void setAnsIndex(Integer ansIndex) {
        this.ansIndex = ansIndex;
    }

    public String getAnsDesc() {
        return ansDesc;
    }

    public void setAnsDesc(String ansDesc) {
        this.ansDesc = ansDesc;
    }

    public String getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

}
