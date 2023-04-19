package com.bovine.taotao.setup.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.utils.BeanCopierUtil;
import com.bovine.taotao.setup.i.service.ModuleService;
import com.bovine.taotao.setup.mapper.ModuleMapper;
import com.bovine.taotao.setup.model.SysModuleModel;
import com.bovine.taotao.setup.view.ModuleView;
import com.bovine.taotao.setup.entity.Module;

/**
 * 模块配置Service业务接口实现类
 * @author eden
 * @date 2023年2月14日 下午6:45:42
 */
@Service("moduleService")
@DubboService(interfaceClass = ModuleService.class)
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements IService<Module>, ModuleService {


	@Override
	public P<Module> getBaseList(BaseModel m) {
		SysModuleModel mv = (SysModuleModel) m;
		IPage<Module> page = new Page<>(m.getPageNum(), m.getPageSize());
		QueryWrapper<Module> wrapper = new QueryWrapper<>();
		wrapper.lambda().like(Module::getName, mv.getName());
		wrapper.orderBy(true, m.getOrder(), m.getOrderField());
		page(page,wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}


	@Override
	public Module getEntity(Serializable id) {
		return this.getById(id);
	}

	@Override
	public Module saveEntity(Module t) {
		this.save(t);
		return t;
	}

	@Override
	public boolean updateEntity(Module t) {
		return this.updateById(t);
	}

	@Override
	public boolean delete(Serializable[] id) {
		return this.removeBatchByIds(Arrays.asList(id));
	}

	@Override
	public List<ModuleView> getModuleList(Long tisid) {
		LambdaQueryWrapper<Module> query = Wrappers.lambdaQuery(Module.class).eq(Module::getTisid, tisid).eq(Module::getStatus, 0).orderByAsc(Module::getSorted);
		return this.list(query).stream().map(m -> {
			ModuleView mv = new ModuleView();
			BeanCopierUtil.copyProperties(m, mv);
			return mv;
		}).collect(Collectors.toList());
	}
	
}
