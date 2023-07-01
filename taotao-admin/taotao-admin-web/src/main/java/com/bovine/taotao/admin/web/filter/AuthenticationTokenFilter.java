package com.bovine.taotao.admin.web.filter;

import com.alibaba.fastjson2.JSON;
import com.bovine.taotao.admin.web.service.impl.UserDetailsServiceImpl.LoginUserDetails;
import com.bovine.taotao.common.core.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 *  Token认证过滤器
 *  token不存在也可以过，认证器会自动认证
 * @author eden
 * @date 2023/6/24 21:06:06
 */
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getRequestToken(request);
        if(StringUtils.isNotBlank(token)) {
            String value = this.redisTemplate.opsForValue().get(Constant.RedisKey.SYS_SESSION_ID_STR_KEY + token);
            LoginUserDetails loginUserDetails = JSON.parseObject(value, LoginUserDetails.class);
            if(loginUserDetails != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDetails, null, loginUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest request){
        //从header中获取token
        String token = request.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(token == null || token.isBlank()){
            token = request.getParameter("token");
        }

        if(token == null || token.isBlank()) {
            Cookie[] cookies = request.getCookies();
            if(Objects.nonNull(cookies)) {
                for(Cookie c : request.getCookies()) {
                    if("token".equals(c.getName())) {
                        token = c.getValue();
                        break;
                    }
                }
            }
        }
        return token;
    }
}
