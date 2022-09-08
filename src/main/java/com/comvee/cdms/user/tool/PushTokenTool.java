package com.comvee.cdms.user.tool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhiGe
 * @description
 * @date 2018/2/3 14:17 create
 */
public class PushTokenTool {


    private static Map<String,Integer> OS_TYPE = new HashMap<>();
    static {
        OS_TYPE.put("android", 1);
        OS_TYPE.put("iphone", 2);
    }

    /**
     * 获取操作系统类型
     * @param s
     * @return
     */
    public static int getOSTypeByDev(String s){
        return OS_TYPE.get(s);
    }
}
