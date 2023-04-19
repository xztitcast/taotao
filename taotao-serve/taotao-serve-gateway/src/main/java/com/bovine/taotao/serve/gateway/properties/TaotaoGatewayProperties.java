package com.bovine.taotao.serve.gateway.properties;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * 网关配置
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "smart")
public class TaotaoGatewayProperties {

	/**
	 * 日志开关
	 */
	private boolean enable;
	
	/**
	 * 二级白名单
	 */
	private Set<String> ignore = new HashSet<>();
	
	
}
