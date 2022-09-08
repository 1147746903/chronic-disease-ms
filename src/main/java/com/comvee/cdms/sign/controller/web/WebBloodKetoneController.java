package com.comvee.cdms.sign.controller.web;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.*;
import com.comvee.cdms.sign.po.BloodKetonePO;
import com.comvee.cdms.sign.service.BloodKetoneService;
import com.comvee.cdms.user.tool.SessionTool;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 罗伟雄
 * @date: 2021/1/20
 */
@RestController
@RequestMapping("/web/bloodKetone")
@RequiresUser
public class WebBloodKetoneController {

    @Autowired
    private BloodKetoneService bloodKetoneService;

    /**
     * web,手动添加血酮值
     * @return
     */
    @RequestMapping("/addBloodKetone")
    @ResponseBody
    public Result addBloodKetone(AddBloodKetoneServiceDTO dto){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(dto.getMemberId(), "memberId");
        ValidateTool.checkParamIsNull(dto.getParamValue(), "paramValue");
        ValidateTool.checkParamIsNull(dto.getRecordDt(), "recordDt");

        dto.setOperatorType(2);
        dto.setOperatorId(doctorSession.getDoctorId());
        dto.setOrigin(SignConstant.ORIGIN_WEB_PROGRAM);
        String sid = bloodKetoneService.addBloodKetone(dto);
        return Result.ok(sid);
    }

    /**
     * web查询血酮记录
     * @return
     */
    @RequestMapping("/listBloodKetone")
    @ResponseBody
    public Result listBloodKetone(ListBloodKetoneDTO listBloodKetoneDTO, PageRequest pr){
        DoctorSessionBO doctorSession = SessionTool.getWebSession();
        ValidateTool.checkParamIsNull(listBloodKetoneDTO.getMemberId(), "memberId");
        PageResult<BloodKetonePO> poPageResult =  bloodKetoneService.listBloodKetone(listBloodKetoneDTO, pr);
        return Result.ok(poPageResult);
    }
}
