package com.comvee.cdms.checkresult.vo;

import java.io.Serializable;
import java.util.Date;

public class InspectionDetailVO implements Serializable {
    private String sid;
    /**
     * 检查报告ID
     */
    private String inspectId;
    /**
     * 检查部位code
     */
    private String inspectPartCode;
    /**
     * 检查部位名称
     */
    private String inspectPartName;
    /**
     * 检查所见
     */
    private String inspectFinding;
    /**
     * 检查结果
     */
    private String inspectResult;
    /**
     * 检查号
     */
    private String inspectNo;
    /**
     * 科室id
     */
    private String departmentId;
    /**
     * 科室名称
     */
    private String departmentName;
    /**
     * 报告日期
     */
    private Date reportDate;
    /**
     * 报告时间
     */
    private String reportTime;
    /**
     * 检查方法描述
     */
    private String inspectMethod;
    /**
     * 病理号
     */
    private String pathologyNo;
    /**
     * 诊断意见
     */
    private String diagnoseOpinion;
    /**
     * 科室code(病区code)
     */
    private String departCode;
    /**
     * 设备号
     */
    private String deviceNo;

    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getInspectId() {
        return inspectId;
    }
    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
    }
    public String getInspectPartCode() {
        return inspectPartCode;
    }
    public void setInspectPartCode(String inspectPartCode) {
        this.inspectPartCode = inspectPartCode;
    }
    public String getInspectPartName() {
        return inspectPartName;
    }
    public void setInspectPartName(String inspectPartName) {
        this.inspectPartName = inspectPartName;
    }
    public String getInspectFinding() {
        return inspectFinding;
    }
    public void setInspectFinding(String inspectFinding) {
        this.inspectFinding = inspectFinding;
    }
    public String getInspectResult() {
        return inspectResult;
    }
    public void setInspectResult(String inspectResult) {
        this.inspectResult = inspectResult;
    }
    public String getInspectNo() {
        return inspectNo;
    }
    public void setInspectNo(String inspectNo) {
        this.inspectNo = inspectNo;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public Date getReportDate() {
        return reportDate;
    }
    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }
    public String getReportTime() {
        return reportTime;
    }
    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
    public String getInspectMethod() {
        return inspectMethod;
    }
    public void setInspectMethod(String inspectMethod) {
        this.inspectMethod = inspectMethod;
    }
    public String getPathologyNo() {
        return pathologyNo;
    }
    public void setPathologyNo(String pathologyNo) {
        this.pathologyNo = pathologyNo;
    }
    public String getDiagnoseOpinion() {
        return diagnoseOpinion;
    }
    public void setDiagnoseOpinion(String diagnoseOpinion) {
        this.diagnoseOpinion = diagnoseOpinion;
    }
    public String getDepartCode() {
        return departCode;
    }
    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }
}
