package com.comvee.cdms.sign.controller.third;

import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.sign.dto.AddBloodSugarServiceDTO;
import com.comvee.cdms.sign.service.BloodSugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/third/bloodSugar")
public class ThirdBloodSugarController {

    @Autowired
    private BloodSugarService bloodSugarService;

    @RequestMapping("addBloodSugar")
    public Result addBloodSugar(@Validated AddBloodSugarServiceDTO addBloodSugarServiceDTO){
        String sid = this.bloodSugarService.addBloodSugar(addBloodSugarServiceDTO);
        return new Result(sid);
    }
}
