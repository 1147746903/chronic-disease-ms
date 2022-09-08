package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DyBloodSugarRemarkDTO implements Serializable {
    @NotBlank(message = "血糖编号，不能为空")
    private String bid;

    @NotBlank(message = "备注内容，不能为空")
    private String content;

    private String operatorId;

    private Integer origin;


    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DyBloodSugarRemarkDTO{" +
                ", bid=" + bid +
                ", content='" + content + '\'' +
                '}';
    }
}
