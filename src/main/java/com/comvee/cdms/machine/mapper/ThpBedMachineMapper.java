package com.comvee.cdms.machine.mapper;

import com.comvee.cdms.machine.model.BedMachineModel;
import com.comvee.cdms.machine.po.BedMachinePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ThpBedMachineMapper {
    /**
     * 根据machineId查询是否被床号绑定
     * @param machineId
     * @return
     */
    Long countMachineToBedByMachineId(@Param("machineId") String machineId);


    /**
     *床号: 绑定设备
     * @param model
     */
    void bedBindEquipment(BedMachineModel model);

    /**
     * 判断设备是否被绑定
     * @param model
     * @return
     */
    List<BedMachineModel> selectByBedMachineModel(BedMachineModel model);
    /**
     * 床号：解除绑定设备
     * @param bedId 住院表主键id
     */
    void unBedBindEquipment(@Param("bedId") String bedId);

    BedMachinePO selectByBedId(@Param("bedId") String bedId);

    BedMachinePO queryBedByMachineCode(@Param("machineId") String machineId);
}