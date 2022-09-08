package com.comvee.cdms.consultation.mapper;

import com.comvee.cdms.consultation.model.param.CountRemoteConsultationParam;
import com.comvee.cdms.consultation.model.param.GetRemoteConsultationMapperParam;
import com.comvee.cdms.consultation.model.param.ListRemoteConsultationParam;
import com.comvee.cdms.consultation.model.param.UpdateRemoteConsultationWhereParam;
import com.comvee.cdms.consultation.model.po.RemoteConsultationPO;
import com.comvee.cdms.consultation.model.vo.RemoteConsultationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RemoteConsultationMapper {

    void addRemoteConsultation(RemoteConsultationPO add);

    RemoteConsultationPO getRemoteConsultationById(@Param("sid") String sid);

    RemoteConsultationPO getRemoteConsultation(GetRemoteConsultationMapperParam param);

    int updateRemoteConsultation(@Param("update") RemoteConsultationPO update ,@Param("where") UpdateRemoteConsultationWhereParam where);

    List<RemoteConsultationVO> listRemoteConsultationVO(ListRemoteConsultationParam param);

    long countRemoteConsultation(CountRemoteConsultationParam param);

    List<RemoteConsultationPO> listRemoteConsultation(ListRemoteConsultationParam param);
}
