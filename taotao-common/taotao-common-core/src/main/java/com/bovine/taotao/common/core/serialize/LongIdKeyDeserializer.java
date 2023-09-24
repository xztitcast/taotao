package com.bovine.taotao.common.core.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

/***
 * Long 类型ID反序列化
 * @author eden
 * @date 2023年4月7日 下午8:10:32
 */
public class LongIdKeyDeserializer extends IdKeyDeserializer<Long> {

	@Override
	public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String value = super.deserialize(p.getText());
		return Long.valueOf(value);
	}
	
}
