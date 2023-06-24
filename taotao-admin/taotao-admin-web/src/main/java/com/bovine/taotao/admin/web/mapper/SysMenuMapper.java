package com.bovine.taotao.admin.web.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bovine.taotao.admin.web.entity.SysMenu;

public interface SysMenuMapper extends BaseMapper<SysMenu>{
	
	List<SysMenu> selectUserMenuTreeList(long userId);

	/**
	 * 查询权限
	 * @param userId 用户ID
	 * @return
	 */
	List<String> selectAllPerms(long userId);

}