package com.comvee.cdms.shiro.token;

import com.comvee.cdms.packages.cfg.ServiceCode;
import com.comvee.cdms.shiro.cfg.PrincipalType;

import java.util.List;

/**
 * 第三方登录验证token
 * @author 林雨堆
 * @date 2019-11-12
 *
 */
public class ThirdToken extends AuthenticationTypeToken {
    /**
     * 主编号
     */
    private String principalId;

    /**
     * 主编号类型
     */
    private PrincipalType principalType;

    /**
     * 简单描述主编号是什么
     */
    private String principalDesc;

    private String host;

    /**
     * 第三方唯一标识
     */
    private String thirdId;
    /**
     * 第三方认证套餐编号
     */
    private String packageId;
    /**
     * 第三方认证服务代码列表
     */
    private List<ServiceCode> serviceCode;

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public PrincipalType getPrincipalType() {
        return principalType;
    }

    public void setPrincipalType(PrincipalType principalType) {
        this.principalType = principalType;
    }

    public String getPrincipalDesc() {
        return principalDesc;
    }

    public void setPrincipalDesc(String principalDesc) {
        this.principalDesc = principalDesc;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public List<ServiceCode> getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(List<ServiceCode> serviceCode) {
        this.serviceCode = serviceCode;
    }
}
