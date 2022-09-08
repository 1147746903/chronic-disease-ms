package com.comvee.cdms.remind.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.MessageTool;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorServiceI;
import com.comvee.cdms.follow.service.FollowServiceI;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.remind.bo.MemberRevisitDataBO;
import com.comvee.cdms.remind.constant.MemberRemindTypeConstant;
import com.comvee.cdms.remind.dto.AddMemberRemindDTO;
import com.comvee.cdms.remind.service.MemberRemindService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: suyz
 * @date: 2018/11/14
 */
@Component
@Deprecated
public class MemberRevisitRemind{

    @Autowired
    private PackageService packageService;

    @Autowired
    private MemberRemindService memberRemindService;

    @Autowired
    private FollowServiceI followServiceI;

    @Autowired
    private DoctorServiceI doctorServiceI;


    @Autowired
    private WechatMessageService wechatMessageService;

    private final static int PAGE_ROWS = 100;

    /**
     * 提前3/1天提醒
     */
    private final static int ADVANCE_DAYS_THREE = 3;
    private final static int ADVANCE_DAYS_ONE = 1;

    @XxlJob("memberRevisitRemind")
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
        List<String> doctorList = this.followServiceI.listMemberRevisitDoctor(memberId, ADVANCE_DAYS_THREE);
        Set<String> doctorSet = null;
        if(doctorList != null && doctorList.size() > 0){
            doctorSet = new HashSet<>(doctorList);
            doctorSet.forEach( x -> {
                revisitRemindHandler(memberId, x, ADVANCE_DAYS_THREE);
            });
        }

        doctorList = this.followServiceI.listMemberRevisitDoctor(memberId, ADVANCE_DAYS_ONE);
        if(doctorList != null && doctorList.size() > 0){
            doctorSet = new HashSet<>(doctorList);
            doctorSet.forEach( x -> {
                revisitRemindHandler(memberId, x, ADVANCE_DAYS_ONE);
            });
        }
    }

    /**
     * 复诊提醒处理
     * @param memberId
     * @param doctorId
     */
    private void revisitRemindHandler(String memberId, String doctorId, int day){
        MemberRevisitDataBO memberMonitorDataBO = new MemberRevisitDataBO();
        memberMonitorDataBO.setContent(MessageTool.format(REMIND_CONTENT, getDoctorName(doctorId), day));
        AddMemberRemindDTO addMemberRemindDTO = new AddMemberRemindDTO();
        addMemberRemindDTO.setDataJsonStr(JSON.toJSONString(memberMonitorDataBO));
        addMemberRemindDTO.setRemindType(MemberRemindTypeConstant.REMIND_TYPE_REVISIT);
        addMemberRemindDTO.setMemberId(memberId);
        addMemberRemindDTO.setForeignId(doctorId);
        addMemberRemindDTO.setRemindMessage(REMIND_MESSAGE);
        this.memberRemindService.addMemberRemind(addMemberRemindDTO);

        addRevisitWechatMessage(memberId, doctorId, day);
    }

    /**
     * 添加微信消息
     * @param memberId
     * @param doctorId
     * @param day
     */
    private void addRevisitWechatMessage(String memberId, String doctorId, int day){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctorId", doctorId);
        jsonObject.put("day", day);
        jsonObject.put("revisitDate", getRevisitDate(day));
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_REVISIT);
        addWechatMessageDTO.setForeignId(Constant.DEFAULT_FOREIGN_ID);
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        addWechatMessageDTO.setMemberId(memberId);
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 获取复诊时间
     * @param jsonObject
     * @return
     */
    private String getRevisitDate(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    private final static String REMIND_MESSAGE = "复诊提醒";
    private final static String REMIND_CONTENT = "小医助提醒您，{0}医生安排你{1}天后回院复诊，请提前做好准备工作哦";

    /**
     * 获取医生姓名
     * @param doctorId
     * @return
     */
    private String getDoctorName(String doctorId){
        DoctorPO doctorPO = this.doctorServiceI.getDoctorById(doctorId);
        if(doctorPO == null){
            return "";
        }
        return doctorPO.getDoctorName();
    }
}
