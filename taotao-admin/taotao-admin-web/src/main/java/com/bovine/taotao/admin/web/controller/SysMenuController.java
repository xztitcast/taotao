package com.bovine.taotao.admin.web.controller;

import java.util.List;
import java.util.Set;

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
import com.bovine.taotao.admin.web.entity.SysMenu;
import com.bovine.taotao.admin.web.service.SysMenuService;
import com.bovine.taotao.common.core.Constant;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;
import com.bovine.taotao.common.core.exception.custom.SysServiceException;

/**
 * 关系系统菜单控制器
 * @author eden
 * @time 2022年7月22日 上午11:37:38
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 菜单权限导航
	 * @return
	 */
	@GetMapping("/nav")
	public R nav() {
		List<SysMenu> menuList = sysMenuService.getNavMenuTreeList(getUserId());
		Set<String> permissions = sysMenuService.getPermissions(getUserId());
		return R.ok().put("menuList", menuList).put("permissions", permissions);
	}
	
	/**
	 * 菜单列表配合前台代码转换成Tree树
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize(value = "hasAuthority('sys:menu:list')")
	public R list(){
		List<SysMenu> menuList = sysMenuService.getMenuTreeList(getUserId());
		return R.ok(menuList);
	}
	
	/**
	 * 过滤权限按钮只获取菜单与目录列表配合前台转换Tree树
	 * @return
	 */
	@GetMapping("/select")
	@PreAuthorize(value = "hasAuthority('sys:menu:select')")
	public R select() {
		List<SysMenu> menuList = sysMenuService.getNotButtonList();
		return R.ok(menuList);
	}

	/**
	 * 单个菜单信息
	 * @param menuId
	 * @return
	 */
	@GetMapping("/info/{menuId}")
	@PreAuthorize(value = "hasAuthority('sys:menu:info')")
	public R info(@PathVariable("menuId") Long menuId){
		SysMenu menu = sysMenuService.getById(menuId);
		return R.ok(menu);
	}
	
	@Log("保存菜单数据")
	@PostMapping("/save")
	@PreAuthorize(value = "hasAuthority('sys:menu:save')")
	public R save(@RequestBody SysMenu menu) {
		//数据校验
		verifyForm(menu);
		
		sysMenuService.save(menu);
		
		return R.ok();
	}
	
	@Log("修改菜单数据")
	@PostMapping("/update")
	@PreAuthorize(value = "hasAuthority('sys:menu:update')")
	public R update(@RequestBody SysMenu menu) {
		//数据校验
		verifyForm(menu);
		
		sysMenuService.updateById(menu);
		
		return R.ok();
	}
	
	/**
	 * 删除菜单
	 * (根菜单id<=28为系统默认框架的菜单不允许删除)
	 * @param menuId
	 * @return
	 */
	@Log("删除菜单数据")
	@DeleteMapping("/delete/{menuId}")
	@PreAuthorize(value = "hasAuthority('sys:menu:delete')")
	public R delete(@PathVariable("menuId") long menuId){
		if(menuId <= 28){
			return R.error(S.MENU_BASEFRAME_REMOVE_ERROR);
		}

		//判断是否有子菜单或按钮
		List<SysMenu> menuList = sysMenuService.getListParentId(menuId);
		if(menuList.size() > 0){
			return R.error(S.MENU_CORRESPONDING_REMOVE_ERROR);
		}

		sysMenuService.delete(menuId);

		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenu menu){
		if(menu.getName() == null || menu.getName().isBlank()){
			throw new SysServiceException(S.MENU_NAME_NOTFOUND_ERROR);
		}
		
		if(menu.getParentId() == null){
			throw new SysServiceException(S.MENU_PARENT_NOTFOUND_ERROR);
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(menu.getUrl() == null || menu.getUrl().isBlank()){
				throw new SysServiceException(S.MENU_URL_NOTFOUND_ERROR);
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new SysServiceException(S.MENU_PARENTMENU_ONLYCATALOG_ERROR);
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new SysServiceException(S.MENU_PARENTMENU_ONLYMENU_ERROR);
			}
			return ;
		}
	}
	
}
