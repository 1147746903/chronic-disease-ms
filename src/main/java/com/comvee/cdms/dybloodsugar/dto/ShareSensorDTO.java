package com.comvee.cdms.dybloodsugar.dto;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

public class ShareSensorDTO implements Serializable{
    /**
     * 分享的传感器编号
     */
    @NotEmpty(message = "分享的传感器编号（sensorNo），不能为空")
    private String sensorNo;

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }
}
