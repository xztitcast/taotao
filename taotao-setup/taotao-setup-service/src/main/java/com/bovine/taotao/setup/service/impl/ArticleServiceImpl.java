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
import com.bovine.taotao.setup.entity.Article;
import com.bovine.taotao.setup.entity.ArticleContent;
import com.bovine.taotao.setup.i.service.ArticleContentService;
import com.bovine.taotao.setup.i.service.ArticleService;
import com.bovine.taotao.setup.mapper.ArticleMapper;
import com.bovine.taotao.setup.model.ArticleAndClassroomModel;
import com.bovine.taotao.setup.model.SysArticleModel;
import com.bovine.taotao.setup.view.ArticleView;

/**
 * 文章Service业务逻辑实现类
 * @author eden
 * @date 2023年2月22日 下午6:23:33
 */
@Service("articleService")
@DubboService(interfaceClass = ArticleService.class)
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IService<Article>, ArticleService {
	
	@Autowired
	private ArticleContentService articleContentService;

	@Override
	public P<Article> getBaseList(BaseModel m) {
		SysArticleModel sam = (SysArticleModel)m;
		IPage<Article> page = new Page<>(sam.getPageNum(), sam.getPageSize());
		QueryWrapper<Article> wrapper = new QueryWrapper<>();
		wrapper.eq(StringUtils.isNotBlank(sam.getName()), "name", sam.getName())
			.eq(StringUtils.isNotBlank(sam.getAuthor()), "author", sam.getAuthor())
			.eq(sam.getStatus() != null, "status", sam.getStatus())
			.orderBy(true, sam.getOrder(), sam.getOrderField());
		page(page,wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Override
	public Article getEntity(Long id) {
		Article entity = this.getById(id);
		ArticleContent ac = articleContentService.getEntity(id);
		entity.setContent(ac.getContent());
		return entity;
	}

	@Override
	@Transactional
	public Article saveEntity(Article t) {
		boolean save = this.save(t);
		if(save) {
			articleContentService.saveEntity(new ArticleContent(t.getId(), t.getContent()));
		}
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(Article t) {
		boolean update = this.updateById(t);
		if(update) {
			update = articleContentService.updateEntity(new ArticleContent(t.getId(), t.getContent()));
		}
		return update;
	}

	@Override
	@Transactional
	public boolean delete(Collection<Long> ids) {
		return this.removeBatchByIds(ids);
	}

	@Override
	public P<ArticleView> getArticleList(ArticleAndClassroomModel acm) {
		IPage<Article> page = new Page<>(acm.getPageNum(), acm.getPageSize());
		LambdaQueryWrapper<Article> query = Wrappers.lambdaQuery(Article.class)
				.eq(acm.getCategoryId() != null ,Article::getCategoryId, acm.getCategoryId())
				.eq(acm.getHot() != null, Article::getHot, acm.getHot())
				.eq(Article::getStatus, 2).orderByDesc(List.of(Article::getTop, Article::getHot, Article::getCreated));
		page(page, query);
		List<ArticleView> record = page.getRecords().stream().map(a -> {
			ArticleView av = new ArticleView();
			BeanCopierUtil.copyProperties(a, av);
			return av;
		}).collect(Collectors.toList());
		return new P<>(page.getTotal(), record);
	}

}
