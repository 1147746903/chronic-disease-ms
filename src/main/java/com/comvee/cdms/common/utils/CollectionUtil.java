package com.comvee.cdms.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 集合工具类
 * 
 * @author antelop
 */
public class CollectionUtil {

    public static <T> String collectionToStr(Collection<String> source) {
        return collectionToStr(source, ",");
    }
    
    public static <T> String collectionToStr(Collection<String> source, String separator) {
        if (source == null || source.size() == 0){
            return "";
        }
        else if (source.size() == 1){
            return String.valueOf(source.iterator().next());
        }

        Iterator<String> iterator = source.iterator();
        String target = String.valueOf(iterator.next());

        for (; iterator.hasNext();) {
            String str = iterator.next();
            if (StringUtils.isNullOrEmpty(str)){
                continue;
            }

            target += separator + str;
        }

        return target;
    }

    /**
     * list 转字符串 默认分隔符为 ,
     * 
     * @param source
     * @return
     */
    public static <T> String listToStr(List<? extends T> source) {
        return listToStr(source, ",");
    }

    /**
     * list 转字符串
     * 
     * @param source
     * @param separator
     *            分隔符
     * @return
     */
    public static <T> String listToStr(List<? extends T> source, String separator) {
        if (source == null || source.size() == 0){
            return "";
        }
        else if (source.size() == 1){
            return String.valueOf(source.get(0));
        }

        String target = String.valueOf(source.get(0));

        for (int i = 1; i < source.size(); i++) {
            String str = String.valueOf(source.get(i));
            if (StringUtils.isNullOrEmpty(str)){
                continue;
            }

            target += separator + str;
        }
        return target;
    }

    /**
     * 单一的list
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<T> singletonList(T t){
        List<T> list = new ArrayList<>(1);
        list.add(t);
        return list;
    }
}
