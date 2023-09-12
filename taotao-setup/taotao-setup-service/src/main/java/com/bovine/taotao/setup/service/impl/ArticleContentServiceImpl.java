package com.bovine.taotao.setup.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.setup.entity.ArticleContent;
import com.bovine.taotao.setup.i.service.ArticleContentService;
import com.bovine.taotao.setup.mapper.ArticleContentMapper;

/**
 * 文章内容Service业务逻辑实现类
 * @author eden
 * @date 2023年2月22日 下午6:35:50
 */
@Service("articleContentService")
public class ArticleContentServiceImpl extends ServiceImpl<ArticleContentMapper, ArticleContent> implements IService<ArticleContent>, ArticleContentService {

	@Override
	public ArticleContent getEntity(Long id) {
		return this.getById(id);
	}

	@Override
	@Transactional
	public ArticleContent saveEntity(ArticleContent t) {
		this.save(t);
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(ArticleContent t) {
		return this.updateById(t);
	}

	@Override
	@Transactional
	public boolean delete(Collection<Long> ids) {
		return this.removeBatchByIds(ids);
	}

	
}
