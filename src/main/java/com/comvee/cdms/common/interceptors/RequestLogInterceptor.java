package com.comvee.cdms.common.interceptors;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.utils.ApiMonitor;
import com.comvee.cdms.common.utils.RequestTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhiGe
 * @description
 * @date 2018/2/2 9:37 create
 */
public class RequestLogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("requestLog");

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        ApiMonitor.start();
        logger.info("[###请求IP###]:{} [#####请求#####]:[{}] {} 参数:{}" ,httpServletRequest.getRemoteAddr()
                ,httpServletRequest.getMethod()
                ,httpServletRequest.getRequestURI()
                ,JSON.toJSONString(RequestTool.getParameterMap(httpServletRequest)));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        ApiMonitor.stop();
    }
}
