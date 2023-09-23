package com.bovine.taotao.common.security.session;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.bovine.taotao.common.core.Principal;
import com.bovine.taotao.common.core.enums.BaseEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 默认Token认证管理器
 * @author eden
 * @date 2023/9/23 22:29:29
 */
public class DefaultAuthenticationTokenWebManager implements AuthenticationTokenWebManager{

    private RedisTemplate<String, String> redisTemplate;

    public DefaultAuthenticationTokenWebManager(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public String createToken(Principal principal) {
        return null;
    }

    @Override
    public String createToken(Principal principal, boolean isVisitor) {
        String value = this.redisTemplate.opsForValue().get(TOKEN_KEY + principal.getId());
        if(StringUtils.isNotBlank(value)) {
            return value;
        }
        String token = DigestUtil.md5Hex(UUID.randomUUID().toString() + principal.getId()).toUpperCase();;
        if(isVisitor) {
            this.redisTemplate.opsForValue().set(TOKEN_KEY.concat(token), JSON.toJSONString(principal), BaseEnum.SEVEN, TimeUnit.MINUTES);
        }else{
            this.redisTemplate.opsForValue().set(TOKEN_KEY.concat(token), JSON.toJSONString(principal), BaseEnum.SEVEN, TimeUnit.DAYS);
            this.redisTemplate.opsForValue().set(TOKEN_KEY + principal.getId(), token, BaseEnum.SEVEN, TimeUnit.DAYS);
        }
        return token;
    }

    @Override
    public Principal getByToken(String token) {
        String value = this.redisTemplate.opsForValue().get(TOKEN_KEY.concat(token));
        return JSON.parseObject(value, Principal.class);
    }

    @Override
    public boolean remove(String token) {
        return this.redisTemplate.delete(TOKEN_KEY.concat(token));
    }
}
