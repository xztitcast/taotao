package com.bovine.taotao.admin.web.support.device;

import com.bovine.taotao.admin.web.config.DeviceConfig.DeviceProperty;

/**
 * 设备处理器
 * @author eden
 * @date 2023年2月24日 下午12:01:52
 */
public interface DeviceProcessor {

	/**
	 * 
	 * @param content
	 * @param property
	 * @return
	 */
	boolean process(String content, DeviceProperty property);
}
