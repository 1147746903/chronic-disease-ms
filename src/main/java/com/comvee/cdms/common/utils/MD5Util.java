package com.comvee.cdms.common.utils;

import java.security.MessageDigest;

/**
 * @author: 苏友智
 */
public class MD5Util {
    /**
     * md5加密
     * @param source
     * @param charsetName
     * @return
     */
    public static String md5(String source, String charsetName) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] bytes = md.digest(source.getBytes(charsetName));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * md5加密
     * @param source
     * @return
     */
    public static String md5(String source) {
        try {
            return md5(source,"UTF-8");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 16进制转化
     * @param bytes
     * @return
     */
    private static String toHex(byte[] bytes) {
        final char[] hexDigits = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(hexDigits[(bytes[i] >> 4) & 0x0f]);
            ret.append(hexDigits[bytes[i] & 0x0f]);
        }
        return ret.toString().toLowerCase();
    }
}
