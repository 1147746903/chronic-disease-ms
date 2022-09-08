package com.comvee.cdms.tcm.mapper;

import com.comvee.cdms.tcm.model.po.TcmCollectDataPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TcmCollectDataMapper {
    void addTcmCollectData(TcmCollectDataPO po);
    void updateTcmCollectData(TcmCollectDataPO po);
    TcmCollectDataPO getTcmCollectDataByTaskId(String taskId);
    TcmCollectDataPO getLastTcmCollectData(String memberId);
}
