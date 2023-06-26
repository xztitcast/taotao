package com.bovine.taotao.admin.web.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * @author eden
 * @date 2023/6/26 11:41:41
 */
@Component
public class MultipleWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new MultipleWebAuthenticationDetails(context, this.redisTemplate);
    }

    public static class MultipleWebAuthenticationDetails extends WebAuthenticationDetails {

        private boolean verify;

        public MultipleWebAuthenticationDetails(HttpServletRequest request) {
            super(request);
        }

        public MultipleWebAuthenticationDetails(HttpServletRequest request, RedisTemplate<String, String> redisTemplate) {
            this(request);
            String captcha = request.getParameter("captcha");
            String uuid = request.getParameter("uuid");
            String value = redisTemplate.opsForValue().get(uuid);
            if(value == null || value.isBlank()) {
                this.verify = false;
            } else if(!value.equals(captcha)) {
                this.verify = false;
                redisTemplate.delete(uuid);
            }else {
                this.verify = true;
            }
        }

        public boolean isVerify(){
            return this.verify;
        }
    }
}
