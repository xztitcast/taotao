package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.AdCategory;
import com.bovine.taotao.setup.i.service.AdCategoryService;
import com.bovine.taotao.setup.model.SysAdCategoryModel;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 后台管理广告类目控制器
 * @author eden
 * @date 2023年2月11日 下午4:43:32
 */
@RestController
@RequestMapping("/sys/ad/category")
public class SysAdCategoryController {

	@DubboReference
	private AdCategoryService adCategoryService;

	/**
	 * 查询列表数据
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:ad:category:list')")
	public R list(SysAdCategoryModel model) {
		P<AdCategory> p = adCategoryService.getBaseList(model);
		return R.ok(p);
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:ad:category:info')")
	public R info(@PathVariable Integer id) {
		AdCategory entity = adCategoryService.getEntity(id);
		return R.ok(entity);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("保存广告类目数据")
	@Fill(FillType.INSERT)
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:ad:category:save')")
	public R save(@RequestBody AdCategory entity) {
		entity = adCategoryService.saveEntity(entity);
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
	@Log("修改广告类目数据")
	@Fill(FillType.UPDATE)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:ad:category:update')")
	public R update(@RequestBody AdCategory entity) {
		boolean update = adCategoryService.updateEntity(entity);
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
	@Log("删除广告数据")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:ad:category:delete')")
	public R delete(@RequestBody Integer[] ids) {
		boolean delete = adCategoryService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 类目选择器
	 * @return
	 */
	@GetMapping("/selector")
	@PreAuthorize("hasAuthority('sys:ad:category:list')")
	public R selector() {
		List<AdCategory> list = adCategoryService.getAdCategoryList();
		return R.ok(list);
	}
}
