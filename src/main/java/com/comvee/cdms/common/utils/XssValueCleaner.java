package com.comvee.cdms.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssValueCleaner {

    static Pattern scriptPattern = Pattern.compile(".*<\\s*script\\s*>.*" ,Pattern.CASE_INSENSITIVE);

    public static String clean(String value){
        String result = value;
        Matcher scriptMatcher = scriptPattern.matcher(result);
        if(scriptMatcher.find()){
            result = scriptMatcher.replaceAll("");
        }
        result = result.replaceAll("(?i)javascript:" ,"");
        return result;
    }

}
