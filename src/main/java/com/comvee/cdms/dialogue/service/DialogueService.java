package com.comvee.cdms.dialogue.service;


import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dialogue.dto.*;
import com.comvee.cdms.dialogue.po.DialogueGroupPO;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.po.ReplyTextPO;
import com.comvee.cdms.dialogue.po.UnReadDialoguePO;
import com.comvee.cdms.dialogue.vo.*;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;

import java.util.List;
import java.util.Map;

/**
 * @author ZhiGe
 * @description
 * @date 2018/1/30 13:41 create
 */
public interface DialogueService {

    /**
     * 发送消息
     * @param addDialogueServiceDTO
     * @return
     */
    AddDialogueReturnVO sendDialogue(AddDialogueServiceDTO addDialogueServiceDTO);

    /**
     * 加载最新消息列表
     * @param doctorModel
     * @param pageRequestModel
     * @param refreshTimeStamp
     * @param beDelete
     * @return
     */
    Map<String,Object> listDialogueLatest(DoctorSessionBO doctorModel, PageRequest pageRequestModel
            , String refreshTimeStamp, Integer beDelete, String param);

    /**
     * 加载医生最新对话列表
     * @param doctorDialogueLatestSearchDTO
     * @return
     */
    ListDialogueLatestVO<DialogueLatestDoctorVO> listDoctorDialogueLatestWithSearch(DoctorDialogueLatestSearchDTO doctorDialogueLatestSearchDTO, PageRequest pr);

    /**
     * 加载对话
     * @author 李左河
     * @date 2018年3月22日 上午9:44:52
     * @param pageRequestModel
     * @param listDialogueServiceDTO
     * @return
     */
    Map<String,Object> listDialogue(PageRequest pageRequestModel, ListDialogueServiceDTO listDialogueServiceDTO);

    /**
     * 添加对话数据
     * @param dialogueModel
     * @return
     */
    AddDialogueReturnVO addDialogue(DialoguePO dialogueModel);

    /**
     * 清除未读数
     * @param memberId
     * @param doctorId
     */
    void clearUnread(String memberId, String doctorId, Integer visitOwner);

    /**
     * 删除医患对话
     * @param doctorId
     * @param memberId
     */
    void deleteDialogueOfDoctorMember(String doctorId, String memberId);

    /**
     * 加载患者最新对话列表
     * @param pr
     * @return
     */
    ListDialogueLatestVO<DialogueLatestMemberVO> listMemberDialogueLatest(PageRequest pr, MemberDialogueLatestDTO memberDialogueLatestDTO);

    /**
     * 统计未读数
     * @param unReadDialogueDTO
     * @return
     */
    UnReadDialoguePO countUnRead(UnReadDialogueDTO unReadDialogueDTO);

    /**
     * 添加消息群发
     * @param addDialogueGroupDTO
     * @param senderId
     */
    void addDialogueGroup(AddDialogueGroupDTO addDialogueGroupDTO , String senderId);

    /**
     * 加载群发消息列表
     * @param pr
     * @param listDialogueGroupDTO
     * @return
     */
    PageResult<DialogueGroupPO> listDialogueGroup(PageRequest pr, ListDialogueGroupDTO listDialogueGroupDTO);

    /**
     * 修改群发数据
     * @param updateDialogueGroupDTO
     */
    void updateDialogueGroup(UpdateDialogueGroupDTO updateDialogueGroupDTO);

    /**
     * 医生撤回消息
     * @param sid
     * @param doctorId
     * @param memberId
     */
    AddDialogueReturnVO recallDocDialogue(String sid, String memberId, String doctorId);

    /**
     * 统计在线咨询人数
     * @param synthesizeDataDTO
     * @return
     */
    long countAllOnlineMember(SynthesizeDataDTO synthesizeDataDTO);

    /**
     * 患者,医生发送消息的条数
     * @param synthesizeDataDTO
     * @return
     */
    Map<String,Object> countExchange(SynthesizeDataDTO synthesizeDataDTO);



    //
    /**
     * 加载医生回复文案列表
     * @param doctorId
     * @param hospitalId
     * @param departmentId
     * @return
     */
    List<ReplyTextPO> listReplyText( ListReplyTextDTO listReplyTextDTO);
    /**
     * 添加医生自定义回复文案
     * @param replyTextPO
     */
    String addReplyText(ReplyTextDTO dto);

    /**
     * 修改医生自定义回复文案
     * @param replyTextPO
     */
    void modifyReplyText(ReplyTextDTO dto);

    /**
     * 删除医生自定义回复文案
     * @param replyTextPO
     */
    void delReplayText(String sid);
    /**
     * 根据医生自定义回复文案id获取文案信息
     * @param sid
     * @return
     */
    ReplyTextPO getReplyTextById( String sid);


}
