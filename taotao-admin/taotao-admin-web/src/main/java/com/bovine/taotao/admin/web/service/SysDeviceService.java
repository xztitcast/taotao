package com.bovine.taotao.admin.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bovine.taotao.admin.web.entity.SysDevice;
import com.bovine.taotao.admin.web.modelAndView.model.DeviceModel;
import com.bovine.taotao.common.core.P;

/**
 * 系统设备授权Service接口
 * @author eden
 * @date 2023年2月22日 上午11:45:37
 */
public interface SysDeviceService extends IService<SysDevice> {

	/**
	 * 获取设备列表
	 * @param bm
	 * @return
	 */
	P<SysDevice> getSysDeviceList(DeviceModel bm);
	
}
