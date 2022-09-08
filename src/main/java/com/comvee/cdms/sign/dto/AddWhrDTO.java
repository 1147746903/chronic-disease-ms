package com.comvee.cdms.sign.dto;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AddWhrDTO implements Serializable {
    @NotEmpty(message = "患者编号不能为空")
    private String memberId;
    @NotEmpty(message = "腰围不能为空")
    private String waist;
    @NotEmpty(message = "臀围不能为空")
    private String hip;
    @NotEmpty(message = "腰臀比不能为空")
    private String whr;
    @NotEmpty(message = "记录时间不能为空")
    private String recordDt;

    /**
     * 操作者类型 1 用户 2 医护
     */
    private Integer operatorType;

    /**
     * 操作者编号
     */
    private String operatorId;

    /**
     * 来源 1小程序 2web 3医生端
     */
    @NotNull(message = "来源不能为空,origin")
    private Integer origin;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getHip() {
        return hip;
    }

    public void setHip(String hip) {
        this.hip = hip;
    }

    public String getWhr() {
        return whr;
    }

    public void setWhr(String whr) {
        this.whr = whr;
    }

    public String getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(String recordDt) {
        this.recordDt = recordDt;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

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
}
