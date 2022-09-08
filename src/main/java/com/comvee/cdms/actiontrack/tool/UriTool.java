package com.comvee.cdms.actiontrack.tool;

public class UriTool {

    public static String uriHandler(String requestUri){
        if(requestUri.endsWith(".do")){
            requestUri = requestUri.replaceAll(".do" ,"");
        }
        return requestUri;
    }
}
