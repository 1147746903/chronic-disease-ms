package com.comvee.cdms.shiro.realm;

import com.comvee.cdms.shiro.cfg.AuthenticationType;
import com.comvee.cdms.shiro.token.AuthenticationTypeToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author: suyz
 * @date: 2018/11/1
 */
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        AuthenticationTypeToken userLoginTypeToken = (AuthenticationTypeToken) authenticationToken;
        AuthenticationType authenticationType = userLoginTypeToken.getAuthenticationType();
        Collection<Realm> realms = getRealms();
        Collection<Realm> typeRealms = new ArrayList<>();
        realms.forEach(x -> {
            if(assertRealmType(x.getName(), authenticationType)){
                typeRealms.add(x);
            }
        });
        if (typeRealms.size() == 1){
            return doSingleRealmAuthentication(typeRealms.iterator().next(), userLoginTypeToken);
        } else{
            return doMultiRealmAuthentication(typeRealms, userLoginTypeToken);
        }

    }

    /**
     * 判断realm类型
     * @param realmName
     * @param authenticationType
     * @return
     */
    private boolean assertRealmType(String realmName, AuthenticationType authenticationType){
        return realmName.toLowerCase().contains(authenticationType.toString().replaceAll("_", "").toLowerCase());
    }
}
