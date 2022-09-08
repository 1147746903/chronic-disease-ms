package com.comvee.cdms.common.utils;

public class StackTraceUtils {

    public static String callChain(){
        StringBuilder result = new StringBuilder();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(int i = 3,max = stackTraceElements.length; i < max ; i++){
            result.append(callInfoResolver(stackTraceElements[i]));
        }
        return result.toString();
    }

    public static String lastCall(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return callInfoResolver(stackTraceElements[3]);
    }

    private static String callInfoResolver(StackTraceElement stackTraceElement){
        return String.format("%s (%s:%s)" ,stackTraceElement.getClassName()
                ,stackTraceElement.getMethodName()
                ,stackTraceElement.getLineNumber());
    }

}
