package com.bovine.taotao.setup.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

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
import com.bovine.taotao.setup.entity.MineModule;
import com.bovine.taotao.setup.i.service.MineModuleService;
import com.bovine.taotao.setup.mapper.MineModuleMapper;

/**
 * "我的"页面模块Service业务逻辑实现类
 * @author eden
 * @date 2023年3月2日 下午8:16:12
 */
@Service("mineModuleService")
@DubboService(interfaceClass = MineModuleService.class)
public class MineModuleServiceImpl extends ServiceImpl<MineModuleMapper, MineModule> implements IService<MineModule>, MineModuleService {
	
	@Override
	public P<MineModule> getBaseList(BaseModel m) {
		IPage<MineModule> page = new Page<>(m.getPageNum(), m.getPageSize());
		QueryWrapper<MineModule> wrapper = new QueryWrapper<>();
		wrapper.orderBy(true, m.getOrder(), m.getOrderField());
		page(page,wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Override
	public MineModule getEntity(Serializable id) {
		return this.getById(id);
	}

	@Override
	@Transactional
	public MineModule saveEntity(MineModule t) {
		this.save(t);
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(MineModule t) {
		return this.updateById(t);
	}

	@Override
	@Transactional
	public boolean delete(Serializable[] id) {
		return this.removeBatchByIds(Arrays.asList(id));
	}

	@Override
	public List<MineModule> getMineModuleList(Long areaId) {
		return this.baseMapper.selectMineModuleList(areaId);
	}

	@Override
	public List<MineModule> getMineModuleNotUsedList() {
		return this.baseMapper.selectMineModuleNotUsedList();
	}

}
