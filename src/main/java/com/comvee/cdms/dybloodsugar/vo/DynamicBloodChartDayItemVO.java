package com.comvee.cdms.dybloodsugar.vo;

import com.comvee.cdms.dybloodsugar.support.*;

import java.util.List;

public class DynamicBloodChartDayItemVO implements DynamicBloodSugarChartDataDiet, DynamicBloodSugarChartDataWorkRest
        ,DynamicBloodSugarChartDataDrug ,DynamicBloodSugarChartDataSport {

    private String date;
    private List<DynamicBloodChartDataItemVO> itemList;
    private DynamicBloodChartDataDietVO breakfastTime;
    private DynamicBloodChartDataDietVO lunchTime;
    private DynamicBloodChartDataDietVO dinnerTime;
    private List<DynamicBloodChartDataDietVO> otherDietList;
    private List<DynamicBloodChartDataDrugVO> drugList;
    private List<DynamicBloodChartDataSportVO> sportList;
    private DynamicBloodChartDataDietVO sleepTime;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DynamicBloodChartDataItemVO> getItemList() {
        return itemList;
    }

    public void setItemList(List<DynamicBloodChartDataItemVO> itemList) {
        this.itemList = itemList;
    }

    public DynamicBloodChartDataDietVO getBreakfastTime() {
        return breakfastTime;
    }

    public void setBreakfastTime(DynamicBloodChartDataDietVO breakfastTime) {
        this.breakfastTime = breakfastTime;
    }

    public DynamicBloodChartDataDietVO getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(DynamicBloodChartDataDietVO lunchTime) {
        this.lunchTime = lunchTime;
    }

    public DynamicBloodChartDataDietVO getDinnerTime() {
        return dinnerTime;
    }

    public void setDinnerTime(DynamicBloodChartDataDietVO dinnerTime) {
        this.dinnerTime = dinnerTime;
    }

    public List<DynamicBloodChartDataDietVO> getOtherDietList() {
        return otherDietList;
    }

    public void setOtherDietList(List<DynamicBloodChartDataDietVO> otherDietList) {
        this.otherDietList = otherDietList;
    }

    @Override
    public void setDietList(List<DynamicBloodChartDataDietVO> dietList) {

    }

    public List<DynamicBloodChartDataDrugVO> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<DynamicBloodChartDataDrugVO> drugList) {
        this.drugList = drugList;
    }

    public List<DynamicBloodChartDataSportVO> getSportList() {
        return sportList;
    }

    public void setSportList(List<DynamicBloodChartDataSportVO> sportList) {
        this.sportList = sportList;
    }

    public DynamicBloodChartDataDietVO getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(DynamicBloodChartDataDietVO sleepTime) {
        this.sleepTime = sleepTime;
    }
}
