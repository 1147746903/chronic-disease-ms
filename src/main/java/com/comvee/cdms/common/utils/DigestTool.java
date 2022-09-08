package com.comvee.cdms.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
/**
 * 完成 md5和sha-1的加密
 * @author Administrator
 *
 */
public class DigestTool {
	public static byte[] md5(String data){
		return DigestUtils.md5(data);
	}
	public static byte[] md5(byte[] data){
		return DigestUtils.md5(data);
	} 
	public static String md5Hex(byte[] data){
		return DigestUtils.md5Hex(data);
	} 
	public static String md5Hex(String data){
		return DigestUtils.md5Hex(data);
	}
	public static byte[] sha(String data){
		return DigestUtils.sha1(data);
	}
	public static byte[] sha(byte[] data){
		return DigestUtils.sha1(data);
	}
	public static String shaHex(String data){
		return DigestUtils.sha1Hex(data);
	}
	public static String getBase64(byte[] b) {
		//		String str = new BASE64Encoder().encode(b);
		String str = Base64.encodeBase64String(b);
		str = str.replaceAll("\r|\n", "");
		return str;
	}
	public static byte[] getFromBase64(String s) {
		byte[] b = null;
//		BASE64Decoder decoder = new BASE64Decoder();
		try {
//			b = decoder.decodeBuffer(s);
			b = Base64.decodeBase64(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	public static String bytesToHex(byte[] data) {
		if (data == null) {
			return "";
		}
		int len = data.length;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if ((data[i] & 0xFF) < 16){
				str.append("0").append(Integer.toHexString(data[i] & 0xFF));
			}
			else{
				str.append(Integer.toHexString(data[i] & 0xFF));
			}

		}
		return str.toString();
	}
	public static byte[] hexToBytes(String hexString) {
		if (hexString == null) {
			return new byte[0];
		}
		if (hexString.length() == 0) {
			return new byte[0];
		}
		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {
			// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

	public static byte[] encrypt(String secretKey,byte[] data) throws Exception {
		byte[] ret = new byte[0];
		int int16 = 16;
		if (secretKey.length() == int16) {
			Cipher aesECB = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
			aesECB.init(Cipher.ENCRYPT_MODE, key);
			ret = aesECB.doFinal(data);
		}
		return ret;
	}
	
	public static void main(String[] args) {
	
	}

	public static byte[] decrypt(String secretKey,byte[] data) throws Exception {
		byte[] ret = new byte[0];
		int int16 = 16;
		if (secretKey.length() == int16) {
			Cipher aesECB = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
			aesECB.init(Cipher.DECRYPT_MODE, key);
			ret = aesECB.doFinal(data);
		}
		return ret;
	}
}
