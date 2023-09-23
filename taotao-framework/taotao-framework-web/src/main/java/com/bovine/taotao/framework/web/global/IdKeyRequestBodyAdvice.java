package com.bovine.taotao.framework.web.global;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.bovine.taotao.common.core.ModelAndView;
import com.bovine.taotao.common.core.injecter.handler.IdTypeHandler;
import com.bovine.taotao.common.core.injecter.handler.IntegerIdTypeHandler;
import com.bovine.taotao.common.core.injecter.handler.LongIdTypeHandler;
import com.bovine.taotao.common.core.injecter.handler.StringIdTypeHandler;
import com.bovine.taotao.common.core.utils.AESBUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * ModelAndView ID主键反序列化器
 * @author eden
 * @date 2023/9/23 16:47:47
 */
@RestControllerAdvice
public class IdKeyRequestBodyAdvice implements RequestBodyAdvice, Ordered {

    private static final String PROPERTY_NAME = "id";

    private static final Map<String, IdTypeHandler<?>> TYPE_HANDLER = Map.of(
            "java.lang.String", new StringIdTypeHandler(),
            "java.lang.Long", new LongIdTypeHandler(),
            "java.lang.Integer", new IntegerIdTypeHandler()
    );

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getParameterAnnotation(RequestBody.class) != null && ModelAndView.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        JSONObject object = JSON.parseObject(inputMessage.getBody());
        String value = object.getString(PROPERTY_NAME);
        String decrypt = AESBUtil.decrypt(value);
        if(StringUtils.isBlank(decrypt)) {
            return inputMessage;
        }
        Type superclass = parameter.getParameterType().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType)superclass;
        Type argument = parameterizedType.getActualTypeArguments()[0];
        IdTypeHandler<?> handler = TYPE_HANDLER.get(argument.getTypeName());
        Object transform = handler.transform(decrypt);
        object.put(PROPERTY_NAME, transform);
        return new IdKeyDecryptHttpInputMessage(inputMessage.getHeaders(), IOUtils.toInputStream(object.toJSONString(), StandardCharsets.UTF_8));
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private static class IdKeyDecryptHttpInputMessage implements HttpInputMessage {

        private HttpHeaders headers;

        @Nullable
        private InputStream body;

        public IdKeyDecryptHttpInputMessage(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public InputStream getBody() throws IOException {
            return this.body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.headers;
        }
    }
}
