package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.MineModule;
import com.bovine.taotao.setup.entity.MineModuleArea;
import com.bovine.taotao.setup.i.service.MineModuleAreaService;
import com.bovine.taotao.setup.i.service.MineModuleService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 后台管理系统 "我的"页面模块控制器
 * @author eden
 * @date 2023年3月3日 上午10:55:49
 */
@RestController
@RequestMapping("/sys/mine/module")
public class SysMineModuleController {

	@DubboReference
	private MineModuleService mineModuleService;
	
	@DubboReference
	private MineModuleAreaService mineModuleAreaService;
	
	/**
	 * 查询列表数据
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:mine:module:list')")
	public R list() {
		List<MineModule> list = mineModuleService.getMineModuleNotUsedList();
		return R.ok(list);
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:mine:module:info')")
	public R info(@PathVariable Long id) {
		MineModule entity = mineModuleService.getEntity(id);
		return R.ok(entity);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("保存我的页面模块数据")
	@Fill(FillType.INSERT)
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:mine:module:save')")
	public R save(@RequestBody MineModule entity) {
		entity = mineModuleService.saveEntity(entity);
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
	@Log("修改我的页面模块数据")
	@Fill(FillType.UPDATE)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:mine:module:update')")
	public R update(@RequestBody MineModule entity) {
		boolean update = mineModuleService.updateEntity(entity);
		if(!update) {
			return R.error();
		}
		return R.ok();
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@Log("删除我的页面模块数据")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:mine:module:delete')")
	public R delete(@RequestBody Long[] ids) {
		boolean delete = mineModuleService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 选择模块
	 * @param entity
	 * @return
	 */
	@Log("选择我的页面模块数据")
	@PostMapping("/choice")
	@PreAuthorize("hasAuthority('sys:mine:module:save')")
	public R choice(@RequestBody MineModuleArea entity) {
		boolean save = mineModuleAreaService.saveEntity(entity);
		if(!save) {
			return R.error();
		}
		return R.ok();
	}
	
	/**
	 * 该删除方法仅仅是删除关联表的中数据(代表该区域不适用开模块了)
	 * @param entity
	 * @return
	 */
	@Log("删除我的页面区域中的模块数据")
	@DeleteMapping("/remove")
	@PreAuthorize("hasAuthority('sys:mine:module:delete')")
	public R remove(@RequestBody MineModuleArea entity) {
		boolean delete = mineModuleAreaService.deleteEntity(entity);
		if(!delete) {
			return R.error();
		}
		return R.ok();
	}
}
