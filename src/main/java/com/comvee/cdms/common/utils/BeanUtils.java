/**    
 * 文件名：BeanUtils.java    
 *    
 * 版本信息：    
 * 日期：2018-1-12    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.comvee.cdms.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**  
 *   
 * @author: 苏友智
 * @time：2018-1-12 下午12:38:39     
 * @version:       
 */
public class BeanUtils {
    
    private final static Logger log = LoggerFactory.getLogger(BeanUtils.class);
    
    /**
     * 拷贝数据
     * dest 目的
     * orig 来源
     * @author 苏友智
     * @time：2018-1-12 下午1:41:54
     */
    public static void copyProperties(Object dest, Object orig){
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            log.error("对象数据拷贝异常",e);
        } 
    }

    /**
     * 拷贝数组数据
     * @param cls
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> copyListProperties(Class<T> cls ,List list){
        if(cls == null){
            throw new IllegalArgumentException("目标类对象不能为空");
        }
        if(list == null || list.isEmpty()){
            return null;
        }
        List<T> result = new ArrayList<>();
        T item = null;
        for(Object obj : list){
            try {
                item = cls.newInstance();
                org.apache.commons.beanutils.BeanUtils.copyProperties(item, obj);
                result.add(item);
            } catch (Exception e) {
                log.error("拷贝数据对象数据失败" ,e);
                break;
            }
        }
        return result;
    }


}
