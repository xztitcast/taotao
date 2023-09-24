package com.bovine.taotao.setup.service.impl;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.setup.entity.Ad;
import com.bovine.taotao.setup.i.service.AdService;
import com.bovine.taotao.setup.mapper.AdMapper;
import com.bovine.taotao.setup.model.SysAdModel;

/**
 * 广告业务接口实现
 * @author eden
 * @date 2023年2月11日 上午11:44:49
 */
@Service("appAdService")
@DubboService(interfaceClass = AdService.class)
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements IService<Ad>, AdService {
	
	@Override
	public P<Ad> getBaseList(BaseModel m) {
		SysAdModel adModel = (SysAdModel)m;
		IPage<Ad> page = new Page<>(m.getPageNum(), m.getPageSize());
		QueryWrapper<Ad> wrapper = new QueryWrapper<>();
		wrapper.lambda().like(StringUtils.isNotBlank(adModel.getNavigateName()),Ad::getNavigateName,adModel.getNavigateName());
		wrapper.orderBy(StringUtils.isNotBlank(m.getOrderField()), m.getOrder(), m.getOrderField());
		page(page,wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Override
	public Ad getEntity(Long id) {
		return this.getById(id);
	}

	@Override
	@Transactional
	public Ad saveEntity(Ad t) {
		this.save(t);
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(Ad t) {
		return this.updateById(t);
	}

	@Override
	@Transactional
	public boolean delete(Collection<Long> ids) {
		return this.removeBatchByIds(ids);
	}
	
}
