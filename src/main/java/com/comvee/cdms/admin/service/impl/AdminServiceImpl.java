package com.comvee.cdms.admin.service.impl;

import com.comvee.cdms.admin.constant.AdminConstant;
import com.comvee.cdms.admin.mapper.AdminMapper;
import com.comvee.cdms.admin.model.dto.AddAdminDTO;
import com.comvee.cdms.admin.model.dto.AdminLoginDTO;
import com.comvee.cdms.admin.model.dto.ListAdminDTO;
import com.comvee.cdms.admin.model.dto.UpdateAdminDTO;
import com.comvee.cdms.admin.model.po.AdminPO;
import com.comvee.cdms.admin.model.vo.AdminVO;
import com.comvee.cdms.admin.service.AdminService;
import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.service.RoleService;
import com.comvee.cdms.common.cfg.SmsVerifyCodeBusinessCode;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.*;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.shiro.exception.MyAuthenticationException;
import com.comvee.cdms.user.cfg.LoginLogConstant;
import com.comvee.cdms.user.cfg.UserConstant;
import com.comvee.cdms.user.mapper.UserDoubleFactorMapper;
import com.comvee.cdms.user.service.LoginLogService;
import com.comvee.cdms.user.tool.PasswordTool;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: suyz
 * @date: 2019/1/15
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private UserDoubleFactorMapper userDoubleFactorMapper;

    @Override
    public PageResult<AdminVO> listAdmin(ListAdminDTO listAdminDTO, PageRequest pr) {
        PageHelper.startPage(pr.getPage(), pr.getRows());
        List<AdminPO> list = this.adminMapper.listAdmin(listAdminDTO);
        PageResult<AdminPO> result = new PageResult<>(list);
        PageResult<AdminVO> pageResult = result.createEmptyPageResult();
        List<AdminVO> adminList = new ArrayList<>();
        result.getRows().forEach( x ->{
            AdminVO adminVO = new AdminVO();
            BeanUtils.copyProperties(adminVO, x);
            adminList.add(adminVO);
        });
        pageResult.setRows(adminList);
        return pageResult;
    }

    @Override
    public String addAdmin(AddAdminDTO addAdminDTO) {
        AdminPO adminResult = this.adminMapper.getAdmin(null, addAdminDTO.getAccountNo());
        if(adminResult != null){
            throw new BusinessException("账号已存在，请重新输入");
        }
        String pwdSalt = UUID.randomUUID().toString();
        String password = PasswordTool.passwordSaltHandler(addAdminDTO.getPassword(), pwdSalt);
        String adminId = DaoHelper.getSeq();
        AdminPO adminPO = new AdminPO();
        BeanUtils.copyProperties(adminPO, addAdminDTO);
        adminPO.setAdminId(adminId);
        adminPO.setPwdSalt(pwdSalt);
        adminPO.setPassword(password);
        this.adminMapper.addAdmin(adminPO);
        this.roleService.addUserRole(addAdminDTO.getRoleIdString(), adminId, AuthorityConstant.TYPE_BACK);
        return adminId;
    }

    @Override
    public void updateAdmin(UpdateAdminDTO updateAdminDTO) {
        AdminPO adminPO = new AdminPO();
        BeanUtils.copyProperties(adminPO, updateAdminDTO);
        if(!StringUtils.isBlank(updateAdminDTO.getPassword())){
            AdminPO admin = getAdmin(updateAdminDTO.getAdminId());
            String newPassword = PasswordTool.passwordSaltHandler(updateAdminDTO.getPassword(), admin.getPwdSalt());
            adminPO.setPassword(newPassword);
        }
        this.adminMapper.updateAdmin(adminPO);
    }

    @Override
    public AdminPO getAdmin(String adminId) {
        return this.adminMapper.getAdmin(adminId, null);
    }

    @Override
    public AdminPO login(AdminLoginDTO loginDTO) {
        int loginResult = LoginLogConstant.LOGIN_RESULT_OTHER;
        AdminPO update = null;
        boolean updateFlag = false;
        try{
            boolean imageVerifyCheckResult = ImageVerifyCodeUtils.doVerifyCode(loginDTO.getVerifyCodeId() ,loginDTO.getVerifyCodeValue());
            if(!imageVerifyCheckResult){
                throw new AuthenticationException("验证码错误");
            }
            AdminPO adminPO = this.adminMapper.getAdmin(null , loginDTO.getAccountNo());
            if(adminPO == null){
                loginResult = LoginLogConstant.LOGIN_RESULT_USER_NOT_EXISTS;
                throw new AuthenticationException("账号/密码信息错误");
            }
            if(AdminConstant.ACCOUNT_STATUS_FORBIDDEN == adminPO.getAccountStatus().intValue()){
                loginResult = LoginLogConstant.LOGIN_RESULT_FORBIDDEN;
                throw new AuthenticationException("账号已被禁用，请联系管理员");
            }
            update = new AdminPO();
            update.setAdminId(adminPO.getAdminId());
            boolean doubleFactorAccount = isDoubleFactorAccount(loginDTO.getAccountNo());
            if(doubleFactorAccount){
                checkLockStatus(adminPO);
            }
            if(!PasswordTool.passwordSaltHandler(loginDTO.getPassword(), adminPO.getPwdSalt()).equals(adminPO.getPassword())){
                int newestLoginFailTimes = adminPO.getLoginFailTimes() + 1;
                update.setLoginFailTimes(newestLoginFailTimes);
                if(newestLoginFailTimes >= MAX_LOGIN_FAIL_TIMES){
                    update.setAccountStatus(UserConstant.USER_STATUS_LOCK);
                    update.setLockDt(DateHelper.getNowDate());
                }
                loginResult = LoginLogConstant.LOGIN_RESULT_PASSWORD_ERROR;
                updateFlag = true;
                throw new AuthenticationException("账号/密码信息错误");
            }
            if(doubleFactorAccount){
                checkLastUpdatePasswordDt(adminPO);
            }
            if(adminPO.getLoginFailTimes() > 0){
                update.setLoginFailTimes(0);
            }
            if(AdminConstant.ACCOUNT_STATUS_LOCK == adminPO.getAccountStatus()){
                update.setAccountStatus(AdminConstant.ACCOUNT_STATUS_OK);
            }
            update.setLastLoginDt(DateHelper.getNowDate());
            update.setLastLoginIp(loginDTO.getLoginIp());
            loginResult = LoginLogConstant.LOGIN_RESULT_SUCCESS;
            updateFlag = true;
            return adminPO;
        }finally {
            if(update != null && updateFlag){
                this.adminMapper.updateAdmin(update);
            }
            //添加登录日志
            this.loginLogService.addLoginLog(loginDTO.getAccountNo() ,LoginLogConstant.USER_TYPE_BACK
                    ,loginDTO.getLoginIp() ,loginResult);
        }
    }

    @Override
    public void updatePassword(String adminId, String oldPassword, String newPassword) {
        AdminPO adminPO = getAdmin(adminId);
        if(!PasswordTool.passwordSaltHandler(oldPassword, adminPO.getPwdSalt()).equals(adminPO.getPassword())){
            throw new BusinessException("旧密码错误，请重新输入");
        }
        String password = PasswordTool.passwordSaltHandler(newPassword, adminPO.getPwdSalt());
        adminPO = new AdminPO();
        adminPO.setAdminId(adminId);
        adminPO.setPassword(password);
        adminPO.setLastUpdatePasswordDt(DateHelper.getNowDate());
        this.adminMapper.updateAdmin(adminPO);
    }

    @Override
    public AdminPO getAdminById(String adminId) {
        return this.adminMapper.getAdmin(adminId, null);
    }

    @Override
    public boolean checkSmsVerify(String userName) {
        AdminPO admin = this.adminMapper.getAdmin(null ,userName);
        if(admin == null){
            throw new BusinessException("账号信息错误");
        }
        if(AdminConstant.ACCOUNT_STATUS_FORBIDDEN == admin.getAccountStatus()){
            throw new BusinessException("账号已被禁用");
        }
        boolean isDoubleFactorAccount = isDoubleFactorAccount(userName);
        if(isDoubleFactorAccount){
            checkLockStatus(admin);
            VerificationCodeUtils.sendVerificationCode(userName , SmsVerifyCodeBusinessCode.ADMIN_BACK_LOGIN_VERIFICATION_CODE ,"");
        }
        return isDoubleFactorAccount;
    }

    @Override
    public void updatePasswordWithAccount(String accountNo, String oldPassword, String newPassword) {
        AdminPO admin = this.adminMapper.getAdmin(null ,accountNo);
        if(admin == null){
            throw new BusinessException("账号信息错误");
        }
        updatePassword(admin.getAdminId() ,oldPassword ,newPassword);
    }

    private boolean isDoubleFactorAccount(String accountNo){
        if(!ValidateTool.isMobileNO(accountNo)){
            return false;
        }
        //2 后台管理员
        long count = this.userDoubleFactorMapper.countDoubleFactorUser(accountNo , UserConstant.USER_TYPE_ADMIN);
        if(count == 0){
            return false;
        }
        return true;
    }

    private void checkLastUpdatePasswordDt(AdminPO admin){
        if(StringUtils.isBlank(admin.getLastUpdatePasswordDt())){
            return;
        }
        LocalDateTime lastUpdatePasswordDateTime = LocalDateTime.ofInstant(DateHelper.getDate(admin.getLastUpdatePasswordDt() ,"yyyy-MM-dd HH:mm:ss").toInstant()
                , ZoneId.systemDefault());
        LocalDateTime checkDate = LocalDateTime.now().minusDays(45L);
        if(lastUpdatePasswordDateTime.isBefore(checkDate)){
            throw new MyAuthenticationException("10" ,"密码已过期，请及时修改密码");
        }
    }

    /**
     *
     * @param admin
     * @return true 账号被锁定
     */
    private void checkLockStatus(AdminPO admin){
        if(StringUtils.isBlank(admin.getLockDt())){
            return;
        }
        LocalDateTime lockDateTime = LocalDateTime.ofInstant(DateHelper.getDate(admin.getLockDt() ,"yyyy-MM-dd HH:mm:ss").toInstant()
                ,ZoneId.systemDefault());
        if(UserConstant.USER_STATUS_LOCK == admin.getAccountStatus()
                && lockDateTime.toLocalDate().isEqual(LocalDate.now())){
            throw new AuthenticationException("密码连续输入错误5次，该账号已被锁定，请明天再试");
        }
    }

    private final static int MAX_LOGIN_FAIL_TIMES = 5;
}
