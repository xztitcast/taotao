package com.bovine.taotao.framework.service.config;

import com.bovine.taotao.common.security.resolver.LoginAuthenticationMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 核心模块MVC配置
 * @author eden
 * @date 2023/9/23 22:43:43
 */
@Configuration
public class FrameworkWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginAuthenticationMethodArgumentResolver loginAuthenticationMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginAuthenticationMethodArgumentResolver);
    }
}
