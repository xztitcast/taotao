package com.bovine.taotao.common.core.utils;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.RandomStringUtils;

import cn.hutool.core.codec.Base58;
import lombok.extern.slf4j.Slf4j;

/**
 * AES对称加密工具
 * 该对称加密工具专门用于前后携带随机数的加解密
 * @author eden
 */
@Slf4j
public class AESBUtil {

	/**
	 * 默认key
	 */
	private static final String DEFAULT_KEY = "@W%s7CuXjK5WD%fL";
	
	/**
	 * 向量(CBC增强)
	 */
	private static final String DEFAULT_IV_STRING = "8GJfpo^&kuU$32cl";
	
	/**
	 * 加密
	 * @param content 需要加密内容
	 * @return
	 */
	public static String encrypt(String content) {
		return encrypt(DEFAULT_KEY, content);
	}
	
	/**
	 * 解密
	 * @param content 需要解密的内容
	 * @return
	 */
	public static String decrypt(String content) {
		return decrypt(DEFAULT_KEY, content);
	}

	/**
	 * 加密
	 * @param key 密钥
	 * @param content 需要加密的内容
	 * @return
	 */
	public static String encrypt(String key, String content) {
		return encrypt(key, content, DEFAULT_IV_STRING);
	}

	/**
	 * 解密
	 * @param key 密钥
	 * @param content 需要加密的内容
	 * @return
	 */
	public static String decrypt(String key, String content) {
		return decrypt(key, content, DEFAULT_IV_STRING);
	}
	
	/**
	 * 加密
	 * @param key 密钥
	 * @param content 需要加密的内容
	 * @param ivStr 向量
	 * @return
	 */
	public static String encrypt(String key, String content, String ivStr) {
		try {
			String left = RandomStringUtils.randomAlphanumeric(6);
			String right = RandomStringUtils.randomAlphanumeric(6);
    		byte[] contentBytes = left.concat(content).concat(right).getBytes(StandardCharsets.UTF_8);
	        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
	        byte[] ivBytes = ivStr.getBytes(StandardCharsets.UTF_8);
	        byte[] encryptedBytes = encrypt(keyBytes, contentBytes, ivBytes);
	        return Base58.encode(encryptedBytes);
		} catch (Exception e) {
			log.error("加密异常!", e);
			return null;
		}
	}

	/**
	 * 解密
	 * @param key 密钥
	 * @param content 需要加密的内容
	 * @param ivStr 向量
	 * @return
	 */
	public static String decrypt(String key, String content, String ivStr) {
		try {
	        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
	        byte[] decryptedBytes = decrypt(keyBytes, Base58.decode(content), ivStr.getBytes(StandardCharsets.UTF_8));
	        String result = new String(decryptedBytes, StandardCharsets.UTF_8);
	        return result.substring(6, result.length()-6);
		} catch (Exception e) {
			log.error("解密异常!", e);
			return null;
		}
	}

	/**
	 * 加密
	 * @param key
	 * @param content
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] key, byte[] content, byte[] iv) throws Exception{
        return cipher(key, content, iv, Cipher.ENCRYPT_MODE);
    }

	/**
	 * 解密
	 * @param key
	 * @param content
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] key, byte[] content, byte[] iv) throws Exception{
        return cipher(key, content, iv, Cipher.DECRYPT_MODE);
    }
    
    private static byte[] cipher(byte[] key, byte[] content, byte[] iv, int mode) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init(mode, secretKeySpec, ivspec);
        return cipher.doFinal(content);
    }

}
