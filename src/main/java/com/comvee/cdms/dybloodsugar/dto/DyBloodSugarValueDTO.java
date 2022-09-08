package com.comvee.cdms.dybloodsugar.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DyBloodSugarValueDTO implements Serializable {

    /**
     * 记录时间
     */
    private String recordTime;

    /**
     * 记录值
     */
    private BigDecimal value;


    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
