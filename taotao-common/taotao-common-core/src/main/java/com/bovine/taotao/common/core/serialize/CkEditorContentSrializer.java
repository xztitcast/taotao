package com.bovine.taotao.common.core.serialize;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * CKEditor序列化器
 * 由于后台管理的在富文本中上传图片使用了<figure></figure>包装导致小程序无法显示所以在序列化器中直接替换
 * @author eden
 * @date 2023年2月27日 下午7:01:48
 */
public class CkEditorContentSrializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(StringUtils.isNotBlank(value)) {
			gen.writeString(value.replaceAll("figure", "div"));
		}else {
			gen.writeString(value);
		}
	}

}
