package com.comvee.cdms.sign.controller.third;

import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddBmiDTO;
import com.comvee.cdms.sign.dto.AddBmiServiceDTO;
import com.comvee.cdms.sign.service.BmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: suyz
 * @date: 2018/10/30
 */
@RestController
@RequestMapping("/third/bmi")
public class ThirdBmiController {

    @Autowired
    private BmiService bmiService;

    /**
     * @api {post}/web/bmi/addBmi.do 新增bmi
     * @author 林雨堆
     * @time 2018/09/11
     * @apiName addBmi
     * @apiGroup web-bmi
     * @apiVersion 4.0.3
     * @apiParam {Float} height 身高
     * @apiParam {Float} weight 体重
     * @apiParam {String} bmi bmi
     * @apiParam {String} recordDt 记录时间
     * @apiParam {String} memberId 患者编号
     * @apiSampleRequest  http://192.168.7.31:9080/intelligent-prescription/web/bmi/addBmi.do
     *
     * @apiSuccess {String} data.obj 返回对象
     * @apiSuccess {Object} data.msg 状态信息
     * @apiSuccess {Object} data.success
     * @apiSuccess {Object} data.code 状态代码 0成功
     */
    @RequestMapping("addBmi")
    public Result addBmi(@Validated AddBmiDTO addBmiDTO){
        AddBmiServiceDTO addBmiServiceDTO = new AddBmiServiceDTO();
        BeanUtils.copyProperties(addBmiServiceDTO, addBmiDTO);
        addBmiServiceDTO.setOperatorId(Constant.DEFAULT_FOREIGN_ID);
        addBmiServiceDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
        addBmiServiceDTO.setOrigin(SignConstant.ORIGIN_FOLLOW);
        String id = this.bmiService.addBmi(addBmiServiceDTO);
        return new Result(id);
    }

}
