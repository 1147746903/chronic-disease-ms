package com.comvee.cdms.wechat.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.ResourcesUtils;
import com.comvee.cdms.wechat.model.WechatAppConfig;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: suyz
 * @date: 2018/10/31
 */
public class WechatConfig {

    private static final Logger log = LoggerFactory.getLogger(WechatConfig.class);
    private final static String CONFIG_FILE_NAME = "wechat-config.yml";

    private static final ConcurrentHashMap<String,WechatAppConfig> secretMap = new ConcurrentHashMap<>(10);
    static {
        try {
            loadConfig();
        } catch (Exception e) {
            log.error("配置文件加载失败",e);
        }
    }

    /**
     * 加载配置文件
     * @throws FileNotFoundException
     */
    private static void loadConfig() throws FileNotFoundException {
        InputStream is = null;
        try{
            is = getStream();
            JSONObject jsonObject = Yaml.loadType(is, JSONObject.class);
            JSONArray jsonArray = jsonObject.getJSONArray("config");
            if(jsonArray == null || jsonArray.size() == 0){
                log.warn("wechat-config.yml 配置文件没有有效配置或配置错误！");
                return;
            }
            Iterator iterator = jsonArray.iterator();
            WechatAppConfig wechatAppConfig;
            JSONObject object;
            while(iterator.hasNext()){
                object = (JSONObject) JSON.toJSON(iterator.next());
                wechatAppConfig = JSON.toJavaObject(object, WechatAppConfig.class);
                secretMap.putIfAbsent(object.getString("appName"), wechatAppConfig);
            }
            log.info("Load Wechat Config Success!Size:" + secretMap.size());
        }catch (Exception e){
            log.error("初始化微信配置异常" ,e);
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

    }

    /**
     * 获取微信配置
     * @param appName
     * @return
     */
    public static WechatAppConfig getWechatAppConfig(String appName){
        return secretMap.get(appName);
    }

    /**
     * 获取配置文件流
     * @return
     * @throws FileNotFoundException
     */
    private static InputStream getStream() {
        InputStream is = ResourcesUtils.loadStream(CONFIG_FILE_NAME);
        if(is == null){
            throw new RuntimeException("读取不到微信配置文件，文件路径:" + CONFIG_FILE_NAME);
        }
        return is;
    }


}
