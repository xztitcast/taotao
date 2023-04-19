package com.bovine.taotao.setup.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.setup.entity.ArticleCategory;
import com.bovine.taotao.setup.i.service.ArticleCategoryService;
import com.bovine.taotao.setup.mapper.ArticleCategoryMapper;
import com.bovine.taotao.setup.model.SysCategoryModel;

/**
 * 文章类目Service业务逻辑实现类
 * @author eden
 * @date 2023年2月22日 下午8:43:06
 */
@Service("articleCategoryService")
@DubboService(interfaceClass = ArticleCategoryService.class)
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements IService<ArticleCategory>, ArticleCategoryService {

	@Override
	public P<ArticleCategory> getBaseList(BaseModel m) {
		SysCategoryModel acm = (SysCategoryModel)m;
		IPage<ArticleCategory> page = new Page<>(acm.getPageNum(), acm.getPageSize());
		QueryWrapper<ArticleCategory> wrapper = new QueryWrapper<>();
		wrapper.eq(StringUtils.isNotBlank(acm.getName()), "name", acm.getName()).orderBy(true, acm.getOrder(), acm.getOrderField());
		page(page,wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Override
	public ArticleCategory getEntity(Serializable id) {
		return this.getById(id);
	}

	@Override
	public ArticleCategory saveEntity(ArticleCategory t) {
		this.save(t);
		return t;
	}

	@Override
	public boolean updateEntity(ArticleCategory t) {
		return this.updateById(t);
	}

	@Override
	public boolean delete(Serializable[] id) {
		return this.removeBatchByIds(Arrays.asList(id));
	}

	@Override
	public List<ArticleCategory> getArticleCategoryList() {
		return this.list();
	}

}
