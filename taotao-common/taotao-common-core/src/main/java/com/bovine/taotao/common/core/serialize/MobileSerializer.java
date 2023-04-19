package com.bovine.taotao.common.core.serialize;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 手机号序列化
 * @author eden
 * @date 2023年2月19日 下午3:40:45
 */
public class MobileSerializer extends JsonSerializer<String> {

	/*
	 * 前三后四
	 */
	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(StringUtils.isNotBlank(value)) {
			gen.writeString(value.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2"));
		}else {
			gen.writeString(value);
		}
	}

}
