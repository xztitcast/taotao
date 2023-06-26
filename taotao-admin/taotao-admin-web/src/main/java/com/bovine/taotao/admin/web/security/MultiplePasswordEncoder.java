package com.bovine.taotao.admin.web.security;

import com.bovine.taotao.common.core.utils.AESBUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 多重密码验证(静态代理增强)
 * @author eden
 * @date 2023/6/26 11:57:57
 */
@Component
public class MultiplePasswordEncoder implements PasswordEncoder {

    private PasswordEncoder passwordEncoder;

    private MultiplePasswordEncoder() {
        this(new BCryptPasswordEncoder());
    }

    private MultiplePasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return this.passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println(rawPassword.toString());
        String decrypt = AESBUtil.decrypt(rawPassword.toString());
        return this.passwordEncoder.matches(decrypt, encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return this.passwordEncoder.upgradeEncoding(encodedPassword);
    }
}
