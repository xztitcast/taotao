package com.bovine.taotao.admin.web.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.bovine.taotao.common.core.Constant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.admin.web.entity.SysMenu;
import com.bovine.taotao.admin.web.mapper.SysMenuMapper;
import com.bovine.taotao.admin.web.service.SysMenuService;
import com.bovine.taotao.admin.web.service.SysRoleMenuService;
import com.bovine.taotao.common.core.Constant.MenuType;
import com.bovine.taotao.common.core.Constant.Sys;

@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
	
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	@Override
	public List<SysMenu> getListParentId(Long parentId) {
		LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getParentId, parentId).orderByAsc(SysMenu::getSorted);
		return this.list(wrapper);
	}

	@Override
	public List<SysMenu> getNavMenuTreeList(Long userId) {
		List<SysMenu> menuList = Sys.SUPER_ADMIN == userId ? this.list(new QueryWrapper<SysMenu>().orderByAsc("sorted")) : this.baseMapper.selectUserMenuTreeList(userId);
		menuList = menuList.stream().filter(s -> MenuType.BUTTON.getValue() != s.getType()).collect(Collectors.toList());
		return resolveMenuTreeList(menuList);
	}

	@Override
	public List<SysMenu> getMenuTreeList(Long userId) {
		List<SysMenu> menuList = Sys.SUPER_ADMIN == userId ? this.list(new QueryWrapper<SysMenu>().orderByAsc("sorted")) : this.baseMapper.selectUserMenuTreeList(userId);
		menuList.forEach(m -> {
			SysMenu menu = this.getById(m.getParentId());
			if(menu == null && m.getParentId() == 0) {
				m.setParentName(Sys.MENU_NAME);
			}else {
				m.setParentName(menu.getName());
			}
		});
		return resolveMenuTreeList(menuList);
	}

	@Override
	public Set<String> getPermissions(Long userId) {
		List<String> permsList;
		if(userId == Constant.Sys.SUPER_ADMIN) {
			permsList = this.list().stream().map(m -> m.getPerms()).collect(Collectors.toList());
		}else {
			permsList = this.baseMapper.selectAllPerms(userId);
		}
		return permsList.stream().filter(p -> StringUtils.isNotBlank(p)).map(p -> Arrays.asList(p.trim().split(","))).collect(HashSet::new, Set::addAll, Set::addAll);
	}

	@Override
	public List<SysMenu> getNotButtonList() {
		LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery(SysMenu.class).ne(SysMenu::getType, 2).orderByAsc(SysMenu::getSorted);
		List<SysMenu> menuList = resolveMenuTreeList(this.list(wrapper));
		return menuList;
	}


	@Override
	@Transactional
	public void delete(Long menuId) {
		this.removeById(menuId);
		sysRoleMenuService.removeByMap(Map.of("id", menuId));
	}

	/**
	 * 解析前端需要的树形结构列表
	 * @param menuList
	 * @return
	 */
	private List<SysMenu> resolveMenuTreeList(final List<SysMenu> menuList) {
		return menuList.stream().filter(s -> s.getParentId() == 0L).map(s -> findNodeTree(menuList, s)).collect(Collectors.toList());
	}

	/**
	 * 查找树形结构的子孙节点
	 * @param menuList
	 * @param sysMenu
	 * @return
	 */
	private SysMenu findNodeTree(List<SysMenu> menuList, SysMenu sysMenu){
		menuList.stream().filter(s -> s.getParentId() == sysMenu.getId().longValue()).forEach(s -> sysMenu.getChildren().add(findNodeTree(menuList, s)));
		return sysMenu;
	}

}
