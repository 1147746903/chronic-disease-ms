package com.comvee.cdms.dybloodsugar.support;

import com.comvee.cdms.dybloodsugar.vo.DynamicBloodChartDataSportVO;

import java.util.List;

public interface DynamicBloodSugarChartDataSport extends DynamicBloodSugarChartDataBase{

    void setSportList(List<DynamicBloodChartDataSportVO> sportList);
}
