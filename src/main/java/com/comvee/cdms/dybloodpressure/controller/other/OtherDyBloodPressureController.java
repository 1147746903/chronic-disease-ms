package com.comvee.cdms.dybloodpressure.controller.other;

import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodpressure.dto.BatchAddDYBloodPressureDTO;
import com.comvee.cdms.dybloodpressure.service.DyBloodPressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @Author linr
 * @Date 2021/10/28
 */
@RestController
@RequestMapping("/other/dy/bloodPressure")
public class OtherDyBloodPressureController {

    @Autowired
    private DyBloodPressureService dyBloodPressureService;

    /**
     * 设备录入血压接口
     * @param batchAddDYBloodPressureDTO
     * @return
     */
    @RequestMapping("addDyBloodPressure")
    public Result addDyBloodPressure(@RequestBody @Valid BatchAddDYBloodPressureDTO batchAddDYBloodPressureDTO){
        this.dyBloodPressureService.addDyBloodPressure(batchAddDYBloodPressureDTO);
        return Result.ok();
    }
}
