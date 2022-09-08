package com.comvee.cdms.remind.job;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.bloodmonitor.dto.MemberMonitorPlanDetailDTO;
import com.comvee.cdms.bloodmonitor.po.MemberMonitorPlanDetailPO;
import com.comvee.cdms.bloodmonitor.service.MemberMonitorPlanDetailServiceI;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.remind.bo.MemberRevisitMonitorDataBO;
import com.comvee.cdms.remind.constant.MemberRemindTypeConstant;
import com.comvee.cdms.remind.dto.AddMemberRemindDTO;
import com.comvee.cdms.remind.service.MemberRemindService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2018/11/14
 */
@Component
public class MemberRevisitMonitorRemind{

    @Autowired
    private PackageService packageService;

    @Autowired
    private MemberRemindService memberRemindService;

    @Autowired
    private FollowServiceI followServiceI;

    @Autowired
    private MemberMonitorPlanDetailServiceI memberMonitorPlanDetailService;

    private final static int PAGE_ROWS = 100;

    private final static int ADVANCE_DAYS = 1;

    @XxlJob("memberRevisitMonitorRemind")
    public ReturnT<String> execute(String param) throws Exception {
        int page = 1;
        PageResult<String> pageResult;
        do{
            pageResult = this.packageService.listHasValidServiceMember(ServiceCode.ZHI_NENG_ZHUI_ZONG, page, PAGE_ROWS);
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
            listRevisitDoctor(x);
        });
    }

    /**
     * 加载复诊医生列表
     * @param memberId
     */
    private void listRevisitDoctor(String memberId){
        List<String> doctorList = this.followServiceI.listMemberRevisitDoctor(memberId, ADVANCE_DAYS);
        if(doctorList == null || doctorList.size() == 0){
            return;
        }
        String timeCode = getTimeCode(memberId);
        revisitMonitorRemindHandler(memberId, timeCode);
    }

    /**
     * 获取复诊前监测方案时间点
     * @param memberId
     * @return
     */
    private String getTimeCode(String memberId){
        MemberMonitorPlanDetailDTO memberMonitorPlanDetailDTO = new MemberMonitorPlanDetailDTO();
        memberMonitorPlanDetailDTO.setMemberId(memberId);
        memberMonitorPlanDetailDTO.setDateCode("8");
        List<MemberMonitorPlanDetailPO> list = this.memberMonitorPlanDetailService.listMemberMonitorPlanDetail(memberMonitorPlanDetailDTO);
        String timeCode = "";
        if(list != null && list.size() > 0){
            timeCode = StringUtils.convertListToSpiltString(
                    list.stream()
                            .map(MemberMonitorPlanDetailPO::getMonitorTime)
                            .collect(Collectors.toList())
                    , ",");
        }else{
            timeCode = "1,3,4,5,6";
        }
        return timeCode;

    }

    /**
     * 复诊提醒处理
     * @param memberId
     */
    private void revisitMonitorRemindHandler(String memberId, String timeCode){
        MemberRevisitMonitorDataBO memberMonitorDataBO = new MemberRevisitMonitorDataBO();
        memberMonitorDataBO.setTimeCode(timeCode);
        AddMemberRemindDTO addMemberRemindDTO = new AddMemberRemindDTO();
        addMemberRemindDTO.setDataJsonStr(JSON.toJSONString(memberMonitorDataBO));
        addMemberRemindDTO.setRemindType(MemberRemindTypeConstant.REMIND_TYPE_REVISIT_MONITOR);
        addMemberRemindDTO.setMemberId(memberId);
        addMemberRemindDTO.setForeignId(DEFAULT_FOREIGN_ID);
        addMemberRemindDTO.setRemindMessage(REMIND_MESSAGE);
        this.memberRemindService.addMemberRemind(addMemberRemindDTO);
    }

    private final static String REMIND_MESSAGE = "复诊前一天测量血糖提醒";
    private final static String DEFAULT_FOREIGN_ID = "-1";
}
