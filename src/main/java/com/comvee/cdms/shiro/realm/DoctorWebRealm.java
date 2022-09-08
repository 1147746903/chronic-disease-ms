package com.comvee.cdms.shiro.realm;

import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.cfg.SmsVerifyCodeBusinessCode;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.common.utils.VerificationCodeUtils;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorCacheServiceI;
import com.comvee.cdms.shiro.token.UserNamePasswordToken;
import com.comvee.cdms.user.dto.LoginDTO;
import com.comvee.cdms.user.dto.UserRelationDTO;
import com.comvee.cdms.user.mapper.UserMapper;
import com.comvee.cdms.user.po.UserPO;
import com.comvee.cdms.user.po.UserRelationPO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: suyz
 * @date: 2018/9/27
 */
public class DoctorWebRealm extends AuthorizingRealm {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private DoctorCacheServiceI doctorCacheService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(principals.getPrimaryPrincipal() instanceof DoctorSessionBO){
            DoctorSessionBO doctorSessionBO = (DoctorSessionBO) principals.getPrimaryPrincipal();
            Set<String> authoritySet = this.authorityService.listUserAuthority(doctorSessionBO.getDoctorId(), AuthorityConstant.TYPE_FRONT);
            simpleAuthorizationInfo.setStringPermissions(authoritySet);
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UserNamePasswordToken userNamePasswordToken = (UserNamePasswordToken) token;
        String userName = userNamePasswordToken.getUserName();
        String password = userNamePasswordToken.getPassword();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);
        loginDTO.setLastLoginIPAddress(userNamePasswordToken.getHost());
        loginDTO.setVerifyCodeId(userNamePasswordToken.getVerifyCodeId());
        loginDTO.setVerifyCodeValue(userNamePasswordToken.getVerifyCodeValue());
        loginDTO.setSmsVerifyCode(userNamePasswordToken.getSmsVerifyCode());
        UserPO userPO = this.userLoginService.login(loginDTO, userNamePasswordToken.getAuthenticationType() ,userNamePasswordToken.getUserType());
        DoctorSessionBO doctorSession = getDoctorInfo(userPO.getUid());
        if(userPO.getNeedDoubleFactor() != null && userPO.getNeedDoubleFactor()){
            checkSmsVerifyCode(doctorSession ,userNamePasswordToken);
        }
        return new SimpleAuthenticationInfo(doctorSession ,password, getName());
    }

    /**
     * 获取医生信息
     * @param uid
     * @return
     */
    private DoctorSessionBO getDoctorInfo(String uid){
        DoctorPO doctorPO = this.doctorCacheService.getDoctorById(uid);
        if(doctorPO == null){
            throw new AuthenticationException("找不到医生信息");
        }
        List<String> doctorIdList1 = this.doctorCacheService.listTeamId(doctorPO.getDoctorId());
        if(doctorIdList1==null||doctorIdList1.size()<=0){
            throw new AuthenticationException("该医生没有可管理的团队");
        }
        DoctorSessionBO doctorBO = new DoctorSessionBO();
        BeanUtils.copyProperties(doctorBO, doctorPO);
        doctorBO.setUid(uid);
        List<DoctorPO> doctorPOList = this.doctorCacheService.listMyDoctor(doctorPO.getDoctorId());
        List<String> doctorIdList = null;
        if(doctorPOList != null && doctorPOList.size() > 0){
            doctorIdList = doctorPOList.stream().map(DoctorPO::getDoctorId).collect(Collectors.toList());
        }else{
            doctorIdList = new ArrayList<>();
        }
        doctorBO.setRelateDoctorList(doctorIdList);
        return doctorBO;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserNamePasswordToken;
    }

    /**
     * 判断短信验证码
     * @param doctorSession
     * @param smsVerifyCode
     */
    private void checkSmsVerifyCode(DoctorSessionBO doctorSession ,UserNamePasswordToken token){
        boolean result = VerificationCodeUtils.checkVerificationCode(doctorSession.getPhoneNo()
                , SmsVerifyCodeBusinessCode.DOCTOR_LOGIN_DOUBLE_FACTOR  ,token.getSmsVerifyCode());
        if(!result){
            throw new AuthenticationException("短信验证码错误");
        }
    }
}
