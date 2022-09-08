package com.comvee.cdms.dybloodsugar.service;

import com.comvee.cdms.dybloodsugar.po.DyMemberSettingPO;
import com.comvee.cdms.dybloodsugar.po.DyRecordSettingPO;

public interface DyMemberSettingService {
    /**
     * 插入自定义动态血糖参考数据
     * @param dyMemberSettingPo 插入的值
     */
    void insertSystemSetting(DyMemberSettingPO dyMemberSettingPo);

    /**
     * 获取当前患者最新的数据
     * @param memberId
     * @return
     */
    DyMemberSettingPO getSystemSetting(String memberId);


    /**
     * 恢复默认设置
     */
    DyMemberSettingPO restoreDefault(String id);

    /**
     * 存放探头失效最后一次对探头设置的值
     * @param dyMemberSettingPo 取动态血糖的数据
     */
    void insertSettingRecord(DyMemberSettingPO dyMemberSettingPo, String sensorNo);


    /**
     * 查询记录指标表中该探头的数据
     * @param sensorNo
     * @return
     */
    DyRecordSettingPO getSettingValues(String sensorNo);


    /**
     * 设置默认值
     */
    DyMemberSettingPO showSystemSetting(String memberId);

}
