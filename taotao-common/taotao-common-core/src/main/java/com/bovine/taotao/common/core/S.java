package com.bovine.taotao.common.core;

/**
 * 状态枚举
 * @author eden
 * 2022年5月11日
 */
public enum S {

	SUCCESS(0, "成功"),

	ERROR(-1, "系统异常稍后重试!"),

	UNAUTHORIZED(401, "登录失效,请重新登录!"),

	RESTRICT_ACCESS(600, "限制访问,只允许访问系统管理"),

	REPEATSUBMIT(602, "系统正在努力处理中...请耐心等待!"),

	SENTINEL_LIMIT(603, "系统正在努力排队中...请稍后重试!"),

	SYSTEM_UNAUTHORIZED(604, "无权限访问,请联系管理员!"),

	PARAMTER_BOUNDS_ERROR(605, "参数异常,请联系技术人员!"),

	DATA_NOTFOUND_ERROR(606, "数据结果集不存在!"),

	DATA_STATUS_ERROR(607, "当前状态已存在,无需重新设置!"),

	TOKEN_EXPIRE(1000, "验证码已失效!"),

	CODE_EXPIRE(1001, "验证码已失效!"),

	CODE_ERROR(1002, "验证码错误!"),

	USER_NOTFOUND_ERROR(1003, "用户不存在!"),

	USER_PWD_ERROR(1004, "账号或密码错误或图片验证码!"),

	USER_INACTIVE_ERROR(1005, "账号已被禁用,请联系管理员!"),

	USER_ORIGINAL_PWD_ERROR(1006, "用户原密码错误!"),

	USER_NOTPERMISSION_ERROR(1007, "无权限修改用户状态!"),

	USER_STATUS_PARAMTER_ERROR(1008, "前端状态参数传递异常!"),

	USER_REMOVE_SUPER_ADMIN_ERROR(1009, "系统管理员不能删除！"),

	USER_CURRENT_REMOVE_ERROR(1010, "当前用户不能被删除!"),

	USER_ADDPERMISSIONS_ERROR(1011, "新增用户所选角色，不是本人创建!"),

	USER_ADDROLE_NOTPERMISSIONS_ERROR(1012, "新增角色的权限，已超出你的权限范围!"),

	MENU_BASEFRAME_REMOVE_ERROR(2000, "主结构菜单不能被删除!"),

	MENU_CORRESPONDING_REMOVE_ERROR(2001, "请删除对应的菜单或者按钮后再删除主菜单!"),

	MENU_PARENTMENU_ONLYCATALOG_ERROR(2002, "父级菜单只能是目录!"),

	MENU_PARENTMENU_ONLYMENU_ERROR(2003, "父级菜单只能是菜单!"),

	MENU_NAME_NOTFOUND_ERROR(2004, "菜单名称不能为空!"),

	MENU_PARENT_NOTFOUND_ERROR(2005, "父级菜单不能为空!"),

	MENU_URL_NOTFOUND_ERROR(2006, "菜单URL不能为空!"),

	ACTIVITY_NOTFOUND_ERROR(20000, "活动不存在或已结束!"),

	ACTIVITY_NOT_STARTED_ERROR(20001, "活动未开始或已结束!");

	private int value;

	private String message;

	S(int value, String message){
		this.value = value;
		this.message = message;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}
}
