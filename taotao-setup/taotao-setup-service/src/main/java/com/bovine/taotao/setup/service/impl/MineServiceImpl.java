package com.bovine.taotao.setup.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.setup.entity.Mine;
import com.bovine.taotao.setup.i.service.MineService;
import com.bovine.taotao.setup.mapper.MineMapper;

/**
 * "我的"页面Service业务逻辑接口
 * @author eden
 * @date 2023年3月2日 下午8:14:15
 */
@Service("mineService")
@DubboService(interfaceClass = MineService.class)
public class MineServiceImpl extends ServiceImpl<MineMapper, Mine> implements IService<Mine>, MineService {

	@Override
	public P<Mine> getBaseList(BaseModel m) {
		return MineService.super.getBaseList(m);
	}

	@Override
	public Mine getEntity(Integer id) {
		return this.getById(id);
	}

	@Override
	@Transactional
	public Mine saveEntity(Mine t) {
		this.save(t);
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(Mine t) {
		return this.updateById(t);
	}

	@Override
	@Transactional
	public boolean delete(Collection<Integer> ids) {
		return this.removeBatchByIds(ids);
	}
	
	@Override
	public Mine getMine(Long tisid) {
		LambdaQueryWrapper<Mine> query = Wrappers.lambdaQuery(Mine.class).eq(Mine::getTisid, tisid).eq(Mine::getStatus, 0);
		return this.getOne(query);
	}

	
}
