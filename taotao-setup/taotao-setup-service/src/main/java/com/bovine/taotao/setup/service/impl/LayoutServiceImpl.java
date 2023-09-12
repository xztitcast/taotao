package com.bovine.taotao.setup.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

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
import com.bovine.taotao.setup.entity.Layout;
import com.bovine.taotao.setup.i.service.LayoutService;
import com.bovine.taotao.setup.mapper.LayoutMapper;

/**
 * 小程序导航全局配置业务实现类
 * @author eden
 * @date 2023年2月10日 下午3:01:21
 */
@Service("navigateConfigService")
@DubboService(interfaceClass = LayoutService.class)
public class LayoutServiceImpl extends ServiceImpl<LayoutMapper, Layout> implements IService<Layout>, LayoutService {

	@Override
	public P<Layout> getBaseList(BaseModel m) {
		IPage<Layout> page = new Page<>(m.getPageNum(), m.getPageSize());
		QueryWrapper<Layout> wrapper = new QueryWrapper<>();
		wrapper.orderBy(true, m.getOrder(), m.getOrderField());
		page(page,wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Override
	public Layout getEntity(Integer id) {
		return this.getById(id);
	}

	@Override
	@Transactional
	public Layout saveEntity(Layout t) {
		this.save(t);
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(Layout t) {
		return this.updateById(t);
	}

	@Override
	@Transactional
	public boolean delete(Collection<Integer> ids) {
		return this.removeBatchByIds(ids);
	}

	@Override
	public Layout getLayout(Long tisid, Integer type) {
		LambdaQueryWrapper<Layout> query = Wrappers.lambdaQuery(Layout.class).eq(Layout::getTisid, tisid).eq(Layout::getType, type).eq(Layout::getStatus, 1);
		return this.getOne(query);
	}

}
