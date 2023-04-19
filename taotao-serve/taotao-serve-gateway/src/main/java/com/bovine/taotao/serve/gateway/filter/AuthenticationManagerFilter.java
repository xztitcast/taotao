package com.bovine.taotao.serve.gateway.filter;

import java.time.Duration;
import java.time.Instant;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.bovine.taotao.serve.gateway.properties.TaotaoGatewayProperties;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 全局管理过滤器
 * @author eden
 * @date 2023年2月24日 上午9:55:46
 */
@Slf4j
@Component
public class AuthenticationManagerFilter implements GlobalFilter, Ordered {
	
	private static final String START_TIME = "startTime";
	
	private final TaotaoGatewayProperties properties;
	
	public AuthenticationManagerFilter(TaotaoGatewayProperties properties) {
		this.properties = properties;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		exchange.getAttributes().put(START_TIME, Instant.now());
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			if(this.properties.isEnable()) {
				long executeTime = Duration.between(exchange.getAttribute(START_TIME), Instant.now()).toMillis();
                log.info("http请求执行时间[{}]", exchange.getRequest().getURI().getRawPath() + " : " + executeTime + "ms");
			}
		}));
	}

	@Override
	public int getOrder() {
		return -1;
	}

}
