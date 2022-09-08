package com.comvee.cdms.common.filter;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.utils.RequestTool;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZhiGe
 * @description 请求信息日志过滤器
 * @date 2018/3/19 13:29 create
 */
public class MdcFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
            , FilterChain filterChain) throws ServletException, IOException {
        try{
            ThreadContext.put("requestInfo", getRequestInfo(httpServletRequest));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            ThreadContext.remove("requestInfo");
            ThreadContext.clearAll();
        }
    }

    /**
     * @description: 获取请求信息
     * @author 苏友智
     * @time 2018/3/19 13:37
     */
    private String getRequestInfo(HttpServletRequest httpServletRequest){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("URI:").append(httpServletRequest.getServletPath())
                .append(",IP:").append(RequestTool.getIpAddr(httpServletRequest))
                .append(",param:");
        String param = JSON.toJSONString(httpServletRequest.getParameterMap());
        if(param != null && param.length() > MAX_PARAM_LOG_LENGTH){
            param.substring(0, MAX_PARAM_LOG_LENGTH);
        }
        stringBuilder.append(param);
        return stringBuilder.toString();
    }

    /**
     * 打印参数最大长度
     */
    private final static int MAX_PARAM_LOG_LENGTH = 2048;
}
