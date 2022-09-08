package com.comvee.cdms.common.cfg;

/**
 * @author: suyz
 * @date: 2019/4/12
 */
public class ServerHostConstant {

    public static String COMVEE_DEFENDER_HOST;
    static {
        COMVEE_DEFENDER_HOST = Config.getValueByKey("comvee.defender.host");
    }
}
