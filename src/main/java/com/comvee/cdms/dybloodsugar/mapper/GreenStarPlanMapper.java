package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.GetGreenStarPlanDTO;
import com.comvee.cdms.dybloodsugar.dto.ListGreenStarPlanDTO;
import com.comvee.cdms.dybloodsugar.po.GreenStarPlanPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface GreenStarPlanMapper {
    void addPlan(GreenStarPlanPO po);
    void updatePlan(GreenStarPlanPO po);
    GreenStarPlanPO getPlanById(String sid);
    GreenStarPlanPO getPlan(GetGreenStarPlanDTO dto);
    List<GreenStarPlanPO> listPlan( String mainPlanId);
    boolean hasPlan(ListGreenStarPlanDTO dto);
    void unLockPlanById(GreenStarPlanPO po);
    void unLockPlanByMainPlan(GreenStarPlanPO po);
    void deletePlanByMainPlanId(String mainPlanId);
}
