package com.comvee.cdms.common.utils;


import java.util.Base64;

/**
 * @author: suyz
 * @date: 2018/11/9
 */
public class Base64Util {

    /**
     * base64编码
     * @param str
     * @return
     */
    public static String encodeBase64(String str){
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(str.getBytes()));
    }

    /**
     * base解码
     * @param str
     * @return
     */
    public static String decodeBase64(String str){
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(str.getBytes()));
    }
}
