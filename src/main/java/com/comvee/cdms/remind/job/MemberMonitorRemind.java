package com.comvee.cdms.remind.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.bloodmonitor.constant.MonitorPlanConstant;
import com.comvee.cdms.bloodmonitor.dto.GetMemberMonitorDTO;
import com.comvee.cdms.bloodmonitor.dto.MemberMonitorPlanDetailDTO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanDetailPO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanDetailServiceI;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanServiceI;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.member.service.MemberManageService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.remind.bo.MemberMonitorDataBO;
import com.comvee.cdms.remind.constant.MemberRemindTypeConstant;
import com.comvee.cdms.remind.dto.AddMemberRemindDTO;
import com.comvee.cdms.remind.service.MemberRemindService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2018/11/8
 */
@Component
public class MemberMonitorRemind{

    @Autowired
    private MemberMonitorPlanDetailServiceI memberMonitorPlanDetailService;

    @Autowired
    private MemberRemindService memberRemindService;

    @Autowired
    private MemberMonitorPlanServiceI memberMonitorPlanService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private MemberManageService memberManageService;

    private final static int PAGE_ROWS = 100;


    @XxlJob("memberMonitorRemind")
    public ReturnT<String> execute(String param) throws Exception {
        int page = 1;
        PageResult<String> pageResult;
        PageRequest pageRequest = new PageRequest();
        pageRequest.setRows(PAGE_ROWS);
        do{
            pageRequest.setPage(page);
            pageResult = this.memberManageService.listAllManageMemberDistinct(pageRequest);
            startHandler(pageResult.getRows());
            page ++;
        }while (pageResult.getTotalPages() > (page -1));

        return ReturnT.SUCCESS;
    }

    /**
     * 开始处理数据
     * @param memberList
     */
    private void startHandler(List<String> memberList){
        if(memberList == null || memberList.size() == 0){
            return;
        }
        memberList.forEach(x -> {
            //血糖监测方案处理
            monitorRemindHandler(x ,MonitorPlanConstant.ILLNESS_TYPE_DIABETES);
            //血压监测方案处理
            monitorRemindHandler(x ,MonitorPlanConstant.ILLNESS_TYPE_HYPERTENSION);
        });
    }

    /**
     * 下发检测提醒
     * @param memberId
     */
    private void monitorRemindHandler(String memberId ,Integer illnessType){
        int dataCode = getDateCode();
        MemberMonitorPlanDetailDTO memberMonitorPlanDetailDTO = new MemberMonitorPlanDetailDTO();
        memberMonitorPlanDetailDTO.setDateCode(String.valueOf(dataCode));
        memberMonitorPlanDetailDTO.setMemberId(memberId);
        memberMonitorPlanDetailDTO.setIllnessType(illnessType);
        List<MemberMonitorPlanDetailPO> list = this.memberMonitorPlanDetailService.listMemberMonitorPlanDetail(memberMonitorPlanDetailDTO);
        //今日无监测方案，直接返回
        if(list == null || list.size() == 0){
            return;
        }
        GetMemberMonitorDTO getMemberMonitorDTO = new GetMemberMonitorDTO();
        getMemberMonitorDTO.setMemberId(memberId);
        getMemberMonitorDTO.setInProgress(MonitorPlanConstant.PLAN_IN_PROGRESS_YES);
        getMemberMonitorDTO.setIllnessType(illnessType);
        MemberMonitorPlanPO memberMonitorPlanPO = this.memberMonitorPlanService.getMemberMonitorPlan(getMemberMonitorDTO);
        if(memberMonitorPlanPO == null){
            return;
        }


        List<String> timeList = list.stream().map(MemberMonitorPlanDetailPO::getMonitorTime).collect(Collectors.toList());
        Collections.sort(timeList);
        String timeCode = StringUtils.convertListToSpiltString(timeList, ",");
        sendMemberMonitorRemind(memberId, timeCode, memberMonitorPlanPO.getPlanId(), dataCode ,illnessType);
        addMonitorRemindWechatMessage(timeCode, memberId, memberMonitorPlanPO.getPlanId() ,illnessType);
    }

    /**
     * 测量提醒
     * @param timeCode
     * @param memberId
     * @param planId
     */
    private void addMonitorRemindWechatMessage(String timeCode, String memberId, String planId ,Integer illnessType){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timeCode", timeCode);
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        addWechatMessageDTO.setForeignId(planId);
        addWechatMessageDTO.setMemberId(memberId);
        if(illnessType == MonitorPlanConstant.ILLNESS_TYPE_DIABETES){
            addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_MONITOR);
        }else if(illnessType == MonitorPlanConstant.ILLNESS_TYPE_HYPERTENSION){
            addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_BLOOD_PRESSURE_MONITOR);
        }
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 下发监测方案提醒
     * @param memberId
     * @param timeCode
     */
    private void sendMemberMonitorRemind(String memberId, String timeCode, String planId, int dataCode ,Integer illnessType){
        AddMemberRemindDTO addMemberRemindDTO = new AddMemberRemindDTO();
        MemberMonitorDataBO memberMonitorDataBO = new MemberMonitorDataBO();
        memberMonitorDataBO.setTimeCode(timeCode);
        memberMonitorDataBO.setWeekText(WEEK_TEXT_MAP.get(dataCode));
        addMemberRemindDTO.setDataJsonStr(JSON.toJSONString(memberMonitorDataBO));
        addMemberRemindDTO.setMemberId(memberId);
        addMemberRemindDTO.setForeignId(planId);
        //血糖
        if(illnessType == MonitorPlanConstant.ILLNESS_TYPE_DIABETES){
            addMemberRemindDTO.setRemindType(MemberRemindTypeConstant.REMIND_TYPE_MONITOR_BLOOD_SUGAR);
            addMemberRemindDTO.setRemindMessage(REMIND_MESSAGE);
        }
        //血压
        else if(illnessType == MonitorPlanConstant.ILLNESS_TYPE_HYPERTENSION){
            addMemberRemindDTO.setRemindType(MemberRemindTypeConstant.REMIND_TYPE_MONITOR_BLOOD_PRESSURE);
            addMemberRemindDTO.setRemindMessage(REMIND_MESSAGE_HYP);
        }
        this.memberRemindService.addMemberRemind(addMemberRemindDTO);
    }

    private final static String REMIND_MESSAGE = "血糖监测提醒";
    private final static String REMIND_MESSAGE_HYP = "血压监测提醒";

    /**
     * 获取星期几的code
     * @return
     */
    private int getDateCode(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static final Map<Integer,String> WEEK_TEXT_MAP = new HashMap<>();
    static {
        WEEK_TEXT_MAP.put(1, "周一");
        WEEK_TEXT_MAP.put(2, "周二");
        WEEK_TEXT_MAP.put(3, "周三");
        WEEK_TEXT_MAP.put(4, "周四");
        WEEK_TEXT_MAP.put(5, "周五");
        WEEK_TEXT_MAP.put(6, "周六");
        WEEK_TEXT_MAP.put(7, "周日");
    }
}
