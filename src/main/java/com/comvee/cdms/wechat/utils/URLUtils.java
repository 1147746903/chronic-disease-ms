package com.comvee.cdms.wechat.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Su on 2018/8/13.
 */
public class URLUtils {

    private final static String DEFAULT_CHARSET = "UTF-8";

    /**
     * 编码
     * @param s
     * @param charset
     * @return
     */
    public static String URLEncoding(String s, String charset){
        try {
            return URLEncoder.encode(s, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 编码
     * @param s
     * @return
     */
    public static String URLEncoding(String s){
        return URLEncoding(s, DEFAULT_CHARSET);
    }

    /**
     * 解码
     * @param s
     * @return
     */
    public static String URLDncoding(String s){
        return URLDncoding(s, DEFAULT_CHARSET);
    }

    /**
     * 解码
     * @param s
     * @param charset
     * @return
     */
    public static String URLDncoding(String s, String charset){
        return URLDncoding(s, charset);
    }
}
