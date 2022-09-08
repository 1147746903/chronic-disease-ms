package com.comvee.cdms.dialogue.controller.wechat;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.dto.*;
import com.comvee.cdms.dialogue.po.UnReadDialoguePO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.dialogue.vo.AddDialogueReturnVO;
import com.comvee.cdms.dialogue.vo.DialogueLatestMemberVO;
import com.comvee.cdms.dialogue.vo.ListDialogueLatestVO;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/11/3
 */
@RestController
@RequestMapping("/wechat/dialogue")
@RequiresUser
public class WechatDialogueController {

    @Autowired
    private DialogueService dialogueService;

    /**
     * 加载患者最新对话列表
     * @param pr
     * @param memberDialogueLatestDTO
     * @return
     */
    @RequestMapping("listDialogueLatest")
    public Result listDialogueLatest(PageRequest pr, @Validated MemberDialogueLatestDTO memberDialogueLatestDTO){
        MemberPO memberPO = SessionTool.getWechatSession();
        memberDialogueLatestDTO.setMemberId(memberPO.getMemberId());
        memberDialogueLatestDTO.setBeDelete(DialogueConstant.DIALOGUE_BE_DELETE_NORMAL);
        ListDialogueLatestVO<DialogueLatestMemberVO> listDialogueLatestVO = this.dialogueService.listMemberDialogueLatest(pr, memberDialogueLatestDTO);
        return new Result(listDialogueLatestVO);
    }

    /**
     * 加载对话
     * @param pr
     * @param listMemberDialogueDTO
     * @return
     */
    @RequestMapping("listDialogue")
    public Result listDialogue(PageRequest pr, @Validated ListMemberDialogueDTO listMemberDialogueDTO){
        MemberPO memberPO = SessionTool.getWechatSession();
        ListDialogueServiceDTO listDialogueServiceDTO = new ListDialogueServiceDTO();
        BeanUtils.copyProperties(listDialogueServiceDTO, listMemberDialogueDTO);
        listDialogueServiceDTO.setMemberId(memberPO.getMemberId());
        List<Integer> showClientList = new ArrayList<>();
        showClientList.add(DialogueConstant.DIALOGUE_SHOW_CLIENT_PATIENT);
        showClientList.add(DialogueConstant.DIALOGUE_SHOW_CLIENT_ALL);
        listDialogueServiceDTO.setShowClientList(showClientList);
        Map<String,Object> map = this.dialogueService.listDialogue(pr, listDialogueServiceDTO);
        //清除小红点
        this.dialogueService.clearUnread(listDialogueServiceDTO.getMemberId(), listDialogueServiceDTO.getDoctorId(), DialogueConstant.DIALOGUE_VISIT_OWNER_MEMBER);
        return new Result(map);
    }


    /**
     * 添加咨询
     * @param addDialogueMemberDTO
     * @return
     */
    @RequestMapping("addDialogue")
    public Result addDialogue(@Validated AddDialogueMemberDTO addDialogueMemberDTO){
        MemberPO memberPO = SessionTool.getWechatSession();
        AddDialogueServiceDTO addDialogueServiceDTO = new AddDialogueServiceDTO();
        BeanUtils.copyProperties(addDialogueServiceDTO, addDialogueMemberDTO);
        addDialogueServiceDTO.setMemberId(memberPO.getMemberId());
        addDialogueServiceDTO.setSenderId(addDialogueMemberDTO.getDoctorId());
        addDialogueServiceDTO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_MEMBER);
        AddDialogueReturnVO addDialogueReturnVO = this.dialogueService.sendDialogue(addDialogueServiceDTO);
        return new Result(addDialogueReturnVO);
    }

    /**
     * 统计未读数
     * @return
     */
    @RequestMapping("countUnRead")
    public Result countUnRead(){
        MemberPO memberPO = SessionTool.getWechatSession();
        UnReadDialogueDTO unReadDialogueDTO = new UnReadDialogueDTO();
        unReadDialogueDTO.setMemberId(memberPO.getMemberId());
        UnReadDialoguePO unReadDialoguePO = this.dialogueService.countUnRead(unReadDialogueDTO);
        return new Result(unReadDialoguePO.getMemberUnRead());
    }
}
