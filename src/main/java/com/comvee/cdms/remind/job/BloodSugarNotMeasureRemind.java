package com.comvee.cdms.remind.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.member.service.MemberService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.packages.service.PackageService;
import com.comvee.cdms.remind.bo.MemberUnMeasureDataBO;
import com.comvee.cdms.remind.constant.MemberRemindTypeConstant;
import com.comvee.cdms.remind.dto.AddMemberRemindDTO;
import com.comvee.cdms.remind.service.MemberRemindService;
import com.comvee.cdms.sign.po.BloodSugarPO;
import com.comvee.cdms.sign.service.BloodSugarService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: suyz
 * @date: 2018/11/16
 */
@Component
public class BloodSugarNotMeasureRemind{

    @Autowired
    private PackageService packageService;

    @Autowired
    private MemberRemindService memberRemindService;

    @Autowired
    private BloodSugarService bloodSugarService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private WechatMessageService wechatMessageService;

    private final static int PAGE_ROWS = 50;

    private final static int INTERVAL_DAY = 8;

    @XxlJob("bloodSugarNotMeasureRemind")
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
            unMeasureRemind(x);
        });
    }

    /**
     * 未检测血糖提醒
     * @param memberId
     */
    private void unMeasureRemind(String memberId){
        BloodSugarPO bloodSugarPO = this.bloodSugarService.getLatestBloodSugar(memberId , null,null);
        String date = "";
        //判断最后一条血糖记录时间
        if(bloodSugarPO != null){
            date = bloodSugarPO.getRecordDt();
        }else{
            //没有血糖记录则根据注册时间判断
            GetMemberDTO getMemberDTO = new GetMemberDTO();
            getMemberDTO.setMemberId(memberId);
            MemberPO memberPO = this.memberService.getMember(getMemberDTO);
            if(memberPO != null){
                date = memberPO.getInsertDt();
            }

        }
        if(StringUtils.isBlank(date)){
            return;
        }
        int diff = getDiffDay(date);
        if(diff > INTERVAL_DAY * REMIND_TIME_MAX){
            return;
        }
        if(diff % INTERVAL_DAY != 0){
            return;
        }
        sendUnMeasureRemind(memberId, diff);
    }

    /**
     * 发送血糖未测量提醒
     * @param memberId
     */
    private void sendUnMeasureRemind(String memberId,int days){
        MemberUnMeasureDataBO memberUnMeasureDataBO = new MemberUnMeasureDataBO();
        memberUnMeasureDataBO.setDays(days - 1);
        memberUnMeasureDataBO.setRemindDate(DateHelper.getToday());

        AddMemberRemindDTO addMemberRemindDTO = new AddMemberRemindDTO();
        addMemberRemindDTO.setDataJsonStr(JSON.toJSONString(memberUnMeasureDataBO));
        addMemberRemindDTO.setRemindType(MemberRemindTypeConstant.REMIND_TYPE_BLOOD_SUGAR_UN_MEASURE);
        addMemberRemindDTO.setMemberId(memberId);
        addMemberRemindDTO.setForeignId(Constant.DEFAULT_FOREIGN_ID);
        addMemberRemindDTO.setRemindMessage(REMIND_MESSAGE);
        this.memberRemindService.addMemberRemind(addMemberRemindDTO);

        addUnMeasureWechatMessage(memberId, days - 1);
    }

    /**
     * 添加微信消息 - 血糖未测提醒
     * @param memberId
     * @param days
     */
    private void addUnMeasureWechatMessage(String memberId,int days){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("days", days);
        jsonObject.put("content", getRemindText(days));
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(memberId);
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_BLOOD_SUGAR_UN_MONITOR);
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        addWechatMessageDTO.setForeignId(Constant.DEFAULT_FOREIGN_ID);
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    /**
     * 获取提醒文案
     * @param days
     * @return
     */
    private String getRemindText(int days){
        String text = "";
        if(days < INTERVAL_DAY){
            text = "您上周忘记测血糖了，本周有没有改善呢？测血糖好处多多，可以帮助您合理挑选食物，选择合适的运动以及观察服用药物后的血糖情况。在控制血糖的道路上，监测血糖尤为重要，告诉小助是什么原因不测了呢？";
        }else{
            text = "您已经许久未测血糖了，目前血糖是否有改善呢？测血糖的好处多多，可以帮助您合理挑选食物，选择合适的运动以及观察服用药物后的血糖情况。在控制血糖的道路上，监测血糖尤为重要，告诉小助是什么原因不测了呢？";
        }
        return text;
    }

    /**
     * 判断是否需要提醒
     * @param date
     * @return
     */
    private static int getDiffDay(String date){
        String nowDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return DateHelper.dateCompareGetDay(nowDay, date);
    }

    private final static String REMIND_MESSAGE = "血糖测量提醒";
    /**
     * 最大提醒次数
     */
    private final static int REMIND_TIME_MAX = 3;

}
