package com.comvee.cdms.user.tool;

import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Config;
import com.comvee.cdms.common.cfg.HxErrorCodeConstant;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.MD5Util;
import com.comvee.cdms.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerificationTool {

    private static final Logger logger = LoggerFactory.getLogger(VerificationTool.class);

    public static JSONObject verificationDoctorToken(String token, String appFlag){
        if(StringUtils.isBlank(token)){
            throw new BusinessException(HxErrorCodeConstant.TOKEN_FAILL_CODE1, "登录令牌验证失败,参数为空无法验证");
        }

        /*CheckTokenForHxDTO dto = new CheckTokenForHxDTO();
        dto.setTimestamp(System.currentTimeMillis());
        return HxPublicAPI.checkDoctorToken(dto);*/
        //TODO Test linyd
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("workNo","123");
        jsonObject.put("uruserId","hx654321");
        return jsonObject;
    }

    public static JSONObject verificationUserToken(String token, String appFlag){
        if(StringUtils.isBlank(token)){
            throw new BusinessException(HxErrorCodeConstant.TOKEN_FAILL_CODE1, "登录令牌验证失败,参数为空无法验证");
        }

        /*CheckTokenForHxDTO dto = new CheckTokenForHxDTO();
        dto.setTimestamp(System.currentTimeMillis());
        return HxPublicAPI.checkUserToken(dto);*/
        //TODO Test linyd
        JSONObject jsonObject = new JSONObject();
        //170709143135243 123123123
        jsonObject.put("cardNo","170709143135243");
        jsonObject.put("uruserId","hx123456");
        return jsonObject;
    }

    public static <T>void verificationDigest(String digest,T dto){
        if(StringUtils.isBlank(digest)||dto==null){
            throw new BusinessException(HxErrorCodeConstant.ORIGIN_FAILL_CODE1, "来源验证失败,参数为空无法验证");
        }
        String originDigest = getMD5Digest(dto);
        if(!digest.equals(originDigest)){
            throw new BusinessException(HxErrorCodeConstant.ORIGIN_FAILL_CODE2, "来源验证失败,验证结果不匹配");
        }
    }

    public static <T>String getMD5Digest(T dto){
        Class clazz = dto.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<String> source = new ArrayList<>(fields.length);
        for(Field field:fields){
            source.add(field.getName());
        }
        StringBuilder sb = new StringBuilder();
        source = listSortFirstLatter(source);
        for(String fieldName : source){
            if("digest".equals(fieldName)){
                continue;
            }
            sb.append(fieldName+"=");
            Object value = getFieldValueByName(dto,fieldName);
            sb.append(value+"&");
        }
        String digestKey = Config.getValueByKey("digestKey");
        if(StringUtils.isBlank(digestKey)){
            digestKey = "comvee";
        }
        sb.append(digestKey);
        String originDigest = sb.toString();
        originDigest = MD5Util.md5(originDigest).toLowerCase();
        return originDigest;
    }

    private static Object getFieldValueByName(Object o, String fieldName) {
        try {
            fieldName = upperFirstLatter(fieldName);
            String getter = "get" + fieldName;
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static List<String> listSortFirstLatter(List<String> source){
        //String.CASE_INSENSITIVE_ORDER A在 a 前面
        Collections.sort(source, String.CASE_INSENSITIVE_ORDER);
        return source;
    }

    private static String upperFirstLatter(String letter){
        return letter.substring(0, 1).toUpperCase()+letter.substring(1);
    }
}
