package com.comvee.cdms.common.utils;

/**
 * @author: suyz
 * @date: 2019/4/10
 */
public class ProfileUtil {

    public static final String PROFILE_ACTIVE = System.getProperty("run.profile");

    /**
     * 获取激活的配置文件名
     * @param fileName
     * @return
     */
    public static String getProfileActiveName(String fileName){
        int index = fileName.indexOf(".");
        StringBuilder stringBuilder = new StringBuilder(fileName);
        if(index >= 0 && !StringUtils.isBlank(PROFILE_ACTIVE)){
            stringBuilder.insert(index , "-" + PROFILE_ACTIVE);
        }
        return stringBuilder.toString();
    }

}
