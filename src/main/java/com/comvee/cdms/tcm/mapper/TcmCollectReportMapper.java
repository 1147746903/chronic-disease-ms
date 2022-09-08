package com.comvee.cdms.tcm.mapper;

import com.comvee.cdms.tcm.model.po.TcmCollectReportPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TcmCollectReportMapper {
    void addTcmCollectReport(TcmCollectReportPO po);

    TcmCollectReportPO getTcmCollectReport(String taskId);
}
