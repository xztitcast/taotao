package com.bovine.taotao.admin.web.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bovine.taotao.admin.web.entity.SysRoleMenu;

/**
 * 系统角色与菜单业务接口
 * @author eden
 * @date 2022年10月23日 上午9:31:00
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 * @param roleId 角色ID
	 * @return
	 */
	List<Long> getMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 * @param roleIds 角色ID数组
	 * @return
	 */
	int deleteBatch(Long... roleIds);
}
