package com.bovine.taotao.admin.web.config;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

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
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.WebFilter;
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
public class AdminWebMvcConfig implements WebMvcConfigurer, AsyncConfigurer {
	
	private static PathMatcher pathMatcher = new AntPathMatcher();
	
	@Autowired
	private DeviceConfig deviceConfig;
	
	private DeviceProcessor deviceProcessor;
	
	/*@Bean
	public RequestInterceptor requestInterceptor(){
		return (request) -> {
			SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
			request.header(Sys.USER_ID, sysUser.getId()+"");
			request.header(Sys.USER_NAME, sysUser.getUsername());
		};
	}*/

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
	
	@Bean
	public WebFilter ignoreFilter() {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			String uri = request.getURI().getRawPath();
			if(!isMatch(deviceConfig.getUrls(), uri)) {
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
		};
	}
	

	
	/**
	 * 路由匹配
	 * @param patterns 路由匹配符集合
	 * @param path 被匹配的路由
	 * @return 是否匹配成功 
	 */
	protected boolean isMatch(Set<String> patterns, String path) {
		for (String pattern : patterns) {
			if (isMatch(pattern, path)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 路由匹配
	 * @param pattern 路由匹配符 
	 * @param path 被匹配的路由  
	 * @return 是否匹配成功 
	 */
	protected boolean isMatch(String pattern, String path) {
		return pathMatcher.match(pattern, path);
	}
	
	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	private String getAddress(ServerHttpRequest request){
		String address = request.getRemoteAddress().getAddress().getCanonicalHostName();
		if(address == null || address.trim().length() == 0 || "unknown".equalsIgnoreCase(address)) {
			HttpHeaders headers = request.getHeaders();
			address = headers.getFirst("x-forwarded-for");
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
		}
		return address;
	}

}
