package com.comvee.cdms.user.controller.third;

import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.shiro.token.DoctorHisToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: suyz
 * @date: 2019/7/23
 */
@Controller
@RequestMapping("/third/")
public class ThirdUserController {

    /**
     * his医生登录
     * @param doctorId
     * @return
     */
    @RequestMapping("/his/doctorLogin")
    @ResponseBody
    public Result hisDoctorLogin(String doctorId){
        ValidateTool.checkParameterIsNull("doctorId" ,doctorId);
        DoctorHisToken doctorHisToken = new DoctorHisToken(doctorId);
        Subject subject = SecurityUtils.getSubject();
        subject.login(doctorHisToken);
        return Result.ok(subject.getPrincipal());
    }

}
