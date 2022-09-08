package com.comvee.cdms.dybloodpressure.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author linr
 * @Date 2021/10/28
 */
public class BatchAddDYBloodPressureDTO {
    /**
     * 设备编号
     */
    @NotBlank(message = "machineNo不允许为空")
    private String machineNo;
    /**
     * 设备型号
     */
    @NotBlank(message = "machineModel不允许为空")
    private String machineModel;
    /**
     * 动态血压数据List
     */
    @NotNull(message = "list不允许为空")
    @Valid
    List<AddDyBpBaseDataDTO> list;

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getMachineModel() {
        return machineModel;
    }

    public void setMachineModel(String machineModel) {
        this.machineModel = machineModel;
    }

    public List<AddDyBpBaseDataDTO> getList() {
        return list;
    }

    public void setList(List<AddDyBpBaseDataDTO> list) {
        this.list = list;
    }
}
