package com.bovine.taotao.admin.web.config;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import com.bovine.taotao.admin.web.filter.AuthenticationTokenFilter;
import com.bovine.taotao.admin.web.security.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson2.JSON;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;

import lombok.extern.slf4j.Slf4j;

/**
 * 后台管理系统 webMvc配置
 * @author eden
 * @date 2023/9/16 9:43:43
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AdminWebMvcConfig implements WebMvcConfigurer, AsyncConfigurer {

	@Autowired
	private AuthenticationTokenFilter authenticationTokenFilter;

	@Autowired
	private DaoAuthenticationProvider daoAuthenticationProvider;

	@Autowired
	private LockedAuthenticationFailureHandler lockedAuthenticationFailureHandler;

	@Autowired
	private RefreshAuthenticationSuccessHandler refreshAuthenticationSuccessHandler;

	@Autowired
	private MultipleWebAuthenticationDetailsSource multipleWebAuthenticationDetailsSource;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.formLogin(login -> login.loginProcessingUrl("/sys/login").authenticationDetailsSource(this.multipleWebAuthenticationDetailsSource).successHandler(this.refreshAuthenticationSuccessHandler).failureHandler(this.lockedAuthenticationFailureHandler))
				.authorizeHttpRequests(request -> request.requestMatchers("/sys/captcha.jpg").permitAll().requestMatchers("/test/**").permitAll())
				.authorizeHttpRequests(request -> request.requestMatchers("/dynamic/captcha/**").permitAll())
				.authorizeHttpRequests(request -> request.anyRequest().authenticated())
				.authenticationProvider(this.daoAuthenticationProvider)
				.addFilterBefore(this.authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(handler -> handler.accessDeniedHandler((request, response, accessDeniedException) -> {
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
					response.getWriter().print(R.error(S.SYSTEM_UNAUTHORIZED));
				}).authenticationEntryPoint((request, response, authException) -> {
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().print(R.error(S.UNAUTHORIZED));
				})).build();
	}

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		int coreNum = (int) (Runtime.getRuntime().availableProcessors() / 0.8);
		executor.setCorePoolSize(coreNum);
		executor.setMaxPoolSize(coreNum * 2);
		executor.setQueueCapacity(512);
		executor.setAwaitTerminationSeconds(30);
		executor.setThreadNamePrefix("smart-sys-pool");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (t, m, args) -> log.error("线程池异常捕获 method = " + m.getName() + "args = " + JSON.toJSONString(args), t);
	}

}
