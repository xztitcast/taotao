package com.bovine.taotao.setup.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.utils.BeanCopierUtil;
import com.bovine.taotao.setup.entity.Article;
import com.bovine.taotao.setup.i.service.ArticleCategoryService;
import com.bovine.taotao.setup.i.service.ArticleService;
import com.bovine.taotao.setup.model.ArticleAndClassroomModel;
import com.bovine.taotao.setup.view.ArticleContentView;
import com.bovine.taotao.setup.view.ArticleView;
import com.bovine.taotao.setup.view.CategoryView;

/**
 * 文章列表
 * @author eden
 * @date 2023年2月23日 下午4:38:28
 */
@RestController
@RequestMapping("/setup/article")
public class ArticleController {
	
	@DubboReference
	private ArticleService articleService;
	
	@DubboReference
	private ArticleCategoryService articleCategoryService;

	/**
	 * 获取文章列表
	 * @param am
	 * @return
	 */
	@PostMapping("/list")
	public R list(@RequestBody ArticleAndClassroomModel acm) {
		P<ArticleView> p = articleService.getArticleList(acm);
		return R.ok(p);
	}
	
	/**
	 * 获取文章内容
	 * @param id
	 * @return
	 */
	@GetMapping("/content/{id}")
	public R cotent(@PathVariable Long id) {
		Article entity = articleService.getEntity(id);
		ArticleContentView acv = new ArticleContentView();
		if(entity != null) {
			BeanCopierUtil.copyProperties(entity, acv);
		}
		return R.ok(acv);
	}
	
	/**
	 * 获取文章分类
	 * @return
	 */
	@GetMapping("/category")
	public R category() {
		List<CategoryView> list = articleCategoryService.getArticleCategoryList().stream().map(a -> new CategoryView(a.getId(), a.getName())).collect(Collectors.toList());
		return R.ok(list);
	}
}
