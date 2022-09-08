package com.comvee.cdms.statistics.vo;

import java.io.Serializable;

public class BloodSugarOfStatisticVO implements Serializable {
    private long testCount;
    private Double[] listLevelOfHeight;
    private Double[] listLevelOfLow;
    private Double[] listLevelOfNormal;
    private Integer normalCount;
    private Integer heightCount;
    private Integer lowCount;
    private Double lowRate;
    private Double normalRate;
    private Double kfHeightRate;
    private Double fkfHeightRate;

    public long getTestCount() {
        return testCount;
    }

    public void setTestCount(long testCount) {
        this.testCount = testCount;
    }

    public void setListLevelOfHeight(Double[] listLevelOfHeight) {
        this.listLevelOfHeight = listLevelOfHeight;
    }

    public Double[] getListLevelOfHeight() {
        return listLevelOfHeight;
    }

    public void setListLevelOfLow(Double[] listLevelOfLow) {
        this.listLevelOfLow = listLevelOfLow;
    }

    public Double[] getListLevelOfLow() {
        return listLevelOfLow;
    }

    public void setListLevelOfNormal(Double[] listLevelOfNormal) {
        this.listLevelOfNormal = listLevelOfNormal;
    }

    public Double[] getListLevelOfNormal() {
        return listLevelOfNormal;
    }

    public void setNormalCount(Integer normalCount) {
        this.normalCount = normalCount;
    }

    public Integer getNormalCount() {
        return normalCount;
    }

    public void setHeightCount(Integer heightCount) {
        this.heightCount = heightCount;
    }

    public Integer getHeightCount() {
        return heightCount;
    }

    public void setLowCount(Integer lowCount) {
        this.lowCount = lowCount;
    }

    public Integer getLowCount() {
        return lowCount;
    }

    public void setLowRate(Double lowRate) {
        this.lowRate = lowRate;
    }

    public Double getLowRate() {
        return lowRate;
    }

    public void setNormalRate(Double normalRate) {
        this.normalRate = normalRate;
    }

    public Double getNormalRate() {
        return normalRate;
    }

    public void setKfHeightRate(Double kfHeightRate) {
        this.kfHeightRate = kfHeightRate;
    }

    public Double getKfHeightRate() {
        return kfHeightRate;
    }

    public void setFkfHeightRate(Double fkfHeightRate) {
        this.fkfHeightRate = fkfHeightRate;
    }

    public Double getFkfHeightRate() {
        return fkfHeightRate;
    }
}
