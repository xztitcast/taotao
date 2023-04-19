package com.bovine.taotao.setup.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.setup.entity.MineModuleArea;
import com.bovine.taotao.setup.i.service.MineModuleAreaService;
import com.bovine.taotao.setup.mapper.MineModuleAreaMapper;

/**
 * "我的"页面区域模块关联Service业务逻辑实现类
 * @author eden
 * @date 2023年3月3日 下午6:21:13
 */
@Service("mineModuleAreaService")
@DubboService(interfaceClass = MineModuleAreaService.class)
public class MineModuleAreaServiceImpl extends ServiceImpl<MineModuleAreaMapper, MineModuleArea> implements IService<MineModuleArea>, MineModuleAreaService {

	@Override
	public List<Long> getMineModuleIdList(Long areaId) {
		LambdaQueryWrapper<MineModuleArea> query = Wrappers.lambdaQuery(MineModuleArea.class).eq(MineModuleArea::getAreaId, areaId);
		return this.list(query).stream().map(MineModuleArea::getModuleId).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public boolean saveEntity(MineModuleArea entity) {
		return this.save(entity);
	}

	@Override
	@Transactional
	public boolean deleteEntity(MineModuleArea entity) {
		return this.remove(Wrappers.lambdaQuery(entity));
	}

}
