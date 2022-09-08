package com.comvee.cdms.consultation.model.param;

import javax.validation.constraints.NotEmpty;

public class ListOldRemoteConsultationMessageParam {

    @NotEmpty(message = "consultationId 不能为空")
    private String consultationId;
    private Long timeStamp;
    private Integer size;

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
