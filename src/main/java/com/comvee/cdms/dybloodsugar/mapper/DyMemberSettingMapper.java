package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.po.DyMemberSettingPO;
import org.apache.ibatis.annotations.Param;

public interface DyMemberSettingMapper {
    /**
     * 插入自定义动态血糖参考数据
     * @param dyMemberSettingPO 插入的值
     */
    void insertSystemSetting(DyMemberSettingPO dyMemberSettingPO);

    /**
     * 修改绑定新增的探头数据
     * @param dyMemberSettingPO
     */
    void updateSystemSetting(DyMemberSettingPO dyMemberSettingPO);

    /**
     * 获取当前探头最新的数据
     * @param memberId
     * @return
     */
    DyMemberSettingPO getSystemSetting(@Param("memberId") String memberId);


    /**
     * 根据患者id查询这个探头的数据
     * @param memberId
     * @return
     */
    DyMemberSettingPO getDyMemberSettingPoByMemberId(@Param("memberId") String memberId);

    /**
     * 添加绑定新增的探头数据
     * @param dyMemberSettingPO
     */
    void insertNewData(DyMemberSettingPO dyMemberSettingPO);

}
