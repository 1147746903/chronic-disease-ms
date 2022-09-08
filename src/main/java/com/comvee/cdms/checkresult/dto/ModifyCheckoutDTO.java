package com.comvee.cdms.checkresult.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

public class ModifyCheckoutDTO implements Serializable {
    @NotEmpty(message = "编号不可为空")
    private String checkoutId;
    @NotEmpty(message = "患者编号不可为空")
    private String memberId;
    @NotEmpty(message = "医院编号不可为空")
    private String hospitalId;
    private String checkoutTitle;
    private String checkoutDate;
    private String checkoutTime;
    /** "检验子项详情json不可为空 [{name:子项名称，value:子项值,code:子项编码," +
            "abnormalSign:异常指标（可选）,unit:单位,acronym:子项缩写（可选）}]")
     */
    private String detailJSON;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

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

    public String getDetailJSON() {
        return detailJSON;
    }

    public void setDetailJSON(String detailJSON) {
        this.detailJSON = detailJSON;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
