package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.ArticleCategory;
import com.bovine.taotao.setup.i.service.ArticleCategoryService;
import com.bovine.taotao.setup.model.SysCategoryModel;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 后台管理系统 文章类目控制器
 * @author eden
 * @date 2023年2月22日 下午9:58:23
 */
@RestController
@RequestMapping("/sys/article/category")
public class SysArticleCategoryController {
	
	@DubboReference
	private ArticleCategoryService articleCategoryService;
	
	/**
	 * 获取物业列表
	 * @param acm
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:article:category:list')")
	public R list(SysCategoryModel acm) {
		P<ArticleCategory> p = articleCategoryService.getBaseList(acm);
		return R.ok(p);
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:article:category:info')")
	public R info(@PathVariable Integer id) {
		ArticleCategory entity = articleCategoryService.getEntity(id);
		return R.ok(entity);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("保存文章类目数据")
	@Fill(FillType.INSERT)
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:article:category:save')")
	public R save(@RequestBody ArticleCategory entity) {
		entity = articleCategoryService.saveEntity(entity);
		if(entity.getId() == null) {
			return R.error();
		}
		return R.ok();
		
	}
	
	/**
	 * 更新
	 * @param entity
	 * @return
	 */
	@Log("修改文章类目数据")
	@Fill(FillType.UPDATE)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:article:category:update')")
	public R update(@RequestBody ArticleCategory entity) {
		boolean update = articleCategoryService.updateEntity(entity);
		if(update) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@Log("删除文章类目数据")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:article:category:delete')")
	public R delete(@RequestBody Integer[] ids) {
		boolean delete = articleCategoryService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 获取文章类目选择器
	 * @return
	 */
	@GetMapping("/selector")
	@PreAuthorize("hasAuthority('sys:article:category:list')")
	public R selector() {
		List<ArticleCategory> list = articleCategoryService.getArticleCategoryList();
		return R.ok(list);
	}
}
