package com.bovine.taotao.admin.web.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bovine.taotao.admin.web.entity.SysUserRole;

/**
 * 系统用户与角色业务接口
 * @author eden
 * @date 2018年7月23日 上午9:28:37
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 * @param userId 用户ID
	 * @return
	 */
	List<Long> getRoleIdList(Long userId);

	/**
	 * 根据角色ID数组，批量删除
	 * @param roleIds 角色ID数组
	 * @return
	 */
	int deleteBatch(Long[] roleIds);
}
