package com.bovine.taotao.setup.i.service;

import java.util.List;

import com.bovine.taotao.setup.SysAdModelAndView;
import com.bovine.taotao.setup.entity.AdJoinContent;

/**
 * 广告与广告内容关联Service接口
 * @author eden
 * @date 2023年2月25日 下午4:57:32
 */
public interface AdJoinContentService {
	
	/**
	 * 获取关联的广告内容
	 * @return
	 */
	List<AdJoinContent> getAdJoinContentList(Long adId);

	/**
	 * 保存关联数据
	 * @param mv  视图数据
	 * @return
	 */
	boolean saveEntity(SysAdModelAndView mv);

	
}
