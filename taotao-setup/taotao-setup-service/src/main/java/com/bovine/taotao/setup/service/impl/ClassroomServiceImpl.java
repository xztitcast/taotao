package com.bovine.taotao.setup.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.bovine.taotao.common.core.utils.BeanCopierUtil;
import com.bovine.taotao.setup.entity.Classroom;
import com.bovine.taotao.setup.entity.ClassroomContent;
import com.bovine.taotao.setup.i.service.ClassroomContentService;
import com.bovine.taotao.setup.i.service.ClassroomService;
import com.bovine.taotao.setup.mapper.ClassroomMapper;
import com.bovine.taotao.setup.model.ArticleAndClassroomModel;
import com.bovine.taotao.setup.model.SysClassroomModel;
import com.bovine.taotao.setup.view.ClassroomView;

/**
 * 数币课堂Service业务逻辑实现类
 * @author eden
 * @date 2023年2月23日 下午7:37:19
 */
@Service("classroomService")
@DubboService(interfaceClass = ClassroomService.class)
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper,  Classroom> implements IService< Classroom>, ClassroomService {

	@Autowired
	private ClassroomContentService classroomContentService;
	
	@Override
	public P<Classroom> getBaseList(BaseModel m) {
		SysClassroomModel sam = (SysClassroomModel)m;
		IPage<Classroom> page = new Page<>(sam.getPageNum(), sam.getPageSize());
		QueryWrapper<Classroom> wrapper = new QueryWrapper<>();
		wrapper.like(StringUtils.isNotBlank(sam.getTitle()), "title", sam.getTitle())
			.eq(StringUtils.isNotBlank(sam.getPublisher()), "publisher", sam.getPublisher())
			.eq(StringUtils.isNotBlank(sam.getStatus()), "status", sam.getStatus())
			.orderBy(true, sam.getOrder(), sam.getOrderField());
		page(page,wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Override
	public Classroom getEntity(Long id) {
		Classroom entity = this.getById(id);
		ClassroomContent cc = classroomContentService.getEntity(id);
		entity.setContent(cc.getContent());
		return entity;
	}

	@Override
	@Transactional
	public Classroom saveEntity(Classroom t) {
		boolean save = this.save(t);
		if(save) {
			classroomContentService.saveEntity(new ClassroomContent(t.getId(), t.getContent()));
		}
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(Classroom t) {
		boolean update = this.updateById(t);
		if(update) {
			update = classroomContentService.updateEntity(new ClassroomContent(t.getId(), t.getContent()));
		}
		return update;
	}

	@Override
	@Transactional
	public boolean delete(Collection<Long> ids) {
		return this.removeBatchByIds(ids);
	}

	@Override
	public P<ClassroomView> getClassroomList(ArticleAndClassroomModel acm) {
		IPage<Classroom> page = new Page<>(acm.getPageNum(), acm.getPageSize());
		LambdaQueryWrapper<Classroom> query = Wrappers.lambdaQuery(Classroom.class)
				.eq(acm.getCategoryId() != null ,Classroom::getCategoryId, acm.getCategoryId())
				.eq(Classroom::getStatus, 2).orderByDesc(List.of(Classroom::getTop, Classroom::getCreated));
		page(page, query);
		List<ClassroomView> record = page.getRecords().stream().map(a -> {
			ClassroomView cv = new ClassroomView();
			BeanCopierUtil.copyProperties(a, cv);
			return cv;
		}).collect(Collectors.toList());
		return new P<>(page.getTotal(), record);
	}

	
}
