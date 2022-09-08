package com.comvee.cdms.shiro.realm;

import com.comvee.cdms.admin.model.dto.AdminLoginDTO;
import com.comvee.cdms.admin.model.po.AdminPO;
import com.comvee.cdms.admin.service.AdminService;
import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.shiro.token.UserNamePasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Set;

/**
 * @author: suyz
 * @date: 2019/1/15
 */
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private AdminService adminService;

    @Autowired
    @Lazy
    private AuthorityService authorityService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(principals.getPrimaryPrincipal() instanceof AdminPO){
            simpleAuthorizationInfo.addRole("ADMIN");
            AdminPO adminPO = (AdminPO) principals.getPrimaryPrincipal();
            Set<String> authoritySet = this.authorityService.listUserAuthority(adminPO.getAdminId(), AuthorityConstant.TYPE_BACK);
            simpleAuthorizationInfo.setStringPermissions(authoritySet);
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UserNamePasswordToken userNamePasswordToken = (UserNamePasswordToken) token;
        AdminLoginDTO loginDTO = new AdminLoginDTO();
        loginDTO.setAccountNo(userNamePasswordToken.getUserName());
        loginDTO.setPassword(userNamePasswordToken.getPassword());
        loginDTO.setVerifyCodeId(userNamePasswordToken.getVerifyCodeId());
        loginDTO.setVerifyCodeValue(userNamePasswordToken.getVerifyCodeValue());
        loginDTO.setSmsVerifyCode(userNamePasswordToken.getSmsVerifyCode());
        loginDTO.setLoginIp(userNamePasswordToken.getHost());
        AdminPO adminPO = this.adminService.login(loginDTO);
        return new SimpleAuthenticationInfo(adminPO, userNamePasswordToken.getPassword(), getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserNamePasswordToken;
    }
}
