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

	protected SysUser getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUserDetails principal = (LoginUserDetails) authentication.getPrincipal();
		return principal.getSysUser();
	}
	
	protected Long getUserId() {
		return getUser().getId();
	}

	protected String getUserName() {return getUser().getUsername();}
	
	protected Long getTisid() {
		return getUser().getTisid();
	}
	
	protected String getTisname() {
		return getUser().getTisname();
	}
}
