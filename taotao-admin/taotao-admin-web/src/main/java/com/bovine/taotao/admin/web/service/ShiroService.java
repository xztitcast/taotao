package com.bovine.taotao.admin.web.service;

import java.util.Set;

import com.bovine.taotao.admin.web.entity.SysUser;

/**
 * Shiro业务接口
 * @author eden
 * @date 2018年7月23日 上午8:37:27
 */
public interface ShiroService {

	/**
	 * 生成token
	 * @param sysUser 系统用户
	 * @return
	 */
	String createToken(SysUser sysUser);
	
	/**
	 * 获取用户信息
	 * @param token 令牌
	 * @return
	 */
	SysUser getByToken(String token);
	
	/**
	 * 删除
	 * @param token 令牌
	 * @return
	 */
	boolean remove(String token);
	
	/**
	 * 获取用户权限
	 * @param userId 用户ID
	 * @return
	 */
	Set<String> getUserPermissions(long userId);
	
}
