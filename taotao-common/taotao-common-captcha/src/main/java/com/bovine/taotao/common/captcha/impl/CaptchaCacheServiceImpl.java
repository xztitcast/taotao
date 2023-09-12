package com.bovine.taotao.common.captcha.impl;

import com.anji.captcha.service.CaptchaCacheService;
import com.bovine.taotao.common.captcha.properties.CaptchaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class CaptchaCacheServiceImpl implements CaptchaCacheService {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate; 

	@Override
	public void set(String key, String value, long expiresInSeconds) {
		this.redisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public boolean exists(String key) {
		return this.redisTemplate.hasKey(key);
	}

	@Override
	public void delete(String key) {
		this.redisTemplate.delete(key);
	}

	@Override
	public String get(String key) {
		return this.redisTemplate.opsForValue().get(key);
	}

	@Override
	public String type() {
		return CaptchaProperties.StorageType.redis.name();
	}
	
	@Override
	public Long increment(String key, long val) {
		return this.redisTemplate.opsForValue().increment(key, val);
	}

}
