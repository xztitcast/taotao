package com.bovine.taotao.admin.web.modelAndView.model;

import com.bovine.taotao.common.core.BaseModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台管理系统 设备交互数据
 * @see SysDeviceController -> list
 * @author eden
 * @date 2023年2月22日 上午11:52:10
 */
@Getter
@Setter
public class DeviceModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 公司名称
	 */
	private String name;

}
