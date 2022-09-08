package com.comvee.cdms.dybloodsugar.contorller.other;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/other/dy/bloodSugar")
public class OtherDyBloodSugarController {

    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    /**
     * 上传动态血糖通知
     * @param sensorNo
     * @param machineNo
     * @param machineEq
     * @return
     */
    @RequestMapping("/uploadDynamicBloodSugarInform")
    public Result uploadDynamicBloodSugarInform(String sensorNo ,String machineNo ,String machineEq){
        ValidateTool.checkParameterIsNull("sensorNo" ,sensorNo);
        ValidateTool.checkParameterIsNull("machineNo" ,machineNo);
        this.dyBloodSugarService.uploadDynamicBloodSugarInform(sensorNo ,machineNo ,machineEq);
        return Result.ok();
    }
}
