package com.bovine.taotao.admin.web.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PasswordModel {
	
	@NotBlank(message = "原密码不能为空")
	private String password;
	
	@NotBlank(message = "新密码不能为空")
	private String newPassword;
}
