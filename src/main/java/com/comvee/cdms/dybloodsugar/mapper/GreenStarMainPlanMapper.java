package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.GetGreenStarMainPlanDto;
import com.comvee.cdms.dybloodsugar.po.GreenStarMainPlanPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GreenStarMainPlanMapper {
    void addMainPlan(GreenStarMainPlanPO PO);
    GreenStarMainPlanPO getMainPlan(GetGreenStarMainPlanDto dto);
    GreenStarMainPlanPO getMainPlanById(String sid);
    GreenStarMainPlanPO getMainPlanByDailyPlanId(String planId);
    boolean hasMainPlan(GetGreenStarMainPlanDto dto);
    boolean isMainPlanOverdue(GetGreenStarMainPlanDto dto);
    void deleteMainPlanBydId(String sid);
}
