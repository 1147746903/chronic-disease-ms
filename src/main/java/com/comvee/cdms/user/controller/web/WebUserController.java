package com.comvee.cdms.user.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ImageVerifyCodeUtils;
import com.comvee.cdms.common.utils.RequestTool;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.shiro.cfg.AuthenticationType;
import com.comvee.cdms.shiro.token.UserNamePasswordToken;
import com.comvee.cdms.user.cfg.LoginLogConstant;
import com.comvee.cdms.user.dto.LoginDTO;
import com.comvee.cdms.user.dto.PasswordDTO;
import com.comvee.cdms.user.dto.PasswordWithUserNameDTO;
import com.comvee.cdms.user.service.UserService;
import com.comvee.cdms.user.tool.SessionTool;
import com.comvee.cdms.user.vo.CreateImageVerifyCodeVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author: suyz
 * @date: 2018/9/27
 */
@RestController
@RequestMapping("/web/user")
public class WebUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    /**
     * 登录
     * @param loginDTO
     * @return
     */
    @RequestMapping("login")
    public Result login(@Validated LoginDTO loginDTO){
        UserNamePasswordToken userLoginTypeToken = new UserNamePasswordToken(loginDTO.getUserName()
                , loginDTO.getPassword(), AuthenticationType.DOCTOR_WEB);
        userLoginTypeToken.setHost(RequestTool.getIpAddr());
        userLoginTypeToken.setUserType(LoginLogConstant.USER_TYPE_WEB);
        userLoginTypeToken.setVerifyCodeId(loginDTO.getVerifyCodeId());
        userLoginTypeToken.setVerifyCodeValue(loginDTO.getVerifyCodeValue());
        userLoginTypeToken.setSmsVerifyCode(loginDTO.getSmsVerifyCode());
        Subject subject = SecurityUtils.getSubject();
        subject.login(userLoginTypeToken);
        DoctorSessionBO doctorPO = (DoctorSessionBO) subject.getPrincipal();
        Set<String> authoritySet = this.authorityService.listUserAuthority(doctorPO.getDoctorId(), AuthorityConstant.TYPE_FRONT);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctor", subject.getPrincipal());
        jsonObject.put("authorityList", authoritySet);
        jsonObject.put("sessionId" ,subject.getSession().getId());
        return Result.ok(jsonObject);
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping("logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return new Result("");
    }

    /**
     * 修改密码
     * @param dto
     * @return
     */
    @RequestMapping("updatePassword")
    public Result updatePassword(@Validated PasswordDTO dto){
        DoctorSessionBO doctorBO = SessionTool.getWebSession();
        this.userService.updatePassword(dto, doctorBO.getUid());
        return new Result("");
    }

    /**
     * 获取验证码
     * @return
     */
    @RequestMapping("getVerifyCode")
    public Result getVerifyCode(){
        ImageVerifyCodeUtils.CreateImageVerifyCodeResult verifyCode = ImageVerifyCodeUtils.createImageVerifyCode();
        CreateImageVerifyCodeVO result = new CreateImageVerifyCodeVO();
        BeanUtils.copyProperties(result ,verifyCode);
        return Result.ok(result);
    }

    /**
     * 判断是否需要短信
     * @return
     */
    @RequestMapping("checkSmsVerify")
    public Result checkSmsVerify(String userName){
        ValidateTool.checkParameterIsNull("userName" ,userName);
        boolean result = this.userService.checkSmsVerify(userName);
        return Result.ok(result);
    }

    /**
     * 通过账号修改密码
     * @return
     */
    @RequestMapping("updatePasswordByUserName")
    public Result updatePasswordByUserName(@Validated PasswordWithUserNameDTO param){
        this.userService.updatePasswordByUserName(param);
        return Result.ok();
    }
}
