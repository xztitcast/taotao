package com.bovine.taotao.setup.service.impl;

import java.util.Collection;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.setup.entity.ClassroomContent;
import com.bovine.taotao.setup.i.service.ClassroomContentService;
import com.bovine.taotao.setup.mapper.ClassroomContentMapper;

/**
 * 数币课堂内容Service业务逻辑实现类
 * @author eden
 * @date 2023年2月23日 下午7:38:41
 */
@Service("classroomContentService")
@DubboService(interfaceClass = ClassroomContentService.class)
public class ClassroomContentServiceImpl extends ServiceImpl<ClassroomContentMapper, ClassroomContent> implements IService<ClassroomContent>, ClassroomContentService {

	@Override
	public ClassroomContent getEntity(Long id) {
		return this.getById(id);
	}

	@Override
	@Transactional
	public ClassroomContent saveEntity(ClassroomContent t) {
		this.save(t);
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(ClassroomContent t) {
		return this.updateById(t);
	}

	@Override
	@Transactional
	public boolean delete(Collection<Long> ids) {
		return this.removeBatchByIds(ids);
	}

	
}
