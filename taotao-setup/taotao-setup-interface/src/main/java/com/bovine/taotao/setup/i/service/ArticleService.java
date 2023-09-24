	package com.bovine.taotao.setup.i.service;

import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.setup.entity.Article;
import com.bovine.taotao.setup.model.ArticleAndClassroomModel;
import com.bovine.taotao.setup.view.ArticleView;

/**
 * 文章Service业务接口
 * @author eden
 * @date 2023年2月22日 下午6:22:12
 */
public interface ArticleService extends FrameworkService<Article, Long> {

	/**
	 * 文章列表
	 * @param bm
	 * @return
	 */
	P<ArticleView> getArticleList(ArticleAndClassroomModel acm);
}
