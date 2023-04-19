package com.bovine.taotao.common.core.serialize;

import java.io.IOException;

import com.bovine.taotao.common.core.utils.AESBUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * ID序列化
 * @author eden
 * @param <T>
 * @date 2023年4月6日 下午6:44:38
 */
public class IdKeySerializer<T> extends JsonSerializer<T> {

	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(value == null) {
			gen.writeString("");
		}else {
			String encrypt = AESBUtil.encrypt(value.toString());
			gen.writeString(encrypt);
		}
	}

}
