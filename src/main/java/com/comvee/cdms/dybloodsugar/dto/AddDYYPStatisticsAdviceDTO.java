package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

public class AddDYYPStatisticsAdviceDTO implements Serializable {
    /**
     * 报告唯一主键编号
     */
    @NotEmpty(message = "报告唯一主键编号(statisticsId)不可为空")
    private String statisticsId;

    /**
     * 医生编号
     */
    @NotEmpty(message = "医生编号(doctorId)不可为空")
    private String doctorId;

    /**
     * 建议内容
     */
    @NotEmpty(message = "建议内容(content)不可为空")
    private String content;

    public String getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(String statisticsId) {
        this.statisticsId = statisticsId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
