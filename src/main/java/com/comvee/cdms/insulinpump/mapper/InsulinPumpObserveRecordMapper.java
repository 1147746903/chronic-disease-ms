package com.comvee.cdms.insulinpump.mapper;


import com.comvee.cdms.insulinpump.model.po.InsulinPumpObserveRecordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InsulinPumpObserveRecordMapper {

    /**
     * 新增观察记录
     * @param add
     */
    void addInsulinPumpObserveRecord(InsulinPumpObserveRecordPO add);

    /**
     * 修改观察记录
     * @param update
     * @return
     */
    int updateInsulinPumpObserveRecord(InsulinPumpObserveRecordPO update);

    /**
     * 根据主键获取观察记录
     * @param sid
     * @return
     */
    InsulinPumpObserveRecordPO getInsulinPumpObserveRecordById(@Param("sid") String sid);

    /**
     * 加载观察记录列表
     * @param memberId
     * @param hospitalNo
     * @return
     */
    List<InsulinPumpObserveRecordPO> listInsulinPumpObserveRecord(@Param("memberId")String memberId ,@Param("virtualWardId") String virtualWardId);
}