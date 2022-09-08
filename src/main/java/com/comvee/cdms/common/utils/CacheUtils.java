package com.comvee.cdms.common.utils;

import com.comvee.cdms.wechat.api.WechatTokenApi;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Su on 2018/8/2.
 */
public class CacheUtils {

    private final static Logger log = LoggerFactory.getLogger(CacheUtils.class);

    /**
     * token/ticket过期时间(120分钟，提前2分钟)
     */
    private static final int TIME_OUT = 118;

    /**
     * token缓存
     */
    public static final LoadingCache<String,String> tokenCache = CacheBuilder.newBuilder()
            .maximumSize(20)
            .expireAfterWrite(TIME_OUT, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return WechatTokenApi.applyAccessToken(key);
                }
            });


    /**
     * 微信公众号授权token
     */
    public static final Cache<String, String> publicWechatToken = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(30 ,TimeUnit.MINUTES)
            .build();

    /**
     * 订单token令牌
     */
    public static final Cache<String, String> ORDER_TOKEN = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(5 ,TimeUnit.MINUTES)
            .build();

    /**
     * 获取缓存值
     * @param cache
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T get(LoadingCache<String,T> cache, String key){
        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            log.warn("获取缓存异常", e);
        }
        return null;
    }

    /**
     * 获取缓存值
     * @param cache
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T get(Cache<String, T> cache ,String key){
        return cache.getIfPresent(key);
    }
}
