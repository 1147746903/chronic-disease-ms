package com.comvee.cdms.complication.model.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class AddScreeningKnowledgeDTO {

    @NotEmpty(message = "标题不能为空")
    private String titleName;

    @NotEmpty(message = "摘要不能为空")
    private String summaryText;

    @NotNull(message = "序号不能为空")
    private Integer serialNumber;

    @NotEmpty(message = "内容不能为空")
    private String contentText;

    @NotNull(message = "声明不能为空")
    private Integer originType;


    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public Integer getOriginType() {
        return originType;
    }

    public void setOriginType(Integer originType) {
        this.originType = originType;
    }
}