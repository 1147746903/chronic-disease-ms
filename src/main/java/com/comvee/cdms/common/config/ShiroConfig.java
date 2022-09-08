package com.comvee.cdms.common.config;

import com.comvee.cdms.shiro.realm.*;
import com.comvee.cdms.shiro.session.MySessionDao;
import com.comvee.cdms.shiro.session.MySessionManager;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
        return shiroFilterFactoryBean;
    }

    @Bean
    public MyModularRealmAuthenticator modularRealmAuthenticator(){
        MyModularRealmAuthenticator myModularRealmAuthenticator = new MyModularRealmAuthenticator();
        myModularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return myModularRealmAuthenticator;
    }

    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehCacheManager;
    }

    @Bean
    public MySessionDao sessionDao(){
        return new MySessionDao();
    }

    @Bean
    public MySessionManager sessionManager(){
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setGlobalSessionTimeout(10800000L);
        mySessionManager.setDeleteInvalidSessions(true);
        mySessionManager.setSessionValidationSchedulerEnabled(true);
        mySessionManager.setSessionDAO(sessionDao());
        mySessionManager.setSessionValidationInterval(1800000L);
        return mySessionManager;
    }

/*    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }*/

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setAuthenticator(modularRealmAuthenticator());
        defaultWebSecurityManager.setCacheManager(ehCacheManager());
        defaultWebSecurityManager.setSessionManager(sessionManager());

        List<Realm> realmList = new ArrayList<>();
        realmList.add(doctorWebRealm());
        realmList.add(memberMiniProgramRealm());
        realmList.add(adminRealm());
        realmList.add(memberIdCardRealm());
        realmList.add(thirdRealm());
        realmList.add(doctorH5WxRealm());
        realmList.add(doctorHisRealm());
        defaultWebSecurityManager.setRealms(realmList);
        return defaultWebSecurityManager;
    }

    @Bean
    public DoctorWebRealm doctorWebRealm(){
        return new DoctorWebRealm();
    }

    @Bean
    public MemberMiniProgramRealm memberMiniProgramRealm(){
        return new MemberMiniProgramRealm();
    }

    @Bean
    public AdminRealm adminRealm(){
        return new AdminRealm();
    }

    @Bean
    public MemberIdCardRealm memberIdCardRealm(){
        return new MemberIdCardRealm();
    }

    @Bean
    public ThirdRealm thirdRealm(){
        return new ThirdRealm();
    }

    @Bean
    public DoctorH5WxRealm doctorH5WxRealm(){
        return new DoctorH5WxRealm();
    }

    @Bean
    public DoctorHisRealm doctorHisRealm(){
        return new DoctorHisRealm();
    }
}
