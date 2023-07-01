package com.bovine.taotao.admin.web.security;

import com.bovine.taotao.common.core.utils.AESBUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author eden
 * @date 2023/6/26 9:40:40
 */
@Component
public class MultipleDaoAuthenticationProvider extends DaoAuthenticationProvider {

    public MultipleDaoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
        setUserDetailsService(userDetailsService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //图形验证
        MultipleWebAuthenticationDetailsSource.MultipleWebAuthenticationDetails details = (MultipleWebAuthenticationDetailsSource.MultipleWebAuthenticationDetails) authentication.getDetails();

        if(!details.isVerify()) {
            throw new BadCredentialsException("数字验证码验证失败!");
        }

        //Spring Security原有认证逻辑(账号密码验证)
        super.additionalAuthenticationChecks(userDetails, authentication);

    }
}
