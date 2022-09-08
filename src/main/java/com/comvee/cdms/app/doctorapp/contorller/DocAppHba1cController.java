package com.comvee.cdms.app.doctorapp.contorller;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.checkresult.constant.CheckoutConstant;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.sign.constant.SignConstant;
import com.comvee.cdms.sign.dto.AddHba1cDTO;
import com.comvee.cdms.sign.service.Hba1cService;
import com.comvee.cdms.user.tool.SessionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/docapp/hba1c")
public class DocAppHba1cController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Hba1cService hba1cService;

    /**
     * 加载患者糖化记录列表
     * @param memberId
     * @param pr
     * @return
     * http://192.168.7.120:9080/intelligent-prescription/docapp/hba1c/listMemberHba1c?memberId=190618141700100078&page=1&rows=10
     */
    @RequestMapping("listMemberHba1c")
    @ResponseBody
    public Result listMemberHba1c(PageRequest pr, String memberId){
        ValidateTool.checkParameterIsNull("memberId" ,memberId);
        PageResult pageResult = this.hba1cService.listMemberHba1c(pr ,memberId);
        logger.info("[listDialogueLatest]加载消息列表:"+ JSON.toJSON(pageResult));
        return Result.ok(pageResult);
    }

    /**
     * 添加糖化记录
     * @param addHba1cDTO
     * @param memberId
     * @return
     */
    @RequestMapping("/addHba1c")
    @ResponseBody
    public Result addHba1c(@Validated AddHba1cDTO addHba1cDTO){
        DoctorSessionBO doctorSessionBO = SessionTool.getWebSession();
        addHba1cDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_DOCTOR);
        addHba1cDTO.setOperatorId(doctorSessionBO.getDoctorId());
        addHba1cDTO.setOperatorType(SignConstant.SIGN_OPERATOR_TYPE_MEMBER);
        if (null == addHba1cDTO.getOrigin()){
            addHba1cDTO.setOrigin(CheckoutConstant.RECORD_ORIGIN_PATIENT_WECHAT);
        }
        String sid = this.hba1cService.addHba1c(addHba1cDTO);
        return Result.ok(sid);
    }
}
