package com.bovine.taotao.admin.web.config;

import java.util.Map;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson2.JSON;
import com.bovine.taotao.common.core.utils.AESUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "device")
public class DeviceConfig {

	private Set<String> urls;
	
	private Map<String, DeviceProperty> property;
	
	@Getter
	@Setter
	public static class DeviceProperty {
		/**
		 * 类型
		 */
		private String type;

		/**
		 * 设备ID
		 */
		private String id;
		
		/**
		 * 密钥
		 */
		private String key;
		
		/**
		 * 盐
		 */
		private String iv;
		
		/**
		 * 授权超时时间
		 */
		private Long timeout = 1500L;
		
		/**
		 * 解密
		 * @param content
		 * @return
		 */
		public SsidEntity decrypt(String content) {
			String decrypt = AESUtil.decrypt(this.key, content, this.iv);
			return JSON.parseObject(decrypt, SsidEntity.class);
		}
		
		/**
		 * 传参标识符实体类
		 * @author eden
		 * @date 2023年3月6日 下午2:19:27
		 */
		@Getter
		@Setter
		public static class SsidEntity {
			
			/**
			 * 设备ID
			 */
			private String deviceId;
			
			/**
			 * nonce值
			 * 一分钟内非不能重复
			 */
			private String nonce;
			
			/**
			 * 时间戳
			 */
			private Long timestamp;

		}
	}
}
