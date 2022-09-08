package com.comvee.cdms.statistics.vo;

/**
 * @Author linr
 * @Date 2022/2/18
 */
public class ListMemberExceptionVO {
    private String sid;
    private String memberId;
    private String memberName;
    private String mobilePhone;
    private String sugarLevel;//糖尿病分标
    private String pressureLevel;//血压分级
    private String pressureLayer;//血压分层
    private String detail;
    private String dataJson;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(String sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public String getPressureLevel() {
        return pressureLevel;
    }

    public void setPressureLevel(String pressureLevel) {
        this.pressureLevel = pressureLevel;
    }

    public String getPressureLayer() {
        return pressureLayer;
    }

    public void setPressureLayer(String pressureLayer) {
        this.pressureLayer = pressureLayer;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
