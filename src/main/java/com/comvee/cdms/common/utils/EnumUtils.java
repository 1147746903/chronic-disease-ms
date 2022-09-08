package com.comvee.cdms.common.utils;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class EnumUtils {

    /**
     * 获取枚举对象
     * @param cls
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T extends Enum> T getEnum(Class<T> cls , Predicate<T> predicate){
        if(!cls.isEnum()){
            throw new RuntimeException();
        }
        T[] arr = cls.getEnumConstants();
        return Stream.of(arr).filter(predicate).findAny().orElse(null);
    }

}
