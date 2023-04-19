package com.bovine.taotao.common.core.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * Integer 类型ID反序列化
 * @author eden
 * @date 2023年4月7日 下午8:11:24
 */
public class IntegerIdKeyDeserializer extends IdKeyDeserializer<Integer>{

	@Override
	public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		return super.deserialize(p, ctxt);
	}

}
