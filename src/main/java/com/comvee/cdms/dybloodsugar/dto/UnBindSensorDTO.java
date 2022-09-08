package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

public class UnBindSensorDTO implements Serializable {
    @NotEmpty(message = "患者编号（memberId）不可为空")
    private String memberId;
    @NotEmpty(message = "传感器编号（sensorNo）不可为空")
    private String sensorNo;
    @NotEmpty(message = "主键id（sid）不可为空")
    private String sid;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
