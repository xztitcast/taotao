package com.bovine.taotao.admin.web.security;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 多重认证模式认证器
 * 直接继承DaoAuthenticationProvider
 * @author eden
 * @date 2023/6/26 9:40:40
 */
@Component
public class MultipleCaptchaDaoAuthenticationProvider extends AbstractMultipleDaoAuthenticationProvider {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public MultipleCaptchaDaoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super(userDetailsService, passwordEncoder);
    }

    @Override
    protected boolean additionalAuthenticationChecks(UsernamePasswordAuthenticationToken authentication) {
        MultipleWebAuthenticationDetailsSource.MultipleWebAuthenticationDetails details = (MultipleWebAuthenticationDetailsSource.MultipleWebAuthenticationDetails) authentication.getDetails();
        HttpServletRequest request = details.getRequest();
        String captcha = request.getParameter("captcha");
        String uuid = request.getParameter("uuid");
        String verify = request.getParameter("captchaVerification");
        if(StringUtils.isBlank(uuid) || StringUtils.isBlank(captcha) || StringUtils.isBlank(verify)) {
           return false;
        }
        String value = redisTemplate.opsForValue().get(uuid);
        this.redisTemplate.delete(uuid);

        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(verify);
        ResponseModel model = this.captchaService.verification(captchaVO);
        return (captcha.equals(value) && model.isSuccess());
    }
}
