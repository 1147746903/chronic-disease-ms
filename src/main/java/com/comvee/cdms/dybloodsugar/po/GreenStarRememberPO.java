package com.comvee.cdms.dybloodsugar.po;


public class GreenStarRememberPO {
    private String sid;
    private String memberId;//患者id
    private String rememberDate; //记一记日期格式 yyyy-MM-dd
    private Integer rememberType;//记一记类型 1 饮食 2 运动 3 用药 4: 其他
    private Integer timeType;//时间类型 1：早餐 2：午餐 3 ：晚餐 4：其他
    private String rememberTime; //记一记时间 格式HH：mm
    private String content; //记一记内容
    private String rememberData; //记一记JSON
    private String insertDt;
    private String modifyDt;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getRememberType() {
        return rememberType;
    }

    public void setRememberType(Integer rememberType) {
        this.rememberType = rememberType;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getRememberTime() {
        return rememberTime;
    }

    public void setRememberTime(String rememberTime) {
        this.rememberTime = rememberTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRememberData() {
        return rememberData;
    }

    public void setRememberData(String rememberData) {
        this.rememberData = rememberData;
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

    public String getRememberDate() {
        return rememberDate;
    }

    public void setRememberDate(String rememberDate) {
        this.rememberDate = rememberDate;
    }
}
