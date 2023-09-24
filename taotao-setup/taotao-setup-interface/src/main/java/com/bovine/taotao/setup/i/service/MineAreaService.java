package com.bovine.taotao.setup.i.service;

import java.util.List;

import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.setup.entity.MineArea;
import com.bovine.taotao.setup.view.MineAreaView;

/**
 * "我的"页面区域Service业务接口
 * @author eden
 * @date 2023年3月2日 下午8:17:07
 */
public interface MineAreaService extends FrameworkService<MineArea, Long> {

	/**
	 * 获取区域数据列表
	 * @param mineId 我的页面主键ID
	 * @return
	 */
	List<MineAreaView> getMineAreaList(Integer mineId);
}
