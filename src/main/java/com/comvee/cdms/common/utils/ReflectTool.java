package com.comvee.cdms.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李左河
 *
 */
public class ReflectTool {
	private static final Logger logger = LoggerFactory.getLogger(ReflectTool.class);

	/**
	 * 
	 * @param myBean
	 * @param needFileds
	 *            需要得属性、不能为空、数组里面也不能有空得值
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 *             没有这个属性
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object> getMapFromBean(Object myBean,
			Object[] needFileds) {
		if(myBean == null){
			return null;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>(16);
		Class beanClass = myBean.getClass();
		for (int i = 0; i < needFileds.length; i++) {
			Field field;
			try {
				field = beanClass.getDeclaredField(needFileds[i].toString());
				field.setAccessible(true);
				String key = needFileds[i].toString();
				Object value;
				try {
					value = field.get(myBean);
					if (value == null) {
						value = "";
					}
					resultMap.put(key, value);
				} catch (IllegalArgumentException e) {
					logger.error("CheckUtil getMapFromBean:" + e);
				} catch (IllegalAccessException e) {
					logger.error("CheckUtil getMapFromBean:" + e);
				}
			} catch (SecurityException e) {
				logger.error("CheckUtil getMapFromBean:" + e);
			} catch (NoSuchFieldException e) {
				logger.error("CheckUtil getMapFromBean:" + e);
			}
		}
		return resultMap;
	}
	
	/**
	 * 
	 * 功能说明:   
	 * author：Suyz 
	 * 创建时间：2015-6-4
	 * @param myBean
	 * @param needFileds
	 * @return
	 */
	public static Map<String, String> getStringMapFromBean(Object myBean,
			Object[] needFileds) {
		if(myBean == null){
			return null;
		}
		Map<String, String> resultMap = new HashMap<String, String>(16);
		Class beanClass = myBean.getClass();
		for (int i = 0; i < needFileds.length; i++) {
			Field field;
			try {
				field = beanClass.getDeclaredField(needFileds[i].toString());
				field.setAccessible(true);
				String key = needFileds[i].toString();
				Object value;
				try {
					value = field.get(myBean);
					resultMap.put(key, StringUtils.converParamToString(value));
				} catch (IllegalArgumentException e) {
					logger.error("CheckUtil getMapFromBean:" + e);
				} catch (IllegalAccessException e) {
					logger.error("CheckUtil getMapFromBean:" + e);
				}
			} catch (SecurityException e) {
				logger.error("CheckUtil getMapFromBean:" + e);
			} catch (NoSuchFieldException e) {
				logger.error("CheckUtil getMapFromBean:" + e);
			}
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @param myBean
	 * @param needFileds
	 *            需要得属性、不能为空、数组里面也不能有空得值
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 *             没有这个属性
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<Map<String, Object>> getListFromBean(List list,
			Object[] needFileds) {
		if(list == null){
			return null;
		}
		List list1 = list;
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		for (Object myBean : list1) {
			Map<String, Object> map = getMapFromBean(myBean, needFileds);
			listResult.add(map);
		}
		return listResult;
	}
	
	
	/**
	 * @param myBean
	 * @param needFileds
	 *            需要得属性、不能为空、数组里面也不能有空得值
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 *             没有这个属性
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<Map<String, Object>> getListFromMap(List list,
			Object[] needFileds) {
		List list1 = list;
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		
		
		for (Object obj : list1) {
			Map myBean = (Map) obj;
			Map map = new HashMap(16);
			for (int i = 0; i < needFileds.length; i++) {
				String key = needFileds[i].toString();
				map.put(key, myBean.get(key) ==  null ? "": myBean.get(key));
			}
			listResult.add(map);
		}
		return listResult;
	}
	
	
	/**
	 * @param myBean
	 * @param needFileds
	 *            需要得属性、不能为空、数组里面也不能有空得值
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 *             没有这个属性
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Map getMapFromKey(Map myBean,Object[] needFileds) {
		Map map = new HashMap(16);
		for (int i = 0; i < needFileds.length; i++) {
			String key = needFileds[i].toString();
			map.put(key, myBean.get(key) ==  null ? "": myBean.get(key));
		}
		return map;
	}

	/**
	 * map直接映射到bean
	 * 
	 * @param <T>
	 * @param map
	 * @param returnClass
	 * @param needFileds
	 * @return
	 */
	public static <T> T getBeanFromMap(Map map, Class<T> returnClass) {
		List list = new ArrayList();
		for (Object o : map.keySet()) {
			list.add(o);
		}
		return ReflectTool.getBeanFromMap(map, returnClass, list.toArray());
	}

	/**
	 * map直接映射到bean
	 * 
	 * @param <T>
	 * @param map111
	 * @param returnClass
	 * @param needFileds
	 * @return
	 */
	public static <T> T getBeanFromMap(Map mapFrom, Class<T> returnClass,
			Object[] needFileds) {
		HashMap<String, Method> map = converBean(returnClass);
		Object obj = null;
		try {
			obj = returnClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Method method = null;
		for (int i = 0; i < needFileds.length; i++) {
			method = map.get(needFileds[i]);
			try {
				if (method != null) {
					method.invoke(obj, mapFrom.get(needFileds[i]));
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return (T) obj;
	}

	/**
	 * 通过class信息获取javebean中属性信息
	 * @param returnClass
	 * @return
	 */
	private static HashMap<String, Method> converBean(Class<?> returnClass) {
		Class<?> stopClass = null;
		// 存放class信息
		BeanInfo drbeaninfo = null;
		// 存放属性信息
		PropertyDescriptor[] props;
		HashMap<String, Method> map = new HashMap<String, Method>(16);
		try {
			 // 获取class中得属性方法信息
			drbeaninfo = Introspector.getBeanInfo(returnClass, stopClass); 
			 // 把class中属性放入PropertyDescriptor数组中
			props = drbeaninfo.getPropertyDescriptors();               
			for (int i = 0; i < props.length; i++) {
				// 获取属性所对应的set方法
				Method setMethod = props[i].getWriteMethod();  
				// 判断属性是否有set方法 如果有放入map<属性名，set方法>中
				if (setMethod != null) {                  
					String field = props[i].getName();
					map.put(field, setMethod);
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 将javaBean转换为Map集合
	 * @param returnClass
	 * @return
	 */
	public static Map<String,Object> beanToMap(Object bean)throws Exception { 
		Class type = bean.getClass(); 
		Map<String,Object> returnMap = new HashMap<String,Object>(16);
		BeanInfo beanInfo = Introspector.getBeanInfo(type); 
		
		PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
		for (int i = 0; i< propertyDescriptors.length; i++) { 
		   PropertyDescriptor descriptor = propertyDescriptors[i]; 
		   String propertyName = descriptor.getName(); 
		    if (!"class".equals(propertyName)) {
		       Method readMethod = descriptor.getReadMethod(); 
		       Object result = readMethod.invoke(bean, new Object[0]); 
		       if (result != null) { 
		           returnMap.put(propertyName, result); 
		       } else { 
		           returnMap.put(propertyName, ""); 
		       } 
		    } 
		} 
		return returnMap; 
	} 
	
}
