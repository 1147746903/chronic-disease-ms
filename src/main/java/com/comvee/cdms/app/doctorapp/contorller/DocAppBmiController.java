package com.comvee.cdms.app.doctorapp.contorller;

import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBmiServiceDTO;
import com.comvee.cdms.sign.service.BmiService;
import com.comvee.cdms.user.tool.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docapp/bmi")
public class DocAppBmiController {

    @Autowired
    private BmiService bmiService;

    /**
     * 新增bmi
     * @param addBmiDTO
     * @return
     */
    @RequestMapping("addBmi")
    public Result addBmi(AddBmiServiceDTO addBmiServiceDTO){
        ValidateTool.checkParamIsNull(addBmiServiceDTO.getMemberId(), "memberId");
        ValidateTool.checkParamIsNull(addBmiServiceDTO.getWeight(), "weight");
        ValidateTool.checkParamIsNull(addBmiServiceDTO.getHeight(), "height");
        ValidateTool.checkParamIsNull(addBmiServiceDTO.getBmi(), "bmi");
        ValidateTool.checkParamIsNull(addBmiServiceDTO.getRecordDt(), "recordDt");
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addBmiServiceDTO.setOperatorId(doctorSessionBO.getDoctorId());
        addBmiServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
        if (null == addBmiServiceDTO.getOrigin()){
            addBmiServiceDTO.setOrigin(CheckoutConstant.RECORD_ORIGIN_DOCTOR_WEHCAT);
        }
        String id = this.bmiService.addBmi(addBmiServiceDTO);
        return new Result(id);
    }

}
