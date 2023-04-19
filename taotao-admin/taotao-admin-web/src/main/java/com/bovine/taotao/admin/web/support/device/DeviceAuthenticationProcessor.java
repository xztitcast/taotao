package com.bovine.taotao.admin.web.support.device;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.bovine.taotao.admin.web.config.DeviceConfig.DeviceProperty;
import com.bovine.taotao.admin.web.config.DeviceConfig.DeviceProperty.SsidEntity;
import com.bovine.taotao.common.core.Constant.RedisKey;

/**
 * 设备验证逻辑处理器
 * @author eden
 * @date 2023年2月24日 下午12:02:03
 */
@Component
public class DeviceAuthenticationProcessor implements DeviceProcessor {
	
	private static final String SSID_LIMIT = "+";
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public boolean process(String ssid, DeviceProperty property) {
		if(ssid == null || ssid.trim().length() == 0) {
			return false;
		}
		int lastIndexOf = ssid.indexOf(SSID_LIMIT);
		final String ssidContnet = ssid.substring(lastIndexOf + 1);
		SsidEntity entity = property.decrypt(ssidContnet);
		if(entity == null) {
			return false;
		}
		if(!entity.getDeviceId().equals(property.getId())) {
			return false;
		}
		long timestampDifference = Math.abs(System.currentTimeMillis() - entity.getTimestamp());
		if(timestampDifference > property.getTimeout()) {
			return false;
		}
		String redisKey = RedisKey.GATEWAY_DEVICEID_STR_KEY + entity.getDeviceId();
		String value = redisTemplate.opsForValue().get(redisKey);
		if(entity.getNonce().equals(value)) {
			return false;
		}
		redisTemplate.opsForValue().set(redisKey, entity.getNonce(), 60, TimeUnit.SECONDS);
		return true;
	}

}
