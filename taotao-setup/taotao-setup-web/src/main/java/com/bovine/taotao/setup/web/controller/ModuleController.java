package com.bovine.taotao.setup.web.controller;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.i.service.ModuleService;
import com.bovine.taotao.setup.view.ModuleView;

/**
 * 前端交互 模块控制器
 * @author eden
 * @date 2023年2月14日 下午7:25:33
 */
@RestController
@RequestMapping("/setup/module")
public class ModuleController {
	
	@DubboReference
	private ModuleService moduleService;
	
	/**
	 * 模块列表
	 * @param tisid
	 * @return
	 */
	@GetMapping("/list")
	public R list(@RequestHeader Long tisid) {
		List<ModuleView> moduleList = moduleService.getModuleList(tisid);
		return R.ok(moduleList);
	}

}
