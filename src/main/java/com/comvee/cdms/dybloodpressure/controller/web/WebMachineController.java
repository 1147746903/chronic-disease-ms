package com.comvee.cdms.dybloodpressure.controller.web;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.dybloodpressure.constant.DyBloodPressureConstant;
import com.comvee.cdms.dybloodpressure.dto.AddBpMemberMachineDTO;
import com.comvee.cdms.dybloodpressure.dto.GetMemberMachineDTO;
import com.comvee.cdms.dybloodpressure.service.DyBpMachineService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Author linr
 * @Date 2021/11/3
 */
@RestController
@RequestMapping("/web/dy/machine")
public class WebMachineController {

    @Autowired
    private DyBpMachineService dyBpMachineService;

    /**
     * 设备绑定
     * @param addBpMemberMachineDTO
     * @return
     */
    @RequestMapping("bind")
    public Result bindMachine(@Valid AddBpMemberMachineDTO addBpMemberMachineDTO){
        DoctorSessionBO webSession = SessionTool.getWebSession();
        addBpMemberMachineDTO.setOperationId(webSession.getDoctorId());
        addBpMemberMachineDTO.setOperationType(DyBloodPressureConstant.OPERATION_TYPE_DOCTOR);
        addBpMemberMachineDTO.setHospitalId(webSession.getHospitalId());
        this.dyBpMachineService.addBpMemberMachineBind(addBpMemberMachineDTO);
        return Result.ok();
    }

    /**
     * 解除设备绑定
     * @param memberId
     * @param machineNo
     * @return
     */
    @RequestMapping("unBind")
    public Result unBindMachine(String memberId,String machineNo){
        ValidateTool.checkParamIsNull(memberId,"memberId");
        ValidateTool.checkParamIsNull(machineNo,"machineNo");
        this.dyBpMachineService.removeBpMemberMachineBind(memberId,machineNo);
        return Result.ok();
    }

    /**
     * 分页加载设备绑定记录（个人）
     * @param getMemberMachineDTO
     * @param pr
     * @return
     */
    @RequestMapping("pageMachineBindList")
    public Result bindMachineList(@Valid GetMemberMachineDTO getMemberMachineDTO, PageRequest pr){
        PageResult pageResult = this.dyBpMachineService.listMemberMachineBind(getMemberMachineDTO, pr);
        return Result.ok(pageResult);
    }

    /**
     * 分页加载设备绑定记录（全部）
     * @param getMemberMachineDTO
     * @param pr
     * @return
     */
    @RequestMapping("pageAllMachineBindList")
    public Result MachineList(GetMemberMachineDTO getMemberMachineDTO, PageRequest pr){
        PageResult pageResult = this.dyBpMachineService.listAllMemberMachineBind(getMemberMachineDTO, pr);
        return Result.ok(pageResult);
    }

    /**
     * 获取设备信息
     * @param machineNo
     * @return
     */
    @RequestMapping("loadMachineInfo")
    public Result loadMachineInfo(String machineNo){
        ValidateTool.checkParamIsNull(machineNo,"machineNo");
        Map<String, Object> map = this.dyBpMachineService.loadMachineInfo(machineNo);
        return Result.ok(map);
    }



}
