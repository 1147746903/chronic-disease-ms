package com.comvee.cdms.common.interceptors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ShiroInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final Class[] shiroAnnotations = new Class[]{RequiresPermissions.class , RequiresRoles.class , RequiresUser.class};

    private final Map<Class, Consumer> handlerMap = new HashMap<>();

    public ShiroInterceptor(){
        initHandler();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            shiroCheck((HandlerMethod) handler);
        }
        return true;
    }

    private void shiroCheck(HandlerMethod handlerMethod){
        Annotation annotation = getMethodAnnotation(handlerMethod.getMethod());
        if(annotation == null){
            annotation = getClassAnnotation(handlerMethod.getBeanType());
        }
        if(annotation == null){
            return;
        }
        doAuthCheck(annotation);
    }

    private Annotation getClassAnnotation(Class cls){
        Annotation result;
        for(Class c : shiroAnnotations){
            result = cls.getAnnotation(c);
            if(result != null){
                return result;
            }
        }
        return null;
    }

    private Annotation getMethodAnnotation(Method method){
        Annotation result;
        for(Class c : shiroAnnotations){
            result = method.getAnnotation(c);
            if(result != null){
                return result;
            }
        }
        return null;
    }

    private void doAuthCheck(Annotation annotation){
        Consumer consumer = handlerMap.get(annotation.annotationType());
        if(consumer == null){
            return;
        }
        consumer.accept(annotation);
    }

    private Consumer requiresUserHandler(){
        return x ->{
            if(getSubject().getPrincipal() == null){
                throw new UnauthenticatedException("当前用户未登录");
            }
        };
    }

    private Consumer requiresRolesHandler(){
        return x ->{
            RequiresRoles requiresRoles = (RequiresRoles) x;
            String[] roles = requiresRoles.value();
            if (roles.length == 1) {
                getSubject().checkRole(roles[0]);
                return;
            }
            if (Logical.AND.equals(requiresRoles.logical())) {
                getSubject().checkRoles(Arrays.asList(roles));
                return;
            }
            if (Logical.OR.equals(requiresRoles.logical())) {
                boolean hasAtLeastOneRole = false;
                for (String role : roles){
                    if (getSubject().hasRole(role)){
                        hasAtLeastOneRole = true;
                    }
                }
                if (!hasAtLeastOneRole){
                    getSubject().checkRole(roles[0]);
                }
            }
        };
    }

    private Consumer requiresPermissionsHandler(){
        return x ->{
            RequiresPermissions requiresPermissions = (RequiresPermissions) x;
            String[] values = requiresPermissions.value();
            Subject subject = getSubject();

            if (values.length == 1) {
                subject.checkPermission(values[0]);
                return;
            }
            if (Logical.AND.equals(requiresPermissions.logical())) {
                getSubject().checkPermissions(values);
                return;
            }
            if (Logical.OR.equals(requiresPermissions.logical())) {
                boolean hasAtLeastOnePermission = false;
                for (String permission : values){
                    if(getSubject().isPermitted(permission)){
                        hasAtLeastOnePermission = true;
                    }
                }
                if (!hasAtLeastOnePermission){
                    getSubject().checkPermission(values[0]);
                }
            }
        };
    }

    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    private void initHandler(){
        handlerMap.put(RequiresUser.class ,requiresUserHandler());
        handlerMap.put(RequiresPermissions.class ,requiresPermissionsHandler());
        handlerMap.put(RequiresRoles.class ,requiresRolesHandler());
    }

}
