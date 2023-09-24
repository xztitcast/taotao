package com.bovine.taotao.setup.i.service;

import java.util.List;

import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.setup.entity.AdContent;
import com.bovine.taotao.setup.model.AdContentModel;

/**
 * 广告内容Service业务接口
 * @author eden
 * @date 2023年2月11日 下午4:27:14
 */
public interface AdContentService extends FrameworkService<AdContent, Long> {
	
	/**
	 * 获取广告内容
	 * @return
	 */
	List<AdContent> getAdContentList();

	/**
	 * 根据广告位id 或者
	 * 根据广告类型和机构号获取指定的广告内容列表
	 * @param adId
	 * @return
	 */
	List<AdContent> getAdContentList(AdContentModel adId);


}
