/**    
 * 文件名：PasswordTool.java    
 *    
 * 版本信息：    
 * 日期：2018-1-15    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.comvee.cdms.user.tool;

import com.comvee.cdms.common.utils.MD5Util;

/**
 *   
 * @author: 苏友智
 * @time：2018-1-15 上午10:10:25     
 * @version:       
 */
public class PasswordTool {
    
    /**
     * 密码盐处理
     * @author 苏友智
     * @time：2018-1-15 上午10:12:14
     */
    public static String passwordSaltHandler(String pwd,String salt){
        return MD5Util.md5(pwd + salt);
    }
}
