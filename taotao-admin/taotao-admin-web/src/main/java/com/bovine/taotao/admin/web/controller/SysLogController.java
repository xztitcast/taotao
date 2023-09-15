package com.bovine.taotao.admin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.admin.web.entity.SysLog;
import com.bovine.taotao.admin.web.modelAndView.model.UserModel;
import com.bovine.taotao.admin.web.service.SysLogService;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;

/**
 * 管理系统日志控制器
 * @author eden
 * @time 2022年7月22日 上午11:36:22
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {

	@Autowired
	private SysLogService sysLogService;
	
	@GetMapping("/list")
	@PreAuthorize(value = "hasAuthority('sys:log:list')")
	public R list(UserModel form) {
		P<SysLog> p = sysLogService.getSysLogList(form);
		return R.ok(p);
	}
	
}
