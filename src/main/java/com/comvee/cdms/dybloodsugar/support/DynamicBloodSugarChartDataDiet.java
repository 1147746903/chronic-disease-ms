package com.comvee.cdms.dybloodsugar.support;

import com.comvee.cdms.dybloodsugar.vo.DynamicBloodChartDataDietVO;

import java.util.List;

public interface DynamicBloodSugarChartDataDiet extends DynamicBloodSugarChartDataBase{

    void setOtherDietList(List<DynamicBloodChartDataDietVO> otherDietList);

    void setDietList(List<DynamicBloodChartDataDietVO> dietList);
}
