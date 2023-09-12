package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.S;
import com.bovine.taotao.setup.entity.Module;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.i.service.ModuleService;
import com.bovine.taotao.setup.model.SysModuleModel;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 后台管理系统模块控制器
 * @author eden
 * @date 2023年2月14日 下午7:51:24
 */
@RestController
@RequestMapping("/sys/module")
public class SysModuleController {
	
	@DubboReference
	private ModuleService moduleService;

	/**
	 * 获取物业列表
	 * @param am
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:module:list')")
	public R list(SysModuleModel am) {
		P<Module> p = moduleService.getBaseList(am);
		return R.ok(p);
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:module:info')")
	public R info(@PathVariable Long id) {
		Module entity = moduleService.getEntity(id);
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
	@PreAuthorize("hasAuthority('sys:module:save')")
	public R save(@RequestBody Module entity) {
		entity = moduleService.saveEntity(entity);
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
	@PreAuthorize("hasAuthority('sys:module:update')")
	public R update(@RequestBody Module entity) {
		boolean update = moduleService.updateEntity(entity);
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
	@PreAuthorize("hasAuthority('sys:module:delete')")
	public R delete(@RequestBody Long[] ids) {
		boolean delete = moduleService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 后台管理系统状态change事件
	 * @param em
	 * @return
	 */
	@Log("修改物业状态")
	@Fill(FillType.UPDATE)
	@PostMapping("/change")
	@PreAuthorize("hasAuthority('sys:estate:change')")
	public R change(@RequestBody Module em) {
		Module entity = moduleService.getEntity(em.getId());
		if(entity == null) {
			return R.error(S.DATA_NOTFOUND_ERROR);
		}
		int status = em.getStatus();
		if(status < 0 && status > 2) {
			return R.error(S.PARAMTER_INDEX_OUTOF_BOUNDS_ERROR);
		}
		if(entity.getStatus() == status) {
			return R.error(S.DATA_PARAMTER_ERROR);
		}
		em.setStatus(em.getStatus());
		boolean update = moduleService.updateEntity(em);
		if(!update) {
			return R.error();
		}
		return R.ok();
	}
	
}
