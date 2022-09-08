package com.comvee.cdms.consultation.mapper;

import com.comvee.cdms.consultation.model.param.ListRemoteConsultationMessageParam;
import com.comvee.cdms.consultation.model.po.RemoteConsultationMessagePO;
import com.comvee.cdms.consultation.model.vo.RemoteConsultationMessageVO;

import java.util.List;

public interface RemoteConsultationMessageMapper {

    void addMessage(RemoteConsultationMessagePO message);

    List<RemoteConsultationMessageVO> listMessage(ListRemoteConsultationMessageParam param);
}
