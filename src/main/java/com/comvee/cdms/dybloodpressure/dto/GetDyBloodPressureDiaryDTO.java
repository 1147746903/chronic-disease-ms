package com.comvee.cdms.dybloodpressure.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Author linr
 * @Date 2021/11/8
 */
public class GetDyBloodPressureDiaryDTO {
    @NotBlank(message = "memberId不允许为空")
    private String memberId;
    @NotBlank(message = "startDt不允许为空")
    private String startDt;
    @NotBlank(message = "endDt不允许为空")
    private String endDt;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

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
}
