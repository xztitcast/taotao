package com.bovine.taotao.admin.web.modelAndView.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "用户名不能为空!")
	protected String username;
	
	@NotBlank(message = "密码不能为空!")
	protected String password;
	
	@NotBlank(message = "验证码不能为空!")
	protected String captcha;

}
