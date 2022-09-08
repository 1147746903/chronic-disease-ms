package com.comvee.cdms.consultation.service;

import com.comvee.cdms.consultation.model.param.ListNewRemoteConsultationMessageParam;
import com.comvee.cdms.consultation.model.param.ListOldRemoteConsultationMessageParam;
import com.comvee.cdms.consultation.model.param.SendRemoteConsultationMessageParam;
import com.comvee.cdms.consultation.model.vo.RemoteConsultationMessageVO;
import com.comvee.cdms.consultation.model.vo.SendRemoteConsultationMessageResultVO;

import java.util.List;

public interface RemoteConsultationMessageService {

    SendRemoteConsultationMessageResultVO sendMessage(SendRemoteConsultationMessageParam param);

    List<RemoteConsultationMessageVO> listOldRemoteConsultationMessage(ListOldRemoteConsultationMessageParam param);

    List<RemoteConsultationMessageVO> listNewRemoteConsultationMessage(ListNewRemoteConsultationMessageParam param);

    void readMessage(String consultationId ,String departId);
}
