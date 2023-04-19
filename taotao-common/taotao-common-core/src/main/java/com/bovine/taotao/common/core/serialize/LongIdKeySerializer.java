package com.bovine.taotao.common.core.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Long类型ID序列化
 * @author eden
 * @date 2023年4月7日 下午8:02:27
 */
public class LongIdKeySerializer extends IdKeySerializer<Long> {

	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		super.serialize(value, gen, serializers);
	}
	
}
