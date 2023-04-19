package com.bovine.taotao.setup.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bovine.taotao.setup.entity.MineModule;

/**
 * "我的"页面模块持久化接口
 * @author eden
 * @date 2023年3月2日 下午8:11:05
 */
public interface MineModuleMapper extends BaseMapper<MineModule> {

	/**
	 * 查询我的页面区域关联的模块数据列表
	 * @param areaId 区域ID
	 * @return
	 */
	List<MineModule> selectMineModuleList(Long areaId);
	
	/**
	 * 查询我的页面没有使用的模块列表数据
	 * @return
	 */
	List<MineModule> selectMineModuleNotUsedList();
}
