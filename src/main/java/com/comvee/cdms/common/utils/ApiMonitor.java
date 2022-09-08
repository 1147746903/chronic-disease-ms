package com.comvee.cdms.common.utils;

import org.apache.logging.log4j.ThreadContext;

import java.util.UUID;

/**
 *
 */
public class ApiMonitor {

    private final static ThreadLocal<Long> TIME = new ThreadLocal();

    /**
     * 接口入口调用
     */
    public static void start(){
        //生成序列号
        ThreadContext.put("seq" , UUID.randomUUID().toString().replaceAll("-" ,""));
        //存放请求开始时间
        TIME.set(System.currentTimeMillis());
    }

    /**
     * 接口结束调用，清空线程数据（必须！！否则可能内存泄漏）
     */
    public static void stop(){
        ThreadContext.clearMap();
        TIME.remove();
    }

    /**
     * 返回接口耗时
     * @return
     */
    public static long costTime(){
        return System.currentTimeMillis() - TIME.get();
    }
}
