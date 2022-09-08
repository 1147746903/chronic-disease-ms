package com.comvee.cdms.dialogue.job;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.constant.DialogueGroupConstant;
import com.comvee.cdms.dialogue.dto.AddDialogueServiceDTO;
import com.comvee.cdms.dialogue.dto.ListDialogueGroupDTO;
import com.comvee.cdms.dialogue.dto.UpdateDialogueGroupDTO;
import com.comvee.cdms.dialogue.po.DialogueGroupPO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/2
 */
@Component
public class DialogueGroupSendJob {

    private final static Logger log = LoggerFactory.getLogger(DialogueGroupSendJob.class);

    @Autowired
    private DialogueService dialogueService;

    private final static int PAGE_SIZE = 50;

    @XxlJob("dialogueGroupSend")
    public ReturnT<String> execute(String param) throws Exception {
        int page = 1;
        PageResult<DialogueGroupPO> poPageResult = null;
        PageRequest pr = null;
        do{
            pr = new PageRequest();
            pr.setPage(page);
            pr.setRows(PAGE_SIZE);
            ListDialogueGroupDTO listDialogueGroupDTO = new ListDialogueGroupDTO();
            listDialogueGroupDTO.setSendStatus(DialogueGroupConstant.SEND_STATUS_NO);
            poPageResult = this.dialogueService.listDialogueGroup(pr, listDialogueGroupDTO);
            startHandler(poPageResult.getRows());
            page ++;
        } while (poPageResult.getTotalPages() > page - 1);
        return ReturnT.SUCCESS;
    }

    /**
     * 开始处理
     * @param list
     */
    private void startHandler(List<DialogueGroupPO> list){
        list.forEach(x -> {
            int sendStatus = 0;
            try{
                groupSend(x);
                sendStatus = DialogueGroupConstant.SEND_STATUS_SUCCESS;
            }catch (Exception e){
                log.error("群发消息失败，消息id:{}", x.getSid(),  e);
                sendStatus = DialogueGroupConstant.SEND_STATUS_FAIL;
            }
            UpdateDialogueGroupDTO updateDialogueGroupDTO = new UpdateDialogueGroupDTO();
            updateDialogueGroupDTO.setSid(x.getSid());
            updateDialogueGroupDTO.setSendStatus(sendStatus);
            this.dialogueService.updateDialogueGroup(updateDialogueGroupDTO);
        });
    }

    /**
     * 群发
     * @param dialogueGroupPO
     */
    private void groupSend(DialogueGroupPO dialogueGroupPO){
        List<String> memberList = Arrays.asList(dialogueGroupPO.getMemberIdData().split(","));
        memberList.forEach( x -> {
            AddDialogueServiceDTO addDialogueServiceDTO = new AddDialogueServiceDTO();
            addDialogueServiceDTO.setTextType(dialogueGroupPO.getMsgType());
            addDialogueServiceDTO.setMemberId(x);
            addDialogueServiceDTO.setDoctorId(dialogueGroupPO.getDoctorId());
            addDialogueServiceDTO.setSenderId(dialogueGroupPO.getSenderId());
            addDialogueServiceDTO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
            dataJsonHandler(dialogueGroupPO, addDialogueServiceDTO);
            this.dialogueService.sendDialogue(addDialogueServiceDTO);
        });

    }

    /**
     * 消息数据处理
     * @param dialogueGroupPO
     * @param addDialogueServiceDTO
     */
    private void dataJsonHandler(DialogueGroupPO dialogueGroupPO, AddDialogueServiceDTO addDialogueServiceDTO){
        JSONObject jsonObject = JSONObject.parseObject(dialogueGroupPO.getDataJson());
        if(DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT == dialogueGroupPO.getMsgType()){
            addDialogueServiceDTO.setMsg(dialogueGroupPO.getMsgContent());
        }else if(DialogueConstant.DIALOGUE_TEXT_TYPE_IMAGE == dialogueGroupPO.getMsgType()){
            addDialogueServiceDTO.setAttachUrl(jsonObject.getString("attachUrl"));
            addDialogueServiceDTO.setImgH(jsonObject.getString("imgH"));
            addDialogueServiceDTO.setImgW(jsonObject.getString("imgW"));
        }else if(DialogueConstant.DIALOGUE_TEXT_TYPE_VOICE == dialogueGroupPO.getMsgType()){
            addDialogueServiceDTO.setAttachUrl(jsonObject.getString("attachUrl"));
            addDialogueServiceDTO.setVoiceLength(jsonObject.getLong("voiceLength"));
        }
    }

}
