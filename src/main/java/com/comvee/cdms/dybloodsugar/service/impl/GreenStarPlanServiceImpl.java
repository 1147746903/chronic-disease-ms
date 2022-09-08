package com.comvee.cdms.dybloodsugar.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.dybloodsugar.constant.GreenStarPlanConstant;
import com.comvee.cdms.dybloodsugar.dto.*;
import com.comvee.cdms.dybloodsugar.mapper.*;
import com.comvee.cdms.dybloodsugar.po.*;
import com.comvee.cdms.dybloodsugar.service.*;
import com.comvee.cdms.dybloodsugar.vo.GreenStarPlanSubItemVO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.records.mapper.DietRecordMapper;
import com.comvee.cdms.records.mapper.SportRecordMapper;
import com.comvee.cdms.records.model.dto.DietRecordDTO;
import com.comvee.cdms.records.model.dto.SportRecordDTO;
import com.comvee.cdms.records.model.po.DietRecordPO;
import com.comvee.cdms.records.model.po.SportRecordPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GreenStarPlanServiceImpl implements GreenStarPlanService {
    @Autowired
    private GreenStarPlanMapper greenStarPlanMapper;
    @Autowired
    private DyMemberSensorService dyMemberSensorService;
    @Autowired
    private DyBloodSugarService dyBloodSugarService;
    @Autowired
    private GsMemberArchivesMapper memberArchivesMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private GreenStarMainPlanMapper greenStarMainPlanMapper;
    @Autowired
    private GreenStarPlanItemMapper greenStarPlanItemMapper;
    @Autowired
    private GreenStarPlanCourseMapper greenStarPlanCourseMapper;
    @Autowired
    private DyRememberService dyRememberService;
    @Autowired
    private DietRecordMapper dietRecordMapper;
    @Autowired
    private SportRecordMapper sportRecordMapper;


    /**
     * 开启计划
     *
     * @param dto
     */
    @Override
    public void openPlan(GetGreenStarMainPlanDto dto) {
        if (hasOpenPlan(dto))
            throw new BusinessException("已经为当前探头生成计划");
        String startDt;
        DYMemberSensorPO po = this.dyMemberSensorService.getDYMemberSensorPO(dto.getSensorNo());

        if ((po == null || po.getMonitoringTime() == null))
            throw new BusinessException("探头不存在");
        List<String> dates = dyBloodSugarService.listBloodSugarRecordDt(po.getSensorNo());
        if (dto.getBindType() == 1 && dates.size() == 0) {
            throw new BusinessException("未检测到您的传感器数据，请重新上传");
        } else {
            Date startDate = new Date(Long.parseLong(po.getMonitoringTime()));
            startDt = DateHelper.getDate(startDate, DateHelper.DAY_FORMAT);
        }
        GreenStarMainPlanPO mainPlanPO = new GreenStarMainPlanPO();
        mainPlanPO.setMemberId(dto.getMemberId());
        mainPlanPO.setSensorNo(dto.getSensorNo());
        mainPlanPO.setSid(DaoHelper.getSeq());
        mainPlanPO.setStartDt(startDt);
        mainPlanPO.setStopDt(DateHelper.plusDate(startDt, 13, DateHelper.DAY_FORMAT));
        greenStarMainPlanMapper.addMainPlan(mainPlanPO);
        GreenStarPlanPO plan;
        String planDt = null;
        String today = DateHelper.getDate(new Date(), DateHelper.DAY_FORMAT);
        for (int i = 1; i < 15; i++) {
            planDt = DateHelper.plusDate(startDt, i - 1, DateHelper.DAY_FORMAT);
            plan = new GreenStarPlanPO();
            plan.setSid(DaoHelper.getSeq());
            plan.setMainPlanId(mainPlanPO.getSid());
            plan.setDateIndex(i);
            plan.setHasDone(0);
            if (Boolean.TRUE.equals(DateHelper.dateAfter(planDt, DateHelper.DAY_FORMAT, today, DateHelper.DAY_FORMAT)))
                plan.setIsLock(1);
            else
                plan.setIsLock(0);
            plan.setPlanDate(planDt);
            greenStarPlanMapper.addPlan(plan);
            List<GreenStarPlanItemPO> items = createOneDayPlan(i == 1);
            items.addAll(createCoursePlan(i, 3));
            for (GreenStarPlanItemPO item : items) {
                item.setSid(DaoHelper.getSeq());
                item.setDailyPlanId(plan.getSid());
                item.setHasDone(0);
                item.setDataJson(JSON.toJSONString(item.getSubPlans()));
                greenStarPlanItemMapper.addPlanItem(item);
            }
        }
    }


    /**
     * 是否已经开启计划
     *
     * @param dto
     * @return
     */
    @Override
    public boolean hasOpenPlan(GetGreenStarMainPlanDto dto) {
        return greenStarMainPlanMapper.hasMainPlan(dto);
    }

    @Override
    public boolean isMainPlanOverdue(GetGreenStarMainPlanDto dto) {
        return greenStarMainPlanMapper.isMainPlanOverdue(dto);
    }

    /**
     * 获取计划列表
     *
     * @param dto
     * @return
     */
    @Override
    public GreenStarMainPlanPO getMainPlan(GetGreenStarMainPlanDto dto) {
        GreenStarMainPlanPO mainPlan = greenStarMainPlanMapper.getMainPlan(dto);
        if (mainPlan == null)
            throw new BusinessException("无法获取主计划");
        List<GreenStarPlanPO> plans = greenStarPlanMapper.listPlan(mainPlan.getSid());
        mainPlan.setDailyPlans(plans);
        unLockPlan(mainPlan);
        mainPlan.setDailyPlans(plans);
        for (GreenStarPlanPO plan : plans) {
            List<GreenStarPlanItemPO> items = greenStarPlanItemMapper.listPlanItem(plan.getSid());
            if (items == null)
                items = new ArrayList<>();
            plan.setPlanCount(items.size());
            int count = (int) items.stream().filter(x -> x.getHasDone() == 1).count();
            plan.setHasDoneCount(count);
        }
        return mainPlan;
    }

    /**
     * 获取主计划信息
     *
     * @param planId 自计划id
     * @return
     */
    @Override
    public GreenStarMainPlanPO getMainPlanByDailyPlanId(String planId) {
        GreenStarMainPlanPO mainPlan = greenStarMainPlanMapper.getMainPlanByDailyPlanId(planId);
        if (mainPlan == null)
            throw new BusinessException("无法获取主计划");
        return mainPlan;
    }

    /**
     * 解锁计划
     */
    @Override
    public void unLockPlan(GreenStarMainPlanPO mainPlan) {
        String today = DateHelper.getDate(new Date(), DateHelper.DAY_FORMAT);
        boolean isCourseComplete = false;
        for (GreenStarPlanPO plan : mainPlan.getDailyPlans()) {
            plan.setPlanItems(listPlanItem(plan.getSid()));
            if (plan.getHasDone() == 0) {
                if (plan.getPlanItems().stream().anyMatch(x -> x.getPlanType() == GreenStarPlanConstant.PLAN_BEHAVIOR && x.getHasDone() == 0))
                    checkBehaviorPlan(plan.getSid());
                if (plan.getDateIndex() == 1) {
                    if (plan.getPlanItems().stream().anyMatch(x -> x.getPlanType() == GreenStarPlanConstant.PLAN_RECORDS && x.getHasDone() == 0))
                        checkRecordPlan(plan, mainPlan.getMemberId());
                }
            }
            if (plan.getDateIndex() == 3) {
                String lastDt = DateHelper.plusDate(plan.getPlanDate(), -1);
                if (getDyBloodSugarCount(mainPlan.getSensorNo(), lastDt) == 96) {
                    plan.getPlanItems().forEach(x -> {
                        if (x.getPlanType() == GreenStarPlanConstant.PLAN_LEARN) {
                            if (x.getSubPlans().stream().allMatch(v -> v.getPlanType().equals("4") && !v.isHasDone())) {
                                greenStarPlanItemMapper.deletePlanItem(x.getSid());
                            }
                            if (x.getSubPlans().stream().noneMatch(v -> v.getPlanType().equals("3"))) {
                                List<GreenStarPlanItemPO> coursePlans = createCoursePlan(3, 3);
                                for (GreenStarPlanItemPO coursePlan : coursePlans) {
                                    coursePlan.setSid(DaoHelper.getSeq());
                                    coursePlan.setDailyPlanId(plan.getSid());
                                    coursePlan.setHasDone(0);
                                    coursePlan.setDataJson(JSON.toJSONString(coursePlan.getSubPlans()));
                                    greenStarPlanItemMapper.addPlanItem(coursePlan);
                                }
                            }
                        }
                    });
                }
            }
            if (isCourseComplete) {
                plan.getPlanItems().forEach(x -> {
                    if (x.getPlanType() == GreenStarPlanConstant.PLAN_LEARN) {
                        x.setLock(false);
                        greenStarPlanItemMapper.updatePlanItem(x);
                    }
                });
            }
            isCourseComplete = plan.getPlanItems().stream().filter(x -> x.getPlanType() == GreenStarPlanConstant.PLAN_LEARN).allMatch(v -> v.getSubPlans().stream().anyMatch(GreenStarPlanSubItemVO::isHasDone));
            if (Boolean.TRUE.equals(DateHelper.dateAfter(today, DateHelper.DAY_FORMAT, plan.getPlanDate(), DateHelper.DAY_FORMAT))) {
                GreenStarPlanPO nextPlan = new GreenStarPlanPO();
                nextPlan.setMainPlanId(mainPlan.getSid());
                nextPlan.setDateIndex(plan.getDateIndex() + 1);
                greenStarPlanMapper.unLockPlanByMainPlan(nextPlan);
            }
        }
    }

    public void checkRecordPlan(GreenStarPlanPO plan, String memberId) {
        if (plan != null) {
            if (plan.getHasDone() == 0) {
                List<GreenStarPlanItemPO> items = listPlanItem(plan.getSid());
                GreenStarPlanItemPO item = items.stream().filter(x -> x.getPlanType() == GreenStarPlanConstant.PLAN_RECORDS && x.getHasDone() == 0).findFirst().orElse(null);
                if (item != null) {
                    JSONObject obj = checkMemberInfo(memberId);
                    if (obj.getBoolean("base") && obj.getBoolean("diabetes") && obj.getBoolean("style")) {
                        item.getSubPlans().forEach(x -> x.setHasDone(true));
                        if (item.getSubPlans().stream().allMatch(GreenStarPlanSubItemVO::isHasDone))
                            item.setHasDone(1);
                        item.setDataJson(JSONArray.toJSONString(item.getSubPlans()));
                        greenStarPlanItemMapper.updatePlanItem(item);
                        if (items.stream().allMatch(x -> x.getSubPlans().stream().allMatch(GreenStarPlanSubItemVO::isHasDone))) {
                            plan.setHasDone(1);
                            greenStarPlanMapper.updatePlan(plan);
                        }
                    }
                }
            }
        }
    }


    /**
     * 检测行为记录是否完成
     */
    @Override
    public boolean checkBehaviorPlan(String planId) {
        GreenStarPlanPO plan = getPlanById(planId);
        if (plan != null) {
            GreenStarMainPlanPO mainPlan = greenStarMainPlanMapper.getMainPlanById(plan.getMainPlanId());
            if (mainPlan != null) {
                List<GreenStarPlanItemPO> items = listPlanItem(planId);
                for (GreenStarPlanItemPO item : items) {
                    if (item.getPlanType() == GreenStarPlanConstant.PLAN_BEHAVIOR && item.getHasDone() == 0) {
                        boolean diet = checkDietRemember(mainPlan.getMemberId(), plan.getPlanDate(), null);
                        boolean sport = checkSportRemember(mainPlan.getMemberId(), plan.getPlanDate());
                        if (diet) {
                            item.getSubPlans().forEach(x -> {
                                if (Objects.equals(x.getPlanType(), GreenStarPlanConstant.PLAN_BEHAVIOR_DIET))
                                    x.setHasDone(true);
                            });
                        }
                        if (sport) {
                            item.getSubPlans().forEach(x -> {
                                if (Objects.equals(x.getPlanType(), GreenStarPlanConstant.PLAN_BEHAVIOR_SPORT))
                                    x.setHasDone(true);
                            });
                        }
                    }
                    if (item.getPlanType() == GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION) {
                        int type = 1;
                        for (GreenStarPlanSubItemVO subItem : item.getSubPlans()
                        ) {
                            switch (subItem.getPlanType()) {
                                case GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_BREAKFAST:
                                    type = 1;
                                    break;
                                case GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_LUNCH:
                                    type = 2;
                                    break;
                                case GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_DINNER:
                                    type = 3;
                                    break;
                            }
                            subItem.setHasDone(checkDietRemember(mainPlan.getMemberId(), DateHelper.plusDate(plan.getPlanDate(), -1), type));
                        }
                    }
                    item.setDataJson(JSON.toJSONString(item.getSubPlans()));
                    if (item.getSubPlans().stream().allMatch(GreenStarPlanSubItemVO::isHasDone)) {
                        item.setHasDone(1);
                    }
                    greenStarPlanItemMapper.updatePlanItem(item);
                }
                if (plan.getHasDone() == 0 && items.stream().allMatch(v -> v.getSubPlans().stream().allMatch(GreenStarPlanSubItemVO::isHasDone))) {
                    plan.setHasDone(1);
                    greenStarPlanMapper.updatePlan(plan);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @param planId
     */
    @Override
    public boolean checkUpdate(String planId) {
        boolean flag = false;
        GreenStarPlanPO plan = getPlanById(planId);
        if (plan != null) {
            GreenStarMainPlanPO mainPlan = greenStarMainPlanMapper.getMainPlanById(plan.getMainPlanId());
            List<DYYPBloodSugarPO> values = this.dyBloodSugarService
                    .listDataWechatSourceOfYPParamLogOfOBDTASC(plan.getPlanDate(), plan.getPlanDate(), mainPlan.getSensorNo());
            if (values.size() > 0) {
                List<GreenStarPlanItemPO> itemArray = plan.getPlanItems();
                if (itemArray != null) {
                    GreenStarPlanItemPO item;
                    item = itemArray.stream().filter(
                            v -> v.getPlanType() == GreenStarPlanConstant.PLAN_UPDATE_DY_DATE).findFirst().orElse(null);
                    if (item != null) {
                        item.getSubPlans().forEach(v -> {
                            v.setHasDone(true);
                        });
                        item.setDataJson(JSON.toJSONString(item.getSubPlans()));
                        item.setHasDone(1);
                        greenStarPlanItemMapper.updatePlanItem(item);
                        if (itemArray.stream().allMatch(x -> x.getHasDone() == 1)) {
                            plan.setHasDone(1);
                            greenStarPlanMapper.updatePlan(plan);
                        }
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }


    /**
     * 添加一天计划
     *
     * @param po
     */
    @Override
    public void addPlan(GreenStarPlanPO po) {
        greenStarPlanMapper.addPlan(po);
    }

    /**
     * 修改计划
     *
     * @param po
     */
    @Override
    public void updatePlan(GreenStarPlanPO po) {
        greenStarPlanMapper.updatePlan(po);
    }

    /**
     * 获取一天计划
     *
     * @return
     */
    @Override
    public GreenStarPlanPO getPlanById(String planId) {
        GreenStarPlanPO plan = greenStarPlanMapper.getPlanById(planId);
        if (plan == null)
            throw new BusinessException("计划不存在");
        List<GreenStarPlanItemPO> items = listPlanItem(planId);
        plan.setPlanCount(items.size());
        int count = (int) items.stream().filter(x -> x.getHasDone() == 1).count();
        plan.setHasDoneCount(count);
        plan.setPlanItems(items);
        return plan;
    }

    public List<GreenStarPlanItemPO> listPlanItem(String planId) {
        List<GreenStarPlanItemPO> items = greenStarPlanItemMapper.listPlanItem(planId);
        for (GreenStarPlanItemPO item : items) {
            List<GreenStarPlanSubItemVO> subPlan = JSON.parseArray(item.getDataJson(), GreenStarPlanSubItemVO.class);
            item.setSubPlans(subPlan);
        }
        return items;
    }

    /**
     * 批量生成计划
     *
     * @param plans
     * @return
     */
    @Override
    public List<GreenStarPlanItemPO> createPlans(Map<Integer, List<JSONObject>> plans) {
        List<GreenStarPlanItemPO> array = new ArrayList<>();
        for (int key : plans.keySet()) {
            GreenStarPlanItemPO item = new GreenStarPlanItemPO();
            List<JSONObject> sub = plans.get(key);
            for (JSONObject desc : sub) {
                if (desc.containsKey("type") && desc.containsKey("value")) {
                    GreenStarPlanSubItemVO subItem = new GreenStarPlanSubItemVO();
                    subItem.setPlanType(desc.getString("type"));
                    subItem.setPlanDesc(desc.getString("value"));
                    subItem.setSid(DaoHelper.getSeq());
                    subItem.setHasDone(false);
                    item.addSubPlan(subItem);
                }
            }
            item.setPlanType(key);
            item.setHasDone(0);
            item.setPlanName(GreenStarPlanConstant.PLAN_NAME.get(key));
            array.add(item);
        }
        return array;
    }

    /**
     * 创建一天计划表
     *
     * @param isFirst
     * @return
     */
    @Override
    public List<GreenStarPlanItemPO> createOneDayPlan(boolean isFirst) {
        List<GreenStarPlanItemPO> array = new ArrayList<>();
        array.addAll(createPlans(GreenStarPlanConstant.DEFAULT_BEHAVIOR_PLAN));
        array.addAll(createPlans(GreenStarPlanConstant.DEFAULT_UPDATE_DY_DATE_PLAN));
        if (isFirst) {
            array.addAll(createPlans(GreenStarPlanConstant.DEFAULT_PLAN_RECORDS_PLAN));
        }
        return array;
    }

    public List<GreenStarPlanItemPO> createCoursePlan(int dateIndex, int type) {
        List<GreenStarPlanItemPO> array = new ArrayList<>();
        List<GreenStarPlanCoursePO> courses = greenStarPlanCourseMapper.listCourseByDate(dateIndex);
        for (GreenStarPlanCoursePO course : courses) {
            if (dateIndex == 3 && course.getCourseIndex() != type)
                continue;
            GreenStarPlanItemPO item = new GreenStarPlanItemPO();
            item.setPlanType(GreenStarPlanConstant.PLAN_LEARN);
            item.setPlanName(GreenStarPlanConstant.PLAN_NAME.get(GreenStarPlanConstant.PLAN_LEARN));
            item.setLock(1 != dateIndex);
            GreenStarPlanSubItemVO subItem = new GreenStarPlanSubItemVO();
            subItem.setSid(DaoHelper.getSeq());
            subItem.setPlanType(course.getCourseIndex().toString());
            subItem.setPlanDesc(course.getTitle());
            subItem.setForeignKey(course.getSid());
            subItem.setHasDone(false);
            item.addSubPlan(subItem);
            array.add(item);
        }
        return array;
    }


    /**
     * 添加一条异常计划
     *
     * @param planId
     * @param type
     * @param exception
     */
    @Override
    public void addExceptionPlan(String planId, String type, String exception) {
        synchronized (this) {
            GreenStarPlanPO plan = getPlanById(planId);
            GreenStarMainPlanPO mainPlan = greenStarMainPlanMapper.getMainPlanById(plan.getMainPlanId());
            if (mainPlan == null)
                throw new BusinessException("未找到相应计划");
            for (GreenStarPlanItemPO item : plan.getPlanItems()) {
                if (item.getPlanType() == GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION) {
                    for (GreenStarPlanSubItemVO subItem : item.getSubPlans()) {
                        if (Objects.equals(type, subItem.getPlanType()))
                            return;
                    }
                }
            }
            for (GreenStarPlanItemPO item : plan.getPlanItems()
            ) {
                if (item.getPlanType() != GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION)
                    continue;
                for (GreenStarPlanSubItemVO subItem : item.getSubPlans()) {
                    if (Objects.equals(subItem.getPlanType(), type))
                        return;
                }
            }

            Integer time = null;
            switch (type) {
                case GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_BREAKFAST:
                    time = 1;
                    break;
                case GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_LUNCH:
                    time = 2;
                    break;
                case GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION_DINNER:
                    time = 3;
                    break;
            }
            if (checkDietRemember(mainPlan.getMemberId(), DateHelper.plusDate(plan.getPlanDate(), -1), time))
                return;
            GreenStarPlanItemPO itemPO = new GreenStarPlanItemPO();
            itemPO.setPlanType(GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION);
            itemPO.setPlanName(GreenStarPlanConstant.PLAN_NAME.get(GreenStarPlanConstant.PLAN_DY_BS_EXCEPTION));
            itemPO.setSid(DaoHelper.getSeq());
            itemPO.setDailyPlanId(planId);
            itemPO.setHasDone(0);
            GreenStarPlanSubItemVO subItem = new GreenStarPlanSubItemVO();
            subItem.setSid(DaoHelper.getSeq());
            subItem.setPlanType(type);
            subItem.setPlanDesc(exception);
            subItem.setHasDone(false);
            List<GreenStarPlanSubItemVO> subItems = new ArrayList<>();
            subItems.add(subItem);
            itemPO.setDataJson(JSON.toJSONString(subItems));
            greenStarPlanItemMapper.addPlanItem(itemPO);
            plan.setHasDone(0);
            greenStarPlanMapper.updatePlan(plan);
        }
    }


    /**
     * @param planId 计划id
     * @param ex
     */
    @Override
    public void createExceptionPlan(String planId, List<JSONObject> ex) {
        GreenStarMainPlanPO mainPlan = greenStarMainPlanMapper.getMainPlanByDailyPlanId(planId);
        if (mainPlan == null)
            throw new BusinessException("未找到相应计划");
        if (ex != null) {
            String error = "您%s，请录入您%s的用餐、运动和用药记录；";
            String s1 = "";
            String s2 = "";
            for (JSONObject obj : ex
            ) {
                if (!obj.containsKey("createError") || obj.getInteger("createError") == 0)
                    continue;
                if (obj.containsKey("errorDesc"))
                    s1 = obj.getString("errorDesc");
                if (obj.containsKey("errorTime"))
                    s2 = obj.getString("errorTime");
                addExceptionPlan(planId, obj.getString("errorType"), String.format(error, s1, s2));
            }
        }
    }


    /**
     * 判断三小时内是否上传数据
     *
     * @param sensorNo
     * @return
     */
    @Override
    public boolean checkUpdateThreeHour(String sensorNo) {
        Date now = new Date();
        String date = DateHelper.getDate(now, DateHelper.DAY_FORMAT);
        //加载数据
        List<DYYPBloodSugarPO> values = this.dyBloodSugarService
                .listDataWechatSourceOfYPParamLogOfOBDTASC(date, date, sensorNo);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, -3);
        Date hourBef = calendar.getTime();
        return values != null && values.stream().anyMatch(v -> v.getRecordTime().before(now) && v.getRecordTime().after(hourBef));
    }

    public int getDyBloodSugarCount(String sensorNo, String date) {
        //加载数据
        List<DYYPBloodSugarPO> values = this.dyBloodSugarService
                .listDataWechatSourceOfYPParamLogOfOBDTASC(date, date, sensorNo);
        return values.size();
    }


    private boolean checkDietRemember(String memberId, String date, Integer type) {
        DietRecordDTO dto = new DietRecordDTO();
        dto.setMemberId(memberId);
        dto.setRecordDt(date);
        dto.setTimeType(type);
        List<DietRecordPO> records = dietRecordMapper.listDietRecord(dto);
        return records != null && records.size() > 0;
    }

    private boolean checkSportRemember(String memberId, String date) {
        SportRecordDTO dto = new SportRecordDTO();
        dto.setMemberId(memberId);
        dto.setRecordDt(date);
        List<SportRecordPO> records = sportRecordMapper.listSportRecord(dto);
        return records != null && records.size() > 0;
    }


    @Override
    public void updateMemberArchives(GsMemberArchivesPO po) {
        if (memberArchivesMapper.hasMemberArchives(po.getMemberId())) {
            memberArchivesMapper.updateMemberArchives(po);
        } else {
            po.setSid(DaoHelper.getSeq());
            memberArchivesMapper.addMemberArchives(po);
        }
    }

    @Override
    public GsMemberArchivesPO getMemberArchivesById(String memberId) {
        return memberArchivesMapper.getMemberArchivesById(memberId);
    }

    /**
     * 验证患者信息是否已经完善
     *
     * @param memberId
     * @return
     */
    @Override
    public JSONObject checkMemberInfo(String memberId) {
        MemberPO memberPO = memberService.getMemberById(memberId);
        GsMemberArchivesPO memberArchives = getMemberArchivesById(memberId);
        JSONObject obj = new JSONObject();
        boolean flag = true;
        if (memberPO == null) {
            flag = false;
        } else {
            if (memberPO.getSex() == null)
                flag = false;
            if (StringUtils.isBlank(memberPO.getBirthday()))
                flag = false;
            if (StringUtils.isBlank(memberPO.getHeight()))
                flag = false;
            if (StringUtils.isBlank(memberPO.getWeight()))
                flag = false;
        }
        obj.put("base", flag);
        flag = true;
        if (memberPO == null) {
            flag = false;
        } else {
            if (StringUtils.isBlank(memberPO.getDiabetesType()))
                flag = false;
            if (StringUtils.isBlank(memberPO.getDiabetesDate()))
                flag = false;
            if (memberPO.getTreatment() == null)
                flag = false;
        }
        obj.put("diabetes", flag);
        flag = true;
        if (memberArchives == null) {
            flag = false;
        } else {
            if (memberArchives.getIsDrink() == null)
                flag = false;
            if (memberArchives.getIsSmoke() == null)
                flag = false;
            if (memberArchives.getDietaryHabit() == null)
                flag = false;
            if (memberArchives.getExerciseIntensity() == null)
                flag = false;
        }
        obj.put("style", flag);
        return obj;
    }

    /**
     * 获取患者记录用餐时间
     *
     * @param memberId 患者id
     * @return
     */
    @Override
    public Map<Integer, String> getMemberEatTime(String memberId, String date) {
        GsMemberArchivesPO memberArchives = getMemberArchivesById(memberId);
        if(memberArchives == null){
            memberArchives = new GsMemberArchivesPO();
        }
        DietRecordDTO dto = new DietRecordDTO();
        dto.setMemberId(memberId);
        dto.setRecordDt(date);
        dto.setTimeType(1);
        String time1 = dietRecordMapper.getEatingTime(dto);
        dto.setTimeType(2);
        String time2 = dietRecordMapper.getEatingTime(dto);
        dto.setTimeType(3);
        String time3 = dietRecordMapper.getEatingTime(dto);
        Map<Integer, String> eatTime = new HashMap<>();
        if (!StringUtils.isBlank(time1))
            eatTime.put(1, time1);
        else if (!StringUtils.isBlank(memberArchives.getBreakfastTime()))
            eatTime.put(1, memberArchives.getBreakfastTime());
        if (!StringUtils.isBlank(time2))
            eatTime.put(2, time2);
        else if (!StringUtils.isBlank(memberArchives.getLunchTime()))
            eatTime.put(2, memberArchives.getLunchTime());
        if (!StringUtils.isBlank(time3))
            eatTime.put(3, time3);
        else if (!StringUtils.isBlank(memberArchives.getDinnerTime()))
            eatTime.put(3, memberArchives.getDinnerTime());
        return eatTime;
    }

    /**
     * 获取绿星计划学习课程
     *
     * @param sid
     * @return
     */
    @Override
    public GreenStarPlanCoursePO getGreenStarCourseById(String sid) {
        return greenStarPlanCourseMapper.getCourseById(sid);
    }

    /**
     * 完成学习计划
     *
     * @param planId 每日计划id
     * @param subId  学习计划item ID
     */
    @Override
    public void completeCoursePlan(String planId, String subId) {
        GreenStarPlanPO plan = getPlanById(planId);
        if (plan == null)
            throw new BusinessException("计划不存在");
        GreenStarPlanItemPO item = plan.getPlanItems().stream().filter(x -> Objects.equals(x.getSid(), subId)).findFirst().orElse(null);
        if (item == null)
            throw new BusinessException("计划不存在");
        item.setHasDone(1);
        item.getSubPlans().forEach(x -> x.setHasDone(true));
        item.setDataJson(JSON.toJSONString(item.getSubPlans()));
        greenStarPlanItemMapper.updatePlanItem(item);
        if (plan.getPlanItems().stream().allMatch(x -> x.getHasDone() == 1)) {
            plan.setHasDone(1);
            greenStarPlanMapper.updatePlan(plan);
        }
    }

    /**
     * 获取有记一记的日期
     *
     * @param dto
     * @return
     */
    @Override
    public List<String> listRecordDt(DietRecordDTO dto) {
        return dietRecordMapper.listRecordDt(dto);
    }

    /**
     * 删除一个完整绿星计划
     *
     * @param sid 主计划id
     */
    @Override
    public void deleteGreenStarPlanById(String sid) {
        greenStarPlanItemMapper.deletePlanItemByMainPlanId(sid);
        greenStarPlanMapper.deletePlanByMainPlanId(sid);
        greenStarMainPlanMapper.deleteMainPlanBydId(sid);
    }

    /**
     * 获取课程列表
     *
     * @return
     */
    @Override
    public List<GreenStarPlanCoursePO> listGreenStarPlanCourse() {
        return greenStarPlanCourseMapper.listCourse();
    }

    /**
     * 更新或添加课程
     *
     * @param course
     */
    @Override
    public void updateGreenStarPlanCourse(GreenStarPlanCoursePO course) {
        if (StringUtils.isBlank(course.getSid())) {
            greenStarPlanCourseMapper.addCourse(course);
        } else {
            greenStarPlanCourseMapper.updateCourse(course);
        }
    }

    @Override
    public List<JSONObject> getGreenStarPlanMenu(GetGreenStarMainPlanDto dto) {
        GreenStarMainPlanPO mainPlanPO = getMainPlan(dto);
        List<JSONObject> menu = new ArrayList<>();
        List<GreenStarPlanPO> plans = mainPlanPO.getDailyPlans();
        for (GreenStarPlanPO planPO : plans) {
            JSONObject json = new JSONObject();
            List<String> list = new ArrayList<>();
            int count = greenStarPlanCourseMapper.countDailyCourse(planPO.getDateIndex());
            if (count > 0) {
                list.add(String.format("学习计划%d篇", count));
            }
            list.add("记录饮食、运动情况");
            json.put("weekIndex", (planPO.getDateIndex() - 1) / 7 + 1);
            json.put("dataIndex", planPO.getDateIndex());
            json.put("item", list);
            menu.add(json);
        }

        return menu;
    }
}
