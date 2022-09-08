package com.comvee.cdms.dybloodsugar.mapper;

import com.comvee.cdms.dybloodsugar.po.DyApplicationMachinePO;
import com.comvee.cdms.dybloodsugar.po.DyApplicationPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DyApplicationMapper {

    /**
     * 添加应用
     * @param dyApplicationPO
     */
    void addDyApplication(DyApplicationPO dyApplicationPO);

    /**
     * 绑定中间件
     * @param dyApplicationMachinePO
     */
    void addApplicationMachine(DyApplicationMachinePO dyApplicationMachinePO);

    /**
     * 根据imei修改
     * @param dyApplicationMachinePO
     */
    void updateDyApplicationMachinePO(DyApplicationMachinePO dyApplicationMachinePO);

    /**
     * 物理删除绑定中间件(dy_application_machine)
     * @param machineNo
     */
    void deleteApplicationMachineBySid(@Param("machineNo") String machineNo);

    /**
     * 逻辑删除绑定中间件(dy_application)
     * @param appId
     */
    void deleteApplicationBySid(@Param("appId") String appId);

    /**
     * 获取设备
     * @param machineNo
     * @return
     */
    Long countMachineByMachineNo(@Param("machineNo") String machineNo);

    List<DyApplicationPO> getDyApplicationPOByAppId(@Param("appId") String appId);

    /**
     * 修改应用表
     * @param dyApplicationPO
     */
    void updateDyApplicationPO(DyApplicationPO dyApplicationPO);
}
