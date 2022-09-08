package com.comvee.cdms.shiro.realm;

import com.comvee.cdms.authority.constant.AuthorityConstant;
import com.comvee.cdms.authority.service.AuthorityService;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorCacheServiceI;
import com.comvee.cdms.shiro.token.DoctorHisToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DoctorHisRealm extends AuthorizingRealm {

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
        DoctorHisToken doctorHisToken = (DoctorHisToken) token;
        DoctorPO doctor = this.doctorCacheService.getDoctorById(doctorHisToken.getDoctorId());
        if(doctor == null){
            throw new AuthenticationException("非法请求");
        }
        return new SimpleAuthenticationInfo(getDoctorInfo(doctor) ,"" ,getName());
    }

    /**
     * 获取医生信息
     * @param uid
     * @return
     */
    private DoctorSessionBO getDoctorInfo(DoctorPO doctorPO){
        DoctorSessionBO doctorBO = new DoctorSessionBO();
        BeanUtils.copyProperties(doctorBO, doctorPO);

        List<DoctorPO> list = this.doctorCacheService.listMyDoctor(doctorPO.getDoctorId());
        if(list != null && !list.isEmpty()){
            List<String> idList = list.stream().map(DoctorPO::getDoctorId).collect(Collectors.toList());
            doctorBO.setRelateDoctorList(idList);
        }else{
            doctorBO.setRelateDoctorList(Collections.singletonList(doctorBO.getDoctorId()));
        }
        return doctorBO;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof DoctorHisToken;
    }
}
