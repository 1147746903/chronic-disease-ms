package com.comvee.cdms.dialogue.mapper;

import com.comvee.cdms.dialogue.dto.ListDialogueGroupDTO;
import com.comvee.cdms.dialogue.dto.UpdateDialogueGroupDTO;
import com.comvee.cdms.dialogue.po.DialogueGroupPO;

import java.util.List;

/**
 * @author: suyz
 * @date: 2019/1/2
 */
public interface DialogueGroupMapper {

    /**
     * 新增群发消息
     * @param dialogueGroupPO
     */
    void addDialogueGroup(DialogueGroupPO dialogueGroupPO);

    /**
     * 加载群发消息列表
     * @return
     */
    List<DialogueGroupPO> listDialogueGroup(ListDialogueGroupDTO listDialogueGroupDTO);

    /**
     * 修改群发数据
     * @param updateDialogueGroupDTO
     */
    void updateDialogueGroup(UpdateDialogueGroupDTO updateDialogueGroupDTO);
}
