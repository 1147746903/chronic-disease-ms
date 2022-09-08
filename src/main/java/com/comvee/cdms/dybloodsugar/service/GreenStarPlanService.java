package com.comvee.cdms.dybloodsugar.service;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.dybloodsugar.dto.GetGreenStarMainPlanDto;
import com.comvee.cdms.dybloodsugar.po.*;
import com.comvee.cdms.records.model.dto.DietRecordDTO;

import java.util.List;
import java.util.Map;

public interface GreenStarPlanService {

    void addPlan(GreenStarPlanPO po);

    void updatePlan(GreenStarPlanPO po);

    GreenStarPlanPO getPlanById(String planId);

    GreenStarMainPlanPO getMainPlan(GetGreenStarMainPlanDto dto);

    void openPlan(GetGreenStarMainPlanDto dto);


    boolean hasOpenPlan(GetGreenStarMainPlanDto dto);

    boolean isMainPlanOverdue(GetGreenStarMainPlanDto dto);

    GreenStarMainPlanPO getMainPlanByDailyPlanId(String planId);

    void unLockPlan(GreenStarMainPlanPO mainPlan);

    List<GreenStarPlanItemPO> createOneDayPlan(boolean isFirst);

    void addExceptionPlan(String planId, String type, String exception);

    List<GreenStarPlanItemPO> createPlans(Map<Integer, List<JSONObject>> plans);

     boolean checkUpdate(String planId);

    void createExceptionPlan(String planId, List<JSONObject> ex);

    boolean checkUpdateThreeHour(String sensor);

    boolean checkBehaviorPlan(String planId);

    void updateMemberArchives(GsMemberArchivesPO po);

    GsMemberArchivesPO getMemberArchivesById(String memberId);

//    void updateRemember(GreenStarRememberPO po);

//    List<GreenStarRememberPO> listRemember(GreenStarRememberDTO dto);

    JSONObject checkMemberInfo(String memberId);

    Map<Integer, String> getMemberEatTime(String memberId, String date);

    GreenStarPlanCoursePO getGreenStarCourseById(String sid);

    void completeCoursePlan(String planId, String subId);

    List<String> listRecordDt(DietRecordDTO dto);

    void deleteGreenStarPlanById(String sid);

    List<GreenStarPlanCoursePO> listGreenStarPlanCourse();

    void updateGreenStarPlanCourse(GreenStarPlanCoursePO course);

    List<JSONObject>getGreenStarPlanMenu(GetGreenStarMainPlanDto dto);
}
