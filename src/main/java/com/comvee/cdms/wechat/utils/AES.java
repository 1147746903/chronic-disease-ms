package com.comvee.cdms.wechat.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * @File name:   AES.java   TODO小程序
 * @Create on:   2018年1月24日
 * @Author   :  zqx
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
public class AES {

    private final static Logger log = LoggerFactory.getLogger(AES.class);

	static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * AES解密
     * @param content 密文
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchProviderException
     */
    public byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
        try {
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Key sKeySpec = new SecretKeySpec(keyByte, "AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化
            byte[] result = cipher.doFinal(content);
            return result;
        }  catch (Exception e) {
            log.error("decrypt失败~", e);
        }
        return null;
    }

    public byte[] decrypt2(byte[] content, byte[] keyByte, byte[] ivByte) throws Exception {
        byte[] result = null ;
        Key sKeySpec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters iv = generateIV(ivByte);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
            result = cipher.doFinal(content);
        }  catch (Exception e) {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
            result = cipher.doFinal(content);
        }
        return result;
    }
    //生成iv
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception{
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }
}
