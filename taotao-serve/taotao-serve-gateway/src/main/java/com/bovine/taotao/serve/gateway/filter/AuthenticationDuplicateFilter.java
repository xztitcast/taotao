package com.bovine.taotao.serve.gateway.filter;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.bovine.taotao.common.core.Constant.RedisKey;
import com.bovine.taotao.common.core.Constant.Sys;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;
import com.bovine.taotao.common.core.Principal;

import reactor.core.publisher.Mono;

/**
 * 防重复请求过滤器
 * 强并发时可能会存在误差
 * @author eden
 * @date 2023年2月25日 下午1:51:51
 */
public class AuthenticationDuplicateFilter implements GlobalFilter, Ordered {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public int getOrder() {
		return 2;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		/*StringBuilder sb = new StringBuilder(RedisKey.GATEWAY_DUPLICATE_STR_KEY);
		ServerHttpRequest request = exchange.getRequest();
		String first = request.getHeaders().getFirst(Sys.TOKEN);
		if(StringUtils.isNotBlank(first)) sb.append(first);
		String subject = request.getHeaders().getFirst(JwtTokenManager.SUBJECT_HEADER);
		Principal principal = JSON.parseObject(subject, Principal.class);
		if(principal != null) {
			sb.append(principal.getUserId());
		}
		String path = request.getURI().getPath();
		String value = this.redisTemplate.opsForValue().get(sb.toString());
		if(StringUtils.isNotBlank(value) && path.equals(value)) {
			ServerHttpResponse response = exchange.getResponse();
        	response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        	String message = JSONObject.toJSONString(R.error(S.REPEATSUBMIT));
        	return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));
		}
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			if(principal != null || StringUtils.isNotBlank(first)) {
				this.redisTemplate.opsForValue().set(sb.toString(), path, 1, TimeUnit.SECONDS);
			}
		}));*/
		return chain.filter(exchange);
	}

}
