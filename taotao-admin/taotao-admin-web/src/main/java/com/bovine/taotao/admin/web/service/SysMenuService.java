package com.bovine.taotao.admin.web.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bovine.taotao.admin.web.entity.SysMenu;

/**
 * 菜单接口
 * @author eden
 * @date 2022年10月23日 上午9:19:34
 */
public interface SysMenuService extends IService<SysMenu> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenu> getListParentId(Long parentId);
	
	/**
	 * 获取用户导航菜单Tree列表
	 * @param userId
	 * @return
	 */
	List<SysMenu> getNavMenuTreeList(Long userId);

	/**
	 * 获取包含按钮的菜单树结构列表
	 * @param userId 
	 * @return
	 */
	List<SysMenu> getMenuTreeList(Long userId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenu> getNotButtonList();

	/**
	 * 删除
	 */
	void delete(Long menuId) ;
}
