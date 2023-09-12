package com.bovine.taotao.admin.web.config;

import com.bovine.taotao.admin.web.filter.AdministratorFilter;
import com.bovine.taotao.admin.web.filter.XssFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置类
 * @author eden
 * @date 2023/6/19 9:43:43
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE - 1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<AdministratorFilter> administratorFilterRegistration() {
        FilterRegistrationBean<AdministratorFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new AdministratorFilter());
        registration.addUrlPatterns("/*");
        registration.setName("administratorFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

}
