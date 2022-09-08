package com.comvee.cdms.common.utils;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.cfg.Config;
import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.FilePart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http请求工具
 * Created by Su on 2018/8/2.
 */
public class HttpUtils {

    private final static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    //最大超时时间
    private final static int MAX_TIME_OUT = 5000;

    private static AsyncHttpClient asyncHttpClient;
    private static boolean enableProxyPass = false;
    private static String proxyPassServerHost;
    static {
        DefaultAsyncHttpClientConfig.Builder builder = new DefaultAsyncHttpClientConfig.Builder();
        builder.setConnectTimeout(MAX_TIME_OUT);
        builder.setReadTimeout(MAX_TIME_OUT);
        builder.setHandshakeTimeout(MAX_TIME_OUT);
        builder.setRequestTimeout(MAX_TIME_OUT);
        asyncHttpClient = new DefaultAsyncHttpClient(builder.build());

        initProxyPassConfig();
    }

    private static void initProxyPassConfig(){
        try{
            enableProxyPass = Boolean.valueOf(Config.getValueByKey("httpUtils.proxyPass.enable"));
            proxyPassServerHost = Config.getValueByKey("httpUtils.proxyPass.serverHost");
        }catch (Exception e){
            log.error("初始化httpUtils请求代理配置失败" ,e);
        }
    }

    /**
     * 执行post请求
     * @param url
     * @param formParam
     * @param header
     * @param queryParam
     * @param body
     * @return
     */
    public static String doPost(String url, Map<String,String> formParam, Map<String,Object> header, Map<String,String> queryParam, String body){
        if(checkProxyPass(url)){
            return doProxyPassRequest(url , HttpMethod.POST ,formParam ,header ,queryParam ,body).getResponseBody();
        }
        BoundRequestBuilder builder = asyncHttpClient.preparePost(url);
        requestBuilderHandler(builder, formParam, header, queryParam, body);
        return execute(builder).getResponseBody();
    }

    /**
     * 执行post请求 返回byte数组
     * @param url
     * @param formParam
     * @param header
     * @param queryParam
     * @param body
     * @return
     */
    public static byte[] doPostAsBytes(String url, Map<String,String> formParam, Map<String,Object> header, Map<String,String> queryParam, String body){
        if(checkProxyPass(url)){
            return doProxyPassRequest(url , HttpMethod.POST ,formParam ,header ,queryParam ,body).getResponseBodyAsBytes();
        }
        BoundRequestBuilder builder = asyncHttpClient.preparePost(url);
        requestBuilderHandler(builder, formParam, header, queryParam, body);
        return execute(builder).getResponseBodyAsBytes();
    }

    /**
     * 执行get请求 返回byte数组
     * @param url
     * @param formParam
     * @param header
     * @param queryParam
     * @param body
     * @return
     */
    public static byte[] doGetAsBytes(String url, Map<String,Object> header, Map<String,String> queryParam){
        if(checkProxyPass(url)){
            return doProxyPassRequest(url , HttpMethod.GET ,null ,header ,queryParam ,null).getResponseBodyAsBytes();
        }
        BoundRequestBuilder builder = asyncHttpClient.prepareGet(url);
        requestBuilderHandler(builder, null, header, queryParam, null);
        return execute(builder).getResponseBodyAsBytes();
    }

    /**
     * 执行post请求
     * @param url
     * @param formParam
     * @param header
     * @return
     */
    public static String doPost(String url, Map<String,String> formParam, Map<String,Object> header){
        return doPost(url, formParam, header , null, null);
    }

    /**
     * 执行post请求
     * @param url
     * @param formParam
     * @return
     */
    public static String doPost(String url, Map<String,String> formParam){
        return doPost(url, formParam , null , null, null);
    }

    /**
     * 执行post请求
     * @param url
     * @param queryParam
     * @param body
     * @return
     */
    public static String doPost(String url, Map<String,String> queryParam, String body){
        return doPost(url , null, null, queryParam, body);
    }

    /**
     * 执行post请求
     * @param url
     * @param body
     * @return
     */
    public static String doPost(String url,  String body){
        return doPost(url , null, null, null, body);
    }

    /**
     * 执行post请求
     * @param url
     * @return
     */
    public static String doPost(String url){
        return doPost(url , null, null, null, null);
    }

    /**
     * 执行get请求
     * @param url
     * @param queryParam
     * @param header
     * @return
     */
    public static String doGet(String url, Map<String,String> queryParam, Map<String,Object> header){
        if(checkProxyPass(url)){
            return doProxyPassRequest(url , HttpMethod.GET ,null ,header ,queryParam ,null).getResponseBody();
        }
        BoundRequestBuilder builder = asyncHttpClient.prepareGet(url);
        requestBuilderHandler(builder, null, header, queryParam, null);
        return execute(builder).getResponseBody();
    }

    /**
     * 执行get请求
     * @param url
     * @return
     */
    public static String doGet(String url){
        return doGet(url, null, null);
    }

    /**
     * 执行get请求
     * @param url
     * @param queryParam
     * @return
     */
    public static String doGet(String url, Map<String,String> queryParam){
        return doGet(url, queryParam, null);
    }

    /**
     * 文件上传(bytes)
     * @param url
     * @param name
     * @param bytes
     * @return
     */
    public static String uploadFile(String url , String name , File file){
        BoundRequestBuilder builder = asyncHttpClient.preparePost(url);
        builder.addBodyPart(new FilePart(name ,file));
        return execute(builder).getResponseBody();
    }

    /**
     * 请求参数处理
     * @param builder
     * @param formParam
     * @param header
     * @param queryParam
     * @param body
     */
    static void requestBuilderHandler(BoundRequestBuilder builder, Map<String,String> formParam, Map<String,Object> header, Map<String,String> queryParam, String body){
        //form表单参数
        if(formParam != null && !formParam.isEmpty()){
            for(Map.Entry<String,String> entry : formParam.entrySet()){
                builder.addFormParam(entry.getKey() , entry.getValue());
            }
        }
        //请求头部
        if(header != null && !header.isEmpty()){
            for(Map.Entry<String,Object> entry : header.entrySet()){
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //query查询参数
        if(queryParam != null && !queryParam.isEmpty()){
            for(Map.Entry<String,String> entry : queryParam.entrySet()){
                builder.addQueryParam(entry.getKey() , entry.getValue());
            }
        }
        if(body !=null && !"".equals(body.trim())){
            builder.setBody(body);
        }
    }

    /**
     * 执行请求
     * @param builder
     * @return
     */
    static Response execute(BoundRequestBuilder builder){
        Request request = builder.build();
        log.info("开始执行http请求 --> url:[{}] {}" ,request.getMethod() ,request.getUrl());
        ListenableFuture<Response> responseListenableFuture = asyncHttpClient.executeRequest(request);
        try {
            return responseListenableFuture.get();
        } catch (Exception e) {
            log.error("http请求失败!请求的url:{}" , builder.build().getUrl() ,e);
            throw new RuntimeException("网络请求失败，异常类型:" + e.getClass().getName());
        }
    }

    //取主机名
    private final static Pattern pattern = Pattern.compile("(?<=\\//)\\S+?(?=\\/)");

    private static boolean checkProxyPass(String url){
        if(!enableProxyPass){
            return false;
        }
        String host = null;
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()){
            host = matcher.group();
        }
        if(host == null){
            return false;
        }
        Boolean cacheResult = HOST_CACHE.get(host);
        if(cacheResult != null){
            return cacheResult;
        }
        if(host.startsWith("10")
            || host.startsWith("172")
            || host.startsWith("192")){
            cacheResult = false;
        }else{
            cacheResult = true;
        }
        HOST_CACHE.put(host ,cacheResult);
        return cacheResult;
    }

    private static Response doProxyPassRequest(String url ,HttpMethod method ,Map<String,String> formParam
            , Map<String,Object> header, Map<String,String> queryParam, String body){
        Map<String ,String> proxyPassParam = new HashMap<>();
        try {
            proxyPassParam.put("url" , URLEncoder.encode(url ,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        proxyPassParam.put("method" ,method.name());
        if(formParam != null){
            proxyPassParam.put("formParam" , JSON.toJSONString(formParam));
        }
        if(queryParam != null) {
            proxyPassParam.put("queryParam", JSON.toJSONString(queryParam));
        }
        if(header != null) {
            proxyPassParam.put("headerParam", JSON.toJSONString(header));
        }
        if(body != null) {
            proxyPassParam.put("body", body);
        }
        String proxyPassUrl = proxyPassServerHost + "/proxyRequest";
        BoundRequestBuilder builder = asyncHttpClient.preparePost(proxyPassUrl);
        requestBuilderHandler(builder, proxyPassParam, null, null, null);
        return execute(builder);
    }

    private static final Map<String ,Boolean> HOST_CACHE = new ConcurrentHashMap<>();
    static {
        HOST_CACHE.put("localhost" ,false);
        HOST_CACHE.put("127.0.0.1" ,false);
    }
}
