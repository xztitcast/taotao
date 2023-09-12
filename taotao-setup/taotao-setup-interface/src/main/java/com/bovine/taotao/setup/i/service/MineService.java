package com.bovine.taotao.setup.i.service;

import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.setup.entity.Mine;

/**
 * "我的"页面Service业务接口
 * @author eden
 * @date 2023年3月2日 下午8:13:04
 */
public interface MineService extends FrameworkService<Mine, Integer> {

	/**
	 * 获取我的页面配置
	 * @param tisid 机构ID
	 * @return
	 */
	Mine getMine(Long tisid);

}
