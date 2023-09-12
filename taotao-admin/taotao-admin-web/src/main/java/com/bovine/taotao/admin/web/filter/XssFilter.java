package com.bovine.taotao.admin.web.filter;

import com.bovine.taotao.common.core.kit.PathMatcherHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Set;

/**
 * XSS过滤
 * @author Eden
 */
public class XssFilter implements Filter {

	private static final Set<String> URL_WHITE = Set.of("/sys/notice/**", "/sys/classroom/**", "/sys/article/**", "/sys/agreement/**");

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		boolean match = PathMatcherHelper.isMatch(URL_WHITE, httpServletRequest.getRequestURI());
		if(match) {
			chain.doFilter(request, response);
		}else {
			XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(httpServletRequest);
			chain.doFilter(xssRequest, response);
		}
	}

	@Override
	public void destroy() {
	}

}