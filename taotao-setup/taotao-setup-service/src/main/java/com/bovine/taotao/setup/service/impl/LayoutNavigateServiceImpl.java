package com.bovine.taotao.setup.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.setup.entity.LayoutNavigate;
import com.bovine.taotao.setup.i.service.LayoutNavigateService;
import com.bovine.taotao.setup.mapper.LayoutNavigateMapper;

/**
 * 小程序导航ICON业务实现类
 * @author eden
 * @date 2023年2月10日 下午4:03:41
 */
@Service("navigateIconService")
@DubboService(interfaceClass = LayoutNavigateService.class)
public class LayoutNavigateServiceImpl extends ServiceImpl<LayoutNavigateMapper, LayoutNavigate> implements IService<LayoutNavigate>, LayoutNavigateService {

	@Override
	public P<LayoutNavigate> getBaseList(BaseModel m) {
		IPage<LayoutNavigate> page = new Page<>(m.getPageNum(), m.getPageSize());
		QueryWrapper<LayoutNavigate> wrapper = new QueryWrapper<>();
		wrapper.orderBy(true, m.getOrder(), m.getOrderField());
		page(page,wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Override
	public LayoutNavigate getEntity(Long id) {
		return this.getById(id);
	}

	@Override
	@Transactional
	public LayoutNavigate saveEntity(LayoutNavigate t) {
		this.save(t);
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(LayoutNavigate t) {
		return this.updateById(t);
	}

	@Override
	@Transactional
	public boolean delete(Collection<Long> ids) {
		return this.removeBatchByIds(ids);
	}

	@Override
	public List<LayoutNavigate> getLayoutNavigateList(Integer layoutId) {
		LambdaQueryWrapper<LayoutNavigate> query = Wrappers.lambdaQuery(LayoutNavigate.class).eq(LayoutNavigate::getLayoutId, layoutId).orderByAsc(LayoutNavigate::getSorted);
		return this.list(query);
	}

}
