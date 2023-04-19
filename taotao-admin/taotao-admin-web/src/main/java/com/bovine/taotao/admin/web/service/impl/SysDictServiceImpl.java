package com.bovine.taotao.admin.web.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.admin.web.entity.SysDict;
import com.bovine.taotao.admin.web.mapper.SysDictMapper;
import com.bovine.taotao.admin.web.model.DictModel;
import com.bovine.taotao.admin.web.service.SysDictService;
import com.bovine.taotao.common.core.Constant.DictTemplate;
import com.bovine.taotao.common.core.P;

/**
 * 数据字典业务接口实现
 * @author eden
 * @date 2022/10/30 11:08:08
 */
@Service("dictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService, InitializingBean, DisposableBean {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public P<SysDict> getDictList(DictModel dm) {
		IPage<SysDict> page = new Page<>(dm.getPageNum(), dm.getPageSize());
		QueryWrapper<SysDict> wrapper = new QueryWrapper<>();
		wrapper.eq(StringUtils.isNotBlank(dm.getKey()), "key", dm.getKey()).orderBy(true, dm.getOrder(), dm.getOrderField());
		page(page, wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, String> dictAll = this.list().stream().collect(HashMap::new, (left, right) -> left.put(right.getKey(), right.getValue()), Map::putAll);
		this.redisTemplate.opsForHash().putAll(DictTemplate.TABLE, dictAll);
	}
	
	@Override
	public void destroy() throws Exception {
		this.redisTemplate.delete(DictTemplate.TABLE);
	}
	
	@Override
	@Transactional
	public boolean save(SysDict entity) {
		this.redisTemplate.opsForHash().put(DictTemplate.TABLE, entity.getKey(), entity.getValue());
		return super.save(entity);
	}

	@Override
	@Transactional
	public boolean updateById(SysDict entity) {
		this.redisTemplate.opsForHash().put(DictTemplate.TABLE, entity.getKey(), entity.getValue());
		return super.updateById(entity);
	}


	@Override
	@Transactional
	public boolean removeByIds(Collection<?> list) {
		this.redisTemplate.opsForHash().delete(DictTemplate.TABLE, list.toArray());
		return super.removeByIds(list);
	}

	
}
