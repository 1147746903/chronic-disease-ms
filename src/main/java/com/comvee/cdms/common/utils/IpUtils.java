package com.comvee.cdms.common.utils;

//import sun.net.util.IPAddressUtil;

/**
 * @author ZhiGe
 * @description
 * @date 2018/5/8 13:11 create
 */
public class IpUtils {

    /**
     * @description: 判断ip是否为内网ip,是返回true,否则返回false
     * @date 2018/5/8 13:12
     * @author 苏友智
     * @param ip
     * @return
     */
//    public static boolean internalIp(String ip) {
//        byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
//        return internalIp(addr);
//    }

    /**
     * @description: 判断ip是否为内网ip,是返回true,否则返回false
     * @date 2018/5/8 13:12
     * @author 苏友智
     * @param addr
     * @return
     */
    public static boolean internalIp(byte[] addr) {
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        //10.x.x.x/8
        final byte section1 = 0x0A;
        //172.16.x.x/12
        final byte section2 = (byte) 0xAC;
        final byte section3 = (byte) 0x10;
        final byte section4 = (byte) 0x1F;
        //192.168.x.x/16
        final byte section5 = (byte) 0xC0;
        final byte section6 = (byte) 0xA8;
        switch (b0) {
            case section1:
                return true;
            case section2:
                if (b1 >= section3 && b1 <= section4) {
                    return true;
                }
            case section5:
                switch (b1) {
                    case section6:
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }
}
