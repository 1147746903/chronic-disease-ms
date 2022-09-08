package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.po.GreenStarPlanItemPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GreenStarPlanItemMapper {
    void addPlanItem(GreenStarPlanItemPO po);
    List<GreenStarPlanItemPO> listPlanItem(String dailyPlanId);
    void updatePlanItem(GreenStarPlanItemPO po);
    void deletePlanItem(String sid);
    void deletePlanItemByMainPlanId(String mainPlanId);
}
