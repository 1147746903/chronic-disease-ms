package com.comvee.cdms.follow.dto;

/**
 * @author wyc
 * @date 2019/10/18 10:20
 */
public class ListFollowReportDTO {

    /**
     * 患者id
     */
    private String memberId;
    /**
     * 医生id
     */
    private String doctorId;
    /**
     * 报告类型 1 首诊报告 2 2型糖尿病随访报告 3 日常随访报告 4 糖尿病随访报告 5 糖尿病足随访报告 6 华西随访总结报告
     */
    private Integer reportType;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }
}
