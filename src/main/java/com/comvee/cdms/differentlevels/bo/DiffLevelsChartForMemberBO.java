package com.comvee.cdms.differentlevels.bo;

import java.io.Serializable;
import java.util.List;

public class DiffLevelsChartForMemberBO implements Serializable {
    private List<String> xArea;
    private List<String> yArea;
    private List<DiffLevelsChartPointInfoBO> arrayData;

    public List<String> getxArea() {
        return xArea;
    }

    public void setxArea(List<String> xArea) {
        this.xArea = xArea;
    }

    public List<String> getyArea() {
        return yArea;
    }

    public void setyArea(List<String> yArea) {
        this.yArea = yArea;
    }

    public List<DiffLevelsChartPointInfoBO> getArrayData() {
        return arrayData;
    }

    public void setArrayData(List<DiffLevelsChartPointInfoBO> arrayData) {
        this.arrayData = arrayData;
    }
}
