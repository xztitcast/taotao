package com.bovine.taotao.setup.i.service;

import java.util.List;

import com.bovine.taotao.setup.entity.LayoutNavigatePath;

/**
 * 导航路径Service业务接口
 * @author eden
 * @date 2023年2月28日 下午4:50:16
 */
public interface LayoutNavigatePathService {

	/**
	 * 获取导航路径列表
	 * @return
	 */
	List<LayoutNavigatePath> getNavigatePathList();
}
