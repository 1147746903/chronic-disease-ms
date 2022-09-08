package com.comvee.cdms.common.cfg;

import com.comvee.cdms.common.utils.ResourcesUtils;
import com.comvee.cdms.common.utils.StackTraceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 
 * @author 李左河
 *
 */
public class Config {

	private final static Logger logger = LoggerFactory.getLogger(Config.class);

	private final static String CONFIG_NAME = "config.properties";

	private final static Properties properties = new Properties();
	static {
		try {
			properties.load(ResourcesUtils.loadStream(CONFIG_NAME));
		} catch (Exception e) {
			logger.error("找不到配置文件config.properties,请检查配置~");
		}
	}

	public static String getValueByKey(String key) {
	    String value = properties.getProperty(key);
        if(value == null){
			logger.warn("找不到对应的配置文件项，配置文件名:{},配置项:{},调用信息:{}" ,CONFIG_NAME ,key , StackTraceUtils.lastCall());
		}
        return value;
	}

}
