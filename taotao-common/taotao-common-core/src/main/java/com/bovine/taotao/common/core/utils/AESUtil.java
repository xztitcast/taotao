package com.bovine.taotao.common.core.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES对称加密工具
 * @author eden
 * @date 2022年11月17日 下午11:46:52
 */
@Slf4j
public abstract class AESUtil {
	
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
    		byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
	        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
	        byte[] ivBytes = ivStr.getBytes(StandardCharsets.UTF_8);
	        byte[] encryptedBytes = encrypt(keyBytes, contentBytes, ivBytes);
	        Base64.Encoder encoder = Base64.getEncoder();
	        return encoder.encodeToString(encryptedBytes);
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
			Base64.Decoder decoder = Base64.getDecoder();
	        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
	        byte[] decryptedBytes = decrypt(keyBytes, decoder.decode(content), ivStr.getBytes(StandardCharsets.UTF_8));
	        return new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			log.error("解密异常!", e);
			return null;
		}
	}
    
    /**
     * 加密
     * @param contentBytes
     * @param keyBytes
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] key, byte[] content, byte[] iv) throws Exception{
        return cipher(key, content, iv, Cipher.ENCRYPT_MODE);
    }
    
    /**
     * 解密
     * @param contentBytes
     * @param keyBytes
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
    
    public static void main(String[] args) {
    	String encrypt = encrypt("123456");
		System.out.println(encrypt);
	}

}
