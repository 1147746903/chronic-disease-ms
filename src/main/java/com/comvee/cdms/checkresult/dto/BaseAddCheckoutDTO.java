package com.comvee.cdms.checkresult.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Author linr
 * @Date 2022/3/17
 */
public class BaseAddCheckoutDTO {
    @NotBlank(message = "名称不可为空")
    private String checkoutTitle;
    @NotBlank(message = "检验日期不可为空 yyyy-MM-dd")
    private String checkoutDate;
    @NotBlank(message = "检验时间不可为空 HH:mm:ss")
    private String checkoutTime;
    @NotBlank(message = "检验子项详情json不可为空 [{name:子项名称，value:子项值,code:子项编码," +
            "abnormalSign:异常指标（可选）,unit:单位,acronym:子项缩写（可选）}]")
    private String detailJSON;
    private String recordTime;//记录时间（用于添加血压）
    private Integer dataType;//1血糖2血压3bmi4腰臀比5hba1c6其他  (仅用于随访、处方)

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

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }


    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

}
