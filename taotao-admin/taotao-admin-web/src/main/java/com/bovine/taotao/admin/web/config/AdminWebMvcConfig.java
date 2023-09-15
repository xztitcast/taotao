package com.bovine.taotao.admin.web.config;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import com.bovine.taotao.admin.web.filter.AuthenticationTokenFilter;
import com.bovine.taotao.admin.web.security.*;
import com.bovine.taotao.common.core.kit.PathMatcherHelper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.bovine.taotao.admin.web.config.DeviceConfig.DeviceProperty;
import com.bovine.taotao.admin.web.support.device.DeviceProcessor;
import com.bovine.taotao.common.core.Constant;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.formLogin(login -> login.loginProcessingUrl("/sys/login").authenticationDetailsSource(this.multipleWebAuthenticationDetailsSource).successHandler(this.refreshAuthenticationSuccessHandler).failureHandler(this.lockedAuthenticationFailureHandler))
				.authorizeHttpRequests(request -> request.requestMatchers("/sys/captcha.jpg").permitAll().requestMatchers("/test/**").permitAll())
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

	//@Component
	public static class IgnoreFilter implements WebFilter {

		private static PathMatcher pathMatcher = new AntPathMatcher();

		//@Autowired
		private DeviceConfig deviceConfig;

		//@Autowired
		private DeviceProcessor deviceProcessor;

		@Override
		public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
			ServerHttpRequest request = exchange.getRequest();
			String uri = request.getURI().getRawPath();
			if(!PathMatcherHelper.isMatch(deviceConfig.getUrls(), uri)) {
				HttpHeaders headers = request.getHeaders();
				String tisid= headers.getFirst(Constant.TISSID);
				String ssid = headers.getFirst(Constant.TOKEN_SSID);
				DeviceProperty deviceProperty = deviceConfig.getProperty().get(tisid);
				boolean process = deviceProcessor.process(ssid, deviceProperty);
				if(!process) {
					log.warn("用户使用非科学方式访问请求地址: {}, IP地址: {}", uri, getAddress(request));
					ServerHttpResponse response = exchange.getResponse();
					response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
					String message = JSONObject.toJSONString(R.error(S.SYSTEM_UNAUTHORIZED));
					return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));
				}
			}
			return chain.filter(exchange);
		}

		/**
		 * 获取IP地址
		 * @param request
		 * @return
		 */
		private String getAddress(ServerHttpRequest request){
			HttpHeaders headers = request.getHeaders();
			String address = headers.getFirst("x-forwarded-for");
			if (address != null && address.length() != 0 && !"unknown".equalsIgnoreCase(address)) {
				// 多次反向代理后会有多个address值，第一个address才是真实address
				if (address.indexOf(",") != -1) {
					address = address.split(",")[0];
				}
			}
			if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
				address = headers.getFirst("Proxy-Client-address");
			}
			if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
				address = headers.getFirst("WL-Proxy-Client-address");
			}
			if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
				address = headers.getFirst("HTTP_CLIENT_address");
			}
			if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
				address = headers.getFirst("HTTP_X_FORWARDED_FOR");
			}
			if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
				address = headers.getFirst("X-Real-address");
			}
			if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
				address = request.getRemoteAddress().getAddress().getHostAddress();
			}
			return address;
		}
	}

}
