package com.comvee.cdms.dialogue.mapper;

import com.comvee.cdms.dialogue.dto.DoctorDialogueLatestSearchDTO;
import com.comvee.cdms.dialogue.dto.ListDialogueLatestMapperDTO;
import com.comvee.cdms.dialogue.dto.UnReadDialogueDTO;
import com.comvee.cdms.dialogue.dto.UpdateDialogueLastestMapperDTO;
import com.comvee.cdms.dialogue.po.DialogueLatestPO;
import com.comvee.cdms.dialogue.po.DialoguePO;
import com.comvee.cdms.dialogue.po.TeamDialoguePO;
import com.comvee.cdms.dialogue.po.UnReadDialoguePO;
import com.comvee.cdms.dialogue.vo.DialogueLatestDoctorVO;
import com.comvee.cdms.statistics.dto.SynthesizeDataDTO;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public interface DialogueMapper {

    /**
     * 新增对话最新消息记录
     * @param record
     */
    void addDialogueLatest(DialogueLatestPO record);

    /**
     * 添加对话记录
     * @param DialoguePO
     */
    void addDialogue(DialoguePO DialoguePO);

    /**
     * 统计最新消息记录数量
     * @param map
     * @return
     */
    long countDialogueLatest(Map<String, Object> map);

    /**
     * 修改最新消息记录
     * @param DialogueLatestPO
     */
    void updateDialogueLatest(UpdateDialogueLastestMapperDTO DialogueLatestPO);

    /**
     * 加载最新消息
     * @param listDialogueLatestMapperDTO
     * @return
     */
    List<DialogueLatestPO> listDialogueLatest(ListDialogueLatestMapperDTO listDialogueLatestMapperDTO);

    /**
     * 加载对话
     * @param map
     * @return
     */
    List<DialoguePO> listDialogue(Map<String, Object> map);

    /**
     * 更新对话
     * @param DialoguePO
     */
    void updateDialogue(DialoguePO DialoguePO);

    /**
     * 加载医生最新对话列表
     * @param doctorDialogueLatestSearchDTO
     * @return
     */
    List<DialogueLatestDoctorVO> listDoctorDialogueLatestWithSearch(DoctorDialogueLatestSearchDTO doctorDialogueLatestSearchDTO);

    /**
     * 统计未读数
     * @param unReadDialogueDTO
     * @return
     */
    UnReadDialoguePO countUnRead(UnReadDialogueDTO unReadDialogueDTO);

    /**
     * 加载对话
     * @param map
     * @return
     */
    List<TeamDialoguePO> listTeamDialogue(Map<String, Object> map);

    /**
     * 根据消息ID获取消息
     * @param sid
     * @return DialoguePO
     */
    DialoguePO getDialogue(String sid);

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


}