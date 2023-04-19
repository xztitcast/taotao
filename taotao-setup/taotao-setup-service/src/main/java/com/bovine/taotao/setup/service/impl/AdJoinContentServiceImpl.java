package com.bovine.taotao.setup.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.setup.SysAdModelAndView;
import com.bovine.taotao.setup.entity.AdJoinContent;
import com.bovine.taotao.setup.i.service.AdJoinContentService;
import com.bovine.taotao.setup.mapper.AdJoinContentMapper;

/**
 * 广告与广告内容Service业务逻辑实现类
 * @author eden
 * @date 2023年2月25日 下午4:59:25
 */
@Service("adJoinContentService")
@DubboService(interfaceClass = AdJoinContentService.class)
public class AdJoinContentServiceImpl extends ServiceImpl<AdJoinContentMapper, AdJoinContent> implements IService<AdJoinContent>, AdJoinContentService {

	@Override
	public List<AdJoinContent> getAdJoinContentList(Long adId) {
		LambdaQueryWrapper<AdJoinContent> query = Wrappers.lambdaQuery(AdJoinContent.class).eq(AdJoinContent::getAdId, adId);
		return this.list(query);
	}
	
	@Override
	@Transactional
	public boolean saveEntity(SysAdModelAndView mv) {
		this.removeByMap(Map.of("ad_id", mv.getAdId()));
		List<AdJoinContent> list = mv.getJoinViewList().stream().map(v -> new AdJoinContent(mv.getAdId(), v.getAdContentId(), v.getSorted())).collect(Collectors.toList());
		return this.saveBatch(list);
	}

}
