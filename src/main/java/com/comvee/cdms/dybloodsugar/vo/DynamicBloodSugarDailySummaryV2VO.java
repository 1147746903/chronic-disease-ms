package com.comvee.cdms.dybloodsugar.vo;

import com.comvee.cdms.dybloodsugar.bo.DynamicBloodSugarSummaryAvgAndAreaBO;
import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarChartDataDiet;
import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarChartDataDrug;
import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarChartDataSport;
import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarChartDataWorkRest;

import java.util.List;

public class DynamicBloodSugarDailySummaryV2VO extends DynamicBloodSugarIndexBaseVO
        implements DynamicBloodSugarChartDataDiet, DynamicBloodSugarChartDataWorkRest
        , DynamicBloodSugarChartDataDrug, DynamicBloodSugarChartDataSport {

    private String date;
    private List<DynamicBloodChartDataItemVO> itemList;
    private DynamicBloodChartDataDietVO breakfastTime;
    private DynamicBloodChartDataDietVO lunchTime;
    private DynamicBloodChartDataDietVO dinnerTime;
    private DynamicBloodChartDataDietVO sleepTime;

    private DynamicBloodSugarSummaryAvgAndAreaBO morningAvgAndArea;
    private DynamicBloodSugarSummaryAvgAndAreaBO noonAvgAndArea;
    private DynamicBloodSugarSummaryAvgAndAreaBO nightAvgAndArea;

    private List<DynamicBloodChartDataDrugVO> drugList;
    private List<DynamicBloodChartDataDietVO> dietList;
    private List<DynamicBloodChartDataSportVO> sportList;
    private List<DynamicBloodChartDataRemarkVO> remarkList;
    private List<DynamicBloodChartDataDietVO> lifeTypeList;

    public List<DynamicBloodChartDataDietVO> getLifeTypeList() {
        return lifeTypeList;
    }

    public void setLifeTypeList(List<DynamicBloodChartDataDietVO> lifeTypeList) {
        this.lifeTypeList = lifeTypeList;
    }

    @Override
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

    @Override
    public void setBreakfastTime(DynamicBloodChartDataDietVO breakfastTime) {
        this.breakfastTime = breakfastTime;
    }

    public DynamicBloodChartDataDietVO getLunchTime() {
        return lunchTime;
    }

    @Override
    public void setLunchTime(DynamicBloodChartDataDietVO lunchTime) {
        this.lunchTime = lunchTime;
    }

    public DynamicBloodChartDataDietVO getDinnerTime() {
        return dinnerTime;
    }

    @Override
    public void setDinnerTime(DynamicBloodChartDataDietVO dinnerTime) {
        this.dinnerTime = dinnerTime;
    }

    public DynamicBloodChartDataDietVO getSleepTime() {
        return sleepTime;
    }

    @Override
    public void setSleepTime(DynamicBloodChartDataDietVO sleepTime) {
        this.sleepTime = sleepTime;
    }

    public DynamicBloodSugarSummaryAvgAndAreaBO getMorningAvgAndArea() {
        return morningAvgAndArea;
    }

    public void setMorningAvgAndArea(DynamicBloodSugarSummaryAvgAndAreaBO morningAvgAndArea) {
        this.morningAvgAndArea = morningAvgAndArea;
    }

    public DynamicBloodSugarSummaryAvgAndAreaBO getNoonAvgAndArea() {
        return noonAvgAndArea;
    }

    public void setNoonAvgAndArea(DynamicBloodSugarSummaryAvgAndAreaBO noonAvgAndArea) {
        this.noonAvgAndArea = noonAvgAndArea;
    }

    public DynamicBloodSugarSummaryAvgAndAreaBO getNightAvgAndArea() {
        return nightAvgAndArea;
    }

    public void setNightAvgAndArea(DynamicBloodSugarSummaryAvgAndAreaBO nightAvgAndArea) {
        this.nightAvgAndArea = nightAvgAndArea;
    }

    public List<DynamicBloodChartDataDrugVO> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<DynamicBloodChartDataDrugVO> drugList) {
        this.drugList = drugList;
    }

    public List<DynamicBloodChartDataDietVO> getDietList() {
        return dietList;
    }

    @Override
    public void setOtherDietList(List<DynamicBloodChartDataDietVO> otherDietList) {

    }

    @Override
    public void setDietList(List<DynamicBloodChartDataDietVO> dietList) {
        this.dietList = dietList;
    }

    public List<DynamicBloodChartDataSportVO> getSportList() {
        return sportList;
    }

    public void setSportList(List<DynamicBloodChartDataSportVO> sportList) {
        this.sportList = sportList;
    }

    public List<DynamicBloodChartDataRemarkVO> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<DynamicBloodChartDataRemarkVO> remarkList) {
        this.remarkList = remarkList;
    }
}
