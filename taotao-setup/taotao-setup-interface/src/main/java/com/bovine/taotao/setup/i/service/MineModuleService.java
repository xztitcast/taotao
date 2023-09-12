package com.bovine.taotao.setup.i.service;

import java.util.List;

import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.setup.entity.MineModule;

/**
 * "我的"页面模块Service业务接口
 * @author eden
 * @date 2023年3月2日 下午8:15:05
 */
public interface MineModuleService extends FrameworkService<MineModule, Long> {
	
	/**
	 * 获取我的页面模块列表数据
	 * @param areaId
	 * @return
	 */
	List<MineModule> getMineModuleList(Long areaId);
	
	/**
	 * 获取我的页面没有使用的模块列表数据
	 * @return
	 */
	List<MineModule> getMineModuleNotUsedList();

}
