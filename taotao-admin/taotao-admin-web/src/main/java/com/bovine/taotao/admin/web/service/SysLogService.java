package com.bovine.taotao.admin.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bovine.taotao.admin.web.entity.SysLog;
import com.bovine.taotao.admin.web.model.UserModel;
import com.bovine.taotao.common.core.P;

/**
 * 系统日志业务接口
 * @author eden
 * @date 2018年7月23日 上午9:31:35
 */
public interface SysLogService extends IService<SysLog> {

	/**
	 * 获取日志代表
	 * @param from
	 * @return
	 */
	P<SysLog> getSysLogList(UserModel from);
	
}
