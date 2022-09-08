package com.comvee.cdms.shiro.realm;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.shiro.token.MemberMiniProgramToken;
import com.comvee.cdms.user.dto.WechatLoginDTO;
import com.comvee.cdms.user.po.UserWechatJoinPO;
import com.comvee.cdms.user.service.UserLoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
public class MemberMiniProgramRealm extends AuthorizingRealm {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        MemberMiniProgramToken memberMiniProgramToken = (MemberMiniProgramToken) token;
        WechatLoginDTO wechatLoginDTO = new WechatLoginDTO();
        BeanUtils.copyProperties(wechatLoginDTO, memberMiniProgramToken);
        UserWechatJoinPO userWechatJoinPO = this.userLoginService.miniProgramlogin(wechatLoginDTO);
        return new SimpleAuthenticationInfo(userWechatJoinPO, memberMiniProgramToken.getSignature(), getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof MemberMiniProgramToken;
    }
}
