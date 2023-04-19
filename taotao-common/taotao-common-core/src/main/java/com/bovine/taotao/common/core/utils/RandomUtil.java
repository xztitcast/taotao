package com.bovine.taotao.common.core.utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 字符串工具类
 * @author eden
 * @date 2023年2月22日 下午2:09:13
 */
public class RandomUtil {

	private static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$&@?<>~!%#";
	
	/**
	 * 随机生成字符串
	 * @param count
	 * @return
	 */
	public static String random(int count) {
		return RandomStringUtils.random(count, SYMBOLS);
	}
}
