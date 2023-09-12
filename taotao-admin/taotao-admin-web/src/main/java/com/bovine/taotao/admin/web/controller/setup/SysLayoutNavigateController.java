package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.LayoutNavigate;
import com.bovine.taotao.setup.entity.LayoutNavigatePath;
import com.bovine.taotao.setup.i.service.LayoutNavigatePathService;
import com.bovine.taotao.setup.i.service.LayoutNavigateService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 后台管理 布局导航栏控制器
 * @author eden
 * @date 2023年2月10日 下午5:54:22
 */
@RestController
@RequestMapping("/sys/layout/navigate")
public class SysLayoutNavigateController {
	
	@DubboReference
	private LayoutNavigateService layoutNavigateService;
	
	@DubboReference
	private LayoutNavigatePathService layoutnavigatePathService;
	
	/**
	 * 查询布局导航栏列表数据
	 * @param layoutId
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:layout:navigate:list')")
	public R list(@RequestParam Integer layoutId) {
		List<LayoutNavigate> list = layoutNavigateService.getLayoutNavigateList(layoutId);
		return R.ok(list);
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:layout:navigate:info')")
	public R info(@PathVariable Long id) {
		LayoutNavigate entity = layoutNavigateService.getEntity(id);
		return R.ok(entity);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("保存导航栏数据")
	@PostMapping("/save")
	@Fill(FillType.INSERT)
	@PreAuthorize("hasAuthority('sys:layout:navigate:save')")
	public R save(@RequestBody LayoutNavigate entity) {
		entity = layoutNavigateService.saveEntity(entity);
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
	@Log("修改导航栏数据")
	@PostMapping("/update")
	@Fill(FillType.UPDATE)
	@PreAuthorize("hasAuthority('sys:layout:navigate:update')")
	public R update(@RequestBody LayoutNavigate entity) {
		boolean update = layoutNavigateService.updateEntity(entity);
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
	@Log("删除导航栏数据")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:layout:navigate:delete')")
	public R delete(@RequestBody Long[] ids) {
		boolean delete = layoutNavigateService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 获取导航路径
	 * @return
	 */
	@GetMapping("/path")
	@PreAuthorize("hasAuthority('sys:layout:navigate:list')")
	public R path() {
		List<LayoutNavigatePath> list = layoutnavigatePathService.getNavigatePathList();
		return R.ok(list);
	}

}
