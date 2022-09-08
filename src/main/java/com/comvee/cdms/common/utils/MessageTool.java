package com.comvee.cdms.common.utils;

import com.comvee.cdms.common.cfg.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

/**
 * 
 * @author 李左河
 *
 */
public class MessageTool {

	private static final Logger logger = LoggerFactory.getLogger(MessageTool.class);
	
	private static Properties messageProperties = new Properties();

	public void destory() throws Exception {
		logger.debug("[MessageService]->destory()");
	}

	public static void init(List<String> paramList) {
		File file = null;
		try {
			messageProperties.clear();
			Properties pTemp = new Properties();
			for (int i = 0; i < paramList.size(); i++) {
				Object obj = paramList.get(i);
				String strFileName = Constant.APP_WEB_INFO_PATH+"classes"+Constant.FILE_SEPARATOR+Constant.FILE_SEPARATOR+"config"+Constant.FILE_SEPARATOR+obj;
				logger.info("[initialize:MessageTool]"+strFileName);
				file = new File(strFileName);
				pTemp.load(new FileInputStream(file));
				messageProperties.putAll(pTemp);
			}
		} catch (Exception e) {
			logger.error("消息属性文件加载错误", e);
		}
		logger.debug("[MessageService]->init()");
	}
	public static String getMessage(String messageKey) {
		String s = messageProperties.getProperty(messageKey);
		return ((s == null) ? "" : s);
	}
	public static String format(String message,Object ...arg){
		MessageFormat formatter = new MessageFormat(message);
		String output = formatter.format(arg); 
		return output;
	}

}

