package com.bovine.taotao.common.core.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Integer类型ID序列化
 * @author eden
 * @date 2023年4月7日 下午8:05:12
 */
public class IntegerIdKeySerializer extends IdKeySerializer<Integer> {

	@Override
	public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		super.serialize(value, gen, serializers);
	}
	
}
