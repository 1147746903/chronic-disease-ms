package com.comvee.cdms.dybloodpressure.service;

import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.dybloodpressure.dto.AddBpMemberMachineDTO;
import com.comvee.cdms.dybloodpressure.dto.GetMemberMachineDTO;

import java.util.Map;

/**
 * @Author linr
 * @Date 2021/11/3
 */
public interface DyBpMachineService {

    /**
     * 添加动态血压设备-患者绑定关系
     * @param addBpMemberMachineDTO
     */
    void addBpMemberMachineBind(AddBpMemberMachineDTO addBpMemberMachineDTO);


    /**
     * 解除动态血压设备-患者绑定关系
     * @param addBpMemberMachineDTO
     */
    void removeBpMemberMachineBind(String memberId, String machineNo);


    PageResult listMemberMachineBind(GetMemberMachineDTO getMemberMachineDTO, PageRequest pr);

    PageResult listAllMemberMachineBind(GetMemberMachineDTO getMemberMachineDTO, PageRequest pr);

    //获取设备信息
    Map<String,Object> loadMachineInfo(String machineNo);

    //到期自动解绑设备
    void autoUnbindMemberMachineHandler();
}
