package com.comvee.cdms.dybloodsugar.vo;

import com.comvee.cdms.dybloodsugar.support.DynamicBloodSugarStatBase;

import java.util.List;

/**
 * 日均血糖平均绝对差 统计结果
 */
public class DynamicBloodSugarMeanAbsoluteDeviationVO implements DynamicBloodSugarStatBase {

    private String sid;
    private String adContent;
    private List<DynamicBloodSugarMeanAbsoluteDeviationItemVO> dataList;

    public String getSid() {
        return sid;
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAdContent() {
        return adContent;
    }

    @Override
    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public List<DynamicBloodSugarMeanAbsoluteDeviationItemVO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DynamicBloodSugarMeanAbsoluteDeviationItemVO> dataList) {
        this.dataList = dataList;
    }
}
