package com.comvee.cdms.common.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
/**
 *  缓存工具类，主要实现缓存的管理
 * @author zgq
 *
 */
public class EhCacheTool {
	private static EhCacheTool ehCacheManager = null;
	private static CacheManager cacheManager = null;
	private EhCacheTool() {
		cacheManager = null;
	}
	/**
	 * 获取缓存工具实例，读取默认的ehcache.xml
	 * @return
	 */
	public static synchronized EhCacheTool getInstance() {
		if (ehCacheManager == null) {
			ehCacheManager = new EhCacheTool();
		}
		return ehCacheManager;
	}
	/**
	 * 获取缓存工具实例，读取指定的ehcache.xml
	 * @param is 指定ehcache.xml的文件流 
	 * @return
	 */	
	public void initCache(InputStream is) {
		try {
			if (cacheManager == null){
				cacheManager = new CacheManager(is);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将序列化数据存放到缓存中
	 * @param cacheName 缓存名字
	 * @param key 键子
	 * @param value 值
	 */
	public void put(String cacheName, Serializable key, Serializable value) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
		cache.flush();
	}
	/**
	 * 将普通对象数据放到缓存中
	 * @param cacheName 缓存名字
	 * @param key 键子
	 * @param value 值
	 */
	public void put(String cacheName, Object key, Object value) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
		cache.flush();
	}
	/**
	 * 将序列对象移出缓存
	 * @param cacheName 缓存名字
	 * @param key 键子
	 * @return true成功
	 */
	public boolean remove(String cacheName, Serializable key) {
		Cache cache = cacheManager.getCache(cacheName);
		return cache.remove(key);
	}
	/**
	 * 将普通对象移出缓存
	 * @param cacheName 缓存名字
	 * @param key 键子
	 * @return true成功
	 */
	public boolean remove(String cacheName, Object key) {
		Cache cache = cacheManager.getCache(cacheName);
		return cache.remove(key);
	}
	/**
	 * 从缓存中取出序列数据
	 * @param cacheName 缓存名字
	 * @param key 键子
	 * @return 序列数据
	 * @throws CacheException
	 */
	public Serializable get(String cacheName, Serializable key)
			throws CacheException {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = cache.get(key);
		if (element == null){
			return null;
		}
		return element.getValue();
	}
	/**
	 * 从缓存中取出普通对象数据
	 * @param cacheName 缓存名字
	 * @param key 键子
	 * @return 普通对象数据
	 * @throws CacheException
	 */
	public Serializable get(String cacheName, Object key) throws CacheException {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = cache.get(key);
		if (element == null){
			return null;
		}
		return element.getValue();
	}  
	public void removeAllCache(){
		cacheManager.removalAll();
    }
	public void removeCache(String cacheName){
		cacheManager.removeCache(cacheName);
	}
	public Cache getCache(String cacheName) {
		return cacheManager.getCache(cacheName);
	}	
	public int getSize(String cacheName) throws CacheException {
		Cache cache = cacheManager.getCache(cacheName);
		return cache.getSize();
	}
	public List getKeys(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		return cache.getKeys();
	}
	/**
	 * 关闭缓存管理器
	 */
	public void shutdown() {
		cacheManager.shutdown();
	}
	/**
	 * 获取缓存管理器
	 * @return
	 */
	public CacheManager getManager() {
		return cacheManager;
	}
}
