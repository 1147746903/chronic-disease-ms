package com.comvee.cdms.differentlevels.vo;

import com.comvee.cdms.differentlevels.bo.DiffLevelsChartForMemberBO;
import com.comvee.cdms.differentlevels.bo.DiffLevelsForMemberBO;

import java.io.Serializable;
import java.util.List;

public class DiffLevelsForMemberVO implements Serializable {

    private DiffLevelsChartForMemberBO chartData;

    private List<DiffLevelsForMemberBO> dataInfos;

    public DiffLevelsChartForMemberBO getChartData() {
        return chartData;
    }

    public void setChartData(DiffLevelsChartForMemberBO chartData) {
        this.chartData = chartData;
    }

    public List<DiffLevelsForMemberBO> getDataInfos() {
        return dataInfos;
    }

    public void setDataInfos(List<DiffLevelsForMemberBO> dataInfos) {
        this.dataInfos = dataInfos;
    }
}
