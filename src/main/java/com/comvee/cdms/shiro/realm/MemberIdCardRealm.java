package com.comvee.cdms.shiro.realm;

import com.comvee.cdms.member.dto.GetMemberDTO;
import com.comvee.cdms.member.mapper.MemberMapper;
import com.comvee.cdms.member.po.MemberPO;
import com.comvee.cdms.shiro.token.MemberIdCardToken;
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
 * @date: 2019/7/23
 */
public class MemberIdCardRealm extends AuthorizingRealm {

    @Autowired
    private MemberMapper memberMapper;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        MemberIdCardToken memberIdCardToken = (MemberIdCardToken) token;
        GetMemberDTO getMemberDTO = new GetMemberDTO();
        getMemberDTO.setIdCard(memberIdCardToken.getIdCard());
        MemberPO memberPO = this.memberMapper.getMember(getMemberDTO);
        if(memberPO == null){
            throw new AuthenticationException("该患者未注册");
        }
        return new SimpleAuthenticationInfo(memberPO, "", getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof MemberIdCardToken;
    }
}
