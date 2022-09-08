package com.comvee.cdms.app.doctorapp.vo;

public class CountMemberVO {
    private Integer undetected; //未检测血糖的免费患者
    private Integer paid; //检测血糖的付费患者
    private Integer undetectedO; //正常血糖的免费患者
    private Integer paidO; //正常血糖的付费患者

    public Integer getUndetected() {
        return undetected;
    }

    public void setUndetected(Integer undetected) {
        this.undetected = undetected;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getUndetectedO() {
        return undetectedO;
    }

    public void setUndetectedO(Integer undetectedO) {
        this.undetectedO = undetectedO;
    }

    public Integer getPaidO() {
        return paidO;
    }

    public void setPaidO(Integer paidO) {
        this.paidO = paidO;
    }

    @Override
    public String toString() {
        return "CountMemberVO{" +
                "undetected=" + undetected +
                ", paid=" + paid +
                ", undetectedO=" + undetectedO +
                ", paidO=" + paidO +
                '}';
    }
}
