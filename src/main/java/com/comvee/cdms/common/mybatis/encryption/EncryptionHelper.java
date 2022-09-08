package com.comvee.cdms.common.mybatis.encryption;

import com.comvee.cdms.common.utils.Des3Tools;
import com.comvee.cdms.common.utils.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncryptionHelper {

    private final static Logger log = LoggerFactory.getLogger(EncryptionHelper.class);

    /**
     * 需要加密处理的表以及属性
     */
    private final static String[] TABLE = {"t_member"};
    private final static String[] PROPERTY = {"idCard" ,"mobilePhone","socialCard"};

    private final static List<Pattern> PATTERN_LIST = new ArrayList<>();

    private final static Map<String ,Boolean> MS_CACHE = new ConcurrentHashMap<>();
    private final static MultiValueMap<String ,String> PROPERTY_CACHE = new LinkedMultiValueMap<>();
    private final static Map<String ,List<ResultMap>> RESULT_MAP_CACHE = new ConcurrentHashMap<>();


    private static final String charsetName = "UTF-8";
    private static final String DES_KEY = "COMV-DES-KEY-202204";
    private static byte[] DES_KEY_BYTES;
    static {
        DES_KEY_BYTES = DES_KEY.getBytes();
        //des key 的长度必须是24
        if(DES_KEY_BYTES.length != 24){
            DES_KEY_BYTES = Arrays.copyOf(DES_KEY_BYTES ,24);
        }

        for(String tableName : TABLE){
            PATTERN_LIST.add(Pattern.compile(tableName + "[' ' | ',' | \\n]"));
        }
    }

    public static String encryptField(String value){
        if(StringUtils.isBlank(value)){
            return value;
        }
        boolean canDecrypt = false;
        try{
            Des3Tools.des3DecodeECB(DES_KEY_BYTES ,Base64.getDecoder().decode(value));
            canDecrypt = true;
        }catch (Exception e){}
        if(canDecrypt){
            return value;
        }
        try {
            return Base64.getEncoder().encodeToString(
                    Des3Tools.des3EncodeECB(DES_KEY_BYTES , value.getBytes(charsetName))
            );
        } catch (Exception e) {
            log.error("数据库字段加密失败! 原始值:{}" ,value ,e);
        }
        return value;
    }

    public static String decryptField(String value){
        if(StringUtils.isBlank(value)){
            return value;
        }
        try {
            byte[] bytes = Des3Tools.des3DecodeECB(DES_KEY_BYTES
                    ,Base64.getDecoder().decode(value));
            return new String(bytes ,charsetName);
        } catch (Exception e) {
            log.error("数据库字段解密失败! 原始值:{}" ,value ,e);
        }
        return value;
    }

    public static boolean checkNeedToResolve(MappedStatement ms ,Object parameter ,String sql){
        String msId = ms.getId();
        Boolean msIdCache = MS_CACHE.get(msId);
        if(msIdCache != null){
            return msIdCache;
        }
        synchronized (EncryptionHelper.class){
            msIdCache = MS_CACHE.get(msId);
            if(msIdCache != null){
                return msIdCache;
            }
            String sqlNew = sql.toLowerCase();
            boolean findTable = findNeedResolveTable(sqlNew);
            boolean findResultMap = false;
            boolean findProperty = false;
            List<String> propertyList = null;
            if(findTable){
                propertyList = findNeedResolveProperty(parameter);
                if(propertyList != null && !propertyList.isEmpty()){
                    PROPERTY_CACHE.addAll(msId ,propertyList);
                    findProperty = true;
                }

                findResultMap = findNeedResolveResultMap(ms);
            }
            boolean result = findTable
                    && (findProperty || findResultMap);
            MS_CACHE.put(msId ,result);
            if(log.isDebugEnabled()){
                log.debug("数据库字段加密拦截器是否执行 -> msId:{} ,是否包含表名:{} ,请求参数是否包含:{} ,返回对象是否包含:{} ,最终结果:{}"
                        ,msId ,findTable ,findProperty ,findResultMap ,result);
            }
            return result;
        }
    }

    public static MappedStatement newMappedStatement(MappedStatement ms) {
        List<ResultMap> cacheResultMaps = RESULT_MAP_CACHE.get(ms.getId());
        if(cacheResultMaps == null){
            return ms;
        }
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), ms.getSqlSource(), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(cacheResultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    public static void resolveParameter(String msId ,Object parameter){
        List<String> propertyList = PROPERTY_CACHE.get(msId);
        if(propertyList == null || propertyList.isEmpty()){
            return;
        }
        if(parameter instanceof Map){
            resolveMapParameter(propertyList , (Map<String, Object>) parameter);
        }else{
            resolveObjectParameter(propertyList ,parameter);
        }
    }

    private static boolean findNeedResolveTable(String sql){
        Matcher matcher;
        for(Pattern pattern : PATTERN_LIST){
            matcher = pattern.matcher(sql);
            if(matcher.find()){
                return true;
            }
        }
        return false;
    }

    private static List<String> findNeedResolveProperty(Object parameter){
        if(parameter == null){
            return null;
        }
        List<String> propertyList = new ArrayList<>();
        if(parameter instanceof Map){
            Map<String ,Object> parameterMap = (Map<String, Object>) parameter;
            for(String property : PROPERTY){
                if(parameterMap.containsKey(property)){
                    propertyList.add(property);
                }
            }
        }else{
            Class clz = parameter.getClass();
            PropertyDescriptor propertyDescriptor = null;
            for(String property : PROPERTY){
                try {
                    propertyDescriptor = new PropertyDescriptor(property ,clz);} catch (IntrospectionException e) {
                }
                if(propertyDescriptor != null){
                    propertyList.add(property);
                }
            }
        }
        return propertyList;
    }

    private static boolean findNeedResolveResultMap(MappedStatement ms){
        boolean find = false;
        List<ResultMap> resultMaps = ms.getResultMaps();
        List<ResultMap> newResultMaps = new ArrayList<>();
        List<ResultMapping> resultMappingList = null;
        for(ResultMap resultMap : resultMaps){
            resultMappingList = new ArrayList<>();
            for(ResultMapping resultMapping : resultMap.getResultMappings()){
                if(checkNeedResolveProperty(resultMapping.getProperty())){
                    resultMappingList.add(new ResultMapping.Builder(ms.getConfiguration() ,resultMapping.getProperty()
                            ,resultMapping.getColumn() ,new EncryptionTypeHandler()).build());
                    find = true;
                }else{
                    resultMappingList.add(resultMapping);
                }
            }
            resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), resultMap.getType(), resultMappingList).build();
            newResultMaps.add(resultMap);
        }
        if(find){
            RESULT_MAP_CACHE.put(ms.getId() ,newResultMaps);
        }
        return find;
    }

    private static boolean checkNeedResolveProperty(String s){
        for(String property : PROPERTY){
            if(property.equals(s)){
                return true;
            }
        }
        return false;
    }

    private static void resolveMapParameter(List<String> propertyList ,Map<String ,Object> parameterMap){
        Object parameterValue = null;
        for(String property : propertyList){
            parameterValue = parameterMap.get(property);
            if(parameterValue == null || "".equals(parameterValue.toString())){
                continue;
            }
            parameterMap.put(property ,encryptField(parameterValue.toString()));
        }
    }

    private static void resolveObjectParameter(List<String> propertyList ,Object parameter){
        Class clz = parameter.getClass();
        PropertyDescriptor propertyDescriptor = null;
        Object parameterValue = null;
        for(String property : propertyList){
            try {
                propertyDescriptor = new PropertyDescriptor(property ,clz);
                parameterValue = propertyDescriptor.getReadMethod().invoke(parameter);
                if(parameterValue == null || "".equals(parameterValue.toString())){
                    continue;
                }
                propertyDescriptor.getWriteMethod().invoke(parameter ,encryptField(parameterValue.toString()));
            } catch (Exception e) {
            }
        }
    }
}
