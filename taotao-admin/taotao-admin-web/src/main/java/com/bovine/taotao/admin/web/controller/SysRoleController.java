package com.bovine.taotao.admin.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.admin.web.entity.SysRole;
import com.bovine.taotao.admin.web.model.RoleModel;
import com.bovine.taotao.admin.web.service.SysRoleMenuService;
import com.bovine.taotao.admin.web.service.SysRoleService;
import com.bovine.taotao.common.core.Constant;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;

/**
 * 管理系统角色控制器
 * @author eden
 * @time 2022年7月22日 上午11:42:04
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysRoleMenuService sysRoleMenuServcie;

	/**
	 * 角色列表
	 * (当管理员为admin时默认获得所有角色)
	 * @param form
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize(value = "hasAuthority('sys:role:list')")
	public R list(RoleModel form) {
		P<SysRole> p = sysRoleService.getSysRoleList(form ,getUserId());
		return R.ok(p);
	}
	
	/**
	 * 获取当前登陆用户创建的所有角色
	 * (当前登陆用户为Admin时获取所有角色)
	 */
	@GetMapping("/select")
	@PreAuthorize(value = "hasAuthority('sys:role:select')")
	public R select() {
		List<SysRole> result = null;
		if(getUserId() == Constant.Sys.SUPER_ADMIN) {
			result = sysRoleService.list();
		}else {
			result = sysRoleService.listByMap(Map.of("creator", getUserId()));
		}
		return R.ok(result);
	}
	
	/**
	 * 获取当个角色信息
	 * @param roleId
	 * @return
	 */
	@GetMapping("/info/{roleId}")
	@PreAuthorize(value = "hasAuthority('sys:role:info')")
	public R info(@PathVariable("roleId") Long roleId) {
		SysRole sysRole = sysRoleService.getById(roleId);
		List<Long> menuIdList = sysRoleMenuServcie.getMenuIdList(roleId);
		sysRole.setMenuIdList(menuIdList);
		return R.ok(sysRole);
	}
	
	@Log("修改角色数据")
	@PostMapping("/save")
	@PreAuthorize(value = "hasAuthority('sys:role:save')")
	public R save(@RequestBody SysRole role) {
		role.setCreator(getUserId());
		sysRoleService.save(role);
		return R.ok();
	}
	
	@Log("保存角色数据")
	@PostMapping("/update")
	@PreAuthorize(value = "hasAuthority('sys:role:update')")
	public R update(@RequestBody SysRole role) {
		role.setCreator(getUserId());
		sysRoleService.saveOrUpdate(role);
		return R.ok();
	}
	
	@Log("删除角色数据")
	@DeleteMapping ("/delete")
	@PreAuthorize(value = "hasAuthority('sys:role:delete')")
	public R delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		return R.ok();
	}
}
