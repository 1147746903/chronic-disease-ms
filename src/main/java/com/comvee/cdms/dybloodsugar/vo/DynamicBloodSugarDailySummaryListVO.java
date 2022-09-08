package com.comvee.cdms.dybloodsugar.vo;

import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarStatBase;

import java.util.List;

public class DynamicBloodSugarDailySummaryListVO implements DynamicBloodSugarStatBase {

    private String sid;
    private String AdContent;
    private List<DynamicBloodSugarDailySummaryVO> dataList;
    /**
     * 设置
     */
    private DynamicBloodSugarSettingsVO settings;


    public DynamicBloodSugarSettingsVO getSettings() {
        return settings;
    }

    public void setSettings(DynamicBloodSugarSettingsVO settings) {
        this.settings = settings;
    }

    public String getSid() {
        return sid;
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAdContent() {
        return AdContent;
    }

    @Override
    public void setAdContent(String adContent) {
        AdContent = adContent;
    }

    public List<DynamicBloodSugarDailySummaryVO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DynamicBloodSugarDailySummaryVO> dataList) {
        this.dataList = dataList;
    }
}
