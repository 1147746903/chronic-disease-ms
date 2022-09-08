package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.dto.DyRememberSleepDTO;
import com.comvee.cdms.dybloodsugar.po.DyRememberSleepPO;

import java.util.List;

public interface DyRememberSleepMapper {

    /**
     * 获取当前患者当天的睡眠记录
     */
    DyRememberSleepPO getDySleepRememberPO(DyRememberSleepDTO dto);

    /**
     * 添加睡眠记录
     * @param dyRememberSleepPO
     */
    void addDySleepRemember(DyRememberSleepPO dyRememberSleepPO);

    /**
     * 修改睡眠记录
     */
    void updateDySleepRemember(DyRememberSleepDTO dto);
    /**
     * 获取医生记录患者的睡眠记录
     * @return
     */
    List<DyRememberSleepPO> getSleepRemember(DyRememberSleepDTO dyRememberSleepDTO);
}
