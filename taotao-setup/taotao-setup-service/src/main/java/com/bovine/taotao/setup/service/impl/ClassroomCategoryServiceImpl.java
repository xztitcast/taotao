package com.bovine.taotao.setup.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import com.bovine.taotao.setup.entity.ClassroomCategory;
import com.bovine.taotao.setup.i.service.ClassroomCategoryService;
import com.bovine.taotao.setup.mapper.ClassroomCategoryMapper;
import com.bovine.taotao.setup.model.SysCategoryModel;

/**
 * 数币课堂类目Service业务逻辑实现类
 * @author eden
 * @date 2023年2月23日 下午7:35:36
 */
@Service("classroomCategoryService")
@DubboService(interfaceClass = ClassroomCategoryService.class)
public class ClassroomCategoryServiceImpl extends ServiceImpl<ClassroomCategoryMapper, ClassroomCategory> implements IService<ClassroomCategory>, ClassroomCategoryService {

	@Override
	public P<ClassroomCategory> getBaseList(BaseModel m) {
		SysCategoryModel scm = (SysCategoryModel)m;
		IPage<ClassroomCategory> page = new Page<>(scm.getPageNum(), scm.getPageSize());
		QueryWrapper<ClassroomCategory> wrapper = new QueryWrapper<>();
		wrapper.eq(StringUtils.isNotBlank(scm.getName()), "name", scm.getName()).orderBy(true, scm.getOrder(), scm.getOrderField());
		page(page,wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Override
	public ClassroomCategory getEntity(Integer id) {
		return this.getById(id);
	}

	@Override
	@Transactional
	public ClassroomCategory saveEntity(ClassroomCategory t) {
		this.save(t);
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(ClassroomCategory t) {
		return this.updateById(t);
	}

	@Override
	@Transactional
	public boolean delete(Collection<Integer> ids) {
		return this.removeBatchByIds(ids);
	}

	@Override
	public List<ClassroomCategory> getArticleCategoryList() {
		return this.list();
	}

	
}
