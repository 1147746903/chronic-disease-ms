package com.comvee.cdms.common.utils;

import com.comvee.cdms.common.exception.BusinessException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author 李左河
 *
 */
public class CodecTools {
	private static final Logger logger = LoggerFactory.getLogger(CodecTools.class);
	
	/**
	 * des3EncodeECB加密key(key长度有限制24位)
	 */
	public static byte[] DES3_ECD_KEY = {0x11,0x22,0x4F,0x58,(byte)0x88,0x10,0x40,0x38,0x28,0x25,0x79,0x51,(byte)0xCB,(byte)0xDD,0x55,0x66,0x77,0x29,0x74,(byte)0x98,0x30,0x40,0x36,(byte)0xE2};
	/**
	 * 3eds解密
	 * 
	 * @param seuid
	 * @return
	 * @throws BusinessException
	 */
	public static String decriptDES3(String str) throws BusinessException {
		byte[] decodeStr;
		try {
			decodeStr = Des3Tools.des3DecodeECB(DES3_ECD_KEY, Hex.decodeHex(str.toCharArray()));
			return new String(decodeStr);
		} catch (Exception e) {
			logger.warn("[str:" + str + "]" + e.getMessage());
			throw new BusinessException("999999","系统错误!", e);
		}
	}

	/**
	 * 加密
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public static String encriptDES3(String str) throws BusinessException {
		//加密
		byte[] byteArray = null;
		try {
			byteArray = Des3Tools.des3EncodeECB(DES3_ECD_KEY, str.getBytes());
			return Hex.encodeHexString(byteArray).toLowerCase();
		} catch (Exception e) {
			logger.warn("[str:" + str + "]" + e.getMessage());
			throw new BusinessException("999999","系统错误!", e);
		}
	}
}
