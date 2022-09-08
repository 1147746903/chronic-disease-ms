package com.comvee.cdms.differentlevels.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

public class ListDiffLevelsOfMemberDTO implements Serializable {
    private String startDt;
    private String endDt;
    @NotEmpty(message = "患者编号不能为空")
    private String memberId;

    /**
     * 系统来源编号（医院编号）
     */
    private String originId;

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
