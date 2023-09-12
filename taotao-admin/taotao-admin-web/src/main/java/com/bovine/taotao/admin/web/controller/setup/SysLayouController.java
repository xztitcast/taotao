package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.admin.web.controller.BaseController;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.Layout;
import com.bovine.taotao.setup.i.service.LayoutService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 后台管理系统布局控制器
 * @author eden
 * @date 2023年2月10日 下午3:40:25
 */
@RestController
@RequestMapping("/sys/layout")
public class SysLayouController extends BaseController {
	
	@DubboReference
	private LayoutService layoutService;
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:layout:info')")
	public R info(@PathVariable Integer id) {
		Layout entity = layoutService.getEntity(id);
		return R.ok(entity);
	}
	
	/**
	 * 查询布局
	 * @param type
	 * @return
	 */
	@GetMapping("/select/{type}")
	@PreAuthorize("hasAuthority('sys:layout:select')")
	public R select(@PathVariable Integer type) {
		Layout entity = layoutService.getLayout(getTisid(), type);
		return R.ok(entity);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("新增布局数据")
	@PostMapping("/save")
	@Fill(FillType.INSERT)
	@PreAuthorize("hasAuthority('sys:layout:save')")
	public R save(@RequestBody Layout entity) {
		entity = layoutService.saveEntity(entity);
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
	@Log("修改布局数据")
	@PostMapping("/update")
	@Fill(FillType.UPDATE)
	@PreAuthorize("hasAuthority('sys:layout:update')")
	public R update(@RequestBody Layout entity) {
		boolean update = layoutService.updateEntity(entity);
		if(update) {
			return R.ok();
		}
		return R.error();
	}
	
}
