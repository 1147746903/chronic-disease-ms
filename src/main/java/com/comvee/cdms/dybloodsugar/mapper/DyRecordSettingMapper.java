package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.po.DyRecordSettingPO;
import org.apache.ibatis.annotations.Param;

public interface DyRecordSettingMapper {

    /**
     * 存放探头失效最后一次对探头设置的值
     * @param dyRecordSettingPO
     */
    void insertSettingRecord(DyRecordSettingPO dyRecordSettingPO);



    /**
     * 查询设置指标表中该探头的数据
     * @param sensorNo
     * @return
     */
    DyRecordSettingPO getSettingValues(@Param("sensorNo") String sensorNo);
}
