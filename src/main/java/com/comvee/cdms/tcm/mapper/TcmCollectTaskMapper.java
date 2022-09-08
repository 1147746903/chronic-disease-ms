package com.comvee.cdms.tcm.mapper;

import com.comvee.cdms.tcm.model.dto.TcmCollectTaskDTO;
import com.comvee.cdms.tcm.model.po.TcmCollectTaskPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TcmCollectTaskMapper {
    void createTcmCollectTask(TcmCollectTaskPO po);

    void deleteTcmCollectTask(String sid);

    TcmCollectTaskPO getTcmCollectTaskById(String sid);

    void updateTcmCollectTask(TcmCollectTaskPO po);

    List<TcmCollectTaskPO> listTcmCollectTask(TcmCollectTaskDTO dto);
}
