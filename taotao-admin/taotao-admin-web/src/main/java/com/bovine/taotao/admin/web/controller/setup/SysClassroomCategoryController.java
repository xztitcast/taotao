package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.ClassroomCategory;
import com.bovine.taotao.setup.i.service.ClassroomCategoryService;
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
@RequestMapping("/sys/classroom/category")
public class SysClassroomCategoryController {
	
	@DubboReference
	private ClassroomCategoryService classroomCategoryService;
	
	/**
	 * 获取物业列表
	 * @param acm
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:classroom:category:list')")
	public R list(SysCategoryModel acm) {
		P<ClassroomCategory> p = classroomCategoryService.getBaseList(acm);
		return R.ok(p);
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:classroom:category:info')")
	public R info(@PathVariable Integer id) {
		ClassroomCategory entity = classroomCategoryService.getEntity(id);
		return R.ok(entity);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("保存数币课堂类目数据")
	@Fill(FillType.INSERT)
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:classroom:category:save')")
	public R save(@RequestBody ClassroomCategory entity) {
		entity = classroomCategoryService.saveEntity(entity);
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
	@Log("修改数币课堂类目数据")
	@Fill(FillType.UPDATE)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:classroom:category:update')")
	public R update(@RequestBody ClassroomCategory entity) {
		boolean update = classroomCategoryService.updateEntity(entity);
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
	@Log("删除数币课堂类目数据")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:classroom:category:delete')")
	public R delete(@RequestBody Integer[] ids) {
		boolean delete = classroomCategoryService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 获取数币课堂类目选择器
	 * @return
	 */
	@GetMapping("/selector")
	@PreAuthorize("hasAuthority('sys:classroom:category:list')")
	public R selector() {
		List<ClassroomCategory> list = classroomCategoryService.getArticleCategoryList();
		return R.ok(list);
	}
}
