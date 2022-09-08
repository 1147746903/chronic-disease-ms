package com.comvee.cdms.prescription.dto;

import java.io.Serializable;

public class GetFoodItemDTO implements Serializable {
    private String param;
    private String type;
    private String prescriptionId;
    private Integer isUse;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    @Override
    public String toString() {
        return "GetFoodItemDTO{" +
                "param='" + param + '\'' +
                ", type='" + type + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                '}';
    }
}
