package com.bovine.taotao.common.security.entity;

import java.io.Serializable;

public class Principal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Principal() {
		super();
	}

	public Principal(Long userId, String username, String openid) {
		super();
		this.userId = userId;
		this.username = username;
		this.openid = openid;
	}

	public Principal(Long userId, String openid) {
		super();
		this.userId = userId;
		this.openid = openid;
	}


	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 用户名(即手机号)
	 */
	private String username;
	
	/**
	 * 小程序登录openid
	 */
	private String openid;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
