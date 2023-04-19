package com.bovine.taotao.setup.i.service;

import java.util.List;

import com.bovine.taotao.setup.entity.MineModuleArea;

/**
 * "我的"页面区域模块关联Service业务接口
 * @author eden
 * @date 2023年3月3日 下午6:19:59
 */
public interface MineModuleAreaService {

	/**
	 * 获取关联的模块ID
	 * @param areaId 区域ID
	 * @return
	 */
	List<Long> getMineModuleIdList(Long areaId);
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	boolean saveEntity(MineModuleArea entity);
	
	/**
	 * 删除
	 * @param entity
	 * @return
	 */
	boolean deleteEntity(MineModuleArea entity);
}
