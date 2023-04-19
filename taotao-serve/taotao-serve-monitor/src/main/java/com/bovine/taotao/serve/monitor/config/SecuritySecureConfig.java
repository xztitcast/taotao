package com.bovine.taotao.serve.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

/**
 * Spring boot admin 监控security配置(如不配置security只需要注意第三点即可)
 * Spring boot admin ui 需要注意几点:
 * 1. 当页面嵌入到Vue管理后台中需要解决iframe跨域问题frameOptions().sameOrigin()
 * 2. 当页面嵌入不在同域下会报 "Blocked autofocusing on a <input> element in a cross-origin subframe."异常
 *    解决方案: (1)不配置登录验证 (2)使用Nginx反向代理配置讲Spring boot admin ui 配置在同域下
 * 3. 当嵌入页面关闭时报 Connection to server failed 警告需要更改源码
 * @author eden
 * @date 2022年10月23 22:17:44
 */
@EnableWebSecurity
public class SecuritySecureConfig {
	
	private final AdminServerProperties adminServer;

    public SecuritySecureConfig(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }

	@Bean
	@Primary
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.path("/"));

        return http.authorizeRequests((authorizeRequests) -> authorizeRequests.antMatchers(this.adminServer.path("/assets/**")).permitAll()
                        .antMatchers(this.adminServer.path("/actuator/**")).permitAll()
                        .antMatchers(this.adminServer.path("/instances")).permitAll()
                        .antMatchers(this.adminServer.path("/css/**")).permitAll()
                        .antMatchers(this.adminServer.path("/js/**")).permitAll()
                        .antMatchers(this.adminServer.path("/image/**")).permitAll()
                        .antMatchers(this.adminServer.path("/login")).permitAll().anyRequest().authenticated()
        ).formLogin(
                (formLogin) -> formLogin.loginPage(this.adminServer.path("/login")).successHandler(successHandler).and()
        ).logout((logout) -> logout.logoutUrl(this.adminServer.path("/logout"))).httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable).headers().frameOptions().sameOrigin().disable().build();
	}
}
