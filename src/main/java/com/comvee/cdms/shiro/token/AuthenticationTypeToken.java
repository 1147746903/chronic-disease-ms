package com.comvee.cdms.shiro.token;

import com.comvee.cdms.shiro.cfg.AuthenticationType;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
public abstract class AuthenticationTypeToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    private AuthenticationType authenticationType;

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

}
