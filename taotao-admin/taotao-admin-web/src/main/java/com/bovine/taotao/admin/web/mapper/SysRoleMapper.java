package com.bovine.taotao.admin.web.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bovine.taotao.admin.web.entity.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {
	
	/**
	 * 获取所有权限
	 * @param userId
	 * @return
	 */
	List<String> selectAllPerms(long userId);

	/**
	 * 查询用户的所有菜单ID
	 * @param userId
	 * @return
	 */
	List<Long> selectAllMenuId(Long userId);
}