package com.comvee.cdms.consultation.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.consultation.model.param.FinishRemoteConsultationParam;
import com.comvee.cdms.consultation.model.param.ListCurrentRemoteConsultationParam;
import com.comvee.cdms.consultation.model.param.ListHistoryRemoteConsultationParam;
import com.comvee.cdms.consultation.model.param.ListRemoteConsultationParam;
import com.comvee.cdms.consultation.model.po.RemoteConsultationPO;
import com.comvee.cdms.consultation.model.vo.RemoteConsultationVO;

import java.util.List;

public interface RemoteConsultationService {

    String addRemoteConsultation(RemoteConsultationPO add);

    RemoteConsultationVO getRemoteConsultationFullInfo(String sid);

    PageResult<RemoteConsultationVO> listCurrentRemoteConsultation(ListCurrentRemoteConsultationParam param ,PageRequest pr);

    void finishRemoteConsultation(FinishRemoteConsultationParam param);

    PageResult<RemoteConsultationVO> listApplyRemoteConsultation(String departId ,PageRequest pr);

    PageResult<RemoteConsultationVO> listHistoryRemoteConsultation(ListHistoryRemoteConsultationParam param ,PageRequest pr);

    void confirmRemoteConsultation(String consultationId ,String doctorId);

    PageResult<RemoteConsultationVO> listRemoteConsultationVO(ListRemoteConsultationParam param , PageRequest pr);

    long getApplyRemindNumber(String departId);

    long getFinishRemindNumber(String departId);

    void delayApplyRemind(String departId);

    void readApplyRemind(String departId);

    void delayFinishRemind(String departId);

    void readFinishRemind(String departId);

    List<RemoteConsultationPO> listCurrentRemoteConsultationByMemberIdList(List<String> memberIdList);

    Boolean checkOperatorMember(String memberId ,String doctorId);
}
