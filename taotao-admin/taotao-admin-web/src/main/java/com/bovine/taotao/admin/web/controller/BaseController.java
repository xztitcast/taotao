package com.bovine.taotao.admin.web.controller;

import org.apache.shiro.SecurityUtils;

import com.bovine.taotao.admin.web.entity.SysUser;

/**
 * 权限基础控制器
 * @author eden
 * @time 2022年7月22日 上午11:36:07
 */
public abstract class BaseController {

	protected SysUser getUser() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
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
