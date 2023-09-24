package com.bovine.taotao.setup.i.service;

import java.util.List;

import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.setup.entity.ArticleCategory;

/**
 * 文章类目Service业务接口
 * @author eden
 * @date 2023年2月22日 下午8:42:10
 */
public interface ArticleCategoryService extends FrameworkService<ArticleCategory, Integer> {

	/**
	 * 获取文章类目列表
	 * @return
	 */
	List<ArticleCategory> getArticleCategoryList();
}
