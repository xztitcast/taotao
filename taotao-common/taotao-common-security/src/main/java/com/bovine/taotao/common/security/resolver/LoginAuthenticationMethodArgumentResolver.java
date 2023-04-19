package com.bovine.taotao.common.security.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson2.JSON;
import com.bovine.taotao.common.core.S;
import com.bovine.taotao.common.core.exception.custom.AuthenticationException;
import com.bovine.taotao.common.security.JwtTokenManager;
import com.bovine.taotao.common.security.annotation.Subject;
import com.bovine.taotao.common.security.entity.Entity;
import com.bovine.taotao.common.security.entity.Principal;

/**
 * 登录验证参数解析器
 * 1.通过网关认证一定能获取到用户ID参数解析成功
 * 2.如果获取用户ID为空，则可以判断用户没有登录或者绕过网关进行攻击
 * @author eden
 * @date 2022年10月17日 下午10:46:51
 */
public class LoginAuthenticationMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Subject.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String header = webRequest.getHeader(JwtTokenManager.SUBJECT_HEADER);
		if(header == null || header.trim().length() == 0) {
			throw new AuthenticationException(S.UNAUTHORIZED.getValue(), S.UNAUTHORIZED.getMessage());
		}
		Principal p = JSON.parseObject(header, Principal.class);
		Subject subject = parameter.getParameterAnnotation(Subject.class);
		Entity value = subject.value();
		if(value == Entity.ID) {
			return p.getUserId();
		}
		if(value == Entity.NAME) {
			return p.getUsername();
		}
		if(value == Entity.OPPENID) {
			return p.getOpenid();
		}
		return p;
	}

}
