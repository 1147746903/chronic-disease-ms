package com.comvee.cdms.checkresult.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AddCheckoutDTO implements Serializable {
    @NotEmpty(message = "名称不可为空")
    private String checkoutTitle;
    @NotEmpty(message = "检验日期不可为空 yyyy-MM-dd")
    private String checkoutDate;
    @NotEmpty(message = "检验时间不可为空 HH:mm:ss")
    private String checkoutTime;
    @NotEmpty(message = "患者编号不可为空")
    private String memberId;
    @NotEmpty(message = "就诊号不可为空，无填'-1'")
    private String visitNo;
    private String yzId;
    @NotEmpty(message = "检验子项详情json不可为空 [{name:子项名称，value:子项值,code:子项编码," +
            "abnormalSign:异常指标（可选）,unit:单位,acronym:子项缩写（可选）}]")
    private String detailJSON;
    @NotNull(message = "来源不可为空")
    private Integer recordOrigin;
    private String recordTime;//记录时间（用于添加血压）

    public String getCheckoutTitle() {
        return checkoutTitle;
    }

    public void setCheckoutTitle(String checkoutTitle) {
        this.checkoutTitle = checkoutTitle;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getYzId() {
        return yzId;
    }

    public void setYzId(String yzId) {
        this.yzId = yzId;
    }

    public String getDetailJSON() {
        return detailJSON;
    }

    public void setDetailJSON(String detailJSON) {
        this.detailJSON = detailJSON;
    }

    public void setRecordOrigin(Integer recordOrigin) {
        this.recordOrigin = recordOrigin;
    }

    public Integer getRecordOrigin() {
        return recordOrigin;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
