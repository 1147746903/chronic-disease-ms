package com.comvee.cdms.other.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: suyz
 * @date: 2019/4/10
 */
public class ThirdWechatMessageTemplateDTO {

    @NotEmpty(message = "患者id不能为空")
    private String memberId;
    @NotNull(message = "消息类型不能为空")
    private Integer dataType;
    @NotEmpty(message = "模板数据json不能为空")
    private String dataJson;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
