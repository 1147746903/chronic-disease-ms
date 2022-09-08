package com.comvee.cdms.shiro.realm;

import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.doctor.bo.DoctorSessionBO;
import com.comvee.cdms.doctor.mapper.DoctorMapper;
import com.comvee.cdms.doctor.po.DoctorPO;
import com.comvee.cdms.doctor.service.DoctorCacheServiceI;
import com.comvee.cdms.shiro.token.DoctorH5WxToken;
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
import java.util.stream.Collectors;

public class DoctorH5WxRealm extends AuthorizingRealm {

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorCacheServiceI doctorCacheService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        DoctorH5WxToken doctorH5WxToken = (DoctorH5WxToken) token;
        DoctorPO doctor = this.doctorMapper.getDoctorByOpenId(doctorH5WxToken.getOpenId());
        if(doctor == null){
            throw new AuthenticationException("微信未绑定医生");
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
        return token instanceof DoctorH5WxToken;
    }
}
