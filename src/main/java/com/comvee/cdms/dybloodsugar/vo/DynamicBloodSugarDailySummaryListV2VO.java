package com.comvee.cdms.dybloodsugar.vo;

import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarStatBase;

import java.util.List;

public class DynamicBloodSugarDailySummaryListV2VO extends DynamicBloodSugarIndexBaseVO  implements DynamicBloodSugarStatBase {

    private String sid;
    private String AdContent;
    private List<DynamicBloodSugarDailySummaryV2VO> dataList;
    private DynamicBloodSugarSettingsVO settings;

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

    public List<DynamicBloodSugarDailySummaryV2VO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DynamicBloodSugarDailySummaryV2VO> dataList) {
        this.dataList = dataList;
    }

    public DynamicBloodSugarSettingsVO getSettings() {
        return settings;
    }

    public void setSettings(DynamicBloodSugarSettingsVO settings) {
        this.settings = settings;
    }
}
