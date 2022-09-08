package com.comvee.cdms.member.job;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.dto.AddDialogueServiceDTO;
import com.comvee.cdms.dialogue.po.DialogueGroupPO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.member.dto.MemberWarmDTO;
import com.comvee.cdms.member.po.MemberWarmPO;
import com.comvee.cdms.member.service.MemberService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class MemberDoctorWarmJob{

    private final static Logger log = LoggerFactory.getLogger(MemberDoctorWarmJob.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private DialogueService dialogueService;

    @XxlJob("memberDoctorWarmJob")
    public ReturnT<String> execute(String param) throws Exception {
        List<MemberWarmPO> list = this.memberService.listMemberWarmByUnSendStatus();
        this.startHandler(list);
        return ReturnT.SUCCESS;
    }

    /**
     * 开始处理
     * @param list
     */
    private void startHandler(List<MemberWarmPO> list){
        log.info("开始处理温馨关怀数据......");
        if(list == null || list.size() == 0){
            log.info("暂无数据处理.");
            return;
        }
        list.forEach(memberWarm -> {
            try{
                //群发消息
                //获取温馨关怀下发时间类型，根据不同的类型进行处理
                Integer pushDtType = memberWarm.getPushDtType();
                switch (pushDtType) {
                    // 立即下发
                    case 1:
                        //当前日期
                        String today = DateHelper.getDate(new Date(),DateHelper.DATETIME_FORMAT);
                        memberWarm.setPushDt(today);
                        //下发
                        pushMemberWarm(memberWarm);
                        break;
                    // 定时下发
                    case 2:
                        String pushDt = memberWarm.getPushDt();
                        if(!StringUtils.isBlank(pushDt) && pushDt.length()>=18){
                            long pushTimes = 0;
                            if(pushDt.length()<18){
                                pushTimes = DateHelper.getDate(pushDt,DateHelper.DAY_FORMAT).getTime();
                            } else {
                                pushTimes = DateHelper.getDate(pushDt,DateHelper.DATETIME_FORMAT).getTime();
                            }
                            long nowTimes = System.currentTimeMillis();
                            long i2 = nowTimes-pushTimes;
                            if (i2 >= 0) {
                                //下发
                                pushMemberWarm(memberWarm);
                            }
                        } else {
                            log.info("定时下发，未设置下发时间");
                        }
                        break;
                    default:
                        log.info("扫描温馨关怀表下发时间类型，不匹配");
                        break;
                }
            }catch (Exception e){
                //群发失败
                log.error("发生异常：" ,e);
            }
        });
        log.info("处理温馨关怀数据结束.");
    }

    /**
     * 下发温馨关怀
     * @author 李左河
     * @date 2018/4/19 17:54
     * @param memberWarm
     * @return void
     *
     */
    private void pushMemberWarm(MemberWarmPO memberWarm) {
        //1、封装下发消息
        List<String> memberList = Arrays.asList(memberWarm.getMemberIds().split(","));
        memberList.forEach( x -> {
            DialogueGroupPO dialogueGroupPO = new DialogueGroupPO();
            AddDialogueServiceDTO addDialogueServiceDTO = new AddDialogueServiceDTO();
            addDialogueServiceDTO.setMsg(memberWarm.getWarmContent());
            addDialogueServiceDTO.setTextType(1);
            addDialogueServiceDTO.setMemberId(x);
            addDialogueServiceDTO.setDoctorId(memberWarm.getDoctorId());
            addDialogueServiceDTO.setSenderId(memberWarm.getDoctorId());
            addDialogueServiceDTO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", "温馨关怀");
            jsonObject.put("date", memberWarm.getPushDt());
            jsonObject.put("content", memberWarm.getWarmContent());
            dialogueGroupPO.setDataJson(jsonObject.toJSONString());
            this.dialogueService.sendDialogue(addDialogueServiceDTO);
        });

        //3、修改温馨关怀信息(已下发)
        MemberWarmDTO dto = new MemberWarmDTO();
        dto.setSid(memberWarm.getSid());
        dto.setPushStatus(2);
        dto.setPushDt(memberWarm.getPushDt());
        this.memberService.updateMemberWarm(dto);
    }
}
