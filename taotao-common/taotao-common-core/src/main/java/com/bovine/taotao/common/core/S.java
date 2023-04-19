package com.bovine.taotao.common.core;

/**
 * 状态枚举
 * @author eden
 * 2022年5月11日
 */
public enum S {

	SUCCESS(0, "成功"),
	
	ERROR(-1, "系统异常稍后重试!"),
	
	UNAUTHORIZED(601, "请科学上网,正常登录!"),

	REPEATSUBMIT(602, "系统正在努力处理中...请耐心等待!"),
	
	SENTINEL_LIMIT(603, "系统正在努力排队中...请稍后重试!"),
	
	SYSTEM_UNAUTHORIZED(604, "请科学上网正常访问...否则封禁哦!"),
	
	PARAMTER_INDEX_OUTOF_BOUNDS_ERROR(605, "传递后端参数下标越界"),
	
	DATA_NOTFOUND_ERROR(606, "数据不存在!"),

	DATA_PARAMTER_ERROR(607, "数据传递后端参数异常"),
	DATA_HOT_ERROR(608, "该数据已经是热门数据"),
	DATA_TOP_ERROR(609, "该数据已经置顶"),
	
	CODE_EXPIRE(1000, "验证码已失效!"),
	
	CODE_ERROR(1001, "验证码错误!"),

	USER_NOTFOUND_ERROR(1002, "用户不存在!"),
	
	USER_PWD_ERROR(1003, "账号或密码错误!"),
	
	USER_INACTIVE_ERROR(1004, "账号已被禁用,请联系管理员!"),

	USER_ORIGINAL_PWD_ERROR(1005, "用户原密码错误!"),
	
	USER_NOTPERMISSION_ERROR(1006, "无权限修改用户状态!"),
	
	USER_STATUS_PARAMTER_ERROR(1007, "前端状态参数传递异常!"),
	
	USER_REMOVE_SUPER_ADMIN_ERROR(1008, "系统管理员不能删除！"),
	
	USER_CURRENT_REMOVE_ERROR(1009, "当前用户不能被删除!"),
	
	USER_ADDPERMISSIONS_ERROR(1010, "新增用户所选角色，不是本人创建!"),
	
	USER_ADDROLE_NOTPERMISSIONS_ERROR(1011, "新增角色的权限，已超出你的权限范围!"),
	
	MENU_BASEFRAME_REMOVE_ERROR(2000, "基础框架菜单不能删除!"),
	
	MENU_CORRESPONDING_REMOVE_ERROR(2001, "请删除对应的菜单或者按钮后再删除主菜单!"),
	
	MENU_PARENTMENU_ONLYCATALOG_ERROR(2002, "父级菜单只能是目录!"),
	
	MENU_PARENTMENU_ONLYMENU_ERROR(2003, "父级菜单只能是菜单!"),
	
	MENU_NAME_NOTFOUND_ERROR(2004, "菜单名称不能为空!"),
	
	MENU_PARENT_NOTFOUND_ERROR(2005, "父级菜单不能为空!"),
	
	MENU_URL_NOTFOUND_ERROR(2006, "菜单URL不能为空!"),
	
	ESTATE_AUTH_ERROR(3000, "你不是该物业人员"),
	
	ESTATE_AUTH_REPEAT_ERROR(3001, "你已认证成功,请勿重复认证!"),
	
	ESTATE_CODE_INVALID_ERROR(3002, "无效的二维码!"),
	
	ESTATE_CODE_VALIDATED_ERROR(3003, "该二维码已核销过了"),
	
	ESTATE_CODE_VALID_ERROR(3004, "核销失败,稍后重试!"),
	
	REGION_NOTFOUND_ERROR(4000, "行政地区不存在"),

	NOTICE_VALID_LIMIT(5000,"只可同时存在5条有效数据"),

    BUSINESS_INACTIVE_ERROR(10000, "商户已被禁用,请联系相关人员!"),
	
	REQUEST_DECRYPT_ERROR(10001, "请求解密异常"),

	REQUEST_PARAMETER_ERROR(10002, "请求参数异常"),

	MACHINE_NAME_ERROR(10005,"售卖机名称重复，请重新输入！"),

	MACHINE_CATEGORY_NAME_ERROR(10006, "售卖机分类名称重复，请重新输入！");
	
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
