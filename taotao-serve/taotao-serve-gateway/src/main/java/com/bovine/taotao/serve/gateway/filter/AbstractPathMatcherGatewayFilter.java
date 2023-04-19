package com.bovine.taotao.serve.gateway.filter;

import java.util.Set;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * 网关路径匹配
 * @author eden
 * @date 2023年3月6日 下午6:52:03
 */
public abstract class AbstractPathMatcherGatewayFilter {

	private static PathMatcher pathMatcher = new AntPathMatcher();
	
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
}
