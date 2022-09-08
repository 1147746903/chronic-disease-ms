package com.comvee.cdms.prescription.bo;

import java.io.Serializable;
import java.util.Map;

/**
 * 摄入量推荐
 */
public class HeatMaxAndMinOfDietBO implements Serializable {

    private String heatMin;
    private String heatMax;
    private Map<String,Double> heatData;

    public void setHeatMin(String heatMin) {
        this.heatMin = heatMin;
    }

    public void setHeatMax(String heatMax) {
        this.heatMax = heatMax;
    }

    public String getHeatMin() {
        return heatMin;
    }

    public String getHeatMax() {
        return heatMax;
    }

    public void setHeatData(Map<String, Double> heatData) {
        this.heatData = heatData;
    }

    public Map<String, Double> getHeatData() {
        return heatData;
    }
}
