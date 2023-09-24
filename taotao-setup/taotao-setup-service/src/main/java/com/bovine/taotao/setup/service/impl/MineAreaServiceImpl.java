package com.bovine.taotao.setup.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.setup.entity.MineArea;
import com.bovine.taotao.setup.entity.MineModule;
import com.bovine.taotao.setup.entity.MineModuleArea;
import com.bovine.taotao.setup.i.service.MineAreaService;
import com.bovine.taotao.setup.i.service.MineModuleAreaService;
import com.bovine.taotao.setup.i.service.MineModuleService;
import com.bovine.taotao.setup.mapper.MineAreaMapper;
import com.bovine.taotao.setup.view.MineAreaView;
import com.bovine.taotao.setup.view.MineAreaView.MineModuleView;

/**
 * "我的"页面区域Service业务逻辑实现类
 * @author eden
 * @date 2023年3月2日 下午8:18:20
 */
@Service("mineAreaService")
@DubboService(interfaceClass = MineAreaService.class)
public class MineAreaServiceImpl extends ServiceImpl<MineAreaMapper, MineArea> implements IService<MineArea>, MineAreaService {
	
	@Autowired
	private MineModuleService mineModuleService;
	
	@Autowired
	private MineModuleAreaService minModuleAreaService;

	@Override
	public MineArea getEntity(Long id) {
		return this.getById(id);
	}

	@Override
	@Transactional
	public MineArea saveEntity(MineArea t) {
		this.save(t);
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(MineArea t) {
		boolean update = this.updateById(t);
		if(update) {
			minModuleAreaService.deleteEntity(new MineModuleArea(t.getId()));
		}
		return update;
	}

	@Override
	@Transactional
	public boolean delete(Collection<Long> ids) {
		return this.removeBatchByIds(ids);
	}

	@Override
	public List<MineAreaView> getMineAreaList(Integer mineId) {
		LambdaQueryWrapper<MineArea> query = Wrappers.lambdaQuery(MineArea.class).eq(MineArea::getMineId, mineId).eq(MineArea::getStatus, 0);
		List<MineArea> list = this.list(query);
		return list.stream().map(m -> {
			MineAreaView mv = new MineAreaView(m.getId(), m.getTitle());
			List<MineModule> moduleList = mineModuleService.getMineModuleList(m.getId());
			mv.setMinModuleList(moduleList.stream().map(mm -> new MineModuleView(mm.getId(), mm.getName(), mm.getIcon(), mm.getUrl())).collect(Collectors.toList()));
			return mv;
		}).collect(Collectors.toList());
	}

}
