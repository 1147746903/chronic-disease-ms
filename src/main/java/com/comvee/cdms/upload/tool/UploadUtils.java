package com.comvee.cdms.upload.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.cfg.Config;
import com.comvee.cdms.common.exception.BusinessException;
import com.comvee.cdms.common.utils.HttpUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.UUID;

public class UploadUtils {

    private final static Logger log = LoggerFactory.getLogger(UploadUtils.class);

    private static String UPLOAD_HOST;
    static {
        try{
            UPLOAD_HOST = Config.getValueByKey("upload.server.host");
        }catch (Exception e){
            log.error("初始化上传服务器地址失败" ,e);
        }
    }

    public static String uploadFile(byte[] bytes ,String extension){
        String url = UPLOAD_HOST + "/uploadFile";
        File file = new File("temp/" + UUID.randomUUID().toString() + "." + extension);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            IOUtils.write(bytes ,fos);
            String responseString = HttpUtils.uploadFile(url ,"file" ,file);
            return resultHandler(responseString);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败" ,e);
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
            try{
                file.delete();
            }catch (Exception e){}
        }
    }

    public static String uploadFile(InputStream is ,String extension){
        try {
            byte[] bytes = IOUtils.toByteArray(is);
            return uploadFile(bytes ,extension);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败(流异常)" ,e);
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static String resultHandler(String s){
        JSONObject jsonObject = null;
        try{
            jsonObject = JSON.parseObject(s);
        }catch (Exception e){
            throw new RuntimeException("文件上传失败(json格式化异常)");
        }
        if(!"0".equals(jsonObject.getString("code"))){
            throw new BusinessException(jsonObject.getString("code") ,jsonObject.getString("message"));
        }
        return jsonObject.getString("obj");
    }
}
