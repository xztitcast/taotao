package com.bovine.taotao.serve.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson2.JSONObject;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;
import com.bovine.taotao.common.security.JwtTokenManager;
import com.bovine.taotao.serve.gateway.properties.TaotaoGatewayProperties;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 工厂
 * @author eden
 * @date 2023年2月24日 上午9:55:26
 */
@Slf4j
@Component
public class AuthenticationTokenFilter extends AbstractPathMatcherGatewayFilter implements GlobalFilter, Ordered {
	
	private TaotaoGatewayProperties properties;
	
	public AuthenticationTokenFilter(TaotaoGatewayProperties properties) {
		this.properties = properties;
	}

	@Override
	public int getOrder() {
		return 2;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		if(!isMatch(this.properties.getIgnore(), request.getURI().getRawPath())) {
			try {
	        	Claims claims = JwtTokenManager.parse(request.getHeaders().getFirst("Authorization"));
	        	request.mutate().header(JwtTokenManager.SUBJECT_HEADER, claims.getSubject()).build();
			} catch (Exception e) {
	        	log.error("JWT解析token异常", e);
	        	ServerHttpResponse response = exchange.getResponse();
	        	response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
	        	String message = JSONObject.toJSONString(R.error(S.UNAUTHORIZED));
	        	return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));
	        }
		}
		return chain.filter(exchange);
	}
}
