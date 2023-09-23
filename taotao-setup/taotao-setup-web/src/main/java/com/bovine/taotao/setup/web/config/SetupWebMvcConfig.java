package com.bovine.taotao.setup.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bovine.taotao.common.security.resolver.LoginAuthenticationMethodArgumentResolver;

/**
 * 设置模块MVC配置
 */
@Configuration
public class SetupWebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private LoginAuthenticationMethodArgumentResolver loginAuthenticationMethodArgumentResolver;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginAuthenticationMethodArgumentResolver);
	}
	
}