package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;
import com.bovine.taotao.setup.entity.Article;
import com.bovine.taotao.setup.i.service.ArticleService;
import com.bovine.taotao.setup.model.SysArticleModel;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * 后台管理系统 文章控制器
 * @author eden
 * @date 2023年2月22日 下午8:45:05
 */
@RestController
@RequestMapping("/sys/article")
public class SysArticleController {

	@DubboReference
	private ArticleService articleService;

	/**
	 * 获取物业列表
	 * @param am
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:article:list')")
	public R list(SysArticleModel am) {
		P<Article> p = articleService.getBaseList(am);
		return R.ok(p);
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:article:info')")
	public R info(@PathVariable Long id) {
		Article entity = articleService.getEntity(id);
		return R.ok(entity);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("保存文章数据")
	@Fill(FillType.INSERT)
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:article:save')")
	public R save(@RequestBody Article entity) {
		entity = articleService.saveEntity(entity);
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
	@Log("修改文章数据")
	@Fill(FillType.UPDATE)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:article:update')")
	public R update(@RequestBody Article entity) {
		if(entity.getStatus() == 2) {
			entity.setPushTime(new Date());
		}
		boolean update = articleService.updateEntity(entity);
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
	@Log("删除文章数据")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:article:delete')")
	public R delete(@RequestBody Long[] ids) {
		boolean delete = articleService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}
	
	@Log("文章置顶或取消置顶")
	@Fill(FillType.UPDATE)
	@PostMapping("/top")
	@PreAuthorize("hasAuthority('sys:article:top')")
	public R top(@RequestBody Article entity) {
		Article article = articleService.getEntity(entity.getId());
		if(article == null) {
			return R.error(S.DATA_NOTFOUND_ERROR);
		}
		int top = entity.getTop();
		if(top < 0 && top > 2) {
			return R.error(S.PARAMTER_BOUNDS_ERROR);
		}
		if(article.getTop() == top) {
			return R.error(S.DATA_STATUS_ERROR);
		}
		entity.setTop(top);
		boolean update = articleService.updateEntity(entity);
		if(!update) {
			return R.error();
		}
		return R.ok();
	}
	
	/**
	 * 修改热门
	 * @param entity
	 * @return
	 */
	@Log("文章设为热门资讯或取消热门资讯")
	@Fill(FillType.UPDATE)
	@PostMapping("/hot")
	@PreAuthorize("hasAuthority('sys:article:hot')")
	public R hot(@RequestBody Article entity) {
		Article article = articleService.getEntity(entity.getId());
		if(article == null) {
			return R.error(S.DATA_NOTFOUND_ERROR);
		}
		int hot = entity.getHot();
		if(hot < 0 && hot > 2) {
			return R.error(S.PARAMTER_BOUNDS_ERROR);
		}
		if(article.getHot() == hot) {
			return R.error(S.DATA_STATUS_ERROR);
		}
		entity.setHot(hot);
		boolean update = articleService.updateEntity(entity);
		if(!update) {
			return R.error();
		}
		return R.ok();
	}
	
	/**
	 * 更改状态
	 * @param entity
	 * @return
	 */
	@Log("文章上架或者下架")
	@Fill(FillType.UPDATE)
	@PostMapping("/change")
	@PreAuthorize("hasAuthority('sys:article:change')")
	public R change(@RequestBody Article entity) {
		Article article = articleService.getEntity(entity.getId());
		if(article == null) {
			return R.error(S.DATA_NOTFOUND_ERROR);
		}
		int status = entity.getStatus();
		if(status < 1 && status > 4) {
			return R.error(S.PARAMTER_BOUNDS_ERROR);
		}
		if(article.getStatus() == status) {
			return R.error(S.DATA_STATUS_ERROR);
		}
		if(status == 2) {
			entity.setPushTime(new Date());
		}
		entity.setStatus(status);
		boolean update = articleService.updateEntity(entity);
		if(!update) {
			return R.error();
		}
		return R.ok();
	}
}
