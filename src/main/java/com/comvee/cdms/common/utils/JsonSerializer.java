package com.comvee.cdms.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * JSON序列化工具类
 * 
 * @author antelop
 */
public class JsonSerializer {
    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);
    private static String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";
    /**默认序列化json配置**/
    private static SerializerFeature[] defaultSerializerFeatures = new SerializerFeature[] {
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.DisableCircularReferenceDetect
    };

    /**
     * 将对象序列化成JSON字节数组
     * 
     * @param object
     *            对象
     * 
     * @return JSON字节数组,失败返回空
     */
    public static <T> byte[] serialize(T object) {
        if (object == null) {
            logger.warn("object is null");
            return null;
        }

        try {
            return JSON.toJSONBytes(object);
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

    /**
     * JSON字节数组反序列化为对象
     * 
     * @param data
     *            JSON字节数组
     * 
     * @param clazz
     *            对象类型
     * 
     * @return 对象,失败返回空
     */
    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        if ((data == null) || (clazz == null)) {
            logger.warn("object is null");
            return null;
        }

        try {
            return JSON.parseObject(data, clazz);
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

    /**
     * 将对象序列化成JSON字符串
     * 
     * @param object
     *            对象
     * 
     * @return JSON字符串,失败返回空
     */
    public static <T> String objectToJson(T object) {
        if (object == null) {
            logger.warn("object is null");
            return null;
        }

        try {
            return JSON.toJSONString(object, defaultSerializerFeatures);
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

    /**
     * 将对象序列化成JSON字符串
     * 
     * @param object
     *            对象
     * @param dateFormat
     *            日期格式化格式,默认为yyyy-MM-dd HH:mm:ss
     * 
     * @return JSON字符串,失败返回空
     */
    public static <T> String objectToJson(T object, String dateFormat) {
        if (object == null) {
            logger.warn("object is null");
            return null;
        }

        try {
            if (!StringUtils.isBlank(dateFormat)) {
                dateFormat = defaultDateFormat;
            }

            SerializeConfig mapping = new SerializeConfig();
            mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
            mapping.put(java.sql.Date.class, new SimpleDateFormatSerializer(dateFormat));
            mapping.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
            mapping.put(java.sql.Time.class, new SimpleDateFormatSerializer(dateFormat));

            return JSON.toJSONString(object, mapping, defaultSerializerFeatures);
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

    /**
     * JSON字符串反序列化为对象
     * 
     * @param data
     *            JSON字符串
     * @param clazz
     *            对象类型
     * 
     * @return 对象,失败返回空
     */
    public static <T> T jsonToObject(String data, Class<T> clazz) {
        if ((data == null) || (clazz == null)) {
            logger.warn("object is null");
            return null;
        }

        try {
            return JSON.parseObject(data, clazz);
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

    /**
     * JSON字符串转为散列表
     * 
     * @param json
     *            字符串
     * 
     * @return 散列表,失败返回空
     */
    public static Map<String, Object> jsonToMap(String json) {
        if (((json == null) || (json.length() <= 0))) {
            logger.warn("object is null");
            return null;
        }

        try {
            return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }
    
    /**
     * JSON字符串转为散列表
     * 
     * @param json
     *            字符串
     * 
     * @return 散列表,失败返回空
     */
    public static Map<String, String> jsonToStringMap(String json) {
        if (((json == null) || (json.length() <= 0))) {
            logger.warn("object is null");
            return null;
        }
        
        try {
            return JSON.parseObject(json, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

    /**
     * JSON字符串转为有序散列表
     *
     * @param json
     *            字符串
     *
     * @return 散列表,失败返回空
     */
    public static Map<String, String> jsonToStringLinkedMap(String json) {
        if (((json == null) || (json.length() <= 0))) {
            logger.warn("object is null");
            return null;
        }

        try {
            return JSON.parseObject(json, new TypeReference<LinkedHashMap<String, String>>() {});
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

    /**
     * JSON字符串反序列化为列表数据
     * 
     * @param data
     *            JSON字符串
     * @param clazz
     *            列表数据类型
     * 
     * @return 列表数据,失败返回空
     */
    public static <T> List<T> jsonToList(String data, Class<T> clazz) {
        if ((data == null) || (clazz == null)) {
            logger.warn("object is null");
            return null;
        }

        try {
            return JSON.parseArray(data, clazz);
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

    /**
     * JSON字符串反序列化为列表数据
     * 
     * @param data
     *            JSON字符串
     * @param clazz
     *            列表数据类型
     * 
     * @return 列表数据,失败返回空
     */
    public static List<Map<String, Object>> jsonToMapList(String data) {
        if (data == null) {
            logger.warn("object is null");
            return null;
        }

        try {
            return JSON.parseObject(data, new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
    }

    /**private utils**/
    /**
     * 将lsit转换成List<JSONObject>
     * @param models
     * @return
     */
    public static List<JSONObject> convertListJsobObj(List models){
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>(models.size());
        for(Object object : models){
            String str = JSON.toJSONString(object);
            JSONObject jsonObject = JSON.parseObject(str);
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    /**
     * 将list结果转为Map,方便匹配拼接
     * @param models 转换list
     * @param key 匹配值
     * @param keyValue 拼接值
     * @return
     */
    public static Map<String,Object> convertListToMap(List<JSONObject> models, String key, String keyValue) {
        Map<String,Object> recMap = new HashMap<String,Object>(16);
        for(JSONObject jsonObject : models){
            recMap.put(jsonObject.getString(key),jsonObject.get(keyValue));
        }
        return recMap;
    }
}
