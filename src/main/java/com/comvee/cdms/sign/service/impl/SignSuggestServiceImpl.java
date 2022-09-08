package com.comvee.cdms.sign.service.impl;

import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.comvee.cdms.dialogue.dto.AddDialogueServiceDTO;
import com.comvee.cdms.dialogue.service.DialogueService;
import com.comvee.cdms.sign.dto.AddSignSuggestDTO;
import com.comvee.cdms.sign.dto.AddSignSuggestMapperDTO;
import com.comvee.cdms.sign.mapper.SignSuggestMapper;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.SignSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
@Service("signSuggestService")
public class SignSuggestServiceImpl implements SignSuggestService {

    @Autowired
    private SignSuggestMapper signSuggestMapper;

    @Autowired
    private DialogueService dialogueService;

    @Override
    public SignSuggestPO getSignSuggetBySignId(String signId) {
        return this.signSuggestMapper.getSignSuggetBySignId(signId);
    }

    @Override
    public String addSignSuggest(AddSignSuggestDTO addSignSuggestDTO) {
        SignSuggestPO signSuggestPO = getSignSuggetBySignId(addSignSuggestDTO.getSignId());
        if(signSuggestPO != null){
            throw new BusinessException("该条异常记录已被处理");
        }
        AddSignSuggestMapperDTO addSignSuggestMapperDTO = new AddSignSuggestMapperDTO();
        BeanUtils.copyProperties(addSignSuggestMapperDTO, addSignSuggestDTO);
        String sid = DaoHelper.getSeq();
        addSignSuggestMapperDTO.setSid(sid);
        this.signSuggestMapper.addSignSuggest(addSignSuggestMapperDTO);
        sendSuggestDialogue(addSignSuggestDTO);
        return sid;
    }

    /**
     * 下发建议消息
     * @param signSuggestDTO
     */
    private void sendSuggestDialogue(AddSignSuggestDTO signSuggestDTO){
        AddDialogueServiceDTO addDialogueServiceDTO = new AddDialogueServiceDTO();
        addDialogueServiceDTO.setSenderId(signSuggestDTO.getSenderId());
        addDialogueServiceDTO.setDoctorId(signSuggestDTO.getDoctorId());
        addDialogueServiceDTO.setMemberId(signSuggestDTO.getMemberId());
        addDialogueServiceDTO.setOwnerType(DialogueConstant.DIALOGUE_OWNER_TYPE_DOCTOR);
        addDialogueServiceDTO.setMsg(signSuggestDTO.getSuggestText());
        addDialogueServiceDTO.setTextType(DialogueConstant.DIALOGUE_TEXT_TYPE_TEXT);
        this.dialogueService.sendDialogue(addDialogueServiceDTO);
    }
}
