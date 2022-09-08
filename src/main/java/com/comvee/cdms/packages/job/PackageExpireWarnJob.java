package com.comvee.cdms.packages.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.other.constant.WechatMessageConstant;
import com.comvee.cdms.other.dto.AddWechatMessageDTO;
import com.comvee.cdms.other.service.WechatMessageService;
import com.comvee.cdms.packages.bo.ExpirePackageRemindDialogueData;
import com.comvee.cdms.packages.dto.CountMemberPackageDTO;
import com.comvee.cdms.packages.po.MemberPackagePO;
import com.comvee.cdms.packages.service.PackageService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author: suyz
 * @date: 2018/12/17
 * 套餐即将过期警告
 */
@Component
public class PackageExpireWarnJob {

    private final static Logger log = LoggerFactory.getLogger(PackageExpireWarnJob.class);

    @Autowired
    private PackageService packageService;

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private WechatMessageService wechatMessageService;

    private final static int PAGE_ROWS = 50;

    private final static int EXPIRE_DAY = 3;

    @XxlJob("packageExpireWarnJob")
    public ReturnT<String> execute(String param) throws Exception {
        int pageNum = 1;
        PageResult<MemberPackagePO> poPageResult = null;
        do {
            poPageResult = this.packageService.listMemberPackageByExpireDay(pageNum, PAGE_ROWS, EXPIRE_DAY);
            startHandler(poPageResult.getRows());
            pageNum ++;
        }while(poPageResult.getTotalPages() > pageNum - 1);
        return ReturnT.SUCCESS;
    }


    /**
     * 开始处理
     * @param list
     */
    private void startHandler(List<MemberPackagePO> list){
        if(list == null || list.size() == 0){
            return;
        }
        list.forEach(x -> {
            try{
                remindHandler(x);
            }catch (Exception e){
                log.error("套餐即将到期提醒处理失败，主键id:{}", x.getSid());
            }
        });
    }

    /**
     * 提醒处理
     * @param memberPackagePO
     */
    private void remindHandler(MemberPackagePO memberPackagePO){
        if(checkRenew(memberPackagePO)){
            return;
        }
        sendDialogue(memberPackagePO);
        sendWechatMessage(memberPackagePO);
    }


    /**
     * 判断续费
     * @param memberPackagePO
     * @return
     */
    private boolean checkRenew(MemberPackagePO memberPackagePO){
        CountMemberPackageDTO countMemberPackageDTO = new CountMemberPackageDTO();
        countMemberPackageDTO.setDoctorId(memberPackagePO.getDoctorId());
        countMemberPackageDTO.setPackageId(memberPackagePO.getPackageId());
        countMemberPackageDTO.setMemberId(memberPackagePO.getMemberId());
        countMemberPackageDTO.setStartDtMin(LocalDateTime.now().plusDays(3)
                .withHour(0).withMinute(0).withSecond(0)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        long count = this.packageService.countMemberPackage(countMemberPackageDTO);
        return count > 0;
    }

    /**
     * 新增对话
     * @param memberPackagePO
     */
    private void sendDialogue(MemberPackagePO memberPackagePO){
        DialoguePO dialoguePO = new DialoguePO();
        dialoguePO.setForeignId(memberPackagePO.getSid());
        dialoguePO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        dialoguePO.setShowType(DialogueConstant.DIALOGUE_SHOW_TYPE_ALL);
        dialoguePO.setShowClient(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        dialoguePO.setMsgType(DialogueConstant.DIALOGUE_PACKAGE_EXPIRE_WARN_MSG_TYPE);
        dialoguePO.setSenderId(memberPackagePO.getDoctorId());
        dialoguePO.setDoctorId(memberPackagePO.getDoctorId());
        dialoguePO.setMemberId(memberPackagePO.getMemberId());
        dialoguePO.setPatientMsg(MESSAGE_TITLE);
        dialoguePO.setDoctorMsg(MESSAGE_TITLE);
        //data数据
        ExpirePackageRemindDialogueData expirePackageRemindDialogueData = new ExpirePackageRemindDialogueData();
        expirePackageRemindDialogueData.setPackageId(memberPackagePO.getPackageId());
        expirePackageRemindDialogueData.setPackageName(memberPackagePO.getPackageName());
        expirePackageRemindDialogueData.setPrice(memberPackagePO.getPrice());
        expirePackageRemindDialogueData.setDate(DateHelper.getToday());
        expirePackageRemindDialogueData.setMemberPackageId(memberPackagePO.getSid());
        expirePackageRemindDialogueData.setEndDt(memberPackagePO.getEndDt());
        dialoguePO.setDataStr(JSON.toJSONString(expirePackageRemindDialogueData));

        this.dialogueService.addDialogue(dialoguePO);
    }

    /**
     * 发送微信模板消息
     * @param memberPackagePO
     */
    private void sendWechatMessage(MemberPackagePO memberPackagePO){
        AddWechatMessageDTO addWechatMessageDTO = new AddWechatMessageDTO();
        addWechatMessageDTO.setMemberId(memberPackagePO.getMemberId());
        addWechatMessageDTO.setForeignId(memberPackagePO.getSid());
        addWechatMessageDTO.setDataType(WechatMessageConstant.DATA_TYPE_PACKAGE_EXPIRE_WARN);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("endDt", memberPackagePO.getEndDt());
        jsonObject.put("packageName", memberPackagePO.getPackageName());
        jsonObject.put("doctorId", memberPackagePO.getDoctorId());
        jsonObject.put("memberId" , memberPackagePO.getMemberId());
        jsonObject.put("packageId" , memberPackagePO.getPackageId());
        jsonObject.put("expireDay", EXPIRE_DAY);
        jsonObject.put("memberPackageId", memberPackagePO.getSid());
        addWechatMessageDTO.setDataJson(jsonObject.toJSONString());
        this.wechatMessageService.addWechatMessage(addWechatMessageDTO);
    }

    private final static String MESSAGE_TITLE = "服务即将到期提醒";
}
