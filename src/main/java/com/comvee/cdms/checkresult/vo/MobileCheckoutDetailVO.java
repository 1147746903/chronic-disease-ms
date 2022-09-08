package com.comvee.cdms.checkresult.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author 李左河
 *
 */
public class MobileCheckoutDetailVO implements Serializable {
    /**
     * 记录唯一编号
     */
     private String logId; 
    /**
     * 标本类型
     */
     private String specimenType;  
    /**
     * 检验内容
     */
     private String checkoutContent;  
    /**
     * 科室
     */
     private String departName;  
    /**
     * 住院类型（病人类型）
     */
     private String inHospitalType;  
    /**
     * 检验单号
     */
     private String orderNo;  
    /**
     * 申请医生
     */
     private String applyDoctor;  
    /**
     * 采样时间
     */
     private String checkoutDt;  
    /**
     * 报告时间
     */
     private String reportDt;  
    /**
     * 检验医生
     */
     private String hospitalName;  
    /**
     * 检验子项目内容
     */
     private List<MobileCheckoutDetailItemVO> items;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getSpecimenType() {
        return specimenType;
    }

    public void setSpecimenType(String specimenType) {
        this.specimenType = specimenType;
    }

    public String getCheckoutContent() {
        return checkoutContent;
    }

    public void setCheckoutContent(String checkoutContent) {
        this.checkoutContent = checkoutContent;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getInHospitalType() {
        return inHospitalType;
    }

    public void setInHospitalType(String inHospitalType) {
        this.inHospitalType = inHospitalType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getApplyDoctor() {
        return applyDoctor;
    }

    public void setApplyDoctor(String applyDoctor) {
        this.applyDoctor = applyDoctor;
    }

    public String getCheckoutDt() {
        return checkoutDt;
    }

    public void setCheckoutDt(String checkoutDt) {
        this.checkoutDt = checkoutDt;
    }

    public String getReportDt() {
        return reportDt;
    }

    public void setReportDt(String reportDt) {
        this.reportDt = reportDt;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public List<MobileCheckoutDetailItemVO> getItems() {
        return items;
    }

    public void setItems(List<MobileCheckoutDetailItemVO> items) {
        this.items = items;
    }
}
