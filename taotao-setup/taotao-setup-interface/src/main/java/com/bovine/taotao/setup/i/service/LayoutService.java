package com.bovine.taotao.setup.i.service;

import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.injecter.BaseService;
import com.bovine.taotao.setup.entity.Layout;

/**
 * 小程序导航全局配置
 * @author eden
 * @date 2023年2月10日 下午2:30:17
 */
public interface LayoutService extends BaseService<Layout, BaseModel> {
	
	/**
	 * 获取layout布局
	 * @param tisid
	 * @param type
	 * @return
	 */
	Layout getLayout(Long tisid, Integer type);
	
}
