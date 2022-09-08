/**    
 * 文件名：ResponsePrint.java    
 *    
 * 版本信息：    
 * 日期：2018-1-13    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.comvee.cdms.common.aop;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.comvee.cdms.common.utils.ApiMonitor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**  
 *   
 * @author: 苏友智
 * @time：2018-1-13 下午2:35:38     
 * @version:       
 */
@Component
@Aspect
public class ResponsePrint {
    
    private final static Logger log = LoggerFactory.getLogger("requestLog");
    
    @Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))")
    public void pointCut1(){}

    @Pointcut("execution(@org.springframework.web.bind.annotation.ExceptionHandler * *(..))")
    public void pointCut2(){}

    @Pointcut("execution(@org.springframework.web.bind.annotation.PostMapping * *(..))")
    public void pointCut3(){}

    @Pointcut("execution(@org.springframework.web.bind.annotation.GetMapping * *(..))")
    public void pointCut4(){}
    @AfterReturning(value = "pointCut1() || pointCut2() || pointCut3() || pointCut4()" , returning = "result")
    public void printResponse(Object result){
        if(result instanceof ResponseEntity){
            log.info("请求耗时:[{}ms],本次请求响应非JSON数据,不打印返回值" , ApiMonitor.costTime());
        }else{
            log.info("请求耗时:[{}ms],响应结果:{}" , ApiMonitor.costTime() ,JSON.toJSONString(result , SerializerFeature.DisableCircularReferenceDetect));
        }
    }
    

}
