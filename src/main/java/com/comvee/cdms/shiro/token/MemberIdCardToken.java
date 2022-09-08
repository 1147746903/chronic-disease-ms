package com.comvee.cdms.shiro.token;

/**
 * @author: suyz
 * @date: 2019/7/23
 */
public class MemberIdCardToken extends AuthenticationTypeToken {

    private String idCard;
    private String host;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return this.idCard;
    }

    @Override
    public Object getCredentials() {
        return "";
    }
}
