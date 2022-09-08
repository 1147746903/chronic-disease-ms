package com.comvee.cdms.machine.mapper;

import com.comvee.cdms.glu.model.GlucometerRequest;
import com.comvee.cdms.glu.model.MachineVersionModel;
import com.comvee.cdms.machine.po.MachinePO;

/**
 * 
 * @author 李左河
 *
 */
public interface MachineMapper {

    /**
     * getMachine
     * @author 李左河
     * @date 2018年3月22日 上午9:59:35
     * @param map
     * @return
     */
    MachinePO getMachine(MachinePO po);
    
    /**
     * 添加机器信息
     * @param machineModel  机器参数信息
     * @throws SmpException 操作数据库失败
     * @author 何健
     * @date 2017/2/16 11:07
     */
    void addMachineModel(MachinePO m);
    
    /**
     * 获取设备版本号
     * @author nzq
     * @return  MachineVersionModel
     */
    MachineVersionModel getMachineVersionModel(GlucometerRequest gr);

    /**
     * 修改设备信息
     * @param m
     * @return
     */
    int updateMachineModel(MachinePO m);
}
