package com.bovine.taotao.admin.web.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 抽象多重校验
 * 在校验密码之前验证数字验证码与图形验证码
 * @author eden
 * @date 2023/9/12 19:31:31
 */
public abstract class AbstractMultipleDaoAuthenticationProvider extends DaoAuthenticationProvider {

    protected AbstractMultipleDaoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
        setUserDetailsService(userDetailsService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if(!additionalAuthenticationChecks(authentication)) throw new BadCredentialsException("图形验证码验证失败!");
        super.additionalAuthenticationChecks(userDetails, authentication);
    }

    /**
     *
     * @param authentication
     * @return
     */
    protected abstract boolean additionalAuthenticationChecks(UsernamePasswordAuthenticationToken authentication);
}
