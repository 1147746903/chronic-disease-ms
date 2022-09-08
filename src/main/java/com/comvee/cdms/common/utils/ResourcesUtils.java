package com.comvee.cdms.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ResourcesUtils {

    private final static Logger log = LoggerFactory.getLogger(ResourcesUtils.class);

    private final static String PROPERTY_KEY = "load.resources.type";

    private final static ResourcesType DEFAULT_TYPE = ResourcesType.classpath;

    /**
     * 是否加载外部资源
     */
    private final static ResourcesType TYPE;
    private final static String USER_DIR;
    static {
        USER_DIR = System.getProperty("user.dir");
        String config = System.getProperty(PROPERTY_KEY ,ResourcesType.classpath.name());
        ResourcesType type = ResourcesType.valueOf(config);
        if(type == null){
            type = DEFAULT_TYPE;
        }
        TYPE = type;
        log.info("初始化资源加载方式配置成功，当前资源加载方式为:{}" ,TYPE.name());
    }

    public static InputStream loadStream(String path){
        InputStream is = null;
        switch (TYPE){
            case classpath:
                is = loadClassPathStream(path);
                break;
            case external:
                is = loadExternalStream(path);
                break;
            default:
                is = loadClassPathStream(path);
                break;
        }
        return is;
    }

    private static InputStream loadExternalStream(String path){
        pathHandler(path);
        String absolutePath = USER_DIR + File.separator + path;
        try {
            return new FileInputStream(new File(absolutePath));
        } catch (Exception e) {
            log.warn("加载外部资源文件失败，资源路径:{}" ,absolutePath ,e);
            return null;
        }
    }

    private static InputStream loadClassPathStream(String path){
        pathHandler(path);
        InputStream is = ResourcesUtils.class.getClassLoader().getResourceAsStream(path);;
        if(is == null){
            log.warn("加载classpath资源文件失败,资源路径:{}" ,path);
        }
        return is;
    }

    private static String pathHandler(String path){
        if(path.startsWith("/")){
            path = path.replaceFirst("\\/" ,"");
        }
        return path;
    }

    private enum ResourcesType{

        classpath,
        external;
    }
}
