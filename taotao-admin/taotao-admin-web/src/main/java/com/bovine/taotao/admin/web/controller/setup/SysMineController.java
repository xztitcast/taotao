package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.admin.web.controller.BaseController;
import com.bovine.taotao.common.core.BaseModel;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.Mine;
import com.bovine.taotao.setup.i.service.MineService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 后台管理系统 "我的"页面控制器
 * @author eden
 * @date 2023年3月2日 下午8:20:08
 */
@RestController
@RequestMapping("/sys/mine")
public class SysMineController extends BaseController {
	
	@DubboReference
	private MineService mineService;

	/**
	 * 查询列表数据
	 * @param bm
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:mine:list')")
	public R list(BaseModel bm) {
		P<Mine> p = mineService.getBaseList(bm);
		return R.ok(p);
	}
	
	/**
	 * 查询单条数据
	 * @return
	 */
	@GetMapping("/info")
	@PreAuthorize("hasAuthority('sys:mine:info')")
	public R info() {
		Mine entity = mineService.getMine(getTisid());
		if(entity == null) {
			entity = new Mine();
			entity.setTisid(getTisid());
			entity.setTisname(getTisname());
			entity.setCreator(getUserId());
			entity.setCreateName(getUserName());
			entity.setUpdater(getUserId());
			entity.setUpdateName(getUserName());
			entity = mineService.saveEntity(entity);
		}
		return R.ok(entity);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("保存我的页面数据")
	@Fill(FillType.INSERT)
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:mine:save')")
	public R save(@RequestBody Mine entity) {
		entity = mineService.saveEntity(entity);
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
	@Log("修改我的页面数据")
	@Fill(FillType.UPDATE)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:mine:update')")
	public R update(@RequestBody Mine entity) {
		boolean update = mineService.updateEntity(entity);
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
	@Log("删除我的页面数据")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:mine:delete')")
	public R delete(@RequestBody Integer[] ids) {
		boolean delete = mineService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}
}
