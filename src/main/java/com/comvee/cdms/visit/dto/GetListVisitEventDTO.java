package com.comvee.cdms.visit.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Author linr
 * @Date 2022/2/25
 */
public class GetListVisitEventDTO {
    private String startDt;
    private String endDt;
    private Integer eventType;
    @NotBlank(message = "memberId不允许为空")
    private String memberId;

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
