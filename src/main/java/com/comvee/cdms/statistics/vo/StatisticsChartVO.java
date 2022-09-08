package com.comvee.cdms.statistics.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: suyz
 * @date: 2018/10/19
 */
public class StatisticsChartVO {

    private String itemCode;

    private List<StatisticsChartItemVO> dataList = new ArrayList<>();

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public List<StatisticsChartItemVO> getDataList() {
        return dataList;
    }

    public void setDataList(List<StatisticsChartItemVO> dataList) {
        this.dataList = dataList;
    }
}
