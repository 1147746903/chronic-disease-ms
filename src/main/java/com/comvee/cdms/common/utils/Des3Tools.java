package com.comvee.cdms.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
/**
 * 
 * @author 李左河
 *
 */
public class Des3Tools {
	public Des3Tools() {
	}

	public static byte[] des3EncodeECB(byte[] abyte0, byte[] abyte1)
			throws Exception {
		javax.crypto.SecretKey secretkey = null;
		DESedeKeySpec desedekeyspec = new DESedeKeySpec(abyte0);
		SecretKeyFactory secretkeyfactory = SecretKeyFactory
				.getInstance("desede");
		secretkey = secretkeyfactory.generateSecret(desedekeyspec);
		Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
		cipher.init(1, secretkey);
		byte[] abyte2 = cipher.doFinal(abyte1);
		return abyte2;
	}

	public static byte[] des3DecodeECB(byte[] abyte0, byte[] abyte1)
			throws Exception {
		javax.crypto.SecretKey secretkey = null;
		DESedeKeySpec desedekeyspec = new DESedeKeySpec(abyte0);
		SecretKeyFactory secretkeyfactory = SecretKeyFactory
				.getInstance("desede");
		secretkey = secretkeyfactory.generateSecret(desedekeyspec);
		Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
		cipher.init(2, secretkey);
		byte[] abyte2 = cipher.doFinal(abyte1);
		return abyte2;
	}

	public static byte[] des3EncodeCBC(byte[] abyte0, byte[] abyte1,
									   byte[] abyte2) throws Exception {
		javax.crypto.SecretKey secretkey = null;
		DESedeKeySpec desedekeyspec = new DESedeKeySpec(abyte0);
		SecretKeyFactory secretkeyfactory = SecretKeyFactory
				.getInstance("desede");
		secretkey = secretkeyfactory.generateSecret(desedekeyspec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ivparameterspec = new IvParameterSpec(abyte1);
		cipher.init(1, secretkey, ivparameterspec);
		byte[] abyte3 = cipher.doFinal(abyte2);
		return abyte3;
	}

	public static byte[] des3DecodeCBC(byte[] abyte0, byte[] abyte1,
									   byte[] abyte2) throws Exception {
		javax.crypto.SecretKey secretkey = null;
		DESedeKeySpec desedekeyspec = new DESedeKeySpec(abyte0);
		SecretKeyFactory secretkeyfactory = SecretKeyFactory
				.getInstance("desede");
		secretkey = secretkeyfactory.generateSecret(desedekeyspec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ivparameterspec = new IvParameterSpec(abyte1);
		cipher.init(2, secretkey, ivparameterspec);
		byte[] abyte3 = cipher.doFinal(abyte2);
		return abyte3;
	}
}
