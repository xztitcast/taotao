package com.bovine.taotao.common.core.serialize;

import org.apache.commons.lang.StringUtils;

import com.bovine.taotao.common.core.utils.AESBUtil;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * ID反序列化
 * @author eden
 * @param <T>
 * @date 2023年4月6日 下午6:44:49
 */
public abstract class IdKeyDeserializer<T> extends JsonDeserializer<T> {

	/**
	 * 反序列化
	 * @param text
	 * @return
	 */
	protected String deserialize(String text) {
		if(StringUtils.isBlank(text)) {
			return null;
		}
		return AESBUtil.decrypt(text);
	}

}
