package com.bovine.taotao.admin.web.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * @author eden
 * @date 2023/6/26 11:41:41
 */
@Component
public class MultipleWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new MultipleWebAuthenticationDetails(context);
    }

    public static class MultipleWebAuthenticationDetails extends WebAuthenticationDetails {

        private static final long serialVersionUID = 1L;

        private HttpServletRequest request;

        public MultipleWebAuthenticationDetails(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        public HttpServletRequest getRequest(){
            return this.request;
        }
    }
}
