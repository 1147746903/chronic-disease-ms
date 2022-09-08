package com.comvee.cdms.actiontrack.core;

import com.comvee.cdms.actiontrack.model.NewAction;
import com.comvee.cdms.common.utils.RequestTool;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class ActionTrackAspect {

    private final static Logger log = LoggerFactory.getLogger(ActionTrackAspect.class);

    @Autowired
    private ActionTrackHandler actionTrackHandler;

    @Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))")
    public void pointCut(){}

    @Around(value = "pointCut()")
    public Object recordAction(ProceedingJoinPoint jp) throws Throwable {
        HttpServletRequest request = RequestTool.getRequest();
        boolean supportResult = this.actionTrackHandler.support(request.getServletPath());
        if(supportResult){
            //逻辑处理前保存一次用户信息，防止因为逻辑清空了用户信息，比如登出
            this.actionTrackHandler.cacheUserInfo();
        }
        try{
            Object result = jp.proceed();
            if(supportResult){
                //重新再尝试保存一次用户信息，有些接口只有处理完之后才有用户信息
                this.actionTrackHandler.cacheUserInfo();
                NewAction newAction = this.actionTrackHandler.assembleNewAction(RequestTool.getRequest() ,result);
                this.actionTrackHandler.newAction(newAction);
            }
            return result;
        }finally {
            this.actionTrackHandler.clearCache();
        }
    }

}
