package com.bovine.taotao.common.core.serialize;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.bovine.taotao.common.core.utils.AESBUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * ID反序列化
 * @author eden
 * @param <T>
 * @date 2023年4月6日 下午6:44:49
 */
public class IdKeyDeserializer<T> extends JsonDeserializer<T> {

	@Override
	@SuppressWarnings("unchecked")
	public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String text = p.getText();
		if(StringUtils.isBlank(text)) {
			return null;
		}
		String decrypt = AESBUtil.decrypt(text);
		return (T)decrypt;
	}

}
