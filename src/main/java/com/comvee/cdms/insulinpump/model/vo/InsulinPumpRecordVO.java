package com.comvee.cdms.insulinpump.model.vo;

public class InsulinPumpRecordVO {

    private String cardNo;
    private String hospitalNo; //住院号
    private String startDt; //首次上泵时间
    private String endDt; //最后下泵时间


    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    @Override
    public String toString() {
        return "InsulinPumpRecordVO{" +
                "hospitalNo='" + hospitalNo + '\'' +
                ", startDt='" + startDt + '\'' +
                ", endDt='" + endDt + '\'' +
                '}';
    }
}
