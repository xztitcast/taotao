package com.bovine.taotao.common.core.serialize;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 密钥序列化器
 * @author eden
 * @date 2023年2月22日 下午12:02:15
 */
public class SecretkeySerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(StringUtils.isBlank(value)) {
			gen.writeString(value);
		}else {
			String right = StringUtils.right(value, 6);
            gen.writeString(StringUtils.leftPad(right, StringUtils.length(value), "*"));
		}
	}

}
