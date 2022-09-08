package com.comvee.cdms.shiro.token;

import com.comvee.cdms.shiro.cfg.AuthenticationType;
import com.comvee.cdms.user.dto.WechatLoginDTO;

/**
 * @author: chenhb
 * @description: 描述
 * @data: 2021/2/2 9:10
 **/
public class ChannelLoginToken extends AuthenticationTypeToken {

    private WechatLoginDTO wechatLoginDTO;

    public ChannelLoginToken(WechatLoginDTO wechatLoginDTO) {
        this.wechatLoginDTO = wechatLoginDTO;
        setAuthenticationType(AuthenticationType.CHANNEL_MINI_PROGRAM);
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

    public WechatLoginDTO getWechatLoginDTO() {
        return wechatLoginDTO;
    }

    public void setWechatLoginDTO(WechatLoginDTO wechatLoginDTO) {
        this.wechatLoginDTO = wechatLoginDTO;
    }
}
