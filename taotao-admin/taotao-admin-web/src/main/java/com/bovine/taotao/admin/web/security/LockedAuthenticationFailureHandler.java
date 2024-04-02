package com.bovine.taotao.admin.web.security;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bovine.taotao.admin.web.entity.SysUser;
import com.bovine.taotao.admin.web.service.SysUserService;
import com.bovine.taotao.common.core.Constant;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author eden
 * @date 2023/6/25 22:39:39
 */
@Component
public class LockedAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = S.USER_PWD_ERROR.getMessage();
        if(exception instanceof LockedException) {
            message = "账号已被锁定, 联系管理员!";
        }else if(exception instanceof DisabledException) {
            message = "账号已被禁用, 请联系管理员!";
        }else {
            String username = request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
            Long increment = this.redisTemplate.opsForValue().increment(Constant.RedisKey.SYS_LOGIN_LOCKED_KEY.concat(username));
            if(increment > 5) {//锁定两个小时
                this.sysUserService.update(new UpdateWrapper<SysUser>().set("locked", DateUtil.offsetHour(new Date(), 2)).eq(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username));
                this.redisTemplate.delete(Constant.RedisKey.SYS_LOGIN_LOCKED_KEY.concat(username));
            }
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().print(R.error(S.USER_PWD_ERROR.getValue(), message));
    }
}
