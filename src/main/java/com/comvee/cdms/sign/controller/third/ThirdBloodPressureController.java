package com.comvee.cdms.sign.controller.third;

import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.po.BloodPressurePO;
import com.comvee.cdms.sign.po.SignSuggestPO;
import com.comvee.cdms.sign.service.BloodPressureService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
@RestController
@RequestMapping("/third/bloodPressure")
public class ThirdBloodPressureController {


    @Autowired
    private BloodPressureService bloodPressureService;

    /**
     * 添加血压
     * @param addBloodPressureDTO
     * @return
     */
    @RequestMapping("addBloodPressure")
    public Result addBloodPressure(@Validated AddBloodPressureDTO addBloodPressureDTO){
        AddBloodPressureServiceDTO addBloodPressureServiceDTO = new AddBloodPressureServiceDTO();
        BeanUtils.copyProperties(addBloodPressureServiceDTO, addBloodPressureDTO);
        addBloodPressureServiceDTO.setOperatorId(Constant.DEFAULT_FOREIGN_ID);
        addBloodPressureServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
        addBloodPressureServiceDTO.setOrigin(SignConstant.ORIGIN_FOLLOW);
        String sid = this.bloodPressureService.addBloodPressure(addBloodPressureServiceDTO);
        return new Result(sid);
    }

}
