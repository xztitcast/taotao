package com.bovine.taotao.setup.web.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bovine.taotao.common.security.resolver.LoginAuthenticationMethodArgumentResolver;

/**
 * 登录信息解析获取
 */
@Configuration
public class SetupWebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginAuthenticationMethodArgumentResolver());
	}
	
}