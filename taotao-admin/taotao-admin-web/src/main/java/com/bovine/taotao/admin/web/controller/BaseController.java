package com.bovine.taotao.admin.web.controller;

import com.bovine.taotao.admin.web.service.impl.UserDetailsServiceImpl.LoginUserDetails;

import com.bovine.taotao.admin.web.entity.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 权限基础控制器
 * @author eden
 * @time 2022年7月22日 上午11:36:07
 */
public abstract class BaseController {

	/**
	 * 获取用户信息
	 * @return SysUser
	 */
	protected SysUser getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUserDetails principal = (LoginUserDetails) authentication.getPrincipal();
		return principal.getSysUser();
	}

	/**
	 * 获取用户ID
	 * @return Long
	 */
	protected Long getUserId() {
		return getUser().getId();
	}

	/**
	 * 获取用户名
	 * @return String
	 */
	protected String getUsername() {return getUser().getUsername();}

	/**
	 * 获取机构ID
	 * @return Long
	 */
	protected Long getTisid() {
		return getUser().getTisid();
	}

	/**
	 * 获取机构名称
	 * @return String
	 */
	protected String getTisname() {
		return getUser().getTisname();
	}
}
