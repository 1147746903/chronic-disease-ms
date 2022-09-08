package com.comvee.cdms.checkresult.vo;

import java.io.Serializable;

/**
 * 
 * @author 李左河
 *
 */
public class MobileInspectionDetailVO implements Serializable {
    /**
     * 设备号
     */
     private String deviceNo; 
    /**
     * 检查日期
     */
     private String inspectDt; 
    /**
     * 报告日期
     */
     private String reportDt; 
    /**
     * 检查号
     */
     private String inspectId; 
    /**
     * 检查名称
     */
     private String inspectName; 
    /**
     * 检查表现
     */
     private String inspectShow; 
    /**
     * 检查方法
     */
     private String inspectFun; 
    /**
     * 诊断意见
     */
     private String suggest; 
    /**
     * 诊断医生
     */
     private String doctorName; 

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getInspectDt() {
        return inspectDt;
    }

    public void setInspectDt(String inspectDt) {
        this.inspectDt = inspectDt;
    }

    public String getReportDt() {
        return reportDt;
    }

    public void setReportDt(String reportDt) {
        this.reportDt = reportDt;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public String getInspectShow() {
        return inspectShow;
    }

    public void setInspectShow(String inspectShow) {
        this.inspectShow = inspectShow;
    }

    public String getInspectFun() {
        return inspectFun;
    }

    public void setInspectFun(String inspectFun) {
        this.inspectFun = inspectFun;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
