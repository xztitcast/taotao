package com.bovine.taotao.admin.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginModel extends SysModel {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "uuid不能为空!")
	private String uuid;
}
