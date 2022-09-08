package com.comvee.cdms.admin.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.admin.model.dto.AddAdminDTO;
import com.comvee.cdms.admin.model.dto.AdminLoginDTO;
import com.comvee.cdms.admin.model.dto.ListAdminDTO;
import com.comvee.cdms.admin.model.dto.UpdateAdminDTO;
import com.comvee.cdms.admin.model.po.AdminPO;
import com.comvee.cdms.admin.model.vo.AdminVO;
import com.comvee.cdms.admin.service.AdminService;
import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.ImageVerifyCodeUtils;
import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.shiro.cfg.AuthenticationType;
import com.comvee.cdms.shiro.token.UserNamePasswordToken;
import com.comvee.cdms.user.tool.SessionTool;
import com.comvee.cdms.user.vo.CreateImageVerifyCodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: suyz
 * @date: 2019/1/15
 */
@RestController
@RequestMapping("/back/admin")
@Api("管理员接口")
public class BackAdminController {

    private final static ReentrantLock ADMIN_LOCK = new ReentrantLock();

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthorityService authorityService;

    /**
     * 登录
     * @param accountNo
     * @param password
     * @return
     */
    @PostMapping("login")
    @ApiOperation("登录")
    public Result login(@Validated AdminLoginDTO loginDTO){
        UserNamePasswordToken userNamePasswordToken = new UserNamePasswordToken(loginDTO.getAccountNo(), loginDTO.getPassword(), AuthenticationType.ADMIN);
        userNamePasswordToken.setVerifyCodeId(loginDTO.getVerifyCodeId());
        userNamePasswordToken.setVerifyCodeValue(loginDTO.getVerifyCodeValue());
        userNamePasswordToken.setSmsVerifyCode(loginDTO.getSmsVerifyCode());
        Subject subject = SecurityUtils.getSubject();
        userNamePasswordToken.setHost(subject.getSession().getHost());
        subject.login(userNamePasswordToken);
        AdminPO adminPO = (AdminPO) subject.getPrincipal();
        Set<String> authoritySet = this.authorityService.listUserAuthority(adminPO.getAdminId(), AuthorityConstant.TYPE_BACK);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("admin", subject.getPrincipal());
        jsonObject.put("authorityList", authoritySet);
        return Result.ok(jsonObject);
    }


    /**
     * 退出登录
     * @return
     */
    @RequestMapping("logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("updatePassword")
    @RequiresUser
    public Result updatePassword(String oldPassword, String newPassword){
        ValidateTool.checkParamIsNull(oldPassword, "oldPassword");
        ValidateTool.checkParamIsNull(newPassword, "newPassword");
        AdminPO adminPO = SessionTool.getAdminSession();
        this.adminService.updatePassword(adminPO.getAdminId(), oldPassword, newPassword);
        return Result.ok();
    }

    /**
     * 新增管理员
     * @param addAdminDTO
     * @return
     */
    @RequestMapping("addAdmin")
    @RequiresUser
    public Result addAdmin(@Validated AddAdminDTO addAdminDTO){
        ADMIN_LOCK.lock();
        String adminId = "";
        try{
            adminId = this.adminService.addAdmin(addAdminDTO);
        } finally {
            ADMIN_LOCK.unlock();
        }
        return Result.ok(adminId);
    }

    /**
     * 修改管理员信息
     * @param updateAdminDTO
     * @return
     */
    @RequestMapping("updateAdmin")
    @RequiresUser
    public Result updateAdmin(@Validated UpdateAdminDTO updateAdminDTO){
        this.adminService.updateAdmin(updateAdminDTO);
        return Result.ok();
    }

    /**
     * 加载管理员列表
     * @param listAdminDTO
     * @param pr
     * @return
     */
    @RequestMapping("listAdmin")
    @RequiresUser
    public Result listAdmin(ListAdminDTO listAdminDTO, PageRequest pr){
        PageResult<AdminVO> result = this.adminService.listAdmin(listAdminDTO, pr);
        return Result.ok(result);
    }

    /**
     * 根据id获取管理员信息
     * @param adminId
     * @return
     */
    @RequestMapping("getAdminById")
    @RequiresUser
    public Result getAdminById(String adminId){
        ValidateTool.checkParamIsNull(adminId, "adminId");
        AdminPO adminPO = this.adminService.getAdminById(adminId);
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(adminVO , adminPO);
        return Result.ok(adminVO);
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
        boolean result = this.adminService.checkSmsVerify(userName);
        return Result.ok(result);
    }

    @RequestMapping("updatePasswordWithAccount")
    public Result updatePasswordWithAccount(String oldPassword, String newPassword ,String accountNo){
        ValidateTool.checkParamIsNull(oldPassword, "oldPassword");
        ValidateTool.checkParamIsNull(newPassword, "newPassword");
        ValidateTool.checkParamIsNull(accountNo, "accountNo");
        this.adminService.updatePasswordWithAccount(accountNo, oldPassword, newPassword);
        return Result.ok();
    }
}
