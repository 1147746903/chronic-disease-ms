package com.comvee.cdms.consultation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.consultation.constant.RemoteConsultationMessageConstant;
import com.comvee.cdms.consultation.mapper.RemoteConsultationMapper;
import com.comvee.cdms.consultation.mapper.RemoteConsultationMessageMapper;
import com.comvee.cdms.consultation.model.param.*;
import com.comvee.cdms.consultation.model.po.RemoteConsultationMessagePO;
import com.comvee.cdms.consultation.model.po.RemoteConsultationPO;
import com.comvee.cdms.consultation.model.vo.RemoteConsultationMessageVO;
import com.comvee.cdms.consultation.model.vo.SendRemoteConsultationMessageResultVO;
import com.comvee.cdms.consultation.service.RemoteConsultationMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("remoteConsultationMessageService")
public class RemoteConsultationMessageServiceImpl implements RemoteConsultationMessageService {


    @Autowired
    private RemoteConsultationMessageMapper remoteConsultationMessageMapper;

    @Autowired
    private RemoteConsultationMapper remoteConsultationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SendRemoteConsultationMessageResultVO sendMessage(SendRemoteConsultationMessageParam param) {
        RemoteConsultationMessagePO add = newMessageHandler(param);
        this.remoteConsultationMessageMapper.addMessage(add);
        newMessageUnReadNumberHandler(param.getConsultationId() ,param.getHospitalId());
        return getSendResult(add);
    }

    @Override
    public List<RemoteConsultationMessageVO> listOldRemoteConsultationMessage(ListOldRemoteConsultationMessageParam param) {
        ListRemoteConsultationMessageParam newParam = new ListRemoteConsultationMessageParam();
        newParam.setConsultationId(param.getConsultationId());
        newParam.setOldTimeStamp(param.getTimeStamp());
        newParam.setOrderSort(" desc");
        newParam.setSize(param.getSize());
        return this.remoteConsultationMessageMapper.listMessage(newParam);
    }

    @Override
    public List<RemoteConsultationMessageVO> listNewRemoteConsultationMessage(ListNewRemoteConsultationMessageParam param) {
        ListRemoteConsultationMessageParam newParam = new ListRemoteConsultationMessageParam();
        newParam.setConsultationId(param.getConsultationId());
        newParam.setNewTimeStamp(param.getTimeStamp());
        newParam.setOrderSort(" asc");
        newParam.setSize(param.getSize());
        return this.remoteConsultationMessageMapper.listMessage(newParam);
    }

    @Override
    public void readMessage(String consultationId, String departId) {
        RemoteConsultationPO remoteConsultation = this.remoteConsultationMapper.getRemoteConsultationById(consultationId);
        if(remoteConsultation == null){
            throw new BusinessException("?????????????????????????????????");
        }
        RemoteConsultationPO update = new RemoteConsultationPO();
        if(departId.equals(remoteConsultation.getFromDepartId())){
            update.setFromUnreadNumber(0);
        }else{
            update.setToUnreadNumber(0);
        }
        UpdateRemoteConsultationWhereParam where = new UpdateRemoteConsultationWhereParam();
        where.setSid(consultationId);
        this.remoteConsultationMapper.updateRemoteConsultation(update ,where);
    }

    /**
     * ???????????????
     * @param param
     * @return
     */
    private RemoteConsultationMessagePO newMessageHandler(SendRemoteConsultationMessageParam param){
        RemoteConsultationMessagePO result = new RemoteConsultationMessagePO();
        BeanUtils.copyProperties(result ,param);
        result.setSendTimestamp(System.currentTimeMillis());
        result.setSendDt(DateHelper.getNowDate());
        result.setSid(DaoHelper.getSeq());
        result.setContentData(contentDataHandler(param));
        return result;
    }

    /**
     * ??????????????????
     * @param param
     * @return
     */
    private String contentDataHandler(SendRemoteConsultationMessageParam param){
        JSONObject result = new JSONObject();
        if(RemoteConsultationMessageConstant.CONTENT_TYPE_TEXT == param.getContentType()){
            if(StringUtils.isBlank(param.getText())){
                throw new BusinessException("??????????????????????????????");
            }
            result.put("text" ,param.getText());
        }
        else{
            throw new BusinessException("????????????????????????");
        }
        return result.toJSONString();
    }

    /**
     * ????????????????????????
     * @param add
     * @return
     */
    private SendRemoteConsultationMessageResultVO getSendResult(RemoteConsultationMessagePO add){
        SendRemoteConsultationMessageResultVO result = new SendRemoteConsultationMessageResultVO();
        result.setSendDt(add.getSendDt());
        result.setId(add.getSid());
        result.setTimestamp(add.getSendTimestamp());
        return result;
    }

    /**
     * ????????? ????????????????????????
     * @param consultationId
     * @param hospitalId
     */
    private void newMessageUnReadNumberHandler(String consultationId ,String hospitalId){
        RemoteConsultationPO remoteConsultation = this.remoteConsultationMapper.getRemoteConsultationById(consultationId);
        if(remoteConsultation == null){
            throw new BusinessException("?????????????????????????????????");
        }
        Integer fromUnReadNumber = null;
        Integer toUnReadNumber = null;
        if(hospitalId.equals(remoteConsultation.getFromHospitalId())){
            toUnReadNumber = remoteConsultation.getToUnreadNumber() + 1;
        }else {
            fromUnReadNumber = remoteConsultation.getFromUnreadNumber() + 1;
        }
        RemoteConsultationPO update = new RemoteConsultationPO();
        update.setToUnreadNumber(toUnReadNumber);
        update.setFromUnreadNumber(fromUnReadNumber);

        UpdateRemoteConsultationWhereParam where = new UpdateRemoteConsultationWhereParam();
        where.setSid(consultationId);
        this.remoteConsultationMapper.updateRemoteConsultation(update ,where);
    }

}
